package org.modelversioning.emfprofile.project.ui.wizard;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.modelversioning.emfprofile.EMFProfilePlugin;
import org.modelversioning.emfprofile.Profile;
import org.modelversioning.emfprofile.diagram.part.EMFProfileDiagramEditorUtil;
import org.modelversioning.emfprofile.project.EMFProfileProjectNature;
import org.modelversioning.emfprofile.project.EMFProfileProjectNatureUtil;

/**
 * @author <a href="mailto:becirb@gmail.com">Becir Basic</a> (initial API and
 *         implementation)
 * @author <a href="mailto:becirb@gmail.com">Becir Basic</a> (creating a
 *         resource for decoration descriptions and adding Xtext nature to the
 *         Profile Project)
 * 
 */
public class NewProfileProjectOperation extends WorkspaceModifyOperation {

	private static final String BUILD_PROP_FILE_NAME = "build.properties";
	private static final String DEFAULT_VERSION = "1.0.0.qualifier";
	private static final String PDE_PLUGIN_NATURE = "org.eclipse.pde.PluginNature";
	private static final String XTEXT_PLUGIN_NATURE = "org.eclipse.xtext.ui.shared.xtextNature";
	private static final String PROFILE_APPLICATION_DECORATION_DESCRIPTIONS_FILE_NAME = "profileapplication.decoration";

	private ProfileProjectData projectData;
	private IProject project;
	private Resource profileDiagramResource;
	private boolean openDiagram;

	public NewProfileProjectOperation(ProfileProjectData projectData) {
		super();
		this.projectData = projectData;
		this.project = projectData.getProjectHandle();
		this.openDiagram = true;
	}

	public NewProfileProjectOperation(ProfileProjectData projectData,
			boolean openDiagram) {
		super();
		this.projectData = projectData;
		this.project = projectData.getProjectHandle();
		this.openDiagram = openDiagram;
	}

	@Override
	protected void execute(IProgressMonitor monitor) throws CoreException,
			InvocationTargetException, InterruptedException {

		monitor.beginTask("Creating new EMF Profile project", 4);

		monitor.subTask("Creating project");
		createPDEProject(new SubProgressMonitor(monitor, 1));
		monitor.worked(1);

		monitor.subTask("Create contents");
		createContents(new SubProgressMonitor(monitor, 1));
		monitor.worked(1);

		monitor.subTask("Create manifest");
		createPluginXml(new SubProgressMonitor(monitor, 1));
		configureBinBuildProperties();
		monitor.worked(1);

		if (openDiagram) {
			monitor.subTask("Open profile");
			openEMFProfileDiagram();
		}
		monitor.worked(1);
	}

	private void createPDEProject(IProgressMonitor monitor)
			throws CoreException {
		if (!project.exists()) {
			if (!Platform.getLocation().equals(projectData.getLocationPath())) {
				IProjectDescription desc = project.getWorkspace()
						.newProjectDescription(project.getName());
				desc.setLocation(projectData.getLocationPath());
				project.create(desc, monitor);
			} else {
				project.create(monitor);
			}
			project.open(null);
		}
		configureNatures(project);
	}

	private void configureNatures(IProject project) throws CoreException {
		if (!project.hasNature(PDE_PLUGIN_NATURE))
			addNature(PDE_PLUGIN_NATURE);
		if (!project.hasNature(EMFProfileProjectNature.NATURE_ID))
			EMFProfileProjectNatureUtil.addNature(project);
		// at the moment it seems that the decoration descriptions editor works
		// also without adding xtext nature to the profile project, nevertheless
		// if at some point in time it is needed uncomment next two lines.
		// if(!project.hasNature(XTEXT_PLUGIN_NATURE))
		// addNature(XTEXT_PLUGIN_NATURE);
	}

	public void addNature(String natureId) throws CoreException {
		IProjectDescription description = project.getDescription();
		String[] natures = description.getNatureIds();
		if (!Arrays.asList(natures).contains(natureId)) {
			String[] newNatures = new String[natures.length + 1];
			System.arraycopy(natures, 0, newNatures, 1, natures.length);
			newNatures[0] = natureId;
			description.setNatureIds(newNatures);
			project.setDescription(description, null);
		}
	}

	private void createPluginXml(IProgressMonitor monitor) throws CoreException {
		IFile pluginXmlFile = project
				.getFile(EMFProfileProjectNature.PLUGIN_XML_FILE_NAME);
		if (!pluginXmlFile.exists()) {
			StringBuffer pluginXmlContent = new StringBuffer();
			pluginXmlContent
					.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"); //$NON-NLS-1$
			pluginXmlContent.append("<?eclipse version=\"3.4\"?>\n"); //$NON-NLS-1$
			pluginXmlContent.append("<plugin\n"); //$NON-NLS-1$
			pluginXmlContent.append("  id=\"" + projectData.getProjectName() //$NON-NLS-1$
					+ "\"\n"); //$NON-NLS-1$
			pluginXmlContent.append("   name=\"" + projectData.getProjectName() //$NON-NLS-1$
					+ "\"\n"); //$NON-NLS-1$
			pluginXmlContent.append("   version=\"" + DEFAULT_VERSION + "\"\n"); //$NON-NLS-1$ //$NON-NLS-2$
			pluginXmlContent.append("  provider=\"\">\n"); //$NON-NLS-1$

			// requires
			pluginXmlContent.append("  <requires>\n"); //$NON-NLS-1$
			// import of emf profile plug-in
			pluginXmlContent
					.append("     <import plugin=\"" + EMFProfilePlugin.ID + "\" "); //$NON-NLS-1$ //$NON-NLS-2$
			pluginXmlContent.append("version=\"1.1.0\" "); //$NON-NLS-1$
			pluginXmlContent.append("match=\"greaterOrEqual\" "); //$NON-NLS-1$
			pluginXmlContent.append("export=\"true\"/>\n"); //$NON-NLS-1$
			// import of emf profile registry plug-in
			pluginXmlContent
					.append("     <import plugin=\"org.modelversioning.emfprofile.registry\" "); //$NON-NLS-1$
			pluginXmlContent.append("version=\"1.0.0\" "); //$NON-NLS-1$
			pluginXmlContent.append("match=\"greaterOrEqual\" "); //$NON-NLS-1$
			pluginXmlContent.append("export=\"true\"/>\n"); //$NON-NLS-1$
			pluginXmlContent.append("  </requires>\n"); //$NON-NLS-1$

			// extension point to register emf profile
			pluginXmlContent
					.append("  <extension point=\"org.modelversioning.emfprofile.profile\">\n"); //$NON-NLS-1$
			pluginXmlContent
					.append("     <profile profile_resource=\"profile.emfprofile_diagram\"/>\n"); //$NON-NLS-1$
			pluginXmlContent.append("  </extension>\n"); //$NON-NLS-1$

			pluginXmlContent.append("</plugin>\n"); //$NON-NLS-1$
			InputStream source = new ByteArrayInputStream(pluginXmlContent
					.toString().getBytes());
			pluginXmlFile.create(source, true, monitor);
		}
	}

	private void configureBinBuildProperties() throws CoreException {
		IFile buildPropFile = project.getFile(BUILD_PROP_FILE_NAME);
		if (!buildPropFile.exists()) {
			StringBuffer buildPropContent = new StringBuffer();
			buildPropContent.append("bin.includes = ");
			buildPropContent
					.append(EMFProfileProjectNature.PLUGIN_XML_FILE_NAME
							+ ",\\\n"); //$NON-NLS-1$
			buildPropContent.append("               "); //$NON-NLS-1$
			buildPropContent.append(EMFProfileProjectNature.ICONS_FOLDER_NAME);
			buildPropContent.append("/,\\\n"); //$NON-NLS-1$
			buildPropContent.append("               "); //$NON-NLS-1$
			buildPropContent
					.append(EMFProfileProjectNature.DEFAULT_PROFILE_DIAGRAM_FILE_NAME
							+ "\n"); //$NON-NLS-1$
			InputStream source = new ByteArrayInputStream(buildPropContent
					.toString().getBytes());
			buildPropFile.create(source, true, null);
		}
	}

	private void openEMFProfileDiagram() {
		if (profileDiagramResource != null) {
			try {
				EMFProfileDiagramEditorUtil.openDiagram(profileDiagramResource);
			} catch (PartInitException e) {
				new InvocationTargetException(e);
			}
		}
	}

	protected void createContents(IProgressMonitor monitor)
			throws CoreException, JavaModelException,
			InvocationTargetException, InterruptedException {
		createIconFolder(monitor, project);
		try {
			createProfile(monitor, project);
		} catch (IOException e) {
			throw new InvocationTargetException(e);
		}
		createProfileApplicationDecorationDescriptionsFile(monitor, project);
	}

	private void createProfileApplicationDecorationDescriptionsFile(
			IProgressMonitor monitor, IProject project) throws CoreException {
		StringBuilder contents = new StringBuilder();
		contents.append("import resource '"
				+ EMFProfileProjectNature.DEFAULT_PROFILE_DIAGRAM_FILE_NAME
				+ "'\n\n");
		contents.append("profile " + projectData.getProfileName() + "\n");
		project.getFile(PROFILE_APPLICATION_DECORATION_DESCRIPTIONS_FILE_NAME)
				.create(new ByteArrayInputStream(contents.toString().getBytes()),
						IResource.NONE, new SubProgressMonitor(monitor, 0));
	}

	private void createIconFolder(IProgressMonitor monitor, IProject project)
			throws CoreException {
		project.getFolder(EMFProfileProjectNature.ICONS_FOLDER_NAME).create(
				false, true, new SubProgressMonitor(monitor, 0));
	}

	private void createProfile(IProgressMonitor monitor, IProject project)
			throws IOException {
		profileDiagramResource = EMFProfileDiagramEditorUtil.createDiagram(
				EMFProfileProjectNatureUtil
						.getDefaultProfileDiagramURI(project),
				new SubProgressMonitor(monitor, 0));
		setProfileDiagramData();
		saveProfileDiagramResource();
	}

	private void saveProfileDiagramResource() throws IOException {
		TransactionalEditingDomain editingDomain = TransactionUtil
				.getEditingDomain(profileDiagramResource);
		profileDiagramResource.save(null);
		((BasicCommandStack) editingDomain.getCommandStack()).saveIsDone();
	}

	private void setProfileDiagramData() {
		final Profile profile = getProfileFromResource();
		TransactionalEditingDomain editingDomain = TransactionUtil
				.getEditingDomain(profileDiagramResource);
		editingDomain.getCommandStack().execute(
				new RecordingCommand(editingDomain) {
					@Override
					protected void doExecute() {
						profile.setName(projectData.getProfileName());
						profile.setNsURI(projectData.getProfileNamespace());
					}
				});
	}

	private Profile getProfileFromResource() {
		if (profileDiagramResource == null)
			return null;
		for (TreeIterator<EObject> contents = profileDiagramResource
				.getAllContents(); contents.hasNext();) {
			EObject next = contents.next();
			if (next instanceof Profile) {
				return (Profile) next;
			}
		}
		return null;
	}

}

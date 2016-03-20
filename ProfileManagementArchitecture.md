# Profile Management Architecture #

## Overview ##

This page gives a short overview of the architecture of plug-in projects realizing the profile management. This architecture has been created to address the following issues:

  * [issue 15](https://code.google.com/p/emf-profiles/issues/detail?id=15)
  * [issue 17](https://code.google.com/p/emf-profiles/issues/detail?id=17)
  * [issue 18](https://code.google.com/p/emf-profiles/issues/detail?id=18)

## Requirements ##

The requirements for the profile management redesign are the following.

  1. Profiles should be created as projects of an own nature and not as single files.
  1. There should be an own profile registry that is synchronized with the EMF metamodel registry.
  1. Profile projects in the workspace should be registered automatically in the profiles registry. If these profile projects are closed or deleted, the profile should be unregistered automatically from the profile registry.
  1. To enable making profiles available globally in Eclipse (without having to have the profile project in the workspace) and to enable sharing profiles via update sites, profile projects should also have the plug-in nature and implement an extension point that adds the respective profile to the registry. Thereby, profile projects should be exportable into a plug-in project and, once these plug-in projects are in the plug-in directory of the Eclipse installation, the profiles are registered on start-up. If under the same namespace, a plug-in profile project and a profile project in the workspace exists, the one in the workspace has higher priority.
  1. There should be a profile registry view which shows the currently available profiles. This view should make a visual differentiation between profile projects in the workspace and those coming from plug-ins.


## Architecture ##

The requirements mentioned above shall be realized by the following list of plug-ins.

  * `org.modelversioning.emfprofiles.project`: Implements the profile project nature and provides some utility methods for working with EMF Profile projects.
  * `org.modelversioning.emfprofiles.project.ui`: Implements the new profile project wizard.
  * `org.modelversioning.emfprofiles.registry`: Keeps track of the registered profiles and exhibits the extension point for registering profiles. This plug-in also contains a synchronizer to keep profile projects in the workspace in sync with the profiles in the registry.
  * `org.modelversioning.emfprofiles.registry.ui`: Implements the view of the registered profiles.
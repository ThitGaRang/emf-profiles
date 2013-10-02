/*
* generated by Xtext
*/
package org.modelversioning.emfprofile.decoration.ui.contentassist.antlr;

import java.util.Collection;
import java.util.Map;
import java.util.HashMap;

import org.antlr.runtime.RecognitionException;
import org.eclipse.xtext.AbstractElement;
import org.eclipse.xtext.ui.editor.contentassist.antlr.AbstractContentAssistParser;
import org.eclipse.xtext.ui.editor.contentassist.antlr.FollowElement;
import org.eclipse.xtext.ui.editor.contentassist.antlr.internal.AbstractInternalContentAssistParser;

import com.google.inject.Inject;

import org.modelversioning.emfprofile.decoration.services.EMFProfileDecorationLanguageGrammarAccess;

public class EMFProfileDecorationLanguageParser extends AbstractContentAssistParser {
	
	@Inject
	private EMFProfileDecorationLanguageGrammarAccess grammarAccess;
	
	private Map<AbstractElement, String> nameMappings;
	
	@Override
	protected org.modelversioning.emfprofile.decoration.ui.contentassist.antlr.internal.InternalEMFProfileDecorationLanguageParser createParser() {
		org.modelversioning.emfprofile.decoration.ui.contentassist.antlr.internal.InternalEMFProfileDecorationLanguageParser result = new org.modelversioning.emfprofile.decoration.ui.contentassist.antlr.internal.InternalEMFProfileDecorationLanguageParser(null);
		result.setGrammarAccess(grammarAccess);
		return result;
	}
	
	@Override
	protected String getRuleName(AbstractElement element) {
		if (nameMappings == null) {
			nameMappings = new HashMap<AbstractElement, String>() {
				private static final long serialVersionUID = 1L;
				{
					put(grammarAccess.getEMFProfileDecorationModelAccess().getGroup(), "rule__EMFProfileDecorationModel__Group__0");
					put(grammarAccess.getDecorationAccess().getGroup(), "rule__Decoration__Group__0");
					put(grammarAccess.getEMFProfileDecorationModelAccess().getImportURIAssignment_1(), "rule__EMFProfileDecorationModel__ImportURIAssignment_1");
					put(grammarAccess.getEMFProfileDecorationModelAccess().getDecorationsAssignment_2(), "rule__EMFProfileDecorationModel__DecorationsAssignment_2");
					put(grammarAccess.getDecorationAccess().getDecorationsAssignment_1(), "rule__Decoration__DecorationsAssignment_1");
				}
			};
		}
		return nameMappings.get(element);
	}
	
	@Override
	protected Collection<FollowElement> getFollowElements(AbstractInternalContentAssistParser parser) {
		try {
			org.modelversioning.emfprofile.decoration.ui.contentassist.antlr.internal.InternalEMFProfileDecorationLanguageParser typedParser = (org.modelversioning.emfprofile.decoration.ui.contentassist.antlr.internal.InternalEMFProfileDecorationLanguageParser) parser;
			typedParser.entryRuleEMFProfileDecorationModel();
			return typedParser.getFollowElements();
		} catch(RecognitionException ex) {
			throw new RuntimeException(ex);
		}		
	}
	
	@Override
	protected String[] getInitialHiddenTokens() {
		return new String[] { "RULE_WS", "RULE_ML_COMMENT", "RULE_SL_COMMENT" };
	}
	
	public EMFProfileDecorationLanguageGrammarAccess getGrammarAccess() {
		return this.grammarAccess;
	}
	
	public void setGrammarAccess(EMFProfileDecorationLanguageGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}
}
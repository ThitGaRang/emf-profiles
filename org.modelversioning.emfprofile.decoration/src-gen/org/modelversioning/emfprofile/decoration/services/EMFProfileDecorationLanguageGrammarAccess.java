/*
* generated by Xtext
*/
package org.modelversioning.emfprofile.decoration.services;

import com.google.inject.Singleton;
import com.google.inject.Inject;

import java.util.List;

import org.eclipse.xtext.*;
import org.eclipse.xtext.service.GrammarProvider;
import org.eclipse.xtext.service.AbstractElementFinder.*;

import org.eclipse.xtext.common.services.TerminalsGrammarAccess;

@Singleton
public class EMFProfileDecorationLanguageGrammarAccess extends AbstractGrammarElementFinder {
	
	
	public class EMFProfileDecorationModelElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "EMFProfileDecorationModel");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cDecoratingProfileKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cImportURIAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cImportURISTRINGTerminalRuleCall_1_0 = (RuleCall)cImportURIAssignment_1.eContents().get(0);
		private final Assignment cDecorationsAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final RuleCall cDecorationsDecorationParserRuleCall_2_0 = (RuleCall)cDecorationsAssignment_2.eContents().get(0);
		
		//EMFProfileDecorationModel:
		//	"decorating profile" importURI=STRING decorations+=Decoration*;
		public ParserRule getRule() { return rule; }

		//"decorating profile" importURI=STRING decorations+=Decoration*
		public Group getGroup() { return cGroup; }

		//"decorating profile"
		public Keyword getDecoratingProfileKeyword_0() { return cDecoratingProfileKeyword_0; }

		//importURI=STRING
		public Assignment getImportURIAssignment_1() { return cImportURIAssignment_1; }

		//STRING
		public RuleCall getImportURISTRINGTerminalRuleCall_1_0() { return cImportURISTRINGTerminalRuleCall_1_0; }

		//decorations+=Decoration*
		public Assignment getDecorationsAssignment_2() { return cDecorationsAssignment_2; }

		//Decoration
		public RuleCall getDecorationsDecorationParserRuleCall_2_0() { return cDecorationsDecorationParserRuleCall_2_0; }
	}

	public class DecorationElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "Decoration");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cDecorationKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cDecorationsAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final CrossReference cDecorationsStereotypeCrossReference_1_0 = (CrossReference)cDecorationsAssignment_1.eContents().get(0);
		private final RuleCall cDecorationsStereotypeIDTerminalRuleCall_1_0_1 = (RuleCall)cDecorationsStereotypeCrossReference_1_0.eContents().get(1);
		private final Keyword cLeftCurlyBracketKeyword_2 = (Keyword)cGroup.eContents().get(2);
		private final Keyword cRightCurlyBracketKeyword_3 = (Keyword)cGroup.eContents().get(3);
		
		/// *
		//Domainmodel:
		//	(elements+=AbstractElement)*;
		//
		//PackageDeclaration:
		//	'package' name=QualifiedName '{'
		//	(elements+=AbstractElement)*
		//	'}';
		//
		//AbstractElement:
		//	PackageDeclaration | Type | Import;
		//
		//QualifiedName:
		//	ID ('.' ID)*;
		//
		//Import:
		//	'import' importedNamespace=QualifiedNameWithWildcard;
		//
		//QualifiedNameWithWildcard:
		//	QualifiedName '.*'?;
		//
		//Type:
		//	DataType | Entity;
		//
		//DataType:
		//	'datatype' name=ID;
		//
		//Entity:
		//	'entity' name=ID
		//	('extends' superType=[Entity|QualifiedName])?
		//	'{'
		//	(features+=Feature)*
		//	'}';
		//
		//Feature:
		//	(many?='many')? name=ID ':' type=[Type|QualifiedName];
		// * / Decoration:
		//	"decoration" decorations=[profile::Stereotype] "{" "}";
		public ParserRule getRule() { return rule; }

		//"decoration" decorations=[profile::Stereotype] "{" "}"
		public Group getGroup() { return cGroup; }

		//"decoration"
		public Keyword getDecorationKeyword_0() { return cDecorationKeyword_0; }

		//decorations=[profile::Stereotype]
		public Assignment getDecorationsAssignment_1() { return cDecorationsAssignment_1; }

		//[profile::Stereotype]
		public CrossReference getDecorationsStereotypeCrossReference_1_0() { return cDecorationsStereotypeCrossReference_1_0; }

		//ID
		public RuleCall getDecorationsStereotypeIDTerminalRuleCall_1_0_1() { return cDecorationsStereotypeIDTerminalRuleCall_1_0_1; }

		//"{"
		public Keyword getLeftCurlyBracketKeyword_2() { return cLeftCurlyBracketKeyword_2; }

		//"}"
		public Keyword getRightCurlyBracketKeyword_3() { return cRightCurlyBracketKeyword_3; }
	}
	
	
	private EMFProfileDecorationModelElements pEMFProfileDecorationModel;
	private DecorationElements pDecoration;
	
	private final Grammar grammar;

	private TerminalsGrammarAccess gaTerminals;

	@Inject
	public EMFProfileDecorationLanguageGrammarAccess(GrammarProvider grammarProvider,
		TerminalsGrammarAccess gaTerminals) {
		this.grammar = internalFindGrammar(grammarProvider);
		this.gaTerminals = gaTerminals;
	}
	
	protected Grammar internalFindGrammar(GrammarProvider grammarProvider) {
		Grammar grammar = grammarProvider.getGrammar(this);
		while (grammar != null) {
			if ("org.modelversioning.emfprofile.decoration.EMFProfileDecorationLanguage".equals(grammar.getName())) {
				return grammar;
			}
			List<Grammar> grammars = grammar.getUsedGrammars();
			if (!grammars.isEmpty()) {
				grammar = grammars.iterator().next();
			} else {
				return null;
			}
		}
		return grammar;
	}
	
	
	public Grammar getGrammar() {
		return grammar;
	}
	

	public TerminalsGrammarAccess getTerminalsGrammarAccess() {
		return gaTerminals;
	}

	
	//EMFProfileDecorationModel:
	//	"decorating profile" importURI=STRING decorations+=Decoration*;
	public EMFProfileDecorationModelElements getEMFProfileDecorationModelAccess() {
		return (pEMFProfileDecorationModel != null) ? pEMFProfileDecorationModel : (pEMFProfileDecorationModel = new EMFProfileDecorationModelElements());
	}
	
	public ParserRule getEMFProfileDecorationModelRule() {
		return getEMFProfileDecorationModelAccess().getRule();
	}

	/// *
	//Domainmodel:
	//	(elements+=AbstractElement)*;
	//
	//PackageDeclaration:
	//	'package' name=QualifiedName '{'
	//	(elements+=AbstractElement)*
	//	'}';
	//
	//AbstractElement:
	//	PackageDeclaration | Type | Import;
	//
	//QualifiedName:
	//	ID ('.' ID)*;
	//
	//Import:
	//	'import' importedNamespace=QualifiedNameWithWildcard;
	//
	//QualifiedNameWithWildcard:
	//	QualifiedName '.*'?;
	//
	//Type:
	//	DataType | Entity;
	//
	//DataType:
	//	'datatype' name=ID;
	//
	//Entity:
	//	'entity' name=ID
	//	('extends' superType=[Entity|QualifiedName])?
	//	'{'
	//	(features+=Feature)*
	//	'}';
	//
	//Feature:
	//	(many?='many')? name=ID ':' type=[Type|QualifiedName];
	// * / Decoration:
	//	"decoration" decorations=[profile::Stereotype] "{" "}";
	public DecorationElements getDecorationAccess() {
		return (pDecoration != null) ? pDecoration : (pDecoration = new DecorationElements());
	}
	
	public ParserRule getDecorationRule() {
		return getDecorationAccess().getRule();
	}

	//terminal ID:
	//	"^"? ("a".."z" | "A".."Z" | "_") ("a".."z" | "A".."Z" | "_" | "0".."9")*;
	public TerminalRule getIDRule() {
		return gaTerminals.getIDRule();
	} 

	//terminal INT returns ecore::EInt:
	//	"0".."9"+;
	public TerminalRule getINTRule() {
		return gaTerminals.getINTRule();
	} 

	//terminal STRING:
	//	"\"" ("\\" ("b" | "t" | "n" | "f" | "r" | "u" | "\"" | "\'" | "\\") | !("\\" | "\""))* "\"" | "\'" ("\\" ("b" | "t" |
	//	"n" | "f" | "r" | "u" | "\"" | "\'" | "\\") | !("\\" | "\'"))* "\'";
	public TerminalRule getSTRINGRule() {
		return gaTerminals.getSTRINGRule();
	} 

	//terminal ML_COMMENT:
	//	"/ *"->"* /";
	public TerminalRule getML_COMMENTRule() {
		return gaTerminals.getML_COMMENTRule();
	} 

	//terminal SL_COMMENT:
	//	"//" !("\n" | "\r")* ("\r"? "\n")?;
	public TerminalRule getSL_COMMENTRule() {
		return gaTerminals.getSL_COMMENTRule();
	} 

	//terminal WS:
	//	(" " | "\t" | "\r" | "\n")+;
	public TerminalRule getWSRule() {
		return gaTerminals.getWSRule();
	} 

	//terminal ANY_OTHER:
	//	.;
	public TerminalRule getANY_OTHERRule() {
		return gaTerminals.getANY_OTHERRule();
	} 
}
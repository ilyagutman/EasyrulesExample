package com.easyrules.launcher;

import org.easyrules.core.AnnotatedRulesEngine;

import com.easyrules.rules.ComplexRule;
import com.easyrules.rules.SimpleRule;

public class SimpleRuleLauncher {

	public static void main(String[] args) {

		String str = new String("Just say yes!");
		
		/**
		 * Create a rules engine and register the business rule
		 */
		AnnotatedRulesEngine rulesEngine = new AnnotatedRulesEngine();
		rulesEngine.registerRule(new SimpleRule(str));
		rulesEngine.registerRule(new ComplexRule(str));

		/**
		 * Fire rules
		 */
		rulesEngine.fireRules();
	}
}

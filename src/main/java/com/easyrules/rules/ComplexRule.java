package com.easyrules.rules;

import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Rule;

@Rule(name = "Simple Yes/No rule", description = "Prints Yes if rule says yes, and No, if rule evaluates to no.")
public class ComplexRule {

	/**
	 * The user input which represents the data that the rule will operate on.
	 */
	private String input;

	public ComplexRule(String input) {
		this.input = input;
	}

	@Condition
	public boolean condition() {
		// The rule should be applied only if
		// the piece of data reads 'yes'
		return input.length() > 5;
	}

	@Action
	public void action() throws Exception {
		System.out.println("the ComplexRule action has fired!");
	}

}
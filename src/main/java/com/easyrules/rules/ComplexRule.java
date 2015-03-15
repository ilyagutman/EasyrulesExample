package com.easyrules.rules;

import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Rule;

@Rule(name = "Complex rule", description = "Succeds if the length of the message is greater than 5")
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
		return input.length() > 5;
	}

	@Action
	public void action() throws Exception {
		System.out.println("the ComplexRule action has fired!");
	}

}
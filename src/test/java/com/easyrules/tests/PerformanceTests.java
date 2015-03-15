package com.easyrules.tests;

import static org.junit.Assert.*;

import org.easyrules.core.AnnotatedRulesEngine;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.easyrules.rules.*;

import java.util.logging.Level;
import java.util.logging.LogManager;

public class PerformanceTests {

	AnnotatedRulesEngine rulesEngine;
	SimpleRule simpleRule;
	ComplexRule complexRule;
	
	private void clearRules() {
		simpleRule = null;
		complexRule = null;
		rulesEngine.clearRules();
	}

	private void runRules() {
		rulesEngine.clearRules();
		rulesEngine.registerRule(simpleRule);
		rulesEngine.registerRule(complexRule);
		rulesEngine.fireRules();
	}

	@Before
	public void initialize () {
		rulesEngine = new AnnotatedRulesEngine();
		LogManager.getLogManager().reset();
	}
	
	@Test
	public void RunSimpleRuleWithEmptyString() throws Exception {
		clearRules();
		simpleRule = new SimpleRule("");
		
		rulesEngine.registerRule(simpleRule);
		rulesEngine.fireRules();
		assertFalse(simpleRule.isExecuted());
	}
	
	
	@Test
	public void RunSimpleRuleWithYesString() throws Exception {
		clearRules();
		simpleRule = new SimpleRule("yes");
		rulesEngine.registerRule(simpleRule);

		rulesEngine.fireRules();
		assertTrue(simpleRule.isExecuted());
	}
	
	@Test
	public void RunComplexRuleWithEmptyString() throws Exception {
		clearRules();
		complexRule = new ComplexRule("");
		rulesEngine.registerRule(complexRule);

		rulesEngine.fireRules();
		assertFalse(complexRule.isExecuted());
	}
	
	@Test
	public void RunComplexRuleWithHelloString() throws Exception {
		clearRules();
		complexRule = new ComplexRule("Hello!!!");
		rulesEngine.registerRule(complexRule);

		rulesEngine.fireRules();
		assertTrue(complexRule.isExecuted());
	}
	
	@Test
	public void RunSimpleAndComplexRulesWithEmptyString() throws Exception {
		clearRules();
		simpleRule = new SimpleRule("");
		complexRule = new ComplexRule("");

		runRules();
		
		assertFalse(simpleRule.isExecuted() || complexRule.isExecuted());
	}
	

	@Test
	public void RunSimpleAndComplexRulesWithLongString() throws Exception {
		clearRules();
		simpleRule = new SimpleRule("YesYes");
		complexRule = new ComplexRule("YesYes");

		runRules();
		
		assertTrue(simpleRule.isExecuted() & complexRule.isExecuted());
	}

	@Test
	public void runBothRulesMillionTimes() throws Exception {
		long startTime = System.nanoTime();
		runMlnTimes();
		long endTime = System.nanoTime();

		System.out.println("performed a million cycles in " + ((endTime - startTime)/1000000) + " milliseconds");
		System.out.println("Througput is:" + (1000000/((endTime - startTime)/1000000000)) + " executions per sec.");
		assertTrue((endTime - startTime)/1000000 < 100);
	}
	
	
	private void runMlnTimes() {
		clearRules();
		simpleRule = new SimpleRule("Yes");
		complexRule = new ComplexRule("Yes");
		rulesEngine.registerRule(simpleRule);
		rulesEngine.registerRule(complexRule);
		
		
		for (long i = 0; i < 1000000; i++) {
			rulesEngine.fireRules();
		}
		
	}

	@After
	public void cleanUp () {
		clearRules();
		rulesEngine = null;
	}

}

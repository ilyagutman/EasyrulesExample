package com.sample;

import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;

import com.sample.DecisionTableTest.Message;

public class DecisionTablePerfTest {

	private  KieServices ks;
	private KieContainer kContainer;
	KieSession kSession;
	static final int TPUT_PER_SEC = 30000;
	static final String FAST = "fast";
	static final String SLOW = "slow";
	static final boolean REPORT = true;

	@Before
	public void setUp() throws Exception {
		try {
			ks = KieServices.Factory.get();
			kContainer = ks.getKieClasspathContainer();
			kSession = kContainer.newKieSession("ksession-dtables");
		}
		catch (Throwable t) {
			t.printStackTrace();
		}
	}

	@After
	public void tearDown() throws Exception {
    	kSession.dispose();
    	kSession.destroy();
	    kContainer = null;
        ks = null;
	}

	@Test
	public void runRuleBatches() throws Exception {
        int iterations = 100000;
        int batches = 1;
//		assertThat(speed(iterations * batches, runRulesBatch(iterations, batches)), is(FAST));
        
        iterations = 100000;
        batches = 10;
		assertThat(speed(iterations * batches, runRulesBatch(iterations, batches)), is(FAST));
		
        iterations = 50000;
        batches = 20;
		assertThat(speed(iterations * batches, runRulesBatch(iterations, batches)), is(FAST));

        iterations = 5000;
        batches = 200;
		assertThat(speed(iterations * batches, runRulesBatch(iterations, batches)), is(FAST));

        iterations = 1000;
        batches = 1000;
		assertThat(speed(iterations * batches, runRulesBatch(iterations, batches)), is(FAST));

        iterations = 1000;
        batches = 2000;
		assertThat(speed(iterations * batches, runRulesBatch(iterations, batches)), is(FAST));

        iterations = 1000;
        batches = 3000;
		assertThat(speed(iterations * batches, runRulesBatch(iterations, batches)), is(FAST));

        iterations = 500;
        batches = 2000;
		assertThat(speed(iterations * batches, runRulesBatch(iterations, batches)), is(FAST));

        iterations = 500;
        batches = 4000;
		assertThat(speed(iterations * batches, runRulesBatch(iterations, batches)), is(FAST));

        iterations = 500;
        batches = 6000;
		assertThat(speed(iterations * batches, runRulesBatch(iterations, batches)), is(FAST));

	}

	
	private long runRulesBatch(int iterations, int batches) {
        long startTime = System.nanoTime();
		for (int i = 0; i < batches; i++) runRules(iterations);
		long endTime = System.nanoTime();

		if (REPORT) logPerformance(iterations, batches, endTime - startTime);
		return (endTime - startTime);
	}
	
	private long runRules(int iterations) {
		int process;
		Message message;
		List<FactHandle> fhList = new ArrayList<FactHandle>();
		long factCount;
				
        for (int i = 0; i < iterations; i++) {
        	process = (int) (Math.random() * 20.0);
        	message = new Message("Hello World " + process, process);
            fhList.add(kSession.insert(message));
		}
        
        kSession.fireAllRules();
        factCount = kSession.getFactCount();
        
        for (FactHandle factHandle : fhList) {
        	kSession.delete(factHandle);
		}
        
        return factCount;
	}

	private String speed(int messages, long runTime) {
		return ((long) througput(messages, runTime) > TPUT_PER_SEC ? FAST : SLOW);
	}

	private double througput(int messages, long runTime) {
		return (messages/(runTime/1000000000.0));
	}

	private void logPerformance(int iterations, int units, long runTime) {
		System.out.println("Iterations: " + iterations + " Units: " + units + 
				" througput:" + String.format( "%.2f", (iterations * units)/(runTime/1000000000.0)) + 
				" executions per sec.");
	}

}

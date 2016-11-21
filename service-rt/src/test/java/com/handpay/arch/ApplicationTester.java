package com.handpay.arch;

import com.handpay.arch.stat.config.service.AlarmWorkerTester;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Created by fczheng on 2016/11/14.
 */

public class ApplicationTester {

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		TestSuite suite = new TestSuite("TestSuite for app");
		suite.addTest(new JUnit4TestAdapter(AlarmWorkerTester.class));
		return suite;
	}
}

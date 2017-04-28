// (c) 2012 uchicom
package com.uchicom.csve;

import java.security.Permission;

import junit.framework.TestCase;

public class TestBase extends TestCase {
	SecurityManager sm = System.getSecurityManager();
	protected void setUp() throws Exception {
		super.setUp();
		System.setSecurityManager(new SecurityManager() {
			public void checkPermission(Permission permission) {
				//
			}


			public void checkExit(int status) {
				throw new TestException(status);
			}
		});
	}

	protected void tearDown() throws Exception {
		System.setSecurityManager(sm);
	}
}

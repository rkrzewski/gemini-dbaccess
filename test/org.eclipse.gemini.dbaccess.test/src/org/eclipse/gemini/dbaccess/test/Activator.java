/*******************************************************************************
 * Copyright (c) 2010 Oracle.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Apache License v2.0 which accompanies this distribution. 
 * The Eclipse Public License is available at
 *     http://www.eclipse.org/legal/epl-v10.html
 * and the Apache License v2.0 is available at 
 *     http://www.opensource.org/licenses/apache2.0.php.
 * You may elect to redistribute this code under either of these licenses.
 *
 * Contributors:
 *     mkeith - Gemini DBAccess tests 
 ******************************************************************************/
package org.eclipse.gemini.dbaccess.test;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import org.osgi.service.jdbc.DataSourceFactory;

/**
 * Activator to start tests when relevant service becomes available
 * 
 * @author mkeith
 */
public class Activator implements BundleActivator, ServiceTrackerCustomizer {

    ServiceTracker dsfTracker;
    int serviceCount = 0;

    BundleContext ctx;
    
    public void start(BundleContext context) throws Exception {
        log("JDBC Tests active ");

        ctx = context;
        JdbcTests.context = context;
                
        dsfTracker = new ServiceTracker(ctx, DataSourceFactory.class.getName(), this);
        dsfTracker.open();
        
        // Run tests from tracker when service is online
    }

    public void stop(BundleContext context) throws Exception {
        dsfTracker.close();        
    }

    void runTest(String descr, Class<?> testClass) {
        log("Running " + descr + ": ");
        
        Result r = JUnitCore.runClasses(testClass);

        log(descr + " results: ");
        logResultStats(r);

        log("Done " + descr);
    }

    /* ServiceTracker methods */

    public Object addingService(ServiceReference ref) {
        Bundle b = ref.getBundle();
        Object service = b.getBundleContext().getService(ref);
        
        String driver = (String)ref.getProperty(DataSourceFactory.OSGI_JDBC_DRIVER_CLASS);

        if (driver!= null) {
            // We have a JDBC service
            log("Test notified of service: " + driver);
            // Don't start the tests until we have all of them 
            if (++serviceCount < 4) return service;
            
            log("Running tests");
            
            // Now run the tests
            runTest("Derby Embedded tests", EmbeddedDriverTests.class);
            runTest("Derby JDBC 4 Embedded tests", Jdbc4EmbeddedDriverTests.class);
            runTest("Derby ClientDriver tests", ClientDriverTests.class);
            runTest("Derby JDBC 4 ClientDriver tests", Jdbc4ClientDriverTests.class);
        }
        return service;
    }

    public void modifiedService(ServiceReference ref, Object service) {}

    public void removedService(ServiceReference ref, Object service) {
        ctx.ungetService(ref);
    }

    void logResultStats(Result r) {
        log("Result: " + 
                " runs=" + r.getRunCount() + 
                " failures=" + r.getFailureCount() +
                " ignore=" + r.getIgnoreCount());        
        log("Failures: " + r.getFailures());
        for (Failure f : r.getFailures())
            log("--- Failure: \n" + f.getTrace());
    }
    
    static void log(String msg) {
        System.out.println("===== " + msg);
    }    
}

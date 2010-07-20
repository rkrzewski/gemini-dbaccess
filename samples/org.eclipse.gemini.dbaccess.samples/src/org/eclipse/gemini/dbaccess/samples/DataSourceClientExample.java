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
package org.eclipse.gemini.dbaccess.samples;

import java.util.Properties;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import javax.sql.DataSource;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import org.osgi.service.jdbc.DataSourceFactory;

/**
 * Example of how to access a DataSource from a client program
 * 
 * @author mkeith
 */
public class DataSourceClientExample implements BundleActivator, ServiceTrackerCustomizer {

    public static final String EMBEDDED_DERBY_DRIVER_NAME = "org.apache.derby.jdbc.EmbeddedDriver";
    public static final String JDBC_4_VERSION = "4.0";

    ServiceTracker dsfTracker;
    BundleContext ctx;

    /* === Activator methods === */
    
    public void start(BundleContext context) throws Exception {
        log("Client active ");
        ctx = context;

        dsfTracker = new ServiceTracker(ctx, DataSourceFactory.class.getName(), this);
        dsfTracker.open();
    }

    public void stop(BundleContext context) throws Exception {
        log("Client active ");
        dsfTracker.close();        
    }

    /* === ServiceTracker methods === */

    public Object addingService(ServiceReference ref) {
        Object service = ctx.getService(ref);
        
        String driver = (String)ref.getProperty(DataSourceFactory.OSGI_JDBC_DRIVER_CLASS);
        String version = (String)ref.getProperty(DataSourceFactory.OSGI_JDBC_DRIVER_VERSION);

        if (driver != null && driver.equalsIgnoreCase(EMBEDDED_DERBY_DRIVER_NAME) &&
                version != null && version.equalsIgnoreCase(JDBC_4_VERSION)) {
            log("Client notified of service: " + driver);

            // We have a JDBC service, now do something with it
            DataSourceFactory dsf = (DataSourceFactory) service;
            useEmbeddedDataSource(dsf);
        }
        return service;
    }

    public void modifiedService(ServiceReference ref, Object service) { /* Do nothing */ }

    public void removedService(ServiceReference ref, Object service) { ctx.ungetService(ref); }

    /* === Supporting methods === */

    void useEmbeddedDataSource(DataSourceFactory dsf) {
        Properties props = new Properties();
        props.put(DataSourceFactory.JDBC_URL, "jdbc:derby:testDB;create=true");
        DataSource ds = null;
        Connection conn = null;
        try {
            ds = dsf.createDataSource(props);
            conn = ds.getConnection();
            DatabaseMetaData metadata = conn.getMetaData();
            log("Driver Name: " + metadata.getDriverName());
            log("Driver Version: " + metadata.getDriverVersion());
            log("User: " + metadata.getUserName());
            conn.close();
        } catch (SQLException sqlEx) {
            log("Error creating or using data source: " + sqlEx);
        }
        log("Client done.");
    }
    
    void log(String msg) { System.out.println("===== " + msg); }
}

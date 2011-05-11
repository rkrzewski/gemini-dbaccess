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

import java.util.Properties;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import org.osgi.service.jdbc.DataSourceFactory;

/**
 * Test class to test OSGi JDBC client driver data source factory service
 * 
 * @author mkeith
 */
public class ClientDriverTests extends JdbcTests {
    
    public static String CLIENT_DRIVER_NAME = "org.apache.derby.jdbc.ClientDriver";
    public static String JDBC_VERSION = "3.0";

    @BeforeClass
    public static void classSetUp() {
        log("In ClientDriverTests setup");
        dsf = lookupDsf(CLIENT_DRIVER_NAME, JDBC_VERSION);
        log("Found DSF service - " + dsf);
    }

    @AfterClass
    public static void classCleanUp() {
        dsf = null;
    }
    
    protected Properties getDataSourceProperties() {
        Properties props = new Properties();
        props.put(DataSourceFactory.JDBC_SERVER_NAME, "localhost");
        props.put(DataSourceFactory.JDBC_PORT_NUMBER, "1527");
        props.put(DataSourceFactory.JDBC_DATABASE_NAME, "accountDB");
        props.put(DataSourceFactory.JDBC_USER, "app");
        props.put(DataSourceFactory.JDBC_PASSWORD, "app");
        props.put("traceLevel", "1");
        props.put("traceFile", "trace.out");
        props.put("traceFileAppend", "true");
        return props;
    }

    protected Properties getUrlProperties() {
        Properties props = new Properties();
        props.put(DataSourceFactory.OSGI_JDBC_DRIVER_NAME,
                "org.apache.derby.jdbc.ClientDriver");
        props.put(DataSourceFactory.JDBC_URL,
                "jdbc:derby://localhost:1527/accountDB;create=true");
        props.put(DataSourceFactory.JDBC_USER, "app");
        props.put(DataSourceFactory.JDBC_PASSWORD, "app");
        return props;
    }
}

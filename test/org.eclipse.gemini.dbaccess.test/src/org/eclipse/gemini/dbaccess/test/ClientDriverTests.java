/*
 * Copyright (C) 2010 Oracle Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.eclipse.gemini.dbaccess.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;
import javax.sql.PooledConnection;
import javax.sql.XAConnection;
import javax.sql.XADataSource;

import org.osgi.framework.BundleContext;
import org.osgi.service.jdbc.DataSourceFactory;
import org.osgi.util.tracker.ServiceTracker;

import org.junit.*;

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
    
    Properties getDataSourceProperties() {
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

    Properties getUrlProperties() {
        Properties props = new Properties();
        props.put(DataSourceFactory.OSGI_JDBC_DRIVER_NAME,
                "org.apache.derby.jdbc.ClientDriver");
        props.put(DataSourceFactory.JDBC_URL,
                "jdbc:derby://localhost:1527/accountDB");
        props.put(DataSourceFactory.JDBC_USER, "app");
        props.put(DataSourceFactory.JDBC_PASSWORD, "app");
        return props;
    }
}

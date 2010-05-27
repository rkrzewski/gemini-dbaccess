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

import java.util.Properties;

import org.junit.*;
import org.osgi.service.jdbc.DataSourceFactory;

/**
 * Test class to test OSGi JDBC embedded driver data source factory service
 * 
 * @author mkeith
 */
public class EmbeddedDriverTests extends JdbcTests {
    
    public static String EMBEDDED_DRIVER_NAME = "org.apache.derby.jdbc.EmbeddedDriver";
    public static String JDBC_VERSION = "3.0";
    
    @BeforeClass
    public static void classSetUp() {
        log("In EmbeddedDriverTests setup");
        dsf = lookupDsf(EMBEDDED_DRIVER_NAME, JDBC_VERSION);
        log("Found DSF service - " + dsf);
    }

    @AfterClass
    public static void classCleanUp() {
        dsf = null;
    }

    Properties getDataSourceProperties() {
        Properties props = new Properties();
        props.put(DataSourceFactory.JDBC_DATABASE_NAME, "accountDB");
//        props.put(DataSourceFactory.JDBC_USER, "app");
//        props.put(DataSourceFactory.JDBC_PASSWORD, "app");
        return props;
    }

    Properties getUrlProperties() {
        Properties props = new Properties();
        props.put(DataSourceFactory.OSGI_JDBC_DRIVER_NAME,
                "org.apache.derby.jdbc.EmbeddedDriver");
        props.put(DataSourceFactory.JDBC_URL,
                "jdbc:derby:accountDB;create=true");
//        props.put(DataSourceFactory.JDBC_USER, "app");
//        props.put(DataSourceFactory.JDBC_PASSWORD, "app");
        return props;
    }
}

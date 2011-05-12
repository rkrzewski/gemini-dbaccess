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

import org.junit.*;
import org.osgi.service.jdbc.DataSourceFactory;

/**
 * Test class to test OSGi JDBC embedded driver data source factory service
 * 
 * @author mkeith
 */
public class EmbeddedDriverTest extends JdbcTests {
    
    public static String EMBEDDED_DRIVER_NAME = "org.apache.derby.jdbc.EmbeddedDriver";
    public static String JDBC_VERSION = "3.0";
   

	@BeforeClass
	public static void setUpClass() throws Exception {
		JdbcTests.setUpClass();
        log("In EmbeddedDriverTests setup");
        dsf = lookupDsf(EMBEDDED_DRIVER_NAME, JDBC_VERSION);
        log("Found DSF service - " + dsf);
    }

    @AfterClass
    public static void classCleanUp() {
        dsf = null;
    }

    protected Properties getDataSourceProperties() {
        Properties props = new Properties();
        props.put(DataSourceFactory.JDBC_DATABASE_NAME, "accountDB");
        return props;
    }

    protected Properties getUrlProperties() {
        Properties props = new Properties();
        props.put(DataSourceFactory.OSGI_JDBC_DRIVER_NAME,
                "org.apache.derby.jdbc.EmbeddedDriver");
        props.put(DataSourceFactory.JDBC_URL,
                "jdbc:derby:accountDB;create=true");
        return props;
    }
}

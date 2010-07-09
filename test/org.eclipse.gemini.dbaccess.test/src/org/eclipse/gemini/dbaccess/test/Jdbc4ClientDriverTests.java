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

import org.junit.AfterClass;
import org.junit.BeforeClass;

/**
 * Test class to test OSGi JDBC 4 client driver data source factory service
 * 
 * @author mkeith
 */
public class Jdbc4ClientDriverTests extends ClientDriverTests {
    
    public static String CLIENT_DRIVER_NAME = "org.apache.derby.jdbc.ClientDriver";
    public static String JDBC_4_VERSION = "4.0";

    @BeforeClass
    public static void classSetUp() {
        log("In Jdbc4ClientDriverTests setup");
        dsf = lookupDsf(CLIENT_DRIVER_NAME, JDBC_4_VERSION);
        log("Found DSF service - " + dsf);
    }

    @AfterClass
    public static void classCleanUp() {
        dsf = null;
    }
}

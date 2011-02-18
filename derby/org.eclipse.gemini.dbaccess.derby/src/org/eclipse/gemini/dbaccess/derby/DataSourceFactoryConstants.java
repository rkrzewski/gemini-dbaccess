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
 *     mkeith - Constants for Derby JDBC support 
 ******************************************************************************/

package org.eclipse.gemini.dbaccess.derby;

/**
 * Contants for Derby data source factory registration.
 */
public class DataSourceFactoryConstants  {

    // Register a service under each of the following driver class names
    public static final String DERBY_EMBEDDED_DRIVER_CLASS = "org.apache.derby.jdbc.EmbeddedDriver";
    public static final String DERBY_CLIENT_DRIVER_CLASS = "org.apache.derby.jdbc.ClientDriver";

    // Register all Derby factory services under this driver name
    public static final String DERBY_DRIVER_NAME = "Derby";

    // Register under the JDBC version the driver supports
    public static final String JDBC_3_DRIVER_VERSION = "3.0";
    public static final String JDBC_4_DRIVER_VERSION = "4.0";
}

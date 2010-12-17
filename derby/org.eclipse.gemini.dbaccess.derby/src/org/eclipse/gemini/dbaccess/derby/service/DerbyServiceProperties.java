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
 *     mkeith - Service property constants for Derby JDBC support 
 ******************************************************************************/

package org.eclipse.gemini.dbaccess.derby.service;

/**
 * Service property values for Derby data source factory services.
 */
public class DerbyServiceProperties {

    // Register a service under each of the following driver class names
    public static final String DERBY_EMBEDDED_DRIVER_CLASS = "org.apache.derby.jdbc.EmbeddedDriver";
    public static final String DERBY_CLIENT_DRIVER_CLASS = "org.apache.derby.jdbc.ClientDriver";

    // All Derby DataSourceFactory services will have their
    // DataSourceFactory.OSGI_JDBC_DRIVER_NAME property set to this driver name
    public static final String DERBY_DRIVER_NAME = "Derby";

    // Derby DataSourceFactory service will have its 
    // DataSourceFactory.OSGI_JDBC_DRIVER_VERSION property
    // set to the JDBC version it supports
    public static final String JDBC_3_DRIVER_VERSION = "3.0";
    public static final String JDBC_4_DRIVER_VERSION = "4.0";
}

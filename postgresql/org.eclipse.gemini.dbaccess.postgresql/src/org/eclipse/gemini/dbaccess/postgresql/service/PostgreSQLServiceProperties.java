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
 *     tware - Added MySQL constants
 *     mkeith - Refactorization
 *     rafal.krzewski@caltha.pl - PostgreSQL support
 ******************************************************************************/

package org.eclipse.gemini.dbaccess.postgresql.service;

/**
 * Service property values for MySQL data source factory services.
 */
public class PostgreSQLServiceProperties {

    // Register data source factory service under the driver class name
    public static final String DRIVER_CLASS = "org.postgresql.Driver";

    // All PostgreSQL DataSourceFactory services will have their
    // DataSourceFactory.OSGI_JDBC_DRIVER_NAME service property set to this driver name
    public static final String DRIVER_NAME = "PostgreSQL";
    
    // DataSourceFactory.OSGI_JDBC_DRIVER_VERSION service property will be set to the version
    // of the bundle that provides this package
    public static final String DRIVER_PACKAGE = "org.postgresql";

}

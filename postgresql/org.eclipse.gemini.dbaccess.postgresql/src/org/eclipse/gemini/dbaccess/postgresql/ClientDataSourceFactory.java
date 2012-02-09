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
 *     mkeith - Template for JDBC driver support
 *     tware - MySQL driver class additions
 *     rafal.krzewski@caltha.pl - PostgreSQL support
 ******************************************************************************/

package org.eclipse.gemini.dbaccess.postgresql;

import java.sql.Driver;
import java.sql.SQLException;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;
import javax.sql.XADataSource;

import org.eclipse.gemini.dbaccess.AbstractDataSourceFactory;
import org.postgresql.ds.PGConnectionPoolDataSource;
import org.postgresql.ds.PGSimpleDataSource;
import org.postgresql.xa.PGXADataSource;

/**
 * A factory for creating PostgreSQL data sources. The properties specified
 * in the create methods determine how the created object is configured.
 *
 * This service also supports a URL-based data source. 
 */
public class ClientDataSourceFactory extends AbstractDataSourceFactory {
    
    public ClientDataSourceFactory() {}

    @Override
    public Driver newJdbcDriver() throws SQLException {
        return new org.postgresql.Driver();
    }

    @Override
    public DataSource newDataSource() throws SQLException {
        return new PGSimpleDataSource();
    }

    @Override
    public ConnectionPoolDataSource newConnectionPoolDataSource() 
            throws SQLException {
        return new PGConnectionPoolDataSource();
    }

    @Override
    public XADataSource newXADataSource() throws SQLException {
        return new PGXADataSource();
    }
}

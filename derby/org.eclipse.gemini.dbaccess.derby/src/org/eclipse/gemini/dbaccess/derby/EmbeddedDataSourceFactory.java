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
 *     JJ Snyder - Embedded Derby JDBC support 
 *     mkeith - Inherit from abstract class
 ******************************************************************************/

package org.eclipse.gemini.dbaccess.derby;

import java.sql.Driver;
import java.sql.SQLException;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;
import javax.sql.XADataSource;

import org.apache.derby.jdbc.EmbeddedDriver;
import org.apache.derby.jdbc.EmbeddedDataSource;
import org.apache.derby.jdbc.EmbeddedDataSource40;
import org.apache.derby.jdbc.EmbeddedConnectionPoolDataSource;
import org.apache.derby.jdbc.EmbeddedConnectionPoolDataSource40;
import org.apache.derby.jdbc.EmbeddedXADataSource;
import org.apache.derby.jdbc.EmbeddedXADataSource40;

import org.eclipse.gemini.dbaccess.AbstractDataSourceFactory;

/**
 * A factory for creating Derby embedded data sources. The properties specified
 * in the create methods determine how the created object is configured.
 * 
 * The following sample code shows how to get an EmbeddedDataSource from a Derby 
 * JDBC service:
 * 
 * ServiceTracker tracker = new ServiceTracker( 
 *        context, DataSourceFactory.class.getName(), null); 
 * tracker.open();
 * DataSourceFactory dsf = (DataSourceFactory) tracker.getService(); 
 * Properties props = new Properties();
 * props.put(DataSourceFactory.JDBC_DATABASE_NAME,
 *           "C:\\Software\\db-derby-10.4.2.0-bin\\testdbs\\firstdb");
 * DataSource ds = dsf.createDataSource(props);
 * 
 * This service also supports a URL-based data source. This means a URL property
 * can be provided instead of the properties listed above:
 * 
 *     props.put(DataSourceFactory.JDBC_URL, "jdbc:derby:myDB");
 */
public class EmbeddedDataSourceFactory extends AbstractDataSourceFactory {

    /** Option to indicate whether to use JDBC 4.0 flavor of the driver */
    boolean jdbc4 = true;
    
    public EmbeddedDataSourceFactory() {}
    public EmbeddedDataSourceFactory(boolean jdbc4) {
        this.jdbc4 = jdbc4;    
    }
    
    @Override
    public Driver newJdbcDriver() throws SQLException {
        return new EmbeddedDriver();
    }

    @Override
    public DataSource newDataSource() throws SQLException {
        return jdbc4
            ? new EmbeddedDataSource40()
            : new EmbeddedDataSource();
    }

    @Override
    public ConnectionPoolDataSource newConnectionPoolDataSource() 
            throws SQLException {
        return jdbc4 
            ? new EmbeddedConnectionPoolDataSource40()
            : new EmbeddedConnectionPoolDataSource();
    }

    @Override
    public XADataSource newXADataSource() throws SQLException {
        return jdbc4 
            ? new EmbeddedXADataSource40()
            : new EmbeddedXADataSource();
    }
}

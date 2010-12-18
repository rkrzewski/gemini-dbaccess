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
 *     mkeith - Client/Server Derby JDBC support 
 ******************************************************************************/

package org.eclipse.gemini.dbaccess.derby;

import java.sql.Driver;
import java.sql.SQLException;

import javax.sql.DataSource;
import javax.sql.ConnectionPoolDataSource;
import javax.sql.XADataSource;

import org.eclipse.gemini.dbaccess.AbstractDataSourceFactory;

import org.apache.derby.jdbc.ClientConnectionPoolDataSource;
import org.apache.derby.jdbc.ClientConnectionPoolDataSource40;
import org.apache.derby.jdbc.ClientDriver;
import org.apache.derby.jdbc.ClientDataSource;
import org.apache.derby.jdbc.ClientDataSource40;
import org.apache.derby.jdbc.ClientXADataSource;
import org.apache.derby.jdbc.ClientXADataSource40;

/**
 * A factory for creating Derby network data sources. The properties specified
 * in the create methods determine how the created object is configured.
 * 
 * Sample code for obtaining a Derby network data source:
 * 
 *     ServiceTracker tracker = 
 *         new ServiceTracker(context, DataSourceFactory.class.getName(), null); 
 *     tracker.open();
 *     DataSourceFactory dsf = (DataSourceFactory) tracker.getService(); 
 *     Properties props = new Properties();
 *     props.put(DataSourceFactory.JDBC_SERVER_NAME, "localhost");
 *     props.put(DataSourceFactory.JDBC_PORT_NUMBER, "1527");
 *     props.put(DataSourceFactory.JDBC_DATABASE_NAME, "myDB");
 *     props.put(DataSourceFactory.JDBC_USER, "mike");
 *     props.put(DataSourceFactory.JDBC_PASSWORD, "password");
 *     DataSource ds = dsf.createDataSource(props);
 *
 * This service also supports a URL-based data source. The following 3 properties
 * can be provided instead of the 5 properties above:
 * 
 *     props.put(DataSourceFactory.JDBC_URL, "jdbc:derby://localhost:1527/myDB");
 *     props.put(DataSourceFactory.JDBC_USER, "mike");
 *     props.put(DataSourceFactory.JDBC_PASSWORD, "password");
 */
public class ClientDataSourceFactory extends AbstractDataSourceFactory {
    
    /** Option to indicate whether to use JDBC 4.0 flavor of the driver */
    boolean jdbc4 = true;

    public ClientDataSourceFactory() {}
    public ClientDataSourceFactory(boolean jdbc4) {
        this.jdbc4 = jdbc4;    
    }

    public Driver newJdbcDriver() throws SQLException {
        return new ClientDriver();
    }

    public DataSource newDataSource() throws SQLException {
        return jdbc4
            ? new ClientDataSource40()
            : new ClientDataSource();
    }

    public ConnectionPoolDataSource newConnectionPoolDataSource() 
            throws SQLException {
        return jdbc4 
            ? new ClientConnectionPoolDataSource40()
            : new ClientConnectionPoolDataSource();
    }

    public XADataSource newXADataSource() throws SQLException {
        return jdbc4 
            ? new ClientXADataSource40()
            : new ClientXADataSource();
    }
}
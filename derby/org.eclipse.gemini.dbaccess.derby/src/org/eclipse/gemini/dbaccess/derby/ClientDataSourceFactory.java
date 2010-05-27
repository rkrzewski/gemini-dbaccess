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
 *     mkeith - CLient/Server Derby JDBC support 
 ******************************************************************************/

package org.eclipse.gemini.dbaccess.derby;

import java.util.Properties;

import java.sql.Driver;
import java.sql.SQLException;

import javax.sql.DataSource;
import javax.sql.ConnectionPoolDataSource;
import javax.sql.XADataSource;

import org.apache.derby.jdbc.ClientConnectionPoolDataSource;
import org.apache.derby.jdbc.ClientConnectionPoolDataSource40;
import org.apache.derby.jdbc.ClientDriver;
import org.apache.derby.jdbc.ClientDataSource;
import org.apache.derby.jdbc.ClientDataSource40;
import org.apache.derby.jdbc.ClientXADataSource;
import org.apache.derby.jdbc.ClientXADataSource40;

import org.osgi.service.jdbc.DataSourceFactory;

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
    
    public ClientDataSourceFactory() {}
    public ClientDataSourceFactory(boolean jdbc4) {
        this.jdbc4 = jdbc4;    
    }
    
    /**
     * Create a Derby DataSource object.
     * 
     * @param props The properties that define the DataSource implementation to
     *        create and how the DataSource is configured.
     * @return The configured DataSource.
     * @throws SQLException
     * @see org.osgi.service.jdbc.DataSourceFactory#createDataSource(java.util.Properties)
     */
    public DataSource createDataSource(Properties props) throws SQLException {
        if (props == null) props = new Properties();
        if (props.get(DataSourceFactory.JDBC_URL) != null) {
            return new UrlBasedDriverDataSource(props, false);
        } else {
            DataSource dataSource = (jdbc4) 
                ? new ClientDataSource40()
                : new ClientDataSource();
            setDataSourceProperties(dataSource, props);
            return dataSource;
        }
    }

    /**
     * Create a Derby ConnectionPoolDataSource object.
     * 
     * @param props The properties that define the ConnectionPoolDataSource
     *        implementation to create and how the ConnectionPoolDataSource is
     *        configured.
     * @return The configured ConnectionPoolDataSource.
     * @throws SQLException
     * @see org.osgi.service.jdbc.DataSourceFactory#createConnectionPoolDataSource(java.util.Properties)
     */
    public ConnectionPoolDataSource createConnectionPoolDataSource(Properties props) throws SQLException {
        if (props == null) props = new Properties();
        ConnectionPoolDataSource dataSource = (jdbc4) 
            ? new ClientConnectionPoolDataSource40()
            : new ClientConnectionPoolDataSource();
        setDataSourceProperties(dataSource, props);
        return dataSource;
    }

    /**
     * Create a Derby XADataSource object.
     * 
     * @param props The properties that define the XADataSource implementation
     *        to create and how the XADataSource is configured.
     * @return The configured XADataSource.
     * @throws SQLException
     * @see org.osgi.service.jdbc.DataSourceFactory#createXADataSource(java.util.Properties)
     */
    public XADataSource createXADataSource(Properties props) throws SQLException {
        if (props == null) props = new Properties();
        XADataSource dataSource = (jdbc4) 
            ? new ClientXADataSource40()
            : new ClientXADataSource();
        setDataSourceProperties(dataSource, props);
        return dataSource;
    }

    /**
     * Create a new org.apache.derby.jdbc.EmbeddedDriver.
     * 
     * @param props The properties used to configure the Driver.  Null 
     *              indicates no properties.
     *              If the property cannot be set on the Driver being 
     *              created then a SQLException must be thrown.
     * @return A configured org.apache.derby.jdbc.EmbeddedDriver.
     * @throws SQLException If the org.apache.derby.jdbc.ClientDriver cannot be created.
     */
    public Driver createDriver(Properties props) throws SQLException {
        // Properties not used when accessing the raw driver.
        Driver driver = new ClientDriver();
        setDataSourceProperties(driver, props);
        return driver;
    }  
}
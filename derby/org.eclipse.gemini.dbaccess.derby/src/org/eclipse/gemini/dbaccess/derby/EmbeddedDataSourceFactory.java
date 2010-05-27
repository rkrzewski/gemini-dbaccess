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
 ******************************************************************************/

package org.eclipse.gemini.dbaccess.derby;

import java.util.Properties;

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
import org.osgi.service.jdbc.DataSourceFactory;

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
    
    public EmbeddedDataSourceFactory() {}
    public EmbeddedDataSourceFactory(boolean jdbc4) {
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
		if (props == null) {
			props = new Properties();
		}
        if (props.get(DataSourceFactory.JDBC_URL) != null) {
            return new UrlBasedDriverDataSource(props, true);
        } else {
    		DataSource dataSource = (jdbc4) 
                ? new EmbeddedDataSource40()
                : new EmbeddedDataSource();
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
	public ConnectionPoolDataSource createConnectionPoolDataSource(
			Properties props) throws SQLException {
		if (props == null) {
			props = new Properties();
		}
		ConnectionPoolDataSource dataSource = (jdbc4) 
		    ? new EmbeddedConnectionPoolDataSource40()
	        : new EmbeddedConnectionPoolDataSource();
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
	public XADataSource createXADataSource(Properties props)
			throws SQLException {
		if (props == null) {
			props = new Properties();
		}
        XADataSource dataSource = (jdbc4) 
            ? new EmbeddedXADataSource40()
            : new EmbeddedXADataSource();
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
     * @throws SQLException If the org.apache.derby.jdbc.EmbeddedDriver cannot be created.
     */
	public Driver createDriver(Properties props) throws SQLException {
		if (props == null) {
			props = new Properties();
		}
		EmbeddedDriver driver = new EmbeddedDriver();
		setDataSourceProperties(driver, props);
		return driver;
	}

}

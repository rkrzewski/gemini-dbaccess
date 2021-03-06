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

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;
import javax.sql.PooledConnection;
import javax.sql.XAConnection;
import javax.sql.XADataSource;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.jdbc.DataSourceFactory;

import org.junit.*;

import static org.junit.Assert.*;

/**
 * Abstract test class to test OSGi JDBC data source factory services
 * 
 * @author mkeith
 */
public abstract class JdbcTests {
    
    protected static BundleContext context;    
    protected static DataSourceFactory dsf;
    
    //-------------------------------------------
    // Test classes must override these methods
    //-------------------------------------------
    protected abstract Properties getUrlProperties();
    protected abstract Properties getDataSourceProperties();
	private static final String ORG_ECLIPSE_GEMINI_DBACCESS = "org.eclipse.gemini.dbaccess.derby";


	@BeforeClass
	public static void setUpClass() throws Exception {
		context = FrameworkUtil.getBundle(JdbcTests.class).getBundleContext();
		assertNotNull("Bundle context is null", context);
		startBundle(ORG_ECLIPSE_GEMINI_DBACCESS);
	}

    //-------------------------------------------
    // Test methods
    //-------------------------------------------
    @Test
    public void testURLDataSourceFactory() throws Exception {
        log("testURLDataSource");
        DataSource ds = dsf.createDataSource(getUrlProperties());
        assert(ds != null);
        log("Created DS - " + ds);
        log("Getting connection");
        Connection con = ds.getConnection();
        assert(con != null);
        log("Got connection - " + con);
        
        this.executeJdbcStatement(con);
        con.close();
    }

    @Test
    public void testVanillaDataSourceFactory() throws Exception {
        log("testVanillaDataSource");
        DataSource ds = dsf.createDataSource(getDataSourceProperties());
        assert(ds != null);
        log("Created DS - " + ds);
        log("Getting connection");
        Connection con = ds.getConnection();
        assert(con != null);
        log("Got connection - " + con);
        
        this.executeJdbcStatement(con);
        con.close();
    }

    @Test
    public void testPooledDataSourceFactory() throws Exception {
        log("testPooledDataSource");
        ConnectionPoolDataSource ds = dsf.createConnectionPoolDataSource(getDataSourceProperties());
        assert(ds != null);
        log("Created DS - " + ds);
        log("Getting connection");
        PooledConnection pcon = ds.getPooledConnection();
        assert(pcon != null);
        log("Got pooled connection - " + pcon);
        Connection con = pcon.getConnection();
        assert(con != null);
        this.executeJdbcStatement(con);
        con.close();
        pcon.close();
    }

    @Test
    public void testXADataSourceFactory() throws Exception {
        log("testXADataSource");
        XADataSource ds = dsf.createXADataSource(getDataSourceProperties());
        assert(ds != null);
        log("Created DS - " + ds);
        log("Getting connection");
        XAConnection xa_con = ds.getXAConnection();
        assert(xa_con != null);
        log("Got XA connection - " + xa_con);
        Connection con = xa_con.getConnection();
        assert(con != null);
        this.executeJdbcStatement(con);
        con.close();
        xa_con.close();
    }

    @Test(expected=SQLException.class)
    public void testErrorCase1() throws Exception {
        log("testErrorCase");
        try {
            DataSource ds = dsf.createDataSource(getErrorProperties());
            ds.getConnection();
        } catch (SQLException sqlEx) {
            log("Caught expected exception: " + sqlEx);
            throw sqlEx;
        }
    }

    //-------------------------------------------
    // Helper methods
    //-------------------------------------------

    private Properties getErrorProperties() {
        Properties props = new Properties();
        props.put("errorProperty", "errorValue");
        return props;
    }
    
    private void executeJdbcStatement(Connection con) throws Exception {
        Statement stmt = con.createStatement();
        boolean result = stmt.execute("SET CURRENT SCHEMA USER");
        log("SQL result: " + result);
        stmt.close();
    }

    protected static DataSourceFactory lookupDsf(String driverName, String version) {
        log("Lookup (" + driverName + ", " + version + ")");
        String filter = "(&("+DataSourceFactory.OSGI_JDBC_DRIVER_CLASS+"="+driverName+")("+
                         DataSourceFactory.OSGI_JDBC_DRIVER_VERSION+"="+version+"))";
        log("Filter is: " + filter);
        ServiceReference[] refs = null;
        try {
            refs = getContext().getServiceReferences(DataSourceFactory.class.getName(), filter);
        } catch (InvalidSyntaxException isEx) {
            new RuntimeException("Bad filter", isEx);
        }
        log("DSF Service refs looked up from registry: " + refs);
        return (refs == null)
            ? null
            : (DataSourceFactory) getContext().getService(refs[0]);
    }
    
	private static BundleContext getContext() {
		return context;
	}
    
    protected static void log(String msg) {
        System.out.println("*** JdbcTest: " + msg);
    }
    
	private static void startBundle(String name) throws Exception {
		Bundle bundle = getBundle(name);
		Assert.assertNotNull("Could not find bundle [" + name + "]", bundle);
		if (bundle.getState() != Bundle.ACTIVE) {
			bundle.start();
		}
	}
	
	private static Bundle getBundle(String symbolicName) {
		Bundle[] bundles = context.getBundles();
		for (Bundle bundle : bundles) {
			if (bundle.getSymbolicName().equals(symbolicName)) {
				return bundle;
			}
		}
		return null;
	}
    
}

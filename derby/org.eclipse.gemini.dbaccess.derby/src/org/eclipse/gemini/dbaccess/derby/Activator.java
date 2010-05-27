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
 *     mkeith - Client/server support
 ******************************************************************************/

package org.eclipse.gemini.dbaccess.derby;

import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import org.osgi.service.jdbc.DataSourceFactory;

/**
 * Creates a {@link DataSourceFactory} for each of the available Derby JDBC driver types.
 */
public class Activator implements BundleActivator {
    
    private ServiceRegistration embeddedService, clientService, embeddedService4, clientService4;	
	
    public void start(BundleContext context) throws Exception {
        
        Hashtable props = new Hashtable();
        props.put(DataSourceFactory.OSGI_JDBC_DRIVER_NAME, DataSourceFactoryConstants.DERBY_DRIVER_NAME);
        
        /* Register the JDBC 3 drivers */
        props.put(DataSourceFactory.OSGI_JDBC_DRIVER_VERSION, DataSourceFactoryConstants.JDBC_3_DRIVER_VERSION);
        
        // Register the embedded driver
        props.put(DataSourceFactory.OSGI_JDBC_DRIVER_CLASS, DataSourceFactoryConstants.DERBY_EMBEDDED_DRIVER_CLASS);
        embeddedService = context.registerService( 
                DataSourceFactory.class.getName(),
                new EmbeddedDataSourceFactory(false), 
                props);
        
        // Register the client driver 
        props.put(DataSourceFactory.OSGI_JDBC_DRIVER_CLASS, DataSourceFactoryConstants.DERBY_CLIENT_DRIVER_CLASS);
        clientService = context.registerService( 
                DataSourceFactory.class.getName(),
                new ClientDataSourceFactory(false),
                props);

        /* Register the JDBC 4 drivers */
        props.put(DataSourceFactory.OSGI_JDBC_DRIVER_VERSION, DataSourceFactoryConstants.JDBC_4_DRIVER_VERSION);
        
        // Register the embedded driver
        props.put(DataSourceFactory.OSGI_JDBC_DRIVER_CLASS, DataSourceFactoryConstants.DERBY_EMBEDDED_DRIVER_CLASS);
        embeddedService4 = context.registerService( 
                DataSourceFactory.class.getName(),
                new EmbeddedDataSourceFactory(true), 
                props);
        
        // Register the client driver 
        props.put(DataSourceFactory.OSGI_JDBC_DRIVER_CLASS, DataSourceFactoryConstants.DERBY_CLIENT_DRIVER_CLASS);
        clientService4 = context.registerService( 
                DataSourceFactory.class.getName(),
                new ClientDataSourceFactory(true),
                props);   
    }

    public void stop(BundleContext context) throws Exception {
        if (embeddedService != null) {
            embeddedService.unregister();
        }
        if (clientService != null) {
            clientService.unregister();
        }
        if (embeddedService4 != null) {
            embeddedService4.unregister();
        }
        if (clientService4 != null) {
            clientService4.unregister();
        }
    }
}

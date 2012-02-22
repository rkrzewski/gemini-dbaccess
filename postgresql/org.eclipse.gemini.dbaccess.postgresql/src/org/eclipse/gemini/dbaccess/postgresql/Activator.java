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

import java.util.Hashtable;

import org.eclipse.gemini.dbaccess.postgresql.service.PostgreSQLServiceProperties;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.jdbc.DataSourceFactory;
import org.osgi.service.packageadmin.ExportedPackage;
import org.osgi.service.packageadmin.PackageAdmin;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Creates a {@link DataSourceFactory} for PostgreSQL JDBC driver.
 */
public class Activator implements BundleActivator {

	private ServiceRegistration dsfService;

	private ServiceTracker packageAdminTracker;

	public void start(BundleContext context) throws Exception {
		System.out.println("Gemini DBAccess - PostgreSQL JDBC starting");
		Hashtable<String, String> props = new Hashtable<String, String>();
		props.put(DataSourceFactory.OSGI_JDBC_DRIVER_NAME,
				PostgreSQLServiceProperties.DRIVER_NAME);
		props.put(DataSourceFactory.OSGI_JDBC_DRIVER_CLASS,
				PostgreSQLServiceProperties.DRIVER_CLASS);
		Bundle driverBundle = findProviderOfPackage(context,
				PostgreSQLServiceProperties.DRIVER_PACKAGE);
		packageAdminTracker = new ServiceTracker(context,
				PackageAdmin.class.getName(), null);
		packageAdminTracker.open();
		if (driverBundle != null) {
			props.put(DataSourceFactory.OSGI_JDBC_DRIVER_VERSION, driverBundle
					.getVersion().toString());
		}

		dsfService = context.registerService(
				new String[] { DataSourceFactory.class.getName() },
				new ClientDataSourceFactory(), props);

	}

	public void stop(BundleContext context) throws Exception {
		packageAdminTracker.close();
		if (dsfService != null) {
			dsfService.unregister();
		}
	}

	private Bundle findProviderOfPackage(BundleContext context,
			String driverPackage) {
		PackageAdmin packageAdmin = (PackageAdmin) packageAdminTracker
				.getService();
		if (packageAdmin != null) {
			ExportedPackage[] expPkgs = packageAdmin
					.getExportedPackages(driverPackage);
			for (ExportedPackage expPkg : expPkgs) {
				for (Bundle impBnd : expPkg.getImportingBundles()) {
					if (impBnd.equals(context.getBundle())) {
						return expPkg.getExportingBundle();
					}
				}
			}
		}
		return null;
	}
}

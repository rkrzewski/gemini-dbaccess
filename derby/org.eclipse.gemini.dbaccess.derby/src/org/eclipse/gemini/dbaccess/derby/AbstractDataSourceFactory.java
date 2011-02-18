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

import java.lang.reflect.Method;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;
import javax.sql.XADataSource;

import org.osgi.service.jdbc.DataSourceFactory;

/**
 * Abstract behavior for Derby data source factories.
 */
public abstract class AbstractDataSourceFactory implements DataSourceFactory {

    /** Option to indicate whether to use JDBC 4.0 flavor of the driver */
    boolean jdbc4 = true;

    /***************************************************
     * Abstract methods must be implemented by subclass 
     ***************************************************/

    public abstract DataSource createDataSource(Properties props) throws SQLException;

    public abstract ConnectionPoolDataSource createConnectionPoolDataSource(Properties props) throws SQLException;

    public abstract XADataSource createXADataSource(Properties props) throws SQLException;

    public abstract Driver createDriver(Properties props) throws SQLException;

    /************************************
     * Methods inherited by subclasses
     ***********************************/

	protected void setDataSourceProperties(Object object, Properties props)
			throws SQLException {

	    if (props == null) return;
	    
		Enumeration<?> enumeration = props.keys();
		while (enumeration.hasMoreElements()) {
			String name = (String) enumeration.nextElement();
			setProperty(object, name, props.getProperty(name));
		}
	}

	protected void throwSQLException(Exception cause, String theType, String value)
			throws SQLException {
		SQLException sqlException = new SQLException("Invalid " + theType
				+ " value: " + value);
		sqlException.initCause(cause);
		throw sqlException;
	}

	protected Object toBasicType(String value, String type) throws SQLException {
		if (value == null) {
			return null;
		}
		else
			if (type == null || type.equals(String.class.getName())) {
				return value;
			}
			else
				if (type.equals(Integer.class.getName())
						|| type.equals(int.class.getName())) {
					try {
						return Integer.valueOf(value);
					}
					catch (NumberFormatException e) {
						throwSQLException(e, "Integer", value);
					}
				}
				else
					if (type.equals(Float.class.getName())
							|| type.equals(float.class.getName())) {
						try {
							return Float.valueOf(value);
						}
						catch (NumberFormatException e) {
							throwSQLException(e, "Float", value);
						}
					}
					else
						if (type.equals(Long.class.getName())
								|| type.equals(long.class.getName())) {
							try {
								return Long.valueOf(value);
							}
							catch (NumberFormatException e) {
								throwSQLException(e, "Long", value);
							}
						}
						else
							if (type.equals(Double.class.getName())
									|| type.equals(double.class.getName())) {
								try {
									return Double.valueOf(value);
								}
								catch (NumberFormatException e) {
									throwSQLException(e, "Double", value);
								}
							}
							else
								if (type.equals(Character.class.getName())
										|| type.equals(char.class.getName())) {
									if (value.length() != 1) {
										throw new SQLException(
												"Invalid Character value: "
														+ value);
									}

									return Character.valueOf(value.charAt(0)); 
								}
								else
									if (type.equals(Byte.class.getName())
											|| type
													.equals(byte.class
															.getName())) {
										try {
											return Byte.valueOf(value);
										}
										catch (NumberFormatException e) {
											throwSQLException(e, "Byte", value);
										}
									}
									else
										if (type.equals(Short.class.getName())
												|| type.equals(short.class
														.getName())) {
											try {
												return Short.valueOf(value);
											}
											catch (NumberFormatException e) {
												throwSQLException(e, "Short",
														value);
											}
										}
										else
											if (type.equals(Boolean.class
													.getName())
													|| type
															.equals(boolean.class
																	.getName())) {
												try {
													return Boolean
															.valueOf(value);
												}
												catch (NumberFormatException e) {
													throwSQLException(e,
															"Boolean", value);
												}
											}
											else {
												throw new SQLException(
														"Invalid property type: "
																+ type);
											}
		return null;
	}

	protected void setProperty(Object object, String name, String value)
			throws SQLException {
		Class<?> type = object.getClass();

		java.beans.PropertyDescriptor[] descriptors;
		try {
			descriptors = java.beans.Introspector.getBeanInfo(type)
					.getPropertyDescriptors();
		}
		catch (Exception exc) {
			SQLException sqlException = new SQLException();
			sqlException.initCause(exc);
			throw sqlException;
		}
		List<String> names = new ArrayList<String>();

		for (int i = 0; i < descriptors.length; i++) {
			if (descriptors[i].getWriteMethod() == null) {
				continue;
			}

			if (descriptors[i].getName().equals(name)) {
				Method method = descriptors[i].getWriteMethod();
				Class<?> paramType = method.getParameterTypes()[0];
				Object param = toBasicType(value, paramType.getName());

				try {
					method.invoke(object, new Object[] {param});
				}
				catch (Exception exc) {
					SQLException sqlException = new SQLException();
					sqlException.initCause(exc);
					throw sqlException;
				}
				return;
			}

			names.add(descriptors[i].getName());
		}
		throw new SQLException("No such property: " + name
				+ ", exists.  Writable properties are: " + names);
	}
}

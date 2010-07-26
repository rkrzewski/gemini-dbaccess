
Gemini DBAccess Milestone M1 - July 26, 2010

This milestone provides you with database access to the server Derby database version 10.5.1. 
To access other versions of Derby you may need to replace the derby.jar and derbyclient.jar 
driver jars inside the org.apache.derby bundle with the version that you require.

For an example of how to access a database connection from an OSGi program run the sample program.
To do this, simply install and start the following bundles that are included in this distribution:

org.apache.derby
osgi.enterprise
org.eclipse.gemini.dbaccess.derby
org.eclipse.gemini.dbaccess.samples

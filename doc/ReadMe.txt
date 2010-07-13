Getting Started with Gemini DBAccess 
------------------------------------

Gemini DBAccess provides a way for a database to be accessed through JDBC in a modular environment like OSGi.


Prerequisites
-------------

To run DBAccess in OSGi you will need to have the following:

1. Database and JDBC driver

You should have installed and started your database server. DBAccess bundles do not start databases or servers. 
It is assumed that such servers are already running. You must also have the JDBC driver jars. If the driver is
not already OSGi-ready then you may need to create a bundle (see the User Guide for how to do this).

2. OSGi Enterprise API bundle

Some of the OSGi Enterprise APIs are used by DBAccess so the osgi.enterprise bundle must be resident. It normally 
includes both the source and the class files so it can be used for both execution and debugging.

3. The DBAccess bundles

There are typically two bundles for each supported database. The first bundle is the JDBC driver bundle 
and includes the JDBC driver code. If a driver is already OSGi-ready then it may already be bundlized
appropriately. If the driver is not already in an appropriate OSGi bundle then DBAccess will either provide 
one, or explain how to create one, depending upon whether the driver JARs may be distributed or not.

The second bundle is a DBAccess bundle that contains all of the logic to implement and register the 
DataSDourcefactory service as specified by the OSGi JDBC specification.


Installation
------------


Configuration
-------------


Execution
---------

If you use Plugin Development Environment (PDE) then load all of the bundles into your workspace:





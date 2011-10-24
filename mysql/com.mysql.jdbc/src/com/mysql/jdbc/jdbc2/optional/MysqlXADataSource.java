package com.mysql.jdbc.jdbc2.optional;

import java.io.PrintWriter;
import java.sql.SQLException;

import javax.sql.XAConnection;
import javax.sql.XADataSource;

public class MysqlXADataSource implements XADataSource {

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public XAConnection getXAConnection() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public XAConnection getXAConnection(String arg0, String arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}

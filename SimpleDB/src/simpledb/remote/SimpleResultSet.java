package simpledb.remote;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * <p>Title:simpledb.remote.MyProject</p>
 * <p>
 * <p>Description: </p>
 * <p>
 * <p>Copyright: Copyright (c) 2001-2013 Newland SoftWare Company</p>
 * <p>
 * <p>Company: Newland SoftWare Company</p>
 *
 * @author Steven
 * @version 1.0 CreateTime：2017/12/17 19:20
 */
public class SimpleResultSet extends ResultSetAdapter{
	private RemoteResultSet rrs;

	public SimpleResultSet(RemoteResultSet s) {
		rrs = s;
	}

	public boolean next() throws SQLException {
		try {
			return rrs.next();
		}
		catch (Exception e) {
			throw new SQLException(e);
		}
	}

	public int getInt(String fldname) throws SQLException {
		try {
			return rrs.getInt(fldname);
		}
		catch (Exception e) {
			throw new SQLException(e);
		}
	}

	public String getString(String fldname) throws SQLException {
		try {
			return rrs.getString(fldname);
		}
		catch (Exception e) {
			throw new SQLException(e);
		}
	}

	public ResultSetMetaData getMetaData() throws SQLException {
		try {
			RemoteMetaData rmd = rrs.getMetaData();
			return new SimpleMetaData(rmd);
		}
		catch (Exception e) {
			throw new SQLException(e);
		}
	}

	public void close() throws SQLException {
		try {
			rrs.close();
		}
		catch (Exception e) {
			throw new SQLException(e);
		}
	}
}
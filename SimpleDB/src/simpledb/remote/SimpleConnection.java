package simpledb.remote;

import java.sql.SQLException;
import java.sql.Statement;

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
 * @version 1.0 CreateTime：2017/12/17 19:05
 */
public class SimpleConnection extends ConnectionAdapter{
	private RemoteConnection rconn;

	public SimpleConnection(RemoteConnection c) {
		rconn = c;
	}

	public Statement createStatement() throws SQLException {
		try {
			RemoteStatement rstmt = rconn.createStatement();
			return new SimpleStatement(rstmt);
		}
		catch(Exception e) {
			throw new SQLException(e);
		}
	}

	public void close() throws SQLException {
		try {
			rconn.close();
		}
		catch(Exception e) {
			throw new SQLException(e);
		}
	}
}

package simpledb.remote;

import simpledb.query.Plan;
import simpledb.query.Scan;
import simpledb.record.Schema;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

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
 * @version 1.0 CreateTime：2017/12/17 18:46
 */
public class RemoteResultSetImpl extends UnicastRemoteObject implements RemoteResultSet{
	private Scan s;
	private Schema sch;
	private RemoteConnectionImpl rconn;

	/**
	 * Creates a RemoteResultSet object.
	 * The specified plan is opened, and the scan is saved.
	 * @param plan the query plan
	 * @param rconn TODO
	 * @throws RemoteException
	 */
	public RemoteResultSetImpl(Plan plan, RemoteConnectionImpl rconn) throws RemoteException {
		s = plan.open();
		sch = plan.schema();
		this.rconn = rconn;
	}

	/**
	 * Moves to the next record in the result set,
	 * by moving to the next record in the saved scan.
	 * @see simpledb.remote.RemoteResultSet#next()
	 */
	public boolean next() throws RemoteException {
		try {
			return s.next();
		}
		catch(RuntimeException e) {
			rconn.rollback();
			throw e;
		}
	}

	/**
	 * Returns the integer value of the specified field,
	 * by returning the corresponding value on the saved scan.
	 * @see simpledb.remote.RemoteResultSet#getInt(java.lang.String)
	 */
	public int getInt(String fldname) throws RemoteException {
		try {
			fldname = fldname.toLowerCase(); // to ensure case-insensitivity
			return s.getInt(fldname);
		}
		catch(RuntimeException e) {
			rconn.rollback();
			throw e;
		}
	}

	/**
	 * Returns the integer value of the specified field,
	 * by returning the corresponding value on the saved scan.
	 * @see simpledb.remote.RemoteResultSet#getInt(java.lang.String)
	 */
	public String getString(String fldname) throws RemoteException {
		try {
			fldname = fldname.toLowerCase(); // to ensure case-insensitivity
			return s.getString(fldname);
		}
		catch(RuntimeException e) {
			rconn.rollback();
			throw e;
		}
	}
	@Override
	public RemoteMetaData getMetaDate() throws RemoteException {
		return null;
	}

	@Override
	public void close() throws RemoteException {

	}
}

package simpledb.remote;

import simpledb.tx.Transaction;

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
 * @version 1.0 CreateTime：2017/12/17 18:26
 */
public class RemoteConnectionImpl extends UnicastRemoteObject implements RemoteConnection{
	private Transaction tx;

	/**
	 * Creates a remote connection
	 * and begins a new transaction for it.
	 * @throws RemoteException
	 */
	RemoteConnectionImpl() throws RemoteException {
		tx = new Transaction();
	}

	@Override
	public RemoteStatement createStatement() throws RemoteException {
		return new RemoteStatementImpl(this);
	}

	@Override
	public void close() throws RemoteException {
		tx.commit();
	}
	// The following methods are used by the server-side classes.

	/**
	 * Returns the transaction currently associated with
	 * this connection.
	 * @return the transaction associated with this connection
	 */
	Transaction getTransaction() {
		return tx;
	}

	/**
	 * Commits the current transaction,
	 * and begins a new one.
	 */
	void commit() {
		tx.commit();
		tx = new Transaction();
	}

	/**
	 * Rolls back the current transaction,
	 * and begins a new one.
	 */
	void rollback() {
		tx.rollback();
		tx = new Transaction();
	}
}

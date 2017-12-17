package simpledb.remote;

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
 * @version 1.0 CreateTime：2017/12/17 18:24
 */
public class RemoteDriverImpl extends UnicastRemoteObject implements RemoteDriver{
	public RemoteDriverImpl() throws RemoteException {
	}

	@Override
	public RemoteConnection connect() throws RemoteException {
		return new RemoteConnectionImpl();
	}
}

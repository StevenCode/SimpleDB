package simpledb.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

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
 * @version 1.0 CreateTime：2017/12/17 18:22
 */
public interface RemoteConnection extends Remote {
	public RemoteStatement createStatement() throws RemoteException;
	public void close() throws RemoteException;
}

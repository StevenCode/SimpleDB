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
 * @version 1.0 CreateTime：2017/12/17 18:37
 */
public interface RemoteResultSet extends Remote{
	public boolean next() throws RemoteException;
	public int getInt(String fldname) throws RemoteException;
	public String getString(String fldname) throws RemoteException;
	public RemoteMetaData getMetaData() throws RemoteException;
	public void close() throws RemoteException;
}

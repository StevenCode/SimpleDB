package simpledb.remote;

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
 * @version 1.0 CreateTime：2017/12/17 18:41
 */
public interface RemoteMetaData {
	public int    getColumnCount()              throws RemoteException;
	public String getColumnName(int column)     throws RemoteException;
	public int    getColumnType(int column)     throws RemoteException;
	public int getColumnDisplaySize(int column) throws RemoteException;
}

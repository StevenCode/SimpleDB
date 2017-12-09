package simpledb.server;

import simpledb.buffer.BufferMgr;
import simpledb.file.FileMgr;
import simpledb.log.LogMgr;

/**
 * <p>Title:simpledb.server.MyProject</p>
 * <p>
 * <p>Description: </p>
 * <p>
 * <p>Copyright: Copyright (c) 2001-2013 Newland SoftWare Company</p>
 * <p>
 * <p>Company: Newland SoftWare Company</p>
 *
 * @author Steven
 * @version 1.0 CreateTime：2017/12/4 23:35
 */
public class SimpleDB {
	public static FileMgr fileMgr() {
		return new FileMgr("");
	}

	public static LogMgr logMgr() {
		return null;
	}

	public static BufferMgr bufferMgr() { return null;}
}

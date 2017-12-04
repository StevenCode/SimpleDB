package server;

import file.FileMgr;
import log.LogMgr;

/**
 * <p>Title:server.MyProject</p>
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
		return new LogMgr();
	}
}

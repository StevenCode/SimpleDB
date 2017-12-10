package simpledb.metadate;

import simpledb.record.Schema;
import simpledb.record.TableInfo;
import simpledb.tx.Transaction;

/**
 * <p>Title:simpledb.metadate.MyProject</p>
 * <p>
 * <p>Description: </p>
 * <p>
 * <p>Copyright: Copyright (c) 2001-2013 Newland SoftWare Company</p>
 * <p>
 * <p>Company: Newland SoftWare Company</p>
 *
 * @author Steven
 * @version 1.0 CreateTime：2017/12/9 19:08
 */
public class MetadataMgr {
	private static TableMgr tblmgr;
	private static StatMgr   statmgr;

	public MetadataMgr(boolean isnew, Transaction tx) {
		tblmgr  = new TableMgr(isnew, tx);
	}

	public void createTable(String tblname, Schema sch, Transaction tx) {
		tblmgr.createTable(tblname, sch, tx);
	}

	public TableInfo getTableInfo(String tblname, Transaction tx) {
		return tblmgr.getTableInfo(tblname, tx);
	}

	public StatInfo getStatInfo(String tblname, TableInfo ti, Transaction tx) {
		return statmgr.getStatInfo(tblname, ti, tx);
	}
}

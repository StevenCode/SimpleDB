package simpledb.metadate;

import simpledb.record.Schema;
import simpledb.record.TableInfo;
import simpledb.tx.Transaction;

import java.util.Map;

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
	private static ViewMgr   viewmgr;
	private static StatMgr   statmgr;
	private static IndexMgr idxmgr;

	public MetadataMgr(boolean isnew, Transaction tx) {
		tblmgr  = new TableMgr(isnew, tx);
		viewmgr = new ViewMgr(isnew, tblmgr, tx);
		statmgr = new StatMgr(tblmgr, tx);
		idxmgr  = new IndexMgr(isnew, tblmgr, tx);
	}

	public void createTable(String tblname, Schema sch, Transaction tx) {
		tblmgr.createTable(tblname, sch, tx);
	}

	public TableInfo getTableInfo(String tblname, Transaction tx) {
		return tblmgr.getTableInfo(tblname, tx);
	}

	public void createView(String viewname, String viewdef, Transaction tx) {
		viewmgr.createView(viewname, viewdef, tx);
	}

	public String getViewDef(String viewname, Transaction tx) {
		return viewmgr.getViewDef(viewname, tx);
	}

	public void createIndex(String idxname, String tblname, String fldname, Transaction tx) {
		idxmgr.createIndex(idxname, tblname, fldname, tx);
	}

	public Map<String,IndexInfo> getIndexInfo(String tblname, Transaction tx) {
		return idxmgr.getIndexInfo(tblname, tx);
	}

	public StatInfo getStatInfo(String tblname, TableInfo ti, Transaction tx) {
		return statmgr.getStatInfo(tblname, ti, tx);
	}
}

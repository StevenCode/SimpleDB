package simpledb.metadate;

import simpledb.record.RecordFile;
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
 * @version 1.0 CreateTime：2017/12/16 18:20
 */
public class ViewMgr {
	private static final int MAX_VIEWDEF = 100;
	TableMgr tblMgr;

	public ViewMgr(boolean isNew, TableMgr tblMgr, Transaction tx) {
		this.tblMgr = tblMgr;
		if (isNew) {
			Schema sch = new Schema();
			sch.addStringField("viewname", TableMgr.MAX_NAME);
			sch.addStringField("viewdef", MAX_VIEWDEF);
			tblMgr.createTable("viewcat", sch, tx);
		}
	}

	public void createView(String vname, String vdef, Transaction tx) {
		TableInfo ti = tblMgr.getTableInfo("viewcat", tx);
		RecordFile rf = new RecordFile(ti, tx);
		rf.insert();
		rf.setString("viewname", vname);
		rf.setString("viewdef", vdef);
		rf.close();
	}

	public String getViewDef(String vname, Transaction tx) {
		String result = null;
		TableInfo ti = tblMgr.getTableInfo("viewcat", tx);
		RecordFile rf = new RecordFile(ti, tx);
		while (rf.next())
			if (rf.getString("viewname").equals(vname)) {
				result = rf.getString("viewdef");
				break;
			}
		rf.close();
		return result;
	}
}

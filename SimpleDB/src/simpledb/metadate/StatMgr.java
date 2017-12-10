package simpledb.metadate;

import simpledb.record.RecordFile;
import simpledb.record.TableInfo;
import simpledb.tx.Transaction;

import java.util.HashMap;
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
 * @version 1.0 CreateTime：2017/12/10 10:31
 */
public class StatMgr {
	private TableMgr tblMgr;
	private Map<String, StatInfo> tablestats;
	private int numcalls;

	/**
	 * Creates the statistics manager.
	 * The initial statistics are calculated by
	 * traversing the entire database.
	 * @param tx the startup transaction
	 */
	public StatMgr(TableMgr tblMgr, Transaction tx) {
		this.tblMgr = tblMgr;
		refreshStatistics(tx);
	}

	/**
	 * Returns the statistical information about the specified table.
	 * @param tblname the name of the table
	 * @param ti the table's metadata
	 * @param tx the calling transaction
	 * @return the statistical information about the table
	 */
	public synchronized StatInfo getStatInfo(String tblname, TableInfo ti, Transaction tx) {
		numcalls++;
		if (numcalls > 100)
			refreshStatistics(tx);
		StatInfo si = tablestats.get(tblname);
		if (si == null) {
			si = calcTableStats(ti, tx);
			tablestats.put(tblname, si);
		}
		return si;
	}

	private synchronized void refreshStatistics(Transaction tx) {
		tablestats = new HashMap<String,StatInfo>();
		numcalls = 0;
		TableInfo tcatmd = tblMgr.getTableInfo("tblcat", tx);
		RecordFile tcatfile = new RecordFile(tcatmd, tx);
		while(tcatfile.next()) {
			String tblname = tcatfile.getString("tblname");
			TableInfo md = tblMgr.getTableInfo(tblname, tx);
			StatInfo si = calcTableStats(md, tx);
			tablestats.put(tblname, si);
		}
		tcatfile.close();
	}

	private synchronized StatInfo calcTableStats(TableInfo ti, Transaction tx) {
		int numRecs = 0;
		RecordFile rf = new RecordFile(ti, tx);
		int numblocks = 0;
		while (rf.next()) {
			numRecs++;
			numblocks = rf.currentRid().blockNumber() + 1;
		}
		rf.close();
		return new StatInfo(numblocks, numRecs);
	}
}

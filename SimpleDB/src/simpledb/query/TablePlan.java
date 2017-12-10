package simpledb.query;

import simpledb.metadate.StatInfo;
import simpledb.record.Schema;
import simpledb.record.TableInfo;
import simpledb.server.SimpleDB;
import simpledb.tx.Transaction;

/**
 * <p>Title:simpledb.query.MyProject</p>
 * <p>
 * <p>Description: </p>
 * <p>
 * <p>Copyright: Copyright (c) 2001-2013 Newland SoftWare Company</p>
 * <p>
 * <p>Company: Newland SoftWare Company</p>
 *
 * @author Steven
 * @version 1.0 CreateTime：2017/12/10 10:24
 */
public class TablePlan implements Plan{
	private Transaction tx;
	private TableInfo ti;
	private StatInfo si;

	/**
	 * Creates a leaf node in the query tree corresponding
	 * to the specified table.
	 * @param tblname the name of the table
	 * @param tx the calling transaction
	 */
	public TablePlan(String tblname, Transaction tx) {
		this.tx = tx;
		ti = SimpleDB.mdMgr().getTableInfo(tblname, tx);
		si = SimpleDB.mdMgr().getStatInfo(tblname, ti, tx);
	}


	/**
	 * Creates a table scan for this query.
	 * @see simpledb.query.Plan#open()
	 */
	public Scan open() {
		return new TableScan(ti, tx);
	}
	/**
	 * Estimates the number of block accesses for the table,
	 * which is obtainable from the statistics manager.
	 * @see simpledb.query.Plan#blocksAccessed()
	 */
	public int blocksAccessed() {
		return si.blocksAccessed();
	}

	/**
	 * Estimates the number of records in the table,
	 * which is obtainable from the statistics manager.
	 * @see simpledb.query.Plan#recordsOutput()
	 */
	public int recordsOutput() {
		return si.recordsOutput();
	}

	/**
	 * Estimates the number of distinct field values in the table,
	 * which is obtainable from the statistics manager.
	 * @see simpledb.query.Plan#distinctValues(java.lang.String)
	 */
	public int distinctValues(String fldname) {
		return si.distinctValues(fldname);
	}

	/**
	 * Determines the schema of the table,
	 * which is obtainable from the catalog manager.
	 * @see simpledb.query.Plan#schema()
	 */
	public Schema schema() {
		return ti.schema();
	}
}

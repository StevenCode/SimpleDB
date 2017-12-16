package simpledb.materialize;

import simpledb.query.TableScan;
import simpledb.query.UpdateScan;
import simpledb.record.Schema;
import simpledb.record.TableInfo;
import simpledb.tx.Transaction;

/**
 * <p>Title:simpledb.materialize.MyProject</p>
 * <p>
 * <p>Description: </p>
 * <p>
 * <p>Copyright: Copyright (c) 2001-2013 Newland SoftWare Company</p>
 * <p>
 * <p>Company: Newland SoftWare Company</p>
 *
 * @author Steven
 * @version 1.0 CreateTime：2017/12/16 21:39
 */
public class TempTable {
	private static int nextTableNum = 0;
	private TableInfo ti;
	private Transaction tx;

	/**
	 * Allocates a name for for a new temporary table
	 * having the specified schema.
	 * @param sch the new table's schema
	 * @param tx the calling transaction
	 */
	public TempTable(Schema sch, Transaction tx) {
		String tblname = nextTableName();
		ti = new TableInfo(tblname, sch);
		this.tx = tx;
	}
	/**
	 * Opens a table scan for the temporary table.
	 */
	public UpdateScan open() {
		return new TableScan(ti, tx);
	}

	/**
	 * Return the table's metadata.
	 * @return the table's metadata
	 */
	public TableInfo getTableInfo() {
		return ti;
	}

	private static synchronized String nextTableName() {
		nextTableNum++;
		return "temp" + nextTableNum;
	}
}

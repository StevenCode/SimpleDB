package simpledb.index.query;

import simpledb.index.Index;
import simpledb.metadate.IndexInfo;
import simpledb.query.Constant;
import simpledb.query.Plan;
import simpledb.query.Scan;
import simpledb.query.TableScan;
import simpledb.record.Schema;
import simpledb.tx.Transaction;

/**
 * <p>Title:simpledb.index.query.MyProject</p>
 * <p>
 * <p>Description: </p>
 * <p>
 * <p>Copyright: Copyright (c) 2001-2013 Newland SoftWare Company</p>
 * <p>
 * <p>Company: Newland SoftWare Company</p>
 *
 * @author Steven
 * @version 1.0 CreateTime：2017/12/16 20:51
 */
public class IndexSelectPlan implements Plan {
	private Plan p;
	private IndexInfo ii;
	private Constant val;

	/**
	 * Creates a new indexselect node in the query tree
	 * for the specified index and selection constant.
	 *
	 * @param p   the input table
	 * @param ii  information about the index
	 * @param val the selection constant
	 * @param tx  the calling transaction
	 */
	public IndexSelectPlan(Plan p, IndexInfo ii, Constant val, Transaction tx) {
		this.p = p;
		this.ii = ii;
		this.val = val;
	}

	@Override
	public Scan open() {
		// throws an exception if p is not a tableplan.
		TableScan ts = (TableScan) p.open();
		Index idx = ii.open();
		return new IndexSelectScan(idx, val, ts);
	}

	/**
	 * Estimates the number of block accesses to compute the
	 * index selection, which is the same as the
	 * index traversal cost plus the number of matching data records.
	 *
	 * @see simpledb.query.Plan#blocksAccessed()
	 */
	public int blocksAccessed() {
		return ii.blocksAccessed() + recordsOutput();
	}

	/**
	 * Estimates the number of output records in the index selection,
	 * which is the same as the number of search key values
	 * for the index.
	 *
	 * @see simpledb.query.Plan#recordsOutput()
	 */
	public int recordsOutput() {
		return ii.recordsOutput();
	}

	/**
	 * Returns the distinct values as defined by the index.
	 *
	 * @see simpledb.query.Plan#distinctValues(java.lang.String)
	 */
	public int distinctValues(String fldname) {
		return ii.distinctValues(fldname);
	}

	/**
	 * Returns the schema of the data table.
	 *
	 * @see simpledb.query.Plan#schema()
	 */
	public Schema schema() {
		return p.schema();
	}
}

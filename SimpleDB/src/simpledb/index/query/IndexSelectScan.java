package simpledb.index.query;

import simpledb.index.Index;
import simpledb.query.Constant;
import simpledb.query.Scan;
import simpledb.query.TableScan;
import simpledb.record.RID;

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
 * @version 1.0 CreateTime：2017/12/16 20:53
 */
public class IndexSelectScan implements Scan{
	private Index idx;
	private Constant val;
	private TableScan ts;

	/**
	 * Creates an index select scan for the specified
	 * index and selection constant.
	 * @param idx the index
	 * @param val the selection constant
	 */
	public IndexSelectScan(Index idx, Constant val, TableScan ts) {
		this.idx = idx;
		this.val = val;
		this.ts  = ts;
		beforeFirst();
	}

	@Override
	public void beforeFirst() {
		idx.beforeFirst(val);
	}

	@Override
	public boolean next() {
		boolean ok = idx.next();
		if (ok) {
			RID rid = idx.getDataRid();
			ts.moveToRid(rid);
		}
		return ok;
	}

	@Override
	public void close() {
		idx.close();
		ts.close();
	}

	public Constant getVal(String fldname) {
		return ts.getVal(fldname);
	}

	/**
	 * Returns the value of the field of the current data record.
	 * @see simpledb.query.Scan#getInt(java.lang.String)
	 */
	public int getInt(String fldname) {
		return ts.getInt(fldname);
	}

	/**
	 * Returns the value of the field of the current data record.
	 * @see simpledb.query.Scan#getString(java.lang.String)
	 */
	public String getString(String fldname) {
		return ts.getString(fldname);
	}

	/**
	 * Returns whether the data record has the specified field.
	 * @see simpledb.query.Scan#hasField(java.lang.String)
	 */
	public boolean hasField(String fldname) {
		return ts.hasField(fldname);
	}
}

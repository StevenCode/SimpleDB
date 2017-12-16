package simpledb.multibuffer;

import simpledb.query.Constant;
import simpledb.query.ProductScan;
import simpledb.query.Scan;
import simpledb.record.TableInfo;
import simpledb.tx.Transaction;

/**
 * <p>Title:simpledb.multibuffer.MyProject</p>
 * <p>
 * <p>Description: </p>
 * <p>
 * <p>Copyright: Copyright (c) 2001-2013 Newland SoftWare Company</p>
 * <p>
 * <p>Company: Newland SoftWare Company</p>
 *
 * @author Steven
 * @version 1.0 CreateTime：2017/12/16 21:05
 */
public class MultiBufferProductScan implements Scan{
	private Scan lhsscan, rhsscan=null, prodscan;
	private TableInfo ti;
	private Transaction tx;
	private int chunksize, nextblknum, filesize;

	/**
	 * Creates the scan class for the product of the LHS scan and a table.
	 * @param lhsscan the LHS scan
	 * @param ti the metadata for the RHS table
	 * @param tx the current transaction
	 */
	public MultiBufferProductScan(Scan lhsscan, TableInfo ti, Transaction tx) {
		this.lhsscan = lhsscan;
		this.ti = ti;
		this.tx = tx;
		filesize = tx.size(ti.fileName());
		chunksize = BufferNeeds.bestFactor(filesize);
		beforeFirst();
	}

	@Override
	public void beforeFirst() {
		nextblknum = 0;
		useNextChunk();
	}

	@Override
	public boolean next() {
		while (!prodscan.next())
			if (!useNextChunk())
				return false;
		return true;
	}

	@Override
	public void close() {
		prodscan.close();
	}

	@Override
	public Constant getVal(String fldname) {
		return prodscan.getVal(fldname);
	}

	@Override
	public int getInt(String fldname) {
		return prodscan.getInt(fldname);
	}

	@Override
	public String getString(String fldname) {
		return prodscan.getString(fldname);
	}

	/**
	 * Returns true if the specified field is in
	 * either of the underlying scans.
	 * @see simpledb.query.Scan#hasField(java.lang.String)
	 */
	public boolean hasField(String fldname) {
		return prodscan.hasField(fldname);
	}

	private boolean useNextChunk() {
		if (rhsscan != null)
			rhsscan.close();
		if (nextblknum >= filesize)
			return false;
		int end = nextblknum + chunksize - 1;
		if (end >= filesize)
			end = filesize - 1;
		rhsscan = new ChunkScan(ti, nextblknum, end, tx);
		lhsscan.beforeFirst();
		prodscan = new ProductScan(lhsscan, rhsscan);
		nextblknum = end + 1;
		return true;
	}

}

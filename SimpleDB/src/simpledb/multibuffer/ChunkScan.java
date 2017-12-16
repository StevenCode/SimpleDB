package simpledb.multibuffer;

import simpledb.file.Block;
import simpledb.query.Constant;
import simpledb.query.IntConstant;
import simpledb.query.Scan;
import simpledb.query.StringConstant;
import simpledb.record.RecordPage;
import simpledb.record.Schema;
import simpledb.record.TableInfo;
import simpledb.tx.Transaction;

import java.util.ArrayList;
import java.util.List;

import static java.sql.Types.INTEGER;

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
 * @version 1.0 CreateTime：2017/12/16 21:13
 */
public class ChunkScan implements Scan{
	private List<RecordPage> pages;
	private int startbnum, endbnum, current;
	private Schema sch;
	private RecordPage rp;

	/**
	 * Creates a chunk consisting of the specified pages.
	 * @param ti the metadata for the chunked table
	 * @param startbnum the starting block number
	 * @param endbnum  the ending block number
	 * @param tx the current transaction
	 */
	public ChunkScan(TableInfo ti, int startbnum, int endbnum, Transaction tx) {
		pages = new ArrayList<RecordPage>();
		this.startbnum = startbnum;
		this.endbnum   = endbnum;
		this.sch = ti.schema();
		String filename = ti.fileName();
		for (int i=startbnum; i<=endbnum; i++) {
			Block blk = new Block(filename, i);
			pages.add(new RecordPage(blk, ti, tx));
		}
		beforeFirst();
	}

	@Override
	public void beforeFirst() {
		moveToBlock(startbnum);
	}

	@Override
	public boolean next() {
		while (true) {
			if (rp.next())
				return true;
			if (current == endbnum)
				return false;
			moveToBlock(current+1);
		}
	}

	@Override
	public void close() {
		for (RecordPage r : pages)
			r.close();
	}

	/**
	 * @see simpledb.query.Scan#getVal(java.lang.String)
	 */
	public Constant getVal(String fldname) {
		if (sch.type(fldname) == INTEGER)
			return new IntConstant(rp.getInt(fldname));
		else
			return new StringConstant(rp.getString(fldname));
	}

	/**
	 * @see simpledb.query.Scan#getInt(java.lang.String)
	 */
	public int getInt(String fldname) {
		return rp.getInt(fldname);
	}

	/**
	 * @see simpledb.query.Scan#getString(java.lang.String)
	 */
	public String getString(String fldname) {
		return rp.getString(fldname);
	}

	/**
	 * @see simpledb.query.Scan#hasField(java.lang.String)
	 */
	public boolean hasField(String fldname) {
		return sch.hasField(fldname);
	}

	private void moveToBlock(int blknum) {
		current = blknum;
		rp = pages.get(current - startbnum);
		rp.moveToId(-1);
	}
}

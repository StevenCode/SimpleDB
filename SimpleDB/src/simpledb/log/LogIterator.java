package simpledb.log;

import simpledb.file.Block;
import simpledb.file.Page;

import java.util.Iterator;

import static simpledb.file.Page.INT_SIZE;

/**
 * <p>Title:simpledb.log.MyProject</p>
 * <p>
 * <p>Description: </p>
 * <p>
 * <p>Copyright: Copyright (c) 2001-2013 Newland SoftWare Company</p>
 * <p>
 * <p>Company: Newland SoftWare Company</p>
 *
 * @author Steven
 * @version 1.0 CreateTime：2017/12/5 23:37
 */
public class LogIterator implements Iterator<BasicLogRecord> {
	private Block blk;
	private Page pg = new Page();
	private int currentrec;

	/**
	 * Creates an iterator for the records in the simpledb.log simpledb.file,
	 * positioned after the last simpledb.log simpledb.record.
	 * This constructor is called exclusively by
	 * {@link LogMgr#iterator()}.
	 */
	LogIterator(Block blk) {
		this.blk = blk;
		pg.read(blk);
		currentrec = pg.getInt(LogMgr.LAST_POS);
	}
	@Override
	public boolean hasNext() {
		return currentrec > 0 || blk.number() > 0;
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Moves to the next simpledb.log simpledb.record in reverse order.
	 * If the current simpledb.log simpledb.record is the earliest in its block,
	 * then the method moves to the next oldest block,
	 * and returns the simpledb.log simpledb.record from there.
	 * @return the next earliest simpledb.log simpledb.record
	 */
	public BasicLogRecord next() {
		if (currentrec == 0)
			moveToNextBlock();
		currentrec = pg.getInt(currentrec);
		return new BasicLogRecord(pg, currentrec+INT_SIZE);
	}

	/**
	 * Moves to the next simpledb.log block in reverse order,
	 * and positions it after the last simpledb.record in that block.
	 */
	private void moveToNextBlock() {
		blk = new Block(blk.fileName(), blk.number()-1);
		pg.read(blk);
		currentrec = pg.getInt(LogMgr.LAST_POS);
	}
}

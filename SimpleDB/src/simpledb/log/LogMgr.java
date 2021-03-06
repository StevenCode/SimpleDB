package simpledb.log;

import simpledb.file.Block;
import simpledb.file.Page;
import simpledb.server.SimpleDB;

import java.util.Iterator;

import static simpledb.file.Page.BLOCK_SIZE;
import static simpledb.file.Page.INT_SIZE;
import static simpledb.file.Page.STR_SIZE;

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
 * @version 1.0 CreateTime：2017/12/5 0:59
 */
public class LogMgr implements Iterable<BasicLogRecord> {
	/**
	 * The location where the pointer to the last integer in the page is.
	 * A value of 0 means that the pointer is the first value in the page.
	 */
	public static final int LAST_POS = 0;

	private String logfile;
	private Page mypage = new Page();
	private Block currentblk;
	private int currentpos;

	/**
	 * Creates the manager for the specified simpledb.log simpledb.file.
	 * If the simpledb.log simpledb.file does not yet exist, it is created
	 * with an empty first block.
	 * This constructor depends on a {@link FileMgr} object
	 * that it gets from the method
	 * {@link simpledb.server.SimpleDB#fileMgr()}.
	 * That object is created during system initialization.
	 * Thus this constructor cannot be called until
	 * {@link simpledb.server.SimpleDB#initFileMgr(String)}
	 * is called first.
	 * @param logfile the name of the simpledb.log simpledb.file
	 */
	public LogMgr(String logfile) {
		this.logfile = logfile;
		int logsize = SimpleDB.fileMgr().size(logfile);
		if (logsize == 0)
			appendNewBlock();
		else {
			currentblk = new Block(logfile, logsize-1);
			mypage.read(currentblk);
			currentpos = getLastRecordPosition() + INT_SIZE;
		}
	}

	/**
	 * Ensures that the simpledb.log records corresponding to the
	 * specified LSN has been written to disk.
	 * All earlier simpledb.log records will also be written to disk.
	 * @param lsn the LSN of a simpledb.log simpledb.record
	 */
	public void flush(int lsn) {
		if (lsn >= currentLSN())
			flush();
	}

	/**
	 * Returns an iterator for the simpledb.log records,
	 * which will be returned in reverse order starting with the most recent.
	 * @see java.lang.Iterable#iterator()
	 */
	public synchronized Iterator<BasicLogRecord> iterator() {
		flush();
		return new LogIterator(currentblk);
	}

	/**
	 * Appends a simpledb.log simpledb.record to the simpledb.file.
	 * The simpledb.record contains an arbitrary array of strings and integers.
	 * The method also writes an integer to the end of each simpledb.log simpledb.record whose value
	 * is the offset of the corresponding integer for the previous simpledb.log simpledb.record.
	 * These integers allow simpledb.log records to be read in reverse order.
	 * @param rec the list of values
	 * @return the LSN of the final value
	 */
	public synchronized int append(Object[] rec) {
		int recsize = INT_SIZE;  // 4 bytes for the integer that points to the previous simpledb.log simpledb.record
		for (Object obj : rec)
			recsize += size(obj);
		if (currentpos + recsize >= BLOCK_SIZE){ // the simpledb.log simpledb.record doesn't fit,
			flush();        // so move to the next block.
			appendNewBlock();
		}
		for (Object obj : rec)
			appendVal(obj);
		finalizeRecord();
		return currentLSN();
	}

	/**
	 * Adds the specified value to the page at the position denoted by
	 * currentpos.  Then increments currentpos by the size of the value.
	 * @param val the integer or string to be added to the page
	 */
	private void appendVal(Object val) {
		if (val instanceof String)
			mypage.setString(currentpos, (String)val);
		else
			mypage.setInt(currentpos, (Integer)val);
		currentpos += size(val);
	}

	/**
	 * Calculates the size of the specified integer or string.
	 * @param val the value
	 * @return the size of the value, in bytes
	 */
	private int size(Object val) {
		if (val instanceof String) {
			String sval = (String) val;
			return STR_SIZE(sval.length());
		}
		else
			return INT_SIZE;
	}

	/**
	 * Returns the LSN of the most recent simpledb.log simpledb.record.
	 * As implemented, the LSN is the block number where the simpledb.record is stored.
	 * Thus every simpledb.log simpledb.record in a block has the same LSN.
	 * @return the LSN of the most recent simpledb.log simpledb.record
	 */
	private int currentLSN() {
		return currentblk.number();
	}

	/**
	 * Writes the current page to the simpledb.log simpledb.file.
	 */
	private void flush() {
		mypage.write(currentblk);
	}

	/**
	 * Clear the current page, and append it to the simpledb.log simpledb.file.
	 */
	private void appendNewBlock() {
		setLastRecordPosition(0);
		currentpos = INT_SIZE;
		currentblk = mypage.append(logfile);
	}

	/**
	 * Sets up a circular chain of pointers to the records in the page.
	 * There is an integer added to the end of each simpledb.log simpledb.record
	 * whose value is the offset of the previous simpledb.log simpledb.record.
	 * The first four bytes of the page contain an integer whose value
	 * is the offset of the integer for the last simpledb.log simpledb.record in the page.
	 */
	private void finalizeRecord() {
		mypage.setInt(currentpos, getLastRecordPosition());
		setLastRecordPosition(currentpos);
		currentpos += INT_SIZE;
	}

	private int getLastRecordPosition() {
		return mypage.getInt(LAST_POS);
	}

	private void setLastRecordPosition(int pos) {
		mypage.setInt(LAST_POS, pos);
	}
}

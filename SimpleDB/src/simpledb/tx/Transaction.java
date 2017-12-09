package simpledb.tx;

import simpledb.buffer.Buffer;
import simpledb.buffer.PageFormatter;
import simpledb.file.Block;
import simpledb.server.SimpleDB;

/**
 * <p>Title:simpledb.tx.MyProject</p>
 * <p>
 * <p>Description: </p>
 * <p>
 * <p>Copyright: Copyright (c) 2001-2013 Newland SoftWare Company</p>
 * <p>
 * <p>Company: Newland SoftWare Company</p>
 *
 * @author Steven
 * @version 1.0 CreateTime：2017/12/9 18:54
 */
public class Transaction {
	private static int nextTxNum = 0;
	private static final int END_OF_FILE = -1;
	private int txnum;
	private BufferList myBuffers = new BufferList();

	/**
	 * Pins the specified block.
	 * The transaction manages the buffer for the client.
	 * @param blk a reference to the disk block
	 */
	public void pin(Block blk) {
		myBuffers.pin(blk);
	}

	/**
	 * Unpins the specified block.
	 * The transaction looks up the buffer pinned to this block,
	 * and unpins it.
	 * @param blk a reference to the disk block
	 */
	public void unpin(Block blk) {
		myBuffers.unpin(blk);
	}

	private static synchronized int nextTxNumber() {
		nextTxNum++;
		System.out.println("new transaction: " + nextTxNum);
		return nextTxNum;
	}

	/**
	 * Returns the number of blocks in the specified file.
	 * This method first obtains an SLock on the
	 * "end of the file", before asking the file manager
	 * to return the file size.
	 * @param filename the name of the file
	 * @return the number of blocks in the file
	 */
	public int size(String filename) {
		Block dummyblk = new Block(filename, END_OF_FILE);
		return SimpleDB.fileMgr().size(filename);
	}

	/**
	 * Returns the integer value stored at the
	 * specified offset of the specified block.
	 * The method first obtains an SLock on the block,
	 * then it calls the buffer to retrieve the value.
	 * @param blk a reference to a disk block
	 * @param offset the byte offset within the block
	 * @return the integer stored at that offset
	 */
	public int getInt(Block blk, int offset) {
		Buffer buff = myBuffers.getBuffer(blk);
		return buff.getInt(offset);
	}

	/**
	 * Returns the string value stored at the
	 * specified offset of the specified block.
	 * The method first obtains an SLock on the block,
	 * then it calls the buffer to retrieve the value.
	 * @param blk a reference to a disk block
	 * @param offset the byte offset within the block
	 * @return the string stored at that offset
	 */
	public String getString(Block blk, int offset) {
		Buffer buff = myBuffers.getBuffer(blk);
		return buff.getString(offset);
	}

	/**
	 * Stores an integer at the specified offset
	 * of the specified block.
	 * The method first obtains an XLock on the block.
	 * It then reads the current value at that offset,
	 * puts it into an update log record, and
	 * writes that record to the log.
	 * Finally, it calls the buffer to store the value,
	 * passing in the LSN of the log record and the transaction's id.
	 * @param blk a reference to the disk block
	 * @param offset a byte offset within that block
	 * @param val the value to be stored
	 */
	public void setInt(Block blk, int offset, int val) {
		Buffer buff = myBuffers.getBuffer(blk);
		//TODO
		int lsn = 0;
		buff.setInt(offset, val, txnum, lsn);
	}

	/**
	 * Stores a string at the specified offset
	 * of the specified block.
	 * The method first obtains an XLock on the block.
	 * It then reads the current value at that offset,
	 * puts it into an update log record, and
	 * writes that record to the log.
	 * Finally, it calls the buffer to store the value,
	 * passing in the LSN of the log record and the transaction's id.
	 * @param blk a reference to the disk block
	 * @param offset a byte offset within that block
	 * @param val the value to be stored
	 */
	public void setString(Block blk, int offset, String val) {
		Buffer buff = myBuffers.getBuffer(blk);
		//TODO
		int lsn = 0;
//		int lsn = recoveryMgr.setString(buff, offset, val);
		buff.setString(offset, val, txnum, lsn);
	}


	/**
	 * Appends a new block to the end of the specified file
	 * and returns a reference to it.
	 * This method first obtains an XLock on the
	 * "end of the file", before performing the append.
	 * @param filename the name of the file
	 * @param fmtr the formatter used to initialize the new page
	 * @return a reference to the newly-created disk block
	 */
	public Block append(String filename, PageFormatter fmtr) {
		Block dummyblk = new Block(filename, END_OF_FILE);
		Block blk = myBuffers.pinNew(filename, fmtr);
		unpin(blk);
		return blk;
	}
}

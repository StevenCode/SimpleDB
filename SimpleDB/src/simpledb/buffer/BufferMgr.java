package simpledb.buffer;

import simpledb.file.Block;

/**
 * <p>Title:simpledb.buffer.MyProject</p>
 * <p>
 * <p>Description: </p>
 * <p>
 * <p>Copyright: Copyright (c) 2001-2013 Newland SoftWare Company</p>
 * <p>
 * <p>Company: Newland SoftWare Company</p>
 *
 * @author Steven
 * @version 1.0 CreateTime：2017/12/5 23:08
 */
public class BufferMgr {
	private static final long MAX_TIME = 10000; // 10 seconds
	private BasicBufferMgr bufferMgr;

	/**
	 * Creates a new simpledb.buffer manager having the specified
	 * number of buffers.
	 * This constructor depends on both the {@link FileMgr} and
	 * {@link simpledb.log.LogMgr LogMgr} objects
	 * that it gets from the class
	 * {@link simpledb.server.SimpleDB}.
	 * Those objects are created during system initialization.
	 * Thus this constructor cannot be called until
	 * {@link simpledb.server.SimpleDB#initFileAndLogMgr(String)} or
	 * is called first.
	 * @param numbuffers the number of simpledb.buffer slots to allocate
	 */
	public BufferMgr(int numbuffers) {
		bufferMgr = new BasicBufferMgr(numbuffers);
	}

	/**
	 * Pins a simpledb.buffer to the specified block, potentially
	 * waiting until a simpledb.buffer becomes available.
	 * If no simpledb.buffer becomes available within a fixed
	 * time period, then a {@link BufferAbortException} is thrown.
	 * @param blk a reference to a disk block
	 * @return the simpledb.buffer pinned to that block
	 */
	public synchronized Buffer pin(Block blk) {
		try {
			long timestamp = System.currentTimeMillis();
			Buffer buff = bufferMgr.pin(blk);
			while (buff == null && !waitingTooLong(timestamp)) {
				wait(MAX_TIME);
				buff = bufferMgr.pin(blk);
			}
			if (buff == null)
				throw new BufferAbortException();
			return buff;
		}
		catch(InterruptedException e) {
			throw new BufferAbortException();
		}
	}

	/**
	 * Pins a simpledb.buffer to a new block in the specified simpledb.file,
	 * potentially waiting until a simpledb.buffer becomes available.
	 * If no simpledb.buffer becomes available within a fixed
	 * time period, then a {@link BufferAbortException} is thrown.
	 * @param filename the name of the simpledb.file
	 * @param fmtr the formatter used to initialize the page
	 * @return the simpledb.buffer pinned to that block
	 */
	public synchronized Buffer pinNew(String filename, PageFormatter fmtr) {
		try {
			long timestamp = System.currentTimeMillis();
			Buffer buff = bufferMgr.pinNew(filename, fmtr);
			while (buff == null && !waitingTooLong(timestamp)) {
				wait(MAX_TIME);
				buff = bufferMgr.pinNew(filename, fmtr);
			}
			if (buff == null)
				throw new BufferAbortException();
			return buff;
		}
		catch(InterruptedException e) {
			throw new BufferAbortException();
		}
	}

	/**
	 * Unpins the specified simpledb.buffer.
	 * If the simpledb.buffer's pin count becomes 0,
	 * then the threads on the wait list are notified.
	 * @param buff the simpledb.buffer to be unpinned
	 */
	public synchronized void unpin(Buffer buff) {
		bufferMgr.unpin(buff);
		if (!buff.isPinned())
			notifyAll();
	}

	/**
	 * Flushes the dirty buffers modified by the specified transaction.
	 * @param txnum the transaction's id number
	 */
	public void flushAll(int txnum) {
		bufferMgr.flushAll(txnum);
	}

	/**
	 * Returns the number of available (ie unpinned) buffers.
	 * @return the number of available buffers
	 */
	public int available() {
		return bufferMgr.available();
	}

	private boolean waitingTooLong(long starttime) {
		return System.currentTimeMillis() - starttime > MAX_TIME;
	}
}

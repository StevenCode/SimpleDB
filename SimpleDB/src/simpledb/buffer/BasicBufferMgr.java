package simpledb.buffer;

import simpledb.file.Block;

/**
 * Manages the pinning and unpinning of buffers to blocks.
 * @author Steven
 * @version 1.0 CreateTime：2017/12/5 0:26
 */
public class BasicBufferMgr {
	private Buffer[] bufferpool;
	private int numAvailable;

	/**
	 * Creates a simpledb.buffer manager having the specified number
	 * of simpledb.buffer slots.
	 * This constructor depends on both the FileMgr and
	 * that it gets from the class
	 * Those objects are created during system initialization.
	 * Thus this constructor cannot be called unitl
	 * initFIleAndLogMgr or
	 * is called first
	 * @param numbuffs the number of simpledb.buffer slots to allocate
	 */
	BasicBufferMgr(int numbuffs) {
		bufferpool = new Buffer[numbuffs];
		numAvailable = numbuffs;
		for (int i = 0; i < numbuffs; i++) {
			bufferpool[i] = new Buffer();
		}
	}

	synchronized void flushAll(int txnum) {
		for (Buffer buff : bufferpool) {
			if (buff.isModifiedBy(txnum))
				buff.flush();
		}
	}

	/**
	 * Pins a simpledb.buffer to the specified block.
	 * If there is already a simpledb.buffer assigned to that block
	 * then that simpledb.buffer is used;
	 * otherwise, an unpinned simpledb.buffer from the pool is chosen.
	 * Returns a null value if there are no available buffers.
	 * @param blk a reference to a disk block
	 * @return the pinned simpledb.buffer
	 */
	synchronized Buffer pin(Block blk) {
		Buffer buff = findExistingBuffer(blk);
		if (buff == null) {
			buff = chooseUnpinnedBuffer();
			if (buff == null)
				return null;
			buff.assignToBlock(blk);
		}
		if (!buff.isPinned())
			numAvailable--;
		buff.pin();
		return buff;
	}



	/**
	 * Allocates a new block in the specified simpledb.file, and
	 * pins a simpledb.buffer to it.
	 * Returns null (without allocating the block) if
	 * there are no available buffers.
	 * @param filename the name of the simpledb.file
	 * @param fmtr a pageformatter object, used to format the new block
	 * @return the pinned simpledb.buffer
	 */
	synchronized Buffer pinNew(String filename, PageFormatter fmtr) {
		Buffer buff = chooseUnpinnedBuffer();
		if (buff == null)
			return null;
		buff.assignToNew(filename, fmtr);
		numAvailable--;
		buff.pin();
		return buff;
	}

	/**
	 * Unpins the specified simpledb.buffer.
	 * @param buff the simpledb.buffer to be unpinned
	 */
	synchronized void unpin(Buffer buff) {
		buff.unpin();
		if (!buff.isPinned())
			numAvailable++;
	}
	/**
	 * Returns the number of available (i.e. unpinned) buffers.
	 * @return the number of available buffers
	 */
	int available() {
		return numAvailable;
	}

	private Buffer findExistingBuffer(Block blk) {
		for (Buffer buff : bufferpool) {
			Block b = buff.block();
			if (b != null && b.equals(blk))
				return buff;
		}
		return null;
	}

	private Buffer chooseUnpinnedBuffer() {
		for (Buffer buff : bufferpool)
			if (!buff.isPinned())
				return buff;
		return null;
	}
}

package simpledb.index.btree;

import simpledb.query.Constant;

/**
 * <p>Title:simpledb.index.btree.MyProject</p>
 * <p>
 * <p>Description: </p>
 * <p>
 * <p>Copyright: Copyright (c) 2001-2013 Newland SoftWare Company</p>
 * <p>
 * <p>Company: Newland SoftWare Company</p>
 *
 * @author Steven
 * @version 1.0 CreateTime：2017/12/10 16:48
 */
public class DirEntry {
	private Constant dataval;
	private int blocknum;

	/**
	 * Creates a new entry for the specified dataval and block number.
	 * @param dataval the dataval
	 * @param blocknum the block number
	 */
	public DirEntry(Constant dataval, int blocknum) {
		this.dataval  = dataval;
		this.blocknum = blocknum;
	}

	/**
	 * Returns the dataval component of the entry
	 * @return the dataval component of the entry
	 */
	public Constant dataVal() {
		return dataval;
	}

	/**
	 * Returns the block number component of the entry
	 * @return the block number component of the entry
	 */
	public int blockNumber() {
		return blocknum;
	}
}
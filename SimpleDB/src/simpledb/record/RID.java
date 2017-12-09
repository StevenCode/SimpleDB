package simpledb.record;

/**
 * <p>Title:simpledb.record.MyProject</p>
 * <p>
 * <p>Description: </p>
 * <p>
 * <p>Copyright: Copyright (c) 2001-2013 Newland SoftWare Company</p>
 * <p>
 * <p>Company: Newland SoftWare Company</p>
 *
 * @author Steven
 * @version 1.0 CreateTime：2017/12/9 22:34
 */
public class RID {
	private int blknum;
	private int id;

	/**
	 * Creates a RID for the record having the
	 * specified ID in the specified block.
	 * @param blknum the block number where the record lives
	 * @param id the record's ID
	 */
	public RID(int blknum, int id) {
		this.blknum = blknum;
		this.id     = id;
	}

	/**
	 * Returns the block number associated with this RID.
	 * @return the block number
	 */
	public int blockNumber() {
		return blknum;
	}

	/**
	 * Returns the ID associated with this RID.
	 * @return the ID
	 */
	public int id() {
		return id;
	}

	public boolean equals(Object obj) {
		RID r = (RID) obj;
		return blknum == r.blknum && id==r.id;
	}

	public String toString() {
		return "[" + blknum + ", " + id + "]";
	}
}

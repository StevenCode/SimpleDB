package simpledb.metadate;

/**
 * <p>Title:simpledb.metadate.MyProject</p>
 * <p>
 * <p>Description: </p>
 * <p>
 * <p>Copyright: Copyright (c) 2001-2013 Newland SoftWare Company</p>
 * <p>
 * <p>Company: Newland SoftWare Company</p>
 *
 * @author Steven
 * @version 1.0 CreateTime：2017/12/10 10:25
 */
public class StatInfo {
	private int numBlocks;
	private int numRecs;

	/**
	 * Creates a StatInfo object.
	 * Note that the number of distinct values is not
	 * passed into the constructor.
	 * The object fakes this value.
	 * @param numblocks the number of blocks in the table
	 * @param numrecs the number of records in the table
	 */
	public StatInfo(int numblocks, int numrecs) {
		this.numBlocks = numblocks;
		this.numRecs   = numrecs;
	}

	/**
	 * Returns the estimated number of blocks in the table.
	 * @return the estimated number of blocks in the table
	 */
	public int blocksAccessed() {
		return numBlocks;
	}

	/**
	 * Returns the estimated number of records in the table.
	 * @return the estimated number of records in the table
	 */
	public int recordsOutput() {
		return numRecs;
	}

	/**
	 * Returns the estimated number of distinct values
	 * for the specified field.
	 * In actuality, this estimate is a complete guess.
	 * @param fldname the name of the field
	 * @return a guess as to the number of distinct field values
	 */
	public int distinctValues(String fldname) {
		return 1 + (numRecs / 3);
	}
}

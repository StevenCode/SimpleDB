package simpledb.file;

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
 * @version 1.0 CreateTime：2017/12/4 22:29
 */
public class Block {
	private String filename;
	private int blknum;

	/**
	 * Constructs a block reference
	 * for the specified filename and block number.
	 * @param filename the name of simpledb.file
	 * @param blknum the block number
	 */
	public Block(String filename, int blknum) {
		this.filename = filename;
		this.blknum = blknum;
	}

	/**
	 * Returns the name of the simpledb.file where the block lives.
	 * @return the filename
	 */
	public String fileName() {
		return filename;
	}

	/**
	 * Returns the location of block within the simpledb.file.
	 * @return
	 */
	public int number() {
		return blknum;
	}

	public boolean equals(Object obj) {
		Block blk =(Block)obj;
		return filename.equals(blk.filename) && blknum == blk.blknum;
	}

	public String toString(){
		return "[simpledb.file " + filename + ", block " + blknum + "]";
	}

	public int hashCode() {
		return toString().hashCode();
	}
}

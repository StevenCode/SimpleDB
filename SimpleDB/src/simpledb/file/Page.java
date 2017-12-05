package simpledb.file;

import simpledb.server.SimpleDB;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * The contents of a disk block in memeory
 * A page is treadted as an array of BLOCK_SIZE bytes.
 * There are methods to get/set values into this array,
 * and to read/write the contents of this array to a disk block.
 *
 * <p>Title:simpledb.file.MyProject</p>
 * <p>
 * <p>Description: </p>
 * <p>
 * <p>Copyright: Copyright (c) 2001-2013 Newland SoftWare Company</p>
 * <p>
 * <p>Company: Newland SoftWare Company</p>
 *
 * @author Steven
 * @version 1.0 CreateTime：2017/12/4 22:53
 */
public class Page {
	/**
	 * the number of bytes in a block.
	 * This values is set unreasonably low, so that is easier
	 * to create and test databases having a lot of blocks.
	 * A more realistic value would be 4K.
	 */
	public static final int BLOCK_SIZE = 400;

	/**
	 * The size of an integer in bytes.
	 * this value is almost certainly 4, but it is
	 * a good idea to encode this value as a contant.
	 */
	public static final int INT_SIZE = Integer.SIZE / Byte.SIZE;

	/**
	 * The maximum size, in bytes, of a string of length n.
	 * A string is represented as the encoding of its characters,
	 * preceded by an integer denoting the number of bytes in this encoding.
	 * If the JVM uses the US_ASCII encoding, then each char
	 * is stored in one byte, so a string of n characters
	 * has a size 4+n bytes.
	 * @param n the size of the string
	 * @return the maximum number of bytes required to store a string of size n
	 */
	public static final int STR_SIZE(int n) {
		float bytesPerChar = Charset.defaultCharset().newEncoder().maxBytesPerChar();
		return INT_SIZE + (n * (int) bytesPerChar);
	}

	private ByteBuffer contents = ByteBuffer.allocateDirect(BLOCK_SIZE);
	private FileMgr filemgr = SimpleDB.fileMgr();

	public Page() {}

	/**
	 * Populates the page with the contents of the specified disk block.
	 * @param blk a reference to a disk block
	 */
	public synchronized void read(Block blk) {
		filemgr.read(blk, contents);
	}

	/**
	 * Writes the contents of the page to the specified simpledb.file.
	 * @param blk a reference to a disk block
	 */
	public synchronized void write(Block blk) {
		filemgr.write(blk, contents);
	}

	/**
	 * Appends the contents of the page to the specified simpledb.file.
	 * @param filename the name of the simpledb.file
	 * @return the reference to the newly-created disk block
	 */
	public synchronized Block append(String filename) {
		return filemgr.append(filename, contents);
	}

	/**
	 * Returns the integer value at a specified offset of the page.
	 * if an integer was not stored at that location,
	 * the behavior of method is unpredictable.
	 * @param offset the byte offset within the page
	 * @return the integer value at that offset
	 */
	public synchronized int getInt(int offset) {
		contents.position(offset);
		return contents.getInt();
	}

	/**
	 * Writes an integer to the specified offset on the page.
	 * @param offset the byte offset within the page
	 * @param val the integer to be written to the page
	 */
	public synchronized void setInt(int offset, int val) {
		contents.position(offset);
		contents.putInt(val);
	}

	/**
	 * Returns the string value at the specified offset of the page.
	 * If a string was not stored at that location,
	 * the behavior of the method is unpredicatable.
	 * @param offset the byte offset within the page
	 * @return the string value at the offset
	 */
	public synchronized String getString(int offset) {
		contents.position(offset);
		int len = contents.getInt();
		byte[] byteval = new byte[len];
		contents.get(byteval);
		return new String(byteval);
	}

	/**
	 * Writes a string to the specified offset on the page.
	 * @param offset the byte offset within the page
	 * @param val the string to be written to the page
	 */
	public synchronized void setString(int offset, String val) {
		contents.position(offset);
		byte[] byteval = val.getBytes();
		contents.putInt(byteval.length);
		contents.put(byteval);
	}
}

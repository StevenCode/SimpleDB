package simpledb.log;

import simpledb.file.Page;

import static simpledb.file.Page.INT_SIZE;
import static simpledb.file.Page.STR_SIZE;

/**
 * A class that provides the ablility to read the values of
 * a simpledb.log simpledb.record.
 * The class has no idea what values are there.
 * @author Steven
 * @version 1.0 CreateTime：2017/12/5 23:29
 */
public class BasicLogRecord {
	private Page pg;
	private int pos;

	/**
	 * A simpledb.log simpledb.record located at the specified position of the specified page.
	 * This constructor is called exclusively by LogIterator
	 * @param pg the page containing the simpledb.log simpledb.record
	 * @param pos the position of the simpledb.log simpledb.record
	 */
	public BasicLogRecord(Page pg, int pos) {
		this.pg = pg;
		this.pos = pos;
	}

	/**
	 * Returns the next value of the current simpledb.log simpledb.record,
	 * assuming it is an integer.
	 * @return the next value of the current simpledb.log simpledb.record
	 */
	public int nextInt() {
		int result = pg.getInt(pos);
		pos += INT_SIZE;
		return result;
	}

	/**
	 * Returns the next value of the current simpledb.log simpledb.record,
	 * assuming it is a string.
	 * @return the next value of the current simpledb.log simpledb.record
	 */
	public String nextString() {
		String result = pg.getString(pos);
		pos += STR_SIZE(result.length());
		return result;
	}
}

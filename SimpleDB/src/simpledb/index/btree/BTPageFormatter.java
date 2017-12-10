package simpledb.index.btree;

import simpledb.buffer.PageFormatter;
import simpledb.file.Page;
import simpledb.record.TableInfo;

import static java.sql.Types.INTEGER;
import static simpledb.file.Page.BLOCK_SIZE;
import static simpledb.file.Page.INT_SIZE;

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
 * @version 1.0 CreateTime：2017/12/10 12:19
 */
public class BTPageFormatter implements PageFormatter{
	private TableInfo ti;
	private int flag;

	/**
	 * Creates a formatter for a new page of the
	 * specified B-tree index.
	 * @param ti the index's metadata
	 * @param flag the page's initial flag value
	 */
	public BTPageFormatter(TableInfo ti, int flag) {
		this.ti = ti;
		this.flag = flag;
	}

	/**
	 * Formats the page by initializing as many index-record slots
	 * as possible to have default values.
	 * Each integer field is given a value of 0, and
	 * each string field is given a value of "".
	 * The location that indicates the number of records
	 * in the page is also set to 0.
	 * @see simpledb.buffer.PageFormatter#format(simpledb.file.Page)
	 */
	public void format(Page page) {
		page.setInt(0, flag);
		page.setInt(INT_SIZE, 0);  // #records = 0
		int recsize = ti.recordLength();
		for (int pos=2*INT_SIZE; pos+recsize<=BLOCK_SIZE; pos += recsize)
			makeDefaultRecord(page, pos);
	}

	private void makeDefaultRecord(Page page, int pos) {
		for (String fldname : ti.schema().fields()) {
			int offset = ti.offset(fldname);
			if (ti.schema().type(fldname) == INTEGER)
				page.setInt(pos + offset, 0);
			else
				page.setString(pos + offset, "");
		}
	}
}

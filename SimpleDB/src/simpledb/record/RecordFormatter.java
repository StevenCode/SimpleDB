package simpledb.record;

import simpledb.buffer.PageFormatter;
import simpledb.file.Page;

import static java.sql.Types.INTEGER;
import static simpledb.file.Page.BLOCK_SIZE;
import static simpledb.file.Page.INT_SIZE;
import static simpledb.record.RecordPage.EMPTY;

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
 * @version 1.0 CreateTime：2017/12/9 21:55
 */
public class RecordFormatter implements PageFormatter{
	private TableInfo ti;


	/**
	 * Creates a formatter for a new page of a table.
	 * @param ti the table's metadata
	 */
	public RecordFormatter(TableInfo ti) {
		this.ti = ti;
	}

	/**
	 * Formats the page by allocating as many record slots
	 * as possible, given the record length.
	 * Each record slot is assigned a flag of EMPTY.
	 * Each integer field is given a value of 0, and
	 * each string field is given a value of "".
	 * @see simpledb.buffer.PageFormatter#format(simpledb.file.Page)
	 */
	public void format(Page page) {
		int recsize = ti.recordLength() + INT_SIZE;
		for (int pos=0; pos+recsize<=BLOCK_SIZE; pos += recsize) {
			page.setInt(pos, EMPTY);
			makeDefaultRecord(page, pos);
		}
	}

	private void makeDefaultRecord(Page page, int pos) {
		for (String fldname : ti.schema().fields()) {
			int offset = ti.offset(fldname);
			if (ti.schema().type(fldname) == INTEGER)
				page.setInt(pos + INT_SIZE + offset, 0);
			else
				page.setString(pos + INT_SIZE + offset, "");
		}
	}
}

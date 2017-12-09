package simpledb.record;

import java.util.HashMap;
import java.util.Map;

import static java.sql.Types.INTEGER;
import static simpledb.file.Page.INT_SIZE;
import static simpledb.file.Page.STR_SIZE;

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
 * @version 1.0 CreateTime：2017/12/9 19:11
 */
public class TableInfo {
	private Schema schema;
	private Map<String, Integer> offsets;
	private int recordlen;
	private String tblname;

	public TableInfo(String tablname, Schema schema) {
		this.schema = schema;
		this.tblname = tablname;
		offsets = new HashMap<String, Integer>();
		int pos = 0;
		for (String fldname : schema.fields()) {
			offsets.put(fldname, pos);
			pos += lengthInBytes(fldname);
		}
		recordlen = pos;
	}

	/**
	 * Creates a TableInfo object from the
	 * specified metadata.
	 * This constructor is used when the metadata
	 * is retrieved from the catalog.
	 * @param tblname the name of the table
	 * @param schema the schema of the table's records
	 * @param offsets the already-calculated offsets of the fields within a record
	 * @param recordlen the already-calculated length of each record
	 */
	public TableInfo(String tblname, Schema schema, Map<String,Integer> offsets, int recordlen) {
		this.tblname   = tblname;
		this.schema    = schema;
		this.offsets   = offsets;
		this.recordlen = recordlen;
	}

	/**
	 * Returns the filename assigned to this table.
	 * Currently, the filename is the table name
	 * followed by ".tbl".
	 * @return the name of the file assigned to the table
	 */
	public String fileName() {
		return tblname + ".tbl";
	}

	/**
	 * Returns the schema of the table's records
	 * @return the table's record schema
	 */
	public Schema schema() {
		return schema;
	}

	/**
	 * Returns the offset of a specified field within a record
	 * @param fldname the name of the field
	 * @return the offset of that field within a record
	 */
	public int offset(String fldname) {
		return offsets.get(fldname);
	}

	/**
	 * Returns the length of a record, in bytes.
	 * @return the length in bytes of a record
	 */
	public int recordLength() {
		return recordlen;
	}

	private int lengthInBytes(String fldname) {
		int fldtype = schema.type(fldname);
		if (fldtype == INTEGER)
			return INT_SIZE;
		else
			return STR_SIZE(schema.length(fldname));
	}
}

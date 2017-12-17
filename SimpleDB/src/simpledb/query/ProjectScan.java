package simpledb.query;

import java.util.Collection;

/**
 * <p>Title:simpledb.query.MyProject</p>
 * <p>
 * <p>Description: </p>
 * <p>
 * <p>Copyright: Copyright (c) 2001-2013 Newland SoftWare Company</p>
 * <p>
 * <p>Company: Newland SoftWare Company</p>
 *
 * @author Steven
 * @version 1.0 CreateTime：2017/12/17 17:27
 */
public class ProjectScan implements Scan{
	private Scan s;
	private Collection<String> fieldlist;

	/**
	 * Creates a project scan having the specified
	 * underlying scan and field list.
	 * @param s the underlying scan
	 * @param fieldlist the list of field names
	 */
	public ProjectScan(Scan s, Collection<String> fieldlist) {
		this.s = s;
		this.fieldlist = fieldlist;
	}

	public void beforeFirst() {
		s.beforeFirst();
	}

	public boolean next() {
		return s.next();
	}

	public void close() {
		s.close();
	}

	public Constant getVal(String fldname) {
		if (hasField(fldname))
			return s.getVal(fldname);
		else
			throw new RuntimeException("field " + fldname + " not found.");
	}

	public int getInt(String fldname) {
		if (hasField(fldname))
			return s.getInt(fldname);
		else
			throw new RuntimeException("field " + fldname + " not found.");
	}

	public String getString(String fldname) {
		if (hasField(fldname))
			return s.getString(fldname);
		else
			throw new RuntimeException("field " + fldname + " not found.");
	}

	/**
	 * Returns true if the specified field
	 * is in the projection list.
	 * @see simpledb.query.Scan#hasField(java.lang.String)
	 */
	public boolean hasField(String fldname) {
		return fieldlist.contains(fldname);
	}
}

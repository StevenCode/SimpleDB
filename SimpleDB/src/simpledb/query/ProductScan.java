package simpledb.query;

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
 * @version 1.0 CreateTime：2017/12/16 21:20
 */
public class ProductScan implements Scan {
	private Scan s1, s2;

	/**
	 * Creates a product scan having the two underlying scans.
	 * @param s1 the LHS scan
	 * @param s2 the RHS scan
	 */
	public ProductScan(Scan s1, Scan s2) {
		this.s1 = s1;
		this.s2 = s2;
		s1.next();
	}

	@Override
	public void beforeFirst() {
		s1.beforeFirst();
		s1.next();
		s2.beforeFirst();
	}

	@Override
	public boolean next() {
		if (s2.next())
			return true;
		else {
			s2.beforeFirst();
			return s2.next() && s1.next();
		}
	}

	@Override
	public void close() {
		s1.close();
		s2.close();
	}

	@Override
	public Constant getVal(String fldname) {
		if (s1.hasField(fldname))
			return s1.getVal(fldname);
		else
			return s2.getVal(fldname);
	}

	@Override
	public int getInt(String fldname) {
		if (s1.hasField(fldname))
			return s1.getInt(fldname);
		else
			return s2.getInt(fldname);
	}

	@Override
	public String getString(String fldname) {
		if (s1.hasField(fldname))
			return s1.getString(fldname);
		else
			return s2.getString(fldname);
	}

	@Override
	public boolean hasField(String fldname) {
		return s1.hasField(fldname) || s2.hasField(fldname);
	}
}

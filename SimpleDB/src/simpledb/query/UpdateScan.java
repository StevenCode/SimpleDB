package simpledb.query;

import simpledb.record.RID;

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
 * @version 1.0 CreateTime：2017/12/10 10:52
 */
public interface UpdateScan extends Scan {
	/**
	 * Modifies the field value of the current record.
	 * @param fldname the name of the field
	 * @param val the new value, expressed as a Constant
	 */
	public void setVal(String fldname, Constant val);

	/**
	 * Modifies the field value of the current record.
	 * @param fldname the name of the field
	 * @param val the new integer value
	 */
	public void setInt(String fldname, int val);

	/**
	 * Modifies the field value of the current record.
	 * @param fldname the name of the field
	 * @param val the new string value
	 */
	public void setString(String fldname, String val);

	/**
	 * Inserts a new record somewhere in the scan.
	 */
	public void insert();

	/**
	 * Deletes the current record from the scan.
	 */
	public void delete();

	/**
	 * Returns the RID of the current record.
	 * @return the RID of the current record
	 */
	public RID getRid();

	/**
	 * Positions the scan so that the current record has
	 * the specified RID.
	 * @param rid the RID of the desired record
	 */
	public void moveToRid(RID rid);
}

package simpledb.materialize;

import simpledb.query.Plan;
import simpledb.query.Scan;
import simpledb.query.UpdateScan;
import simpledb.record.Schema;
import simpledb.record.TableInfo;
import simpledb.tx.Transaction;

import static simpledb.file.Page.BLOCK_SIZE;

/**
 * <p>Title:simpledb.materialize.MyProject</p>
 * <p>
 * <p>Description: </p>
 * <p>
 * <p>Copyright: Copyright (c) 2001-2013 Newland SoftWare Company</p>
 * <p>
 * <p>Company: Newland SoftWare Company</p>
 *
 * @author Steven
 * @version 1.0 CreateTime：2017/12/16 21:46
 */
public class MaterializePlan implements Plan{
	private Plan srcplan;
	private Transaction tx;

	/**
	 * Creates a materialize plan for the specified query.
	 * @param srcplan the plan of the underlying query
	 * @param tx the calling transaction
	 */
	public MaterializePlan(Plan srcplan, Transaction tx) {
		this.srcplan = srcplan;
		this.tx = tx;
	}

	@Override
	public Scan open() {
		Schema sch = srcplan.schema();
		TempTable temp = new TempTable(sch, tx);
		Scan src = srcplan.open();
		UpdateScan dest = temp.open();
		while (src.next()) {
			dest.insert();
			for (String fldname : sch.fields())
				dest.setVal(fldname, src.getVal(fldname));
		}
		src.close();
		dest.beforeFirst();
		return dest;
	}

	@Override
	public int blocksAccessed() {
		// create a dummy TableInfo object to calculate record length
		TableInfo ti = new TableInfo("", srcplan.schema());
		double rpb = (double) (BLOCK_SIZE / ti.recordLength());
		return (int) Math.ceil(srcplan.recordsOutput() / rpb);
	}

	@Override
	public int recordsOutput() {
		return srcplan.recordsOutput();	}

	@Override
	public int distinctValues(String fldname) {
		return srcplan.distinctValues(fldname);
	}

	@Override
	public Schema schema() {
		return srcplan.schema();
	}
}

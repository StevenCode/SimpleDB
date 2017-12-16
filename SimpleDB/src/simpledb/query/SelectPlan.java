package simpledb.query;

import simpledb.record.Schema;

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
 * @version 1.0 CreateTime：2017/12/16 16:50
 */
public class SelectPlan implements Plan{
	private Plan p;
	private Predicate pred;

	public SelectPlan(Plan p, Predicate pred) {
		this.p = p;
		this.pred = pred;
	}

	@Override
	public Scan open() {
		Scan s = p.open();
		return new SelectScan(s, pred);
	}

	/**
	 * Estimates the number of block accesses in the selection,
	 * which is the same as in the underlying query.
	 * @see simpledb.query.Plan#blocksAccessed()
	 */
	public int blocksAccessed() {
		return p.blocksAccessed();
	}

	/**
	 * Estimates the number of output records in the selection,
	 * which is determined by the
	 * reduction factor of the predicate.
	 * @see simpledb.query.Plan#recordsOutput()
	 */
	public int recordsOutput() {
		return p.recordsOutput() / pred.reductionFactor(p);
	}

	/**
	 * Estimates the number of distinct field values
	 * in the projection.
	 * If the predicate contains a term equating the specified
	 * field to a constant, then this value will be 1.
	 * Otherwise, it will be the number of the distinct values
	 * in the underlying query
	 * (but not more than the size of the output table).
	 * @see simpledb.query.Plan#distinctValues(java.lang.String)
	 */
	public int distinctValues(String fldname) {
		if (pred.equatesWithConstant(fldname) != null)
			return 1;
		else {
			String fldname2 = pred.equatesWithField(fldname);
			if (fldname2 != null)
				return Math.min(p.distinctValues(fldname),
						p.distinctValues(fldname2));
			else
				return Math.min(p.distinctValues(fldname),
						recordsOutput());
		}
	}

	@Override
	public Schema schema() {
		return p.schema();
	}
}

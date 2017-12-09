package simpledb.planner;

import simpledb.parse.QueryData;
import simpledb.query.Plan;
import simpledb.tx.Transaction;

/**
 * <p>Title:simpledb.planner.MyProject</p>
 * <p>
 * <p>Description: </p>
 * <p>
 * <p>Copyright: Copyright (c) 2001-2013 Newland SoftWare Company</p>
 * <p>
 * <p>Company: Newland SoftWare Company</p>
 *
 * @author Steven
 * @version 1.0 CreateTime：2017/12/9 18:57
 */
public interface QueryPlanner {
	/**
	 * Creates a plan for the parsed query.
	 * @param data the parsed representation of the query
	 * @param tx the calling transaction
	 * @return a plan for that query
	 */
	public Plan createPlan(QueryData data, Transaction tx);
}

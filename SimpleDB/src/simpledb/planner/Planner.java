package simpledb.planner;

import simpledb.parse.CreateTableData;
import simpledb.parse.InsertData;
import simpledb.parse.Parser;
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
 * @version 1.0 CreateTime：2017/12/9 18:52
 */
public class Planner {
	private QueryPlanner qplanner;
	private UpdatePlanner uplanner;

	public Planner(QueryPlanner qplanner, UpdatePlanner uplanner) {
		this.qplanner = qplanner;
		this.uplanner = uplanner;
	}

	/**
	 * Creates a plan for an SQL select statement, using the supplied planner.
	 * @param qry the SQL query string
	 * @param tx the transaction
	 * @return the scan corresponding to the query plan
	 */
	public Plan createQueryPlan(String qry, Transaction tx) {
		Parser parser = new Parser(qry);
		QueryData data = parser.query();
		return qplanner.createPlan(data, tx);
	}

	public int executeUpdate(String cmd, Transaction tx) {
		Parser parser = new Parser(cmd);
		Object obj = parser.updateCmd();

		if (obj instanceof InsertData)
			return uplanner.executeInsert((InsertData)obj, tx);
		if (obj instanceof CreateTableData)
			return uplanner.executeCreateTable((CreateTableData) obj, tx);
		else
			return 0;
	}
}

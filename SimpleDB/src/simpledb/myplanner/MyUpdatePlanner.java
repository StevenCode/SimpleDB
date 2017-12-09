package simpledb.myplanner;

import simpledb.parse.*;
import simpledb.planner.UpdatePlanner;
import simpledb.tx.Transaction;

/**
 * <p>Title:simpledb.myplanner.MyProject</p>
 * <p>
 * <p>Description: </p>
 * <p>
 * <p>Copyright: Copyright (c) 2001-2013 Newland SoftWare Company</p>
 * <p>
 * <p>Company: Newland SoftWare Company</p>
 *
 * @author Steven
 * @version 1.0 CreateTime：2017/12/9 19:05
 */
public class MyUpdatePlanner implements UpdatePlanner{
	@Override
	public int executeInsert(InsertData data, Transaction tx) {
		return 0;
	}

	@Override
	public int executeDelete(DeleteData data, Transaction tx) {
		return 0;
	}

	@Override
	public int executeModify(ModifyData data, Transaction tx) {
		return 0;
	}

	@Override
	public int executeCreateTable(CreateTableData data, Transaction tx) {
		return 0;
	}

	@Override
	public int executeCreateView(CreateViewData data, Transaction tx) {
		return 0;
	}

	@Override
	public int executeCreateIndex(CreateIndexData data, Transaction tx) {
		return 0;
	}
}

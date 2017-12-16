package simpledb.myplanner;

import simpledb.index.Index;
import simpledb.metadate.IndexInfo;
import simpledb.parse.*;
import simpledb.planner.UpdatePlanner;
import simpledb.query.*;
import simpledb.record.RID;
import simpledb.server.SimpleDB;
import simpledb.tx.Transaction;

import java.util.Iterator;
import java.util.Map;

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
		String tblname = data.tableName();
		Plan plan = new TablePlan(tblname, tx);

		UpdateScan upSc = (UpdateScan) plan.open();

		upSc.insert();
		RID rid = upSc.getRid();

		Map<String, IndexInfo> indexes = SimpleDB.mdMgr().getIndexInfo(tblname, tx);

		Iterator<Constant> valIter = data.vals().iterator();

		String fldname;
		int i = 0;
		while (i < data.fields().size()) {
			i++;

			fldname = data.fields().get(i);

			Constant constant = valIter.next();
			System.out.println("Modify field " + fldname + "to value" + constant);
			upSc.setVal(fldname, constant);

			IndexInfo inIn = indexes.get(fldname);

			if (inIn != null) {

				Index index = inIn.open();
				index.insert(constant, rid);
				index.close();
			}
		}

		upSc.close();
		return 1;
	}
	public int executeDelete(DeleteData data, Transaction tx) {

		String tblname = data.tableName();
		Plan plan = new TablePlan(tblname, tx);
		plan = new SelectPlan(plan, data.pred());

		Map<String,IndexInfo> indexes = SimpleDB.mdMgr().getIndexInfo(tblname, tx);

		UpdateScan upSc = (UpdateScan) plan.open();
		int count = 0;

		while(upSc.next()) {

			RID rid = upSc.getRid();

			int i = 0;
			String fldname;

			while (i < indexes.keySet().size()) {

				i++;

				fldname = indexes.keySet().iterator().next();
				//fldname = indexes.keySet().toArray()[i].toString();
				Constant constant = upSc.getVal(fldname);
				Index index = indexes.get(fldname).open();
				index.delete(constant, rid);
				index.close();
			}

			upSc.delete();
			count++;
		}

		upSc.close();
		return count;
	}

	public int executeModify(ModifyData data, Transaction tx) {

		String tblname = data.tableName();
		String fldname = data.targetField();
		Plan plan = new TablePlan(tblname, tx);
		plan = new SelectPlan(plan, data.pred());

		IndexInfo inIn = SimpleDB.mdMgr().getIndexInfo(tblname, tx).get(fldname);

		Index index = (inIn == null) ? null : inIn.open();

		UpdateScan upSc = (UpdateScan) plan.open();
		int count = 0;

		while(upSc.next()) {

			Constant newval = data.newValue().evaluate(upSc);
			Constant oldval = upSc.getVal(fldname);
			upSc.setVal(data.targetField(), newval);


			if (index != null) {

				RID rid = upSc.getRid();
				index.delete(oldval, rid);
				index.insert(newval, rid);
			}

			count++;
		}

		if (index != null) index.close();
		upSc.close();

		return count;
	}

	@Override
	public int executeCreateTable(CreateTableData data, Transaction tx) {
		SimpleDB.mdMgr().createTable(data.tableName(), data.newSchema(), tx);
		return 0;
	}

	@Override
	public int executeCreateView(CreateViewData data, Transaction tx) {
		return 0;
	}

	@Override
	public int executeCreateIndex(CreateIndexData data, Transaction tx) {
		SimpleDB.mdMgr().createIndex(data.indexName(), data.tableName(), data.fieldName(), tx);
		return 0;
	}
}

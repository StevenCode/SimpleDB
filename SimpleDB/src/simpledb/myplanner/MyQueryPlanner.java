package simpledb.myplanner;

import simpledb.index.query.IndexSelectPlan;
import simpledb.metadate.IndexInfo;
import simpledb.multibuffer.MultiBufferProductPlan;
import simpledb.parse.QueryData;
import simpledb.planner.QueryPlanner;
import simpledb.query.*;
import simpledb.record.Schema;
import simpledb.server.SimpleDB;
import simpledb.tx.Transaction;

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
 * @version 1.0 CreateTime：2017/12/16 18:30
 */
public class MyQueryPlanner implements QueryPlanner{
	@Override
	public Plan createPlan(QueryData data, Transaction tx) {
		return null;
	}

	class MyPlanner{

		protected Map<String, IndexInfo> _indexes;

		protected Transaction _tx;
		protected Schema _mySchema;
		protected TablePlan _myPlan;
		protected Predicate _myPredicate;

		public MyPlanner(String tableName, Predicate myPredicate, Transaction tx) {

			this._myPredicate = myPredicate;

			this._tx = tx;

			this._myPlan   = new TablePlan(tableName, this._tx);

			this._mySchema = this._myPlan.schema();

			this._indexes  = SimpleDB.mdMgr().getIndexInfo(tableName, this._tx);

		}

		public Plan makeSelect() {

			for (String fieldName : this._indexes.keySet()) {
				Constant constant_ = _myPredicate.equatesWithConstant(fieldName);

				if (constant_ != null) {
					IndexInfo indexInfo = this._indexes.get(fieldName);
					Plan p = new IndexSelectPlan(this._myPlan, indexInfo, constant_, this._tx);

					// select prediction
					Predicate selectPredicate = _myPredicate.selectPred(this._mySchema);
					if (selectPredicate != null)
						return new SelectPlan(p, selectPredicate);
					else
						return p;

				}
			}

			// select prediction
			Predicate selectPredicate = _myPredicate.selectPred(this._mySchema);

			if (selectPredicate != null)
				return new SelectPlan(this._myPlan, selectPredicate);

			else
				return this._myPlan;
		}

		public Plan createProduct(Plan current) {

			// select prediction
			Predicate selectPredicate = _myPredicate.selectPred(this._mySchema);

			if (selectPredicate != null) {

				return new MultiBufferProductPlan(current, new SelectPlan(this._myPlan, selectPredicate), this._tx);

			} else {

				return new MultiBufferProductPlan(current, this._myPlan, this._tx);

			}

		}

	}
}

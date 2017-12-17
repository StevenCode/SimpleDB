package simpledb.server;

import simpledb.buffer.BufferMgr;
import simpledb.file.FileMgr;
import simpledb.log.LogMgr;
import simpledb.metadate.MetadataMgr;
import simpledb.myplanner.MyQueryPlanner;
import simpledb.myplanner.MyUpdatePlanner;
import simpledb.planner.Planner;
import simpledb.planner.QueryPlanner;
import simpledb.planner.UpdatePlanner;
import simpledb.tx.Transaction;

/**
 * <p>Title:simpledb.server.MyProject</p>
 * <p>
 * <p>Description: </p>
 * <p>
 * <p>Copyright: Copyright (c) 2001-2013 Newland SoftWare Company</p>
 * <p>
 * <p>Company: Newland SoftWare Company</p>
 *
 * @author Steven
 * @version 1.0 CreateTime：2017/12/4 23:35
 */
public class SimpleDB {
	public static int BUFFER_SIZE = 8;
	public static String LOG_FILE = "simpledb.log";

	private static FileMgr     fm;
	private static BufferMgr   bm;
	private static LogMgr      logm;
	private static MetadataMgr mdm;

	/**
	 * Initializes the system.
	 * This method is called during system startup.
	 * @param dirname the name of the database directory
	 */
	public static void init(String dirname) {
		initFileLogAndBufferMgr(dirname);
		Transaction tx = new Transaction();
		boolean isnew = fm.isNew();
		if (isnew)
			System.out.println("creating new database");
		else {
			System.out.println("recovering existing database");
//			tx.recover();
		}
		initMetadataMgr(isnew, tx);
		tx.commit();
	}

	// The following initialization methods are useful for
	// testing the lower-level components of the system
	// without having to initialize everything.

	/**
	 * Initializes only the file manager.
	 * @param dirname the name of the database directory
	 */
	public static void initFileMgr(String dirname) {
		fm = new FileMgr(dirname);
	}

	/**
	 * Initializes the file and log managers.
	 * @param dirname the name of the database directory
	 */
	public static void initFileAndLogMgr(String dirname) {
		initFileMgr(dirname);
		logm = new LogMgr(LOG_FILE);
	}

	/**
	 * Initializes the file, log, and buffer managers.
	 * @param dirname the name of the database directory
	 */
	public static void initFileLogAndBufferMgr(String dirname) {
		initFileAndLogMgr(dirname);
		bm = new BufferMgr(BUFFER_SIZE);
	}

	/**
	 * Initializes metadata manager.
	 * @param isnew an indication of whether a new
	 * database needs to be created.
	 * @param tx the transaction performing the initialization
	 */
	public static void initMetadataMgr(boolean isnew, Transaction tx) {
		mdm = new MetadataMgr(isnew, tx);
	}

	public static FileMgr     fileMgr()   { return fm; }
	public static BufferMgr   bufferMgr() { return bm; }
	public static LogMgr      logMgr()    { return logm; }
	public static MetadataMgr mdMgr()     { return mdm; }

	/**
	 * Creates a planner for SQL commands.
	 * To change how the planner works, modify this method.
	 * @return the system's planner for SQL commands
	 */public static Planner planner() {
		// QueryPlanner  qplanner = new BasicQueryPlanner();
		// UpdatePlanner uplanner = new BasicUpdatePlanner();
		QueryPlanner qplanner = new MyQueryPlanner();
		UpdatePlanner uplanner = new MyUpdatePlanner();
		return new Planner(qplanner, uplanner);
	}
}

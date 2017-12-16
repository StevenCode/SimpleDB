package simpledb.multibuffer;

import simpledb.server.SimpleDB;

/**
 * <p>Title:simpledb.multibuffer.MyProject</p>
 * <p>
 * <p>Description: </p>
 * <p>
 * <p>Copyright: Copyright (c) 2001-2013 Newland SoftWare Company</p>
 * <p>
 * <p>Company: Newland SoftWare Company</p>
 *
 * @author Steven
 * @version 1.0 CreateTime：2017/12/16 21:09
 */
public class BufferNeeds {

	/**
	 * This method considers the various roots
	 * of the specified output size (in blocks),
	 * and returns the highest root that is less than
	 * the number of available buffers.
	 * @param size the size of the output file
	 * @return the highest number less than the number of available buffers, that is a root of the plan's output size
	 */
	public static int bestRoot(int size) {
		int avail = SimpleDB.bufferMgr().available();
		if (avail <= 1)
			return 1;
		int k = Integer.MAX_VALUE;
		double i = 1.0;
		while (k > avail) {
			i++;
			k = (int)Math.ceil(Math.pow(size, 1/i));
		}
		return k;
	}

	/**
	 * This method considers the various factors
	 * of the specified output size (in blocks),
	 * and returns the highest factor that is less than
	 * the number of available buffers.
	 * @param size the size of the output file
	 * @return the highest number less than the number of available buffers, that is a factor of the plan's output size
	 */
	public static int bestFactor(int size) {
		int avail = SimpleDB.bufferMgr().available();
		if (avail <= 1)
			return 1;
		int k = size;
		double i = 1.0;
		while (k > avail) {
			i++;
			k = (int)Math.ceil(size / i);
		}
		return k;
	}
}

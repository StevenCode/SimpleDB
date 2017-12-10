package simpledb.index.btree;

import simpledb.file.Block;
import simpledb.query.Constant;
import simpledb.record.RID;
import simpledb.record.TableInfo;
import simpledb.tx.Transaction;

/**
 * <p>Title:simpledb.index.btree.MyProject</p>
 * <p>
 * <p>Description: </p>
 * <p>
 * <p>Copyright: Copyright (c) 2001-2013 Newland SoftWare Company</p>
 * <p>
 * <p>Company: Newland SoftWare Company</p>
 *
 * @author Steven
 * @version 1.0 CreateTime：2017/12/10 16:49
 */
public class BTreeLeaf {
	private TableInfo ti;
	private Transaction tx;
	private Constant searchkey;
	private BTreePage contents;
	private int currentslot;

	/**
	 * Opens a page to hold the specified leaf block.
	 * The page is positioned immediately before the first record
	 * having the specified search key (if any).
	 * @param blk a reference to the disk block
	 * @param ti the metadata of the B-tree leaf file
	 * @param searchkey the search key value
	 * @param tx the calling transaction
	 */
	public BTreeLeaf(Block blk, TableInfo ti, Constant searchkey, Transaction tx) {
		this.ti = ti;
		this.tx = tx;
		this.searchkey = searchkey;
		contents = new BTreePage(blk, ti, tx);
		currentslot = contents.findSlotBefore(searchkey);
	}


	/**
	 * Closes the leaf page.
	 */
	public void close() {
		contents.close();
	}

	/**
	 * Moves to the next leaf record having the
	 * previously-specified search key.
	 * Returns false if there is no more such records.
	 * @return false if there are no more leaf records for the search key
	 */
	public boolean next() {
		currentslot++;
		if (currentslot >= contents.getNumRecs())
			return tryOverflow();
		else if (contents.getDataVal(currentslot).equals(searchkey))
			return true;
		else
			return tryOverflow();
	}

	/**
	 * Returns the dataRID value of the current leaf record.
	 * @return the dataRID of the current record
	 */
	public RID getDataRid() {
		return contents.getDataRid(currentslot);
	}

	/**
	 * Deletes the leaf record having the specified dataRID
	 * @param datarid the dataRId whose record is to be deleted
	 */

	public void delete(RID datarid) {
		while(next())
			if(getDataRid().equals(datarid)) {
				contents.delete(currentslot);
				return;
			}
	}
	private boolean tryOverflow() {
		Constant firstkey = contents.getDataVal(0);
		int flag = contents.getFlag();
		if (!searchkey.equals(firstkey) || flag < 0)
			return false;
		contents.close();
		Block nextblk = new Block(ti.fileName(), flag);
		contents = new BTreePage(nextblk, ti, tx);
		currentslot = 0;
		return true;
	}
}

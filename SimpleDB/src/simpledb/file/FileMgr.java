package simpledb.file;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;

import static simpledb.file.Page.BLOCK_SIZE;

/**
 * <p>Title:simpledb.file.MyProject</p>
 * <p>
 * <p>Description: </p>
 * <p>
 * <p>Copyright: Copyright (c) 2001-2013 Newland SoftWare Company</p>
 * <p>
 * <p>Company: Newland SoftWare Company</p>
 *
 * @author Steven
 * @version 1.0 CreateTime：2017/12/4 22:14
 */
public class FileMgr {
	private File dbDirectory;
	private boolean isNew;
	private Map<String, FileChannel> openFiles = new HashMap<String, FileChannel>();

	public FileMgr(String dbname) {
		String homedir = System.getProperty("user.home");
		dbDirectory = new File(homedir, dbname);
		isNew = !dbDirectory.exists();

		// create the directory if the database is new
		if (isNew && !dbDirectory.mkdir())
			throw new RuntimeException("cannot create " + dbname);

		// remove any leftover temporary tables
		for (String filename : dbDirectory.list()) {
			if (filename.startsWith("temp"))
				new File(dbDirectory, filename).delete();
		}
	}

	/**
	 * Reads the contents of disk block into bytebuffer.
	 * @param blk a reference to disk block
	 * @param bb the bytebuffer
	 */
	synchronized void read(Block blk, ByteBuffer bb) {
		try {
			bb.clear();
			FileChannel fc = getFile(blk.fileName());
			fc.read(bb, blk.number() * BLOCK_SIZE);
		} catch (IOException e) {
			throw new RuntimeException("cannot read block " + blk);
		}
	}

	/**
	 * Writes the contents of a bytebuffer into a disk block.
	 * @param blk a reference to a disk block
	 * @param bb the bytebuffer
	 */
	synchronized void write(Block blk, ByteBuffer bb) {
		try {
			bb.rewind();
			FileChannel fc = getFile(blk.fileName());
			fc.write(bb, blk.number() * BLOCK_SIZE);
		} catch (IOException e) {
			throw new RuntimeException("cannot write block" + blk);
		}
	}

	/**
	 * Appends the contents of a bytebuffer to the end
	 * of the specified simpledb.file.
	 * @param filename the name of simpledb.file
	 * @param bb the bytebuffer
	 * @return a reference to the newly-created block.
	 */
	synchronized Block append(String filename, ByteBuffer bb) {
		int newblknum = size(filename);
		Block blk = new Block(filename, newblknum);
		write(blk, bb);
		return blk;
	}

	/**
	 * Returns the number of blocks in the specified simpledb.file.
	 * @param filename the name of the simpledb.file
	 * @return the number of blocks in the simpledb.file
	 */
	public synchronized int size(String filename) {
		try {
			FileChannel fc = getFile(filename);
			return (int) (fc.size() / BLOCK_SIZE);
		} catch (IOException e) {
			throw new RuntimeException("cannot access " + filename);
		}
	}

	/**
	 * Returns a boolean indicating whether the simpledb.file manager
	 * had to create a new database directory.
	 * @return true if the database is new
	 */
	public boolean isNew() {
		return isNew;
	}
	/**
	 * Returns the simpledb.file channel for the specified filename.
	 * The simpledb.file channel is stored in a map keyed on the filename.
	 * If the simpledb.file is not open, then it is opened and the simpledb.file channel
	 * is added to the map.
	 * @param filename the simpledb.file channel associated filename
	 * @return the simpledb.file channel associated with the open simpledb.file.
	 * @throws IOException
	 */
	private FileChannel getFile(String filename) throws IOException {
		FileChannel fc = openFiles.get(filename);
		if (fc == null) {
			File dbTable = new File(dbDirectory, filename);
			RandomAccessFile f = new RandomAccessFile(dbTable, "rws");
			fc = f.getChannel();
			openFiles.put(filename, fc);
		}
		return fc;
	}
}

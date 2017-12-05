package simpledb.buffer;

/**
 * A runtime exception indicating that the transaction
 * needs to abort because a simpledb.buffer request could not be satisfied.
 */
@SuppressWarnings("serial")
public class BufferAbortException extends RuntimeException {}

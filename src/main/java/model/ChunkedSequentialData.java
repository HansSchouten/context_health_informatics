package model;

import java.util.HashMap;

/**
 * This class represents an object which contains for each chunk the SequentialData object.
 * @author Hans Schouten
 *
 */
public class ChunkedSequentialData extends SequentialData {

	/**
	 * Serial ID.
	 */
	private static final long serialVersionUID = -849276162891794544L;

	/**
	 * The chunked data.
	 */
	protected HashMap<Object, SequentialData> chunkedData;

	/**
	 * ChunkedSequentialData constructor.
	 */
	public ChunkedSequentialData() {
		chunkedData = new HashMap<Object, SequentialData>();
	}

	/**
	 * Add a new chunk.
	 * @param key		the chunk identifier.
	 * @param data		the data corresponding with this chunk.
	 */
	public void add(Object key, SequentialData data) {
		chunkedData.put(key, data);
	}

	/**
	 * Return the data of one chunk.
	 * @param key		the chunk identifier.
	 * @return			the data corresponding with this chunk.
	 */
	public SequentialData get(Object key) {
		return chunkedData.get(key);
	}

	/**
	 * Return the number of chunks.
	 * @return			the number of chunks
	 */
	public int size() {
		return chunkedData.size();
	}

}

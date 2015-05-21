package analyze.chunking;

import model.Record;

/**
 * This class represents an interface for classes that can chunk objects.
 * @author Hans Schouten
 *
 */
public interface ChunkType {

	/**
	 * Return the key of the chunk to which the given record belongs.
	 * @param record	the record that needs to be added to a chunk
	 * @return			the chunk the given records belongs to
	 */
	Object getChunk(Record record);

}

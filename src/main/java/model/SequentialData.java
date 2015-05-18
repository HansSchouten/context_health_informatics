package model;

import java.util.TreeSet;

/**
 * This class represents data that has been ordered in sequential order.
 * @author Matthijs
 *
 */
public class SequentialData extends TreeSet<Record> {

    /**
     * Serial Version ID.
     */
	private static final long serialVersionUID = -5826890838002651687L;


	/**
	 * Add a recordList to this group.
	 * @param recordList   - Recordlist that should be added.
	 */
	public void addRecordList(RecordList recordList) {
		for (Record record : recordList)
			add(record);
	}

}

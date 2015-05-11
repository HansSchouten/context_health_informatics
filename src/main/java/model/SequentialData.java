package model;

import java.util.TreeSet;

public class SequentialData extends TreeSet<Record> {
	
	private static final long serialVersionUID = -5826890838002651687L;

	/**
	 * SequentialData constructor
	 */
	public SequentialData() {}
	
	/**
	 * Add a recordList to this group
	 * @param filePath
	 * @param recordList
	 */
	public void addRecordList(RecordList recordList)
	{
		for(Record record : recordList)
			add(record);
	}

}

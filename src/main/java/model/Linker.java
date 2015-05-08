package model;

import java.util.HashMap;
import java.util.List;

/**
 * Link groups together
 * @author Hans Schouten
 *
 */
public class Linker {
	
	/**
	 * Linker constructor
	 */
	public Linker() {}
	
	/**
	 * Link the given groups together using their primary keys
	 * @param groups
	 * @return
	 */
	public HashMap<String, Group> link(List<Group> fileGroups) {
		HashMap<String, Group> linkedGroups = new HashMap<String, Group>(); 
		
		for(Group fileGroup : fileGroups) {
			HashMap<String, RecordList> grouped = fileGroup.groupByPrimary();
			for(String id : grouped.keySet()) {
				if(linkedGroups.containsKey(id)) {
					Group linkedGroup = linkedGroups.get(id);
					linkedGroup.addRecordList(grouped.get(id));
				} else {
					Group newGroup = new Group(id);
					newGroup.addRecordList(grouped.get(id));
					linkedGroups.put(id, newGroup);
				}
			}
		}
		
		return linkedGroups;
	}

}

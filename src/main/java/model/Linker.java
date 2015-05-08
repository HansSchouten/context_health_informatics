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
	public HashMap<String, SequentialData> link(List<Group> fileGroups) {
		HashMap<String, SequentialData> linkedGroups = new HashMap<String, SequentialData>(); 
		
		for(Group fileGroup : fileGroups) {
			HashMap<String, RecordList> grouped = fileGroup.groupByPrimary();
			for(String id : grouped.keySet()) {
				if(linkedGroups.containsKey(id)) {
					SequentialData tree = linkedGroups.get(id);
					tree.addAll(grouped.get(id));
				} else {
					SequentialData tree = new SequentialData();
					tree.addAll(grouped.get(id));
					linkedGroups.put(id, tree);
				}
			}
		}
		
		return linkedGroups;
	}

}

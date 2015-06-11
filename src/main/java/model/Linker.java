package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class is used to link groups together.
 * A sequential data object is used after that.
 * @author Hans Schouten
 *
 */
public class Linker {

    /**
     * Linker constructor.
     */
    public Linker() { }

    /**
     * Link the given groups together using their primary keys.
     * @param fileGroups   - Files that need to be linked.
     * @return             - Element that is linked in sequential oreder.
     */
    public HashMap<String, SequentialData> link(List<Group> fileGroups) {
        HashMap<String, SequentialData> linkedGroups = new HashMap<String, SequentialData>();

        ArrayList<Group> addAll = new ArrayList<Group>();

        for (Group fileGroup : fileGroups) {
            if (fileGroup.primary.isNoKey()) {
                addAll.add(fileGroup);
            }
            HashMap<String, RecordList> grouped = fileGroup.groupByPrimary();
            for (String id : grouped.keySet()) {
                if (linkedGroups.containsKey(id)) {
                    SequentialData tree = linkedGroups.get(id);
                    tree.addAll(grouped.get(id));
                } else {
                    SequentialData tree = new SequentialData();
                    tree.addAll(grouped.get(id));
                    linkedGroups.put(id, tree);
                }
            }
        }
        // Add all the unknown groups
        for (Group group: addAll) {
            for (RecordList values : group.values()) {
                for (SequentialData linked : linkedGroups.values()) {
                    linked.addAll(values);
                }
            }
        }
        return linkedGroups;
    }
}

package model.importcontroller;

/**
 * This class represents a primary key, constisting of a column.
 * @author Matthijs
 *
 */
public class ColumnKey implements PrimaryKey {

    protected String name;
    
    public ColumnKey(String nm) {
        name = nm;
    }
    
    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean isFileNameKey() {
        return false;
    }

    @Override
    public boolean isNoKey() {
        return false;
    }

}

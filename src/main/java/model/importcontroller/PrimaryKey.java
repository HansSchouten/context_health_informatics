package model.importcontroller;

/**
 * This interface is used to represent a primary key.
 * @author Matthijs
 *
 */
public interface PrimaryKey {

    /**
     * This method should return whether the primary key is the filename.
     * @return      - True if the filename has a key, false otherwise.
     */
    public boolean isFileNameKey();

    /**
     * This method should return whether the filename has no key.
     * @return      - True if the group does not have a key, false otherwise.
     */
    public boolean isNoKey();
}

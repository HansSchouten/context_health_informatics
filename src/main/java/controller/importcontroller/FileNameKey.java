package controller.importcontroller;

/**
 * This class represents a primary key consisting of a file name.
 * @author Matthijs
 *
 */
public class FileNameKey implements PrimaryKey {

    /** This variable stores the message of this FileNameKey. */
    protected String message;

    /**
     * construct a FileName Key.
     * @param fileNameMessage       - Message to represent this file name key.
     */
    public FileNameKey(String fileNameMessage) {
        message = fileNameMessage;
    }

    @Override
    public String toString() {
        return message;
    }

    @Override
    public boolean isFileNameKey() {
        return true;
    }

    @Override
    public boolean isNoKey() {
        return false;
    }

}

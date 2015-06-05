package controller.importcontroller;

/**
 * This class represents a primary key, when none is given.
 * So no key is used here.
 * @author Matthijs
 *
 */
public class NoKey implements PrimaryKey {

    /** This variable stores the message of the no-key object. */
    protected String message;

    /**
     * Construct a no key object.
     * @param noKeyMessage      - Message to show for this key.
     */
    public NoKey(String noKeyMessage) {
        message = noKeyMessage;
    }

    @Override
    public String toString() {
        return message;
    }

    @Override
    public boolean isFileNameKey() {
        return false;
    }

    @Override
    public boolean isNoKey() {
        return true;
    }

}

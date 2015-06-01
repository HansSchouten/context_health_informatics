package model.importcontroller;
/**
 * This class creates keys, depending on the input.
 * @author Matthijs
 *
 */
public class KeyFactory {

    /** This variable stores the key factory of the program */
    private static KeyFactory keyfactory = new KeyFactory();

    /** This variable stores the message for no keys */
    protected String noKeyMessage;
    /** This variable stores the message for the filename as key */
    protected String fileNameMessage;

    /**
     * This method contstructs a new KeyFactory.
     */
    private KeyFactory () {
        noKeyMessage = "No primary key";
        fileNameMessage = "File name";
    }

    /**
     * This methods returns a primary key, depending on the name of the input.
     * @param name      - Name, for what you want a primary key.
     * @return          - Primary key for the file, NoKey if none.
     */
    public PrimaryKey getNewKey(String name) {
        if (name.equals(noKeyMessage)) {
            return new NoKey(noKeyMessage);
        } else if (name.equals(fileNameMessage)) {
            return new FileNameKey(fileNameMessage);
        } else {
            return new ColumnKey(name);
        }
    }

    /**
     * This method returns the only instance of the keyfactory.
     * @return      - The keyfactory in the program.
     */
    public static KeyFactory getInstance() {
        return keyfactory;
    }
}

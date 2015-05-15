package model;

/**
 * This interface defines a datafield,
 * In a class that implement this interface, some data is stored..
 * @author Matthijs
 *
 */
public interface DataField {

// Here we can specief all the operations that need to be done on all the data.
    
    /**
     * This method should return the Integer of the DataField
     * @return      - integer representation of this datafield
     * @throws UnsupportedFormatException   - thrown when the datafield cannot be converted to the required format.
     */
    public int getIntegerValue() throws UnsupportedFormatException;

}
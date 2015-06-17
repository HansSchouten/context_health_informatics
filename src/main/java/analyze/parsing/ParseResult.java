package analyze.parsing;

import java.io.Serializable;

/**
 * This interface defines a structure for objects that can be returned after parsing one line of the script.
 * @author Hans Schouten
 *
 */
public interface ParseResult extends Serializable {

    /**
     * Return the string representation of this object.
     * @return      the string representation
     */
    String toString();
}

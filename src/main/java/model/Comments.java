package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class represents the comments added to the record.
 * @author Matthijs
 *
 */
public class Comments implements Serializable {

    /**
     * The ID for serializing.
     */
    private static final long serialVersionUID = 6131524612383044454L;

    /**
     * Variable to store all the comments that are added to this list.
     */
    protected ArrayList<String> commentlist;

    /**
     * Construct a new comments object.
     */
    public Comments() {

        commentlist = new ArrayList<String>();
    }

    /**
     * This methods adds a comment to the commentlist.
     * @param comment
     *     - String containing the comment that needs to be added to the commentlist.
     */
    public void addComments(final String comment) {
        commentlist.add(comment);
    }

    /**
     * String that prints all the comments that are in this commentlist.
     * @param delimiter - Delimiter used to print several comments.
     * @return            - String containing all the comments
     */
    public String printComments(final String delimiter) {
        StringBuffer buf = new StringBuffer();
        for (String comment: commentlist) {
            buf.append(comment);
            buf.append(delimiter);
        }

        if (buf.length() > delimiter.length()) {
            return buf.substring(0, buf.length() - delimiter.length());
        }

        return buf.toString();
    }
}

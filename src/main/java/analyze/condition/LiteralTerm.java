package analyze.condition;

import model.Record;
import model.datafield.DataField;

/**
 * This class represents a literal term.
 * In other words a term that is just a value.
 * @author Matthijs
 *
 */
public class LiteralTerm implements Expression {

    /**
     * Variable that stores the value of the literal.
     */
    private DataField value;

    /**
     * Construct a literal component with the value in a datafield.
     * @param literal       - value of the literal.
     */
    public LiteralTerm(DataField literal) {
        value = literal;
    }

    @Override
    public DataField evaluate(Record record) {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}

package analyze.parsing;

import analyze.AnalyzeException;
import model.DataField;
import model.SequentialData;

/**
 * This interface defines the outline of a SubParser object.
 * @author Hans Schouten
 *
 */
interface SubParser {

    /**
     * Parse the operation on the given data using the given arguments.
     * @param operation                       the operation that needs to be performed
     * @param data                            the data to execute the operation on
     * @return                                the result of executing the operation
     * @throws AnalyzeException               Analyzing was not possible
     */
    ParseResult parseOperation(String operation, SequentialData data) throws AnalyzeException;

    /**
     * Parse the operation on the given data using the given arguments.
     * @param operation                       the operation that needs to be performed
     * @param data                            the data to execute the operation on
     * @return                                the result of executing the operation
     * @throws AnalyzeException               Analyzing was not possible
     */
    ParseResult parseOperation(String operation, DataField data) throws AnalyzeException;

}

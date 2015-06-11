package analyze.parsing;

import java.util.HashMap;
import java.util.Scanner;

import analyze.AnalyzeException;
import model.ChunkedSequentialData;
import model.datafield.DataField;
import model.datafield.DataFieldString;
import model.Record;
import model.SequentialData;

/**
 * This class represents an object that will parse the typed language.
 * @author Hans Schouten
 *
 */
public class Parser {

    /**
     * This HashMap stores for each variable the corresponding value.
     */
    protected HashMap<String, ParseResult> variables;

    /**
     * Parser constructor.
     */
    public Parser() {
        this.variables = new HashMap<String, ParseResult>();
    }

    /**
     * Parse the given script.
     * @param script               the script that needs to be parsed
     * @param input                the inputdata
     * @return                     the result of parsing the script
     * @throws Exception    exception thrown if script can't be parsed correctly
     */
    public ParseResult parse(String script, ParseResult input) throws Exception {
        ParseResult result = input;
        ParseResult resultWithoutVar = input;

        Scanner scanner = new Scanner(script);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] lineDataSplit = line.split("USING", 2);
            String lineWithoutUsing = lineDataSplit[0].trim();
            input = resultWithoutVar;
            if (lineDataSplit.length == 2) {
                String variable = lineDataSplit[1].trim();
                if (!variables.containsKey(variable)) {
                    scanner.close();
                    throw new ParseException("Using undefined variable: " + variable);
                }
                input = variables.get(variable);
            }

            String variable = null;
            if (lineWithoutUsing.startsWith("$")) {
                String[] variableOperationSplit = lineWithoutUsing.split(" =", 2);
                if (variableOperationSplit.length == 1) {
                    scanner.close();
                    throw new ParseException("'" + lineWithoutUsing + "' contains no valid operation");
                }
                variable = variableOperationSplit[0];
                lineWithoutUsing = variableOperationSplit[1].trim();
            }

            result = parseLine(replaceVariables(lineWithoutUsing), input);

            if (variable != null) {
                variables.put(variable, result);
            } else {
                resultWithoutVar = result;
            }
        }
        scanner.close();

        return result;
    }

    /**
     * Replace all occurrences of variables for the string representation of the first record in the sequential data.
     * @param line              a line that possibly contains variables
     * @return                  the line after replacing all variables
     * @throws ParseException   something went wrong while replacing variables
     */
    protected String replaceVariables(String line) throws ParseException {
        String replacedVars = line;

        for (String variable : variables.keySet()) {
            ParseResult data = variables.get(variable);
            if (!(data instanceof DataField) && replacedVars.contains(variable)) {
                throw new ParseException("Inline variables are only allowed to contain single values");
            }
            replacedVars = replacedVars.replaceAll("\\" + variable, data.toString());
        }
        return replacedVars;
    }

    /**
     * Parse one line of the script.
     * @param line                    the line that needs to be parsed
     * @param data                    the data to perform this operation on
     * @return                        the result of parsing the line
     * @throws AnalyzeException       exception thrown if script can't be parsed correctly
     */
    protected ParseResult parseLine(String line, ParseResult data) throws AnalyzeException {
        if (line.trim().isEmpty()) {
            return data;
        }

        String[] operatorOperationSplit = line.split(" ", 2);
        if (operatorOperationSplit.length == 1) {
            throw new ParseException("'" + line + "' contains no valid operation");
        }

        String operator = operatorOperationSplit[0];
        SubParser parser = this.getSubParser(operator);
        String operation = operatorOperationSplit[1].trim();

        ParseResult result;
        if (data instanceof ChunkedSequentialData) {
            ChunkedSequentialData chunkedData = ((ChunkedSequentialData) data);
            result = this.parseChunkedSequentialData(parser, operation, chunkedData);
        } else if (data instanceof DataField) {
            result = parser.parseOperation(operation, ((DataField) data));
        } else {
            result = parser.parseOperation(operation, ((SequentialData) data));
        }

        return result;
    }

    /**
     * Parse the given operation on chunked SequentialData.
     * @param parser                   the subparser that can parse the given operation
     * @param operation                the operation that needs to be parsed
     * @param chunkedData              the chunked data
     * @return                         the results of performing the operation on each chunk
     * @throws AnalyzeException        something went wrong during analysis
     */
    protected SequentialData parseChunkedSequentialData(
                SubParser parser, String operation, ChunkedSequentialData chunkedData
            ) throws AnalyzeException {
        SequentialData result = new SequentialData();
        for (Object chunk : chunkedData.getChunkedData().keySet()) {
            ParseResult chunkResult = parser.parseOperation(operation, chunkedData.get(chunk));
            if (chunkResult instanceof SequentialData) {
                for (Record record : (SequentialData) chunkResult) {
                    record.put("chunk", new DataFieldString(chunk.toString()));
                    result.add(record);
                }
            } else {
                // Use the timestamp of the first record of this chunk to maintain correct order
                Record record = new Record(chunkedData.get(chunk).pollFirst().getTimeStamp());
                record.put("chunk", new DataFieldString(chunk.toString()));
                record.put(operation, (DataField) chunkResult);
                result.add(record);
            }
        }
        return result;
    }

    /**
     * Get the SubParser that can parse the given operator.
     * @param operator           the operator for which we want the SubParser
     * @return                   the SubParser for this operation
     * @throws ParseException    Something went wrong while parsing
     */
    protected SubParser getSubParser(String operator) throws ParseException {
        switch (operator.toLowerCase()) {
        case "chunk":
            return new ChunkingParser();
        case "compute":
            return new ComputingParser();
        case "label":
            return new CodingParser();
        case "filter":
            return new ConstrainParser();
        case "comment":
            return new CommentingParser();
        case "compare":
            return new ComparisonParser();
        case "exclude":
            return new ProjectParser();
        case "convert":
            return new ConversionParser();
        default:
            throw new ParseException("'" + operator + "' is not a valid operation");
        }
    }
}

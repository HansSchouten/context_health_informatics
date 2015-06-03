package analyze.parsing;

import java.util.HashMap;
import java.util.Scanner;

import analyze.AnalyzeException;
import model.ChunkedSequentialData;
import model.Record;
import model.SequentialData;

/**
 * This class represents an object that will parse the typed language.
 * @author Hans Schouten
 *
 */
public class Parser {

    /**
     * This HashMap stores for each variable the corresponding SequentialData.
     */
    protected HashMap<String, SequentialData> variables;

    /**
     * Parser constructor.
     */
    public Parser() {
        this.variables = new HashMap<String, SequentialData>();
    }

    /**
     * Parse the given script.
     * @param script               the script that needs to be parsed
     * @param input                the inputdata
     * @return                     the result of parsing the script
     * @throws AnalyzeException    exception thrown if script can't be parsed correctly
     */
    public SequentialData parse(String script, SequentialData input) throws AnalyzeException {
        SequentialData result = input;

        Scanner scanner = new Scanner(script);
        while (scanner.hasNextLine()) {
          String line = scanner.nextLine();
          String[] lineDataSplit = line.split("USING", 2);
          String lineWithoutUsing = lineDataSplit[0].trim();
          if (lineDataSplit.length == 2) {
              String variable = lineDataSplit[1].trim();
              if (!variables.containsKey(variable)) {
                  scanner.close();
                  throw new ParseException("Using undefined variable: " + variable);
              }
              result = variables.get(variable);
          }
          result = parseLine(lineWithoutUsing, result);
        }
        scanner.close();

        return result;
    }

    /**
     * Parse one line of the script.
     * @param line                    the line that needs to be parsed
     * @param data                    the data to perform this operation on
     * @return                        the result of parsing the line
     * @throws AnalyzeException       exception thrown if script can't be parsed correctly
     */
    protected SequentialData parseLine(String line, SequentialData data) throws AnalyzeException {
        String variable = null;
        if (line.startsWith("$")) {
            String[] variableOperationSplit = line.split(" =", 2);
            if (variableOperationSplit.length == 1) {
                throw new ParseException("'" + line + "' contains no valid operation");
            }
            variable = variableOperationSplit[0];
            line = variableOperationSplit[1].trim();
        }

        String[] operatorOperationSplit = line.split(" ", 2);
        if (operatorOperationSplit.length == 1) {
            throw new ParseException("'" + line + "' contains no valid operation");
        }

        String operator = operatorOperationSplit[0];
        SubParser parser = this.getSubParser(operator);
        String operation = operatorOperationSplit[1].trim();

        SequentialData result = new SequentialData();
        if (data instanceof ChunkedSequentialData) {
            ChunkedSequentialData chunkedData = ((ChunkedSequentialData) data);
            result = this.parseChunkedSequentialData(parser, operation, chunkedData);
        } else {
            result = parser.parseOperation(operation, data);
        }

        if (variable != null) {
            variables.put(variable, result);
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
            SequentialData chunkResult = parser.parseOperation(operation, chunkedData.get(chunk));
            if (chunkResult.size() == 1) {
                // Use the timestamp of the first record of this chunk to maintain correct order
                Record record = chunkResult.pollFirst();
                record.setTimeStamp(chunkedData.get(chunk).pollFirst().getTimeStamp());
                result.add(record);
            } else {
                result.addAll(chunkResult);
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
        default:
            throw new ParseException("'" + operator + "' is not a valid operation");
        }
    }

}

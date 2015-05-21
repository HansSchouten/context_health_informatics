package analyze.parsing;

import java.util.Scanner;

import model.SequentialData;
import model.UnsupportedFormatException;

/**
 * This class represents an object that will parse the typed language.
 * @author Hans Schouten
 *
 */
public class Parser {

	/**
	 * The data to execute the script on.
	 */
	protected SequentialData input;

	/**
	 * Parser constructor.
	 * @param inputData			the data to execute the script on
	 */
	public Parser(SequentialData inputData) {
		this.input = inputData;
	}

	/**
	 * Parse the given script.
	 * @param script			the script that needs to be parsed
	 * @return 					the result of parsing the script
	 * @throws UnsupportedFormatException 
	 * @throws ParseException 
	 * @throws ComputationTypeException 
	 */
	public SequentialData parse(String script) throws UnsupportedFormatException {
		SequentialData result = this.input;

		Scanner scanner = new Scanner(script);
		while (scanner.hasNextLine()) {
		  String line = scanner.nextLine();
		  result = parseLine(line, result);
		}
		scanner.close();

		return result;
	}

	/**
	 * Parse one line of the script.
	 * @param line				the line that needs to be parsed
	 * @param data				the data to perform this operation on
	 * @return 					the result of parsing the line
	 * @throws UnsupportedFormatException 
	 * @throws ParseException 
	 * @throws ComputationTypeException 
	 */
	protected SequentialData parseLine(String line, SequentialData data) throws UnsupportedFormatException {
		String[] splitted = line.split(" ", 2);
		String operator = splitted[0];
		String operation = splitted[1];

		SubParser parser = this.getSubParser(operator);
		return parser.parseOperation(operation, data);
	}

	/**
	 * Get the SubParser that can parse the given operator.
	 * @param operator			the operator for which we want the SubParser
	 * @return					the SubParser for this operation
	 */
	protected SubParser getSubParser(String operator) {
		switch (operator.toLowerCase()) {
		case "chunk":
			return new ChunkingParser();
		case "compute":
			return new ComputingParser();
		default:
			//TODO
			//unsupported operation exception
			return null;
		}
	}

}

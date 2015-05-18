package model.parsing;

import java.util.Scanner;

import model.SequentialData;

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
	 */
	public SequentialData parse(String script) {
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
	 */
	protected SequentialData parseLine(String line, SequentialData data) {
		String[] splitted = line.split(" ", 2);
		String operation = splitted[0];
		String[] arguments = splitted[1].split(" ");

		SubParser parser = this.getSubParser(operation);
		return parser.parseOperation(arguments, data);
	}

	/**
	 * Get the SubParser that will parse the given operation.
	 * @param operation			the operation for which we want the SubParser
	 * @return					the SubParser for this operation
	 */
	protected SubParser getSubParser(String operation) {
		switch (operation.toLowerCase()) {
		case "chunk":
			return new ChunkingParser();
		default:
			//TODO:
			//unsupported operation exception
			return null;
		}
	}

}

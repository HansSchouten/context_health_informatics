package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class is used to write the data back to the file.
 * @author Elvan
 *
 */
public class Writer {

    /**
     * This variable stores the delimiter of the file.
     */
	protected String delimiter;

	/**
     * This variable stores the columns of the sequential data object.
     */
	protected Column[] columns;

    /**
     * Create a new writer object.
     * @param del       - The delimiter of the file.
     */
	public Writer(String del) {
		delimiter = del;
	}

	/**
     * Makes a string of the column names.
     * @param columns       - List of selected columns
     * @return 				- String containing column names
     */
	public String columnNamesToString(Column[] columns) {
		StringBuilder out = new StringBuilder();

		for (int i = 0; i < columns.length; i++) {
	          out.append(columns[i].getName() + delimiter);
		}
		out.setLength(out.length() - 1);
		out.append("\r\n ");

		String output = out.toString();

		return output;
	}

	/**
	 * This method writes the data to the file.
	 * @param data         - Data to be written.
	 * @param fileName     - File to write to.
	 * @param extension    - Extension of the file.
	 * @param columns      - List of selected columns.
	 * @param colnames	   - If you want the column names included
	 * @throws IOException - When the file cannot be found
	 */
	public void writeData(SequentialData data, String fileName, String extension,
			Column[] columns, Boolean colnames) throws IOException {
        if (data.size() != 0) {
        	if (!extension.startsWith(".")) {
        		extension = "." + extension;
        	}
        	if (!fileName.endsWith(extension)) {
        	    fileName += extension;
        	}
 
            FileWriter fw = new FileWriter(fileName, false);
            
            if (colnames) {
            	fw.write(columnNamesToString(columns));
            }
            fw.write(data.toString(delimiter, columns));
            fw.close();
        }
    }

	/**
	 * Writes a file to a given location with a string as content.
	 * @param file The file to be written
	 * @param text The content of the file
	 * @throws IOException - If close goes wrong.
	 */
	public static void writeFile(File file, String text) throws IOException {
	    FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(file);
			fileWriter.write(text);
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		    if (fileWriter != null) {
		        fileWriter.close();
		    }
		}
	}
}

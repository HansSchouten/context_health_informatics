package graphs;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class represents a CSV reader to read CSV files.
 * @author Matthijs
 *
 */
public class CSVReader{

    /**
     * This method creates a CSVReader.
     */
    public CSVReader() { }

    /**
     * This function reads the CSV file.
     * @return      - Recordlist, containing the CSV file.
     * @throws IOException 
     */
    public String[][] readCSV(String filepath) throws IOException {
        BufferedReader bufferedReader = null;
        try {
        bufferedReader = new BufferedReader(new FileReader(filepath));
        String[] names = getNames(bufferedReader.readLine());
        
        
        } catch (IOException e) {
            System.out.println("wrong");
        }
        if (bufferedReader != null) {
            bufferedReader.close();
        }
        return null;
    }

    /**
     * This function gets the names the columns of the file.
     * @param readLine      - The line with the names.
     * @return              - array with the names of the columns.
     */
    private String[] getNames(String readLine) {
        String[] parts = readLine.split(",");
        
        for (int i = 0; i < parts.length; i++) {
            parts[i] = parts[i].trim();
        }
        
        return parts;
    }

}

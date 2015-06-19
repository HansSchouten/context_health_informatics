package analyze.comparing;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.text.ParseException;

import model.Column;
import model.ColumnType;
import model.DateColumn;
import model.DateUtils;
import model.Reader;
import model.Record;
import model.RecordList;
import model.SequentialData;
import model.datafield.DataField;
import model.datafield.DataFieldDouble;
import model.datafield.DataFieldString;

import org.junit.Before;
import org.junit.Test;

import analyze.AnalyzeException;
import analyze.parsing.ComputingParser;
import analyze.parsing.Parser;

public class ComparerTest {

    Column[] columns = 
        {new Column("column1", ColumnType.STRING), new Column("column2", ColumnType.STRING)};
            
    private String delimiter = ",";
    private SequentialData userData; 
    private SequentialData actual;
    private SequentialData result;
    private SequentialData expected;
    private Parser p;

    @Before
    public void setup() throws IOException, ParseException {
        
        columns[0] = new DateColumn("datum1", ColumnType.DATEandTIME, "yyyy-MM-dd HH:mm", true);
        columns[1] = new DateColumn("datum2", ColumnType.DATEandTIME, "yyyy-MM-dd HH:mm", true);

        userData = new SequentialData();

        Reader reader = new Reader(columns, delimiter);
        RecordList recordList = reader.read("src/main/resources/testfiles/test_comparing.txt", false);
        userData.addRecordList(recordList);
        
        p = new Parser();
        
        expected = new SequentialData();
        
        Record expect = new Record(DateUtils.parseDate(
                "1111/11/11",
                "yyyy/mm/DD"));
        DataFieldString result = new DataFieldString("0y0m5d 3h5m");
        expect.put("Time difference", result);
        expected.add(expect);

    }
    
    /** Test the actual comparison between datecolumns
     * @throws ParseException 
     * @throws AnalyzeException 
     */
    @Test
    public void parseCompareTest() throws ParseException, AnalyzeException {
        Comparer comparer = new Comparer(userData, columns[0], columns[1]);
                
        actual = comparer.compare();

        assertEquals(expected.last().get("Time difference").toString(),
                actual.last().get("Time difference").toString());

    }
    
    /** Test the parsing of the comparison operation
     * @throws ParseException 
     * @throws AnalyzeException 
     */
    @Test
    public void testParseTimeBetween() throws AnalyzeException, ParseException, Exception {
        result = (SequentialData) p.parse("COMPARE COL(datum1) AND COL(datum2)", userData);
            
        assertEquals(expected.last().get("Time difference").toString(), 
                result.last().get("Time difference").toString());

    }
    
    /** Test the parsing of the comparison operation
     * @throws ParseException 
     * @throws AnalyzeException 
     */
    @Test
    public void testParseTimeBetween2() throws AnalyzeException, ParseException, Exception {
        result = (SequentialData) p.parse("COMPARE COL(datum2) AND COL(datum1)", userData);
        
        String expected = "-0y0m-5d -3h-5m";
        
        assertEquals(expected, result.last().get("Time difference").toString());

    }
    
    /** Test the parsing of the comparison operation
     * @throws ParseException 
     * @throws AnalyzeException 
     * @throws IOException 
     */
    @Test
    public void testValueDifference() throws AnalyzeException, ParseException, IOException, Exception {
        Column[] columns2 =
            {new Column("value1", ColumnType.INT), new Column("value2", ColumnType.DOUBLE), new Column("value3", ColumnType.INT)};
        columns2[0] = new DateColumn("datum", ColumnType.DATE, "yyMMdd", true);

        Reader reader2 = new Reader(columns2, delimiter);
        RecordList recordList2 = reader2.read("src/main/resources/testfiles/test_comparing2.txt", false);
        SequentialData values = new SequentialData();
        values.addRecordList(recordList2);

        actual = (SequentialData) p.parse("COMPARE COL(value2) AND COL(value3)", values);

        Double expectedValue = -2.0;
        Double expectedValue2 = 3.0;

        assertEquals(expectedValue, actual.first().get("Value difference").getDoubleValue(), 0.01);
        assertEquals(expectedValue2, actual.last().get("Value difference").getDoubleValue(), 0.01);

    }
    
    /** Test the parsing of the measurement comparison
     * @throws Exception 
     */
    @Test
    public void testMeasurementDifference() throws Exception {
        Column[] columns2 =
            {new Column("value1", ColumnType.INT), new Column("value2", ColumnType.DOUBLE), new Column("value3", ColumnType.INT)};
        columns2[0] = new DateColumn("datum", ColumnType.DATE, "yyMMdd", true);

        Reader reader2 = new Reader(columns2, delimiter);
        RecordList recordList2 = reader2.read("src/main/resources/testfiles/test_comparing2.txt", false);
        SequentialData values = new SequentialData();
        values.addRecordList(recordList2);

        actual = (SequentialData) p.parse("COMPARE MEASUREMENTS COL(value2) COL(value3)", values);

        Double expectedValue = 2.0;
        Double expectedValue2 = -3.0;

        assertEquals(expectedValue, actual.first().get("Measurement difference").getDoubleValue(), 0.01);
        assertEquals(expectedValue2, actual.last().get("Measurement difference").getDoubleValue(), 0.01);

    }
}
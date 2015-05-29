package analyze.comparing;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.text.ParseException;

import model.Column;
import model.ColumnType;
import model.DataField;
import model.DataFieldDouble;
import model.DataFieldString;
import model.DateColumn;
import model.DateUtils;
import model.Reader;
import model.Record;
import model.RecordList;
import model.SequentialData;

import org.junit.Before;
import org.junit.Test;

import analyze.AnalyzeException;
import analyze.parsing.ComputingParser;
import analyze.parsing.Parser;

public class ComparerTest {

	Column[] columns = 
		{new Column("column1"), new Column("column2")};
			
	private String delimiter = ",";
	private SequentialData userData; 
	private SequentialData actual;
	private SequentialData result;
	private SequentialData expected;
	private Parser p;

	@Before
	public void setup() throws IOException, ParseException {
		
		columns[0] = new DateColumn("datum1", "yyyy-MM-dd HH:mm", true);
	    columns[0].setType(ColumnType.DATEandTIME);
	
		columns[1] = new DateColumn("datum2", "yyyy-MM-dd HH:mm", true);
	    columns[1].setType(ColumnType.DATEandTIME);

	    userData = new SequentialData();

	    Reader reader = new Reader(columns, delimiter);
		RecordList recordList = reader.read("src/main/resources/test_comparing.txt", false);
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
		
		assertEquals(expected.first().get("Time difference").getStringValue(), actual.first().get("Time difference").getStringValue());

    }
	
	/** Test the parsing of the comparison operation
	 * @throws ParseException 
	 * @throws AnalyzeException 
	 */
	@Test
	public void testParseTimeBetween() throws AnalyzeException, ParseException {
		result = p.parse("COMPARE TIME BETWEEN datum1 AND datum2", userData);
		
		System.out.println("res" + result);
	
		assertEquals(expected.first().get("Time difference").getStringValue(), result.first().get("Time difference").getStringValue());

	}
}

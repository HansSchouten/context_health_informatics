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

		assertEquals(expected.last().get("Time difference").getStringValue(), actual.last().get("Time difference").getStringValue());

    }
	
	/** Test the parsing of the comparison operation
	 * @throws ParseException 
	 * @throws AnalyzeException 
	 */
	@Test
	public void testParseTimeBetween() throws AnalyzeException, ParseException {
		result = p.parse("COMPARE TIME BETWEEN datum1 AND datum2", userData);
			
		assertEquals(expected.last().get("Time difference").getStringValue(), result.last().get("Time difference").getStringValue());

	}
	
	/** Test the parsing of the comparison operation
	 * @throws ParseException 
	 * @throws AnalyzeException 
	 * @throws IOException 
	 */
	@Test
	public void testValueDifference() throws AnalyzeException, ParseException, IOException {
		Column[] columns2 =
			{new Column("value1"), new Column("value2"), new Column("value3")};
		columns2[0] = new DateColumn("datum", "yyMMdd", true);
	    columns2[0].setType(ColumnType.DATE);
	    columns2[1].setType(ColumnType.DOUBLE);
	    columns2[2].setType(ColumnType.INT);

		Reader reader2 = new Reader(columns2, delimiter);
		RecordList recordList2 = reader2.read("src/main/resources/test_comparing2.txt", false);
		SequentialData values = new SequentialData();
		values.addRecordList(recordList2);

		Comparer comparer = new Comparer(values, columns2[1], columns2[2]);

		actual = comparer.compare();

		Double expectedValue = 2.0;
		Double expectedValue2 = 3.0;

		assertEquals(expectedValue, actual.first().get("Value difference").getDoubleValue(), 0.01);
		assertEquals(expectedValue2, actual.last().get("Value difference").getDoubleValue(), 0.01);

	}
}

package analyze.computing;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.text.ParseException;

import model.Column;
import model.ColumnType;
import model.DataField;
import model.DataFieldDouble;
import model.DataFieldInt;
import model.DateColumn;
import model.DateUtils;
import model.Reader;
import model.Record;
import model.RecordList;
import model.SequentialData;
import model.UnsupportedFormatException;
import model.Writer;

import org.junit.Before;
import org.junit.Test;

import analyze.AnalyzeException;
import analyze.parsing.*;

public class ComputerTest {
	
	private Column[] columns = 
		{new Column("column1"), new Column("column2"), new Column("column3"), new Column("column4")};
	private String delimiter = ",";
	private SequentialData userData; 

	@Before
	public void setup() throws IOException {

		columns[0].setType(ColumnType.DOUBLE);
		columns[1] = new DateColumn("datum", "yyMMdd", true);
	    columns[1].setType(ColumnType.DATE);
	    columns[2] = new DateColumn("tijd", "HHmm", true);
	    columns[2].setType(ColumnType.TIME);
	    columns[3].setType(ColumnType.STRING);

	    userData = new SequentialData();

	    Reader reader = new Reader(columns, delimiter);
		RecordList recordList = reader.read("src/main/resources/test_input_compute.txt", false);

		userData.addRecordList(recordList);

	}

	/** Test the computation of SUM.
	 * @throws ParseException 
	 * @throws AnalyzeException 
	 */
	@Test
    public void parseSUMTest() throws ParseException, AnalyzeException {

		String operation = "SUM(COL(column1))";
		
		ComputingParser parser = new ComputingParser();

		SequentialData actual = parser.parseOperation(operation, userData);

		SequentialData expected = new SequentialData();
		Record expect = new Record(DateUtils.parseDate(
				"1111/11/11",
				"yyyy/mm/DD"));
		DataField result = new DataFieldDouble(42.0);
		expect.put("column1", result);
		expected.add(expect);

		assertEquals(expected.first().get("column1").getDoubleValue(), actual.first().get("column1").getDoubleValue(), 0.001);

    }
	

	/**Test the computation of MIN.
	 * @throws IOException
	 * @throws ParseException 
	 * @throws AnalyzeException 
	 */
	@Test
    public void parseMINTest() throws ParseException, AnalyzeException {
		String operation = "MIN(COL(column1))";
		
		ComputingParser parser = new ComputingParser();

		SequentialData actual = parser.parseOperation(operation, userData);

		SequentialData expected = new SequentialData();

		Record expect = new Record(DateUtils.parseDate(
				"1111/11/11",
				"yyyy/mm/DD"));
		DataField result = new DataFieldDouble(10.0);
		expect.put("column1", result);
		expected.add(expect);

		assertEquals(expected.first().get("column1").getDoubleValue(), actual.first().get("column1").getDoubleValue(), 0.001);

    }
	
	@Test(expected=UnsupportedFormatException.class)
	public void parseInvalidSUMTest() throws AnalyzeException, ParseException {
		String operation = "SUM(COL(datum))";
		
		ComputingParser parser = new ComputingParser();

		SequentialData actual = parser.parseOperation(operation, userData);

		SequentialData expected = new SequentialData();

		Record expect = new Record(DateUtils.parseDate(
				"1111/11/11",
				"yyyy/mm/DD"));
		DataField result = new DataFieldDouble(10.0);
		expect.put("column1", result);
		expected.add(expect);

		assertEquals(expected.first().get("column1").getDoubleValue(), actual.first().get("column1").getDoubleValue(), 0.001);

    }

	/** Test the computation of MAX
	 * @throws IOException
	 * @throws ParseException 
	 * @throws AnalyzeException 
	 */
	@Test
    public void parseMAXTest() throws ParseException, AnalyzeException {
		String operation = "MAX(COL(column1))";

		ComputingParser parser = new ComputingParser();

		SequentialData actual = parser.parseOperation(operation, userData);

		SequentialData expected = new SequentialData();

		Record expect = new Record(DateUtils.parseDate(
				"1111/11/11",
				"yyyy/mm/DD"));
		DataField result = new DataFieldDouble(17.0);
		expect.put("column1", result);
		expected.add(expect);

		assertEquals(expected.first().get("column1").getDoubleValue(), actual.first().get("column1").getDoubleValue(), 0.001);

    }

	/** Test the computation of sum of squares.
	 * @throws IOException
	 * @throws ParseException 
	 * @throws AnalyzeException 
	 */
	@Test
    public void parseSQUARESTest() throws ParseException, AnalyzeException {
		String operation = "SQUARED(COL(column1))";

		ComputingParser parser = new ComputingParser();

		SequentialData actual = parser.parseOperation(operation, userData);

		SequentialData expected = new SequentialData();

		Record expect = new Record(DateUtils.parseDate(
				"1111/11/11",
				"yyyy/mm/DD"));
		DataField result = new DataFieldDouble(26.0);
		expect.put("column1", result);
		expected.add(expect);

		assertEquals(expected.first().get("column1").getDoubleValue(), actual.first().get("column1").getDoubleValue(), 0.001);

    }

	/** Test the computation of average deviation
	 * @throws IOException
	 * @throws ParseException 
	 * @throws AnalyzeException 
	 */
	@Test
    public void parseDEVIATIONTest() throws ParseException, AnalyzeException {
		String operation = "DEVIATION(COL(column1))";

		ComputingParser parser = new ComputingParser();

		SequentialData actual = parser.parseOperation(operation, userData);

		SequentialData expected = new SequentialData();
		
		Record expect = new Record(DateUtils.parseDate(
				"1111/11/11",
				"yyyy/mm/DD"));
		DataField result = new DataFieldDouble(2.66);
		expect.put("column1", result);
		expected.add(expect);
		
		assertEquals(expected.first().get("column1").getDoubleValue(), actual.first().get("column1").getDoubleValue(), 0.1);

    }

	/** Test the computation of variance.
	 * @throws IOException
	 * @throws ParseException 
	 * @throws AnalyzeException 
	 */
	@Test
    public void parseVARIANCETest() throws ParseException, AnalyzeException {
		String operation = "VAR(COL(column1))";

		ComputingParser parser = new ComputingParser();

		SequentialData actual = parser.parseOperation(operation, userData);

		SequentialData expected = new SequentialData();

		Record expect = new Record(DateUtils.parseDate(
				"1111/11/11",
				"yyyy/mm/DD"));
		DataField result = new DataFieldDouble(13.0);
		expect.put("column1", result);
		expected.add(expect);

		assertEquals(expected.first().get("column1").getDoubleValue(), actual.first().get("column1").getDoubleValue(), 0.1);

    }

	/**
	 * Test the computation of AVERAGE
	 * @throws ParseException 
	 * @throws AnalyzeException 
	 */
	@Test
    public void parseAVGTest() throws ParseException, AnalyzeException {
        // COMPUTE AVERAGE(COL(creatinelevel))
		String operation = "AVERAGE(COL(column1))";

		ComputingParser parser = new ComputingParser();
		
		SequentialData actual = parser.parseOperation(operation, userData);

		SequentialData expected = new SequentialData();

		Record expect = new Record(DateUtils.parseDate(
				"1111/11/11",
				"yyyy/mm/DD"));
		DataField result = new DataFieldDouble(14.0);
		expect.put("column1", result);
		expected.add(expect);

		assertEquals(expected.first().get("column1").getDoubleValue(), actual.first().get("column1").getDoubleValue(), 0.001);

    }

	/**
	 * Test the computation of COUNT
	 * @throws IOException
	 * @throws ParseException 
	 * @throws AnalyzeException 
	 */
	@Test
    public void parseCOUNTTest() throws ParseException, AnalyzeException {
        // COMPUTE COUNT(COL(creatinelevel))
		String operation = "COUNT(COL(column1))";

		ComputingParser parser = new ComputingParser();

		SequentialData actual = parser.parseOperation(operation, userData);

		SequentialData expected = new SequentialData();
		
		Record expect = new Record(DateUtils.parseDate(
				"1111/11/11",
				"yyyy/mm/DD"));
		DataField result = new DataFieldInt(3);
		expect.put("column1", result);
		expected.add(expect);

		assertEquals(expected.first().get("column1").getIntegerValue(), 
				actual.first().get("column1").getIntegerValue());;

    }


}

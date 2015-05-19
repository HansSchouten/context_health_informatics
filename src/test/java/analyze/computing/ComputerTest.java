package analyze.computing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

import analyze.parsing.*;

public class ComputerTest {
	
	Column[] columns = 
		{new Column("column1"), new Column("column2"), new Column("column3"), new Column("column4")};
	String delimiter = ",";
	SequentialData userData; 
	
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
		RecordList recordList = reader.read("src/main/resources/test_input_compute.txt");
		
		userData.addRecordList(recordList);
	    
	}
	
	/**
	 * Test the computation of SUM
	 * @throws IOException
	 * @throws ParseException 
	 */
	@Test
    public void parseSUMTest() throws UnsupportedFormatException, ParseException {
   
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
	
	/**
	 * Test with invalid column type
	 * @throws UnsupportedFormatException 
	 * @throws ComputationTypeException 
	 * @throws IOException
	 * @throws ParseException 
	 */
	
	@Test(expected=UnsupportedFormatException.class)
	public void parseInvalidSUMTest() throws UnsupportedFormatException {
		String operation = "SUM(COL(datum))";
		
		ComputingParser parser = new ComputingParser();
		
		SequentialData res = parser.parseOperation(operation, userData);
		
	}
	
	/**
	 * Test the computation of AVERAGE
	 * @throws IOException
	 * @throws ParseException 
	 */
	@Test
    public void parseAVGTest() throws UnsupportedFormatException, ParseException {
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
	 */
	@Test
    public void parseCOUNTTest() throws UnsupportedFormatException, ParseException {
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
		
		assertEquals(expected.first().get("column1").getIntegerValue(), actual.first().get("column1").getIntegerValue());;
        
    }
	
	
	
	
	
	
	

}

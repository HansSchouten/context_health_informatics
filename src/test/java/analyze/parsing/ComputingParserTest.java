package analyze.parsing;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.text.ParseException;

import model.Column;
import model.ColumnType;
import model.DataField;
import model.DateColumn;
import model.Reader;
import model.Record;
import model.RecordList;
import model.SequentialData;
import model.UnsupportedFormatException;
import model.Writer;

import org.junit.Before;
import org.junit.Test;

import analyze.parsing.*;

public class ComputingParserTest {

	Column[] columns =
		{new Column("column1"), new Column("column2"), new Column("column3"), new Column("column4")};
	String delimiter = ",";
	SequentialData userData;

	ComputingParser parser;

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

		parser = new ComputingParser();

	}

	@Test
    public void parseSUMTest() throws UnsupportedFormatException {

		String operation = "SUM(COL(column1))";

		SequentialData res = parser.parseOperation(operation, userData);

		//Test if right computation is parsed
		assertEquals(parser.computation, "SUM");
		// Test if the right column name is parsed
		assertEquals(parser.colname, "column1");
    }

	@Test
    public void parseAVGTest() throws UnsupportedFormatException {
        // COMPUTE AVERAGE(COL(creatinelevel))
		String operation = "AVERAGE(COL(column1))";

		SequentialData res = parser.parseOperation(operation, userData);

		//Test if right computation is parsed
		assertEquals(parser.computation, "AVERAGE");

    }

	@Test
    public void parseCOUNTTest() throws UnsupportedFormatException {
        // COMPUTE SUM(COL(creatinelevel))
		String operation = "COUNT(COL(column1))";

		SequentialData res = parser.parseOperation(operation, userData);

		//Test if right computation is parsed
		assertEquals(parser.computation, "COUNT");

    }

	@Test
    public void parseDATUMTest() throws UnsupportedFormatException {
        // COMPUTE SUM(COL(creatinelevel))
		String operation = "COUNT(COL(datum))";

		SequentialData res = parser.parseOperation(operation, userData);

		//Test if right column is parsed
		assertEquals(parser.colname, "datum");

    }




}
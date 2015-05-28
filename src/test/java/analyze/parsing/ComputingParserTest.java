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

import analyze.AnalyzeException;
import analyze.parsing.*;

public class ComputingParserTest {

	Column[] columns =
		{new Column("column1", ColumnType.DOUBLE), new Column("column2", ColumnType.STRING), new Column("column3", ColumnType.STRING), new Column("column4", ColumnType.STRING)};
	String delimiter = ",";
	SequentialData userData;

	ComputingParser parser;

	@Before
	public void setup() throws IOException {

		columns[1] = new DateColumn("datum", ColumnType.DATE, "yyMMdd", true);
	    columns[2] = new DateColumn("tijd", ColumnType.TIME, "HHmm", true);

	    userData = new SequentialData();

	    Reader reader = new Reader(columns, delimiter);
		RecordList recordList = reader.read("src/main/resources/test_input_compute.txt", false);

		userData.addRecordList(recordList);

		parser = new ComputingParser();

	}

	@Test
    public void parseSUMTest() throws AnalyzeException {

		String operation = "SUM(COL(column1))";

		SequentialData res = parser.parseOperation(operation, userData);

		//Test if right computation is parsed
		assertEquals(parser.computation, "SUM");
		// Test if the right column name is parsed
		assertEquals(parser.colname, "column1");
    }

	@Test
    public void parseAVGTest() throws AnalyzeException {
        // COMPUTE AVERAGE(COL(creatinelevel))
		String operation = "AVERAGE(COL(column1))";

		SequentialData res = parser.parseOperation(operation, userData);

		//Test if right computation is parsed
		assertEquals(parser.computation, "AVERAGE");

    }

	@Test
    public void parseCOUNTTest() throws AnalyzeException {
        // COMPUTE SUM(COL(creatinelevel))
		String operation = "COUNT(COL(column1))";

		SequentialData res = parser.parseOperation(operation, userData);

		//Test if right computation is parsed
		assertEquals(parser.computation, "COUNT");

    }

	@Test
    public void parseDATUMTest() throws AnalyzeException {
        // COMPUTE SUM(COL(creatinelevel))
		String operation = "COUNT(COL(datum))";

		SequentialData res = parser.parseOperation(operation, userData);

		//Test if right column is parsed
		assertEquals(parser.colname, "datum");

    }




}
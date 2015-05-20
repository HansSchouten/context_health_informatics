package model;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

public class ConstraintTest {

	
	String delimiter = ",";

	@Test
	public void testFilterGreater() throws IOException  {
		Column[] columns = new Column[3];
		Column column1 = new Column("column1");
		Column column2 = new Column("column2");
		Column column3 = new Column("column3");
		
		columns[0] = column1;
		columns[1] = column2;
		columns[2] = column3;
		
		Reader reader = new Reader(columns, delimiter);
		RecordList recordList = reader.read("src/main/resources/test_input_constraint.txt");
		Constraint con = new Constraint(">", "column2", 2);
		
		Record rec = recordList.get(1);
		Record rec2 = recordList.get(2);
		
		RecordList list = new RecordList(columns);
		list.addRecord(rec);
		list.addRecord(rec2);
		
		assertEquals(list, con.filter(recordList));
		
	}

	@Test
	public void testFilterEqual() throws IOException  {
		
		Column[] columns = new Column[3];
		Column column1 = new Column("column1");
		Column column2 = new Column("column2");
		Column column3 = new Column("column3");
		
		columns[0] = column1;
		columns[1] = column2;
		columns[2] = column3;
		
		Reader reader = new Reader(columns, delimiter);
		RecordList recordList = reader.read("src/main/resources/test_input_constraint.txt");

		Constraint con = new Constraint("=", "column2", 5);
		
		Record rec = recordList.get(1);
		Record rec2 = recordList.get(2);
		
		RecordList list = new RecordList(columns);
		list.addRecord(rec);
		list.addRecord(rec2);
		
		assertEquals(list, con.filter(recordList));
		
	}
	
	@Test
	public void testFilterSmallerEqual() throws IOException  {
		
		Column[] columns = new Column[3];
		Column column1 = new Column("column1");
		Column column2 = new Column("column2");
		Column column3 = new Column("column3");
		
		columns[0] = column1;
		columns[1] = column2;
		columns[2] = column3;
		
		Reader reader = new Reader(columns, delimiter);
		RecordList recordList = reader.read("src/main/resources/test_input_constraint.txt");
	
		Constraint con = new Constraint("<=", "column2", 2);
		
		Record rec = recordList.get(0);
		
		RecordList list = new RecordList(columns);
		list.addRecord(rec);
		
		assertEquals(list, con.filter(recordList));
		
	} 
}
package model;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class ConstraintListTest {

	String delimiter = ",";

	@Test
	public void testFilterGreaterAndEqual() throws IOException  {
		Column[] columns = new Column[3];
		Column column1 = new Column("column1");
		Column column2 = new Column("column2");
		Column column3 = new Column("column3");
		
		columns[0] = column1;
		columns[1] = column2;
		columns[2] = column3;
		
		Reader reader = new Reader(columns, delimiter);
		RecordList recordList = reader.read("src/main/resources/test_input_constraint.txt", false);
		
		Constraint con = new Constraint(">", "column2", 2);
		Constraint con2 = new Constraint("=", "column3", 8);
		ConstraintList conlist = new ConstraintList();
		conlist.add(con);
		conlist.add(con2);
		
		Record rec2 = recordList.get(2);
		RecordList list = new RecordList(columns);
		list.addRecord(rec2);
		
		assertEquals(list, conlist.filterBinary(recordList, "AND"));
		
	}
	
	@Test
	public void testFilterGreaterAndEqual2() throws IOException  {
		Column[] columns = new Column[3];
		Column column1 = new Column("column1");
		Column column2 = new Column("column2");
		Column column3 = new Column("column3");
		
		columns[0] = column1;
		columns[1] = column2;
		columns[2] = column3;
		
		Reader reader = new Reader(columns, delimiter);
		RecordList recordList = reader.read("src/main/resources/test_input_constraint.txt", false);
		
		Constraint con = new Constraint(">", "column1", 4);
		Constraint con2 = new Constraint("=", "column3", 8);
		
		ConstraintList conlist = new ConstraintList();
		conlist.add(con);
		conlist.add(con2);
		
		Record rec2 = recordList.get(2);
		RecordList list = new RecordList(columns);
		
		
		assertEquals(list, conlist.filterBinary(recordList, "AND"));	
	} 
	
	@Test
	public void testFilterGreaterOrEqual() throws IOException  {
		Column[] columns = new Column[3];
		Column column1 = new Column("column1");
		Column column2 = new Column("column2");
		Column column3 = new Column("column3");
		
		columns[0] = column1;
		columns[1] = column2;
		columns[2] = column3;
		
		Reader reader = new Reader(columns, delimiter);
		RecordList recordList = reader.read("src/main/resources/test_input_constraint.txt", false);
		
		Constraint con = new Constraint(">", "column1", 4);
		Constraint con2 = new Constraint("=", "column3", 8);
		
		ConstraintList conlist = new ConstraintList();
		conlist.add(con);
		conlist.add(con2);
		
		Record rec2 = recordList.get(2);
		RecordList list = new RecordList(columns);
		list.add(rec2);
		
		assertEquals(list, conlist.filterBinary(recordList, "OR"));	
	}
	
	@Test
	public void testFilterSmallerGreaterOrEqual2() throws IOException  {
		Column[] columns = new Column[3];
		Column column1 = new Column("column1");
		Column column2 = new Column("column2");
		Column column3 = new Column("column3");
		
		columns[0] = column1;
		columns[1] = column2;
		columns[2] = column3;
		
		Reader reader = new Reader(columns, delimiter);
		RecordList recordList = reader.read("src/main/resources/test_input_constraint.txt", false);
		
		Constraint con = new Constraint(">=", "column1", 4);
		Constraint con2 = new Constraint("=", "column2", 2);
		
		ConstraintList conlist = new ConstraintList();
		conlist.add(con);
		conlist.add(con2);
		
		Record rec2 = recordList.get(2);
		Record rec1 = recordList.get(1);
		Record rec0 = recordList.get(0);
		
		RecordList list = new RecordList(columns);
		list.add(rec0);
		list.add(rec1);
		list.add(rec2);
		
		assertEquals(list, conlist.filterBinary(recordList, "OR"));	
	}
	


}

package model;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import controller.importcontroller.ColumnKey;
import controller.importcontroller.FileNameKey;
import controller.importcontroller.NoKey;

public class LinkerTest {
    
    @Test
    public void testLinkGroups1() throws IOException {
        Column[] cols1 = new Column[3];
        cols1[0] = new Column("patient", ColumnType.STRING);
        cols1[1] = new Column("group", ColumnType.STRING);
        cols1[2] = new DateColumn("date", ColumnType.DATE, "dd-MM-yyyy", true);
        Group hospital = new Group("Hospital Appointments", ",", cols1, new ColumnKey("patient"));
        hospital.addFile("src/main/resources/linkertest/hospital_appointments.txt", true);
        
        Column[] cols2 = new Column[3];
        cols2[0] = new Column("creatinine", ColumnType.STRING);
        cols2[1] = new Column("unit", ColumnType.STRING);
        cols2[2] = new DateColumn("date", ColumnType.DATE, "dd-MM-yyyy", true);
        Group admire = new Group("Statt sensor", ",", cols2, new FileNameKey("File name"));
        admire.addFile("src/main/resources/linkertest/ADMIRE_2.txt", true);
        
        ArrayList<Group> groups = new ArrayList<Group>();
        groups.add(hospital);
        groups.add(admire);
        
        Linker linker = new Linker();
        HashMap<String, SequentialData> linkedGroups = linker.link(groups);
        
        // Test whether for the user all corresponding records are present
        assertEquals(4, linkedGroups.get("2").size());
    }
    
    @Test
    public void testLinkGroups2() throws IOException {
        Column[] cols1 = new Column[3];
        cols1[0] = new Column("patient", ColumnType.STRING);
        cols1[1] = new Column("group", ColumnType.STRING);
        cols1[2] = new DateColumn("date", ColumnType.DATE, "dd-MM-yyyy", true);
        Group hospital = new Group("Hospital Appointments", ",", cols1, new ColumnKey("patient"));
        hospital.addFile("src/main/resources/linkertest/hospital_appointments.txt", true);
        
        Column[] cols2 = new Column[3];
        cols2[0] = new Column("creatinine", ColumnType.STRING);
        cols2[1] = new Column("unit", ColumnType.STRING);
        cols2[2] = new DateColumn("date", ColumnType.DATE, "dd-MM-yyyy", true);
        Group admire = new Group("Statt sensor", ",", cols2, new FileNameKey("File name"));
        admire.addFile("src/main/resources/linkertest/ADMIRE_2.txt", true);
        admire.addFile("src/main/resources/linkertest/ADMIRE_4.txt", true);
        
        ArrayList<Group> groups = new ArrayList<Group>();
        groups.add(hospital);
        groups.add(admire);
        
        Linker linker = new Linker();
        HashMap<String, SequentialData> linkedGroups = linker.link(groups);
        
        // Test whether for the users all corresponding records are present
        SequentialData groupUser2 = linkedGroups.get("2");
        assertEquals(4, groupUser2.size());
        SequentialData groupUser4 = linkedGroups.get("4");
        assertEquals(5, groupUser4.size());
    }
    
    @Test
    public void testLinkGroupsNoPrimary() throws IOException {
        Column[] cols1 = new Column[3];
        cols1[0] = new Column("patient", ColumnType.STRING);
        cols1[1] = new Column("group", ColumnType.STRING);
        cols1[2] = new DateColumn("date", ColumnType.DATE, "dd-MM-yyyy", true);
        Group hospital = new Group("Hospital Appointments", ",", cols1, new NoKey("No primary key"));
        hospital.addFile("src/main/resources/linkertest/hospital_appointments.txt", true);
        
        Column[] cols2 = new Column[3];
        cols2[0] = new Column("creatinine", ColumnType.STRING);
        cols2[1] = new Column("unit", ColumnType.STRING);
        cols2[2] = new DateColumn("date", ColumnType.DATE, "dd-MM-yyyy", true);
        Group admire = new Group("Statt sensor", ",", cols2, new FileNameKey("File name"));
        admire.addFile("src/main/resources/linkertest/ADMIRE_2.txt", true);
        admire.addFile("src/main/resources/linkertest/ADMIRE_4.txt", true);
        
        ArrayList<Group> groups = new ArrayList<Group>();
        groups.add(hospital);
        groups.add(admire);
        
        Linker linker = new Linker();
        HashMap<String, SequentialData> linkedGroups = linker.link(groups);
        
        // Test whether for the users all corresponding records are present
        SequentialData groupUser2 = linkedGroups.get("2");
        assertEquals(8, groupUser2.size());
        SequentialData groupUser4 = linkedGroups.get("4");
        assertEquals(8, groupUser4.size());
    }
}
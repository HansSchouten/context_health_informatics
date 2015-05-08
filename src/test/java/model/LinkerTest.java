package model;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

public class LinkerTest {
	
	@Test
	public void testLinkGroups1() throws IOException {
		String[] cols1 = {"patient", "group", "date"};
		Group hospital = new Group("Hospital Appointments", ",", cols1, "patient");
		hospital.addFile("src/main/resources/linkertest/hospital_appointments.txt");
		
		String[] cols2 = {"creatinine","unit"};
		Group admire = new Group("Statt sensor", ",", cols2, null);
		admire.addFile("src/main/resources/linkertest/ADMIRE_2.txt");
		
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
		String[] cols1 = {"patient", "group", "date"};
		Group hospital = new Group("Hospital Appointments", ",", cols1, "patient");
		hospital.addFile("src/main/resources/linkertest/hospital_appointments.txt");
		
		String[] cols2 = {"creatinine","unit"};
		Group admire = new Group("Statt sensor", ",", cols2, null);
		admire.addFile("src/main/resources/linkertest/ADMIRE_2.txt");
		admire.addFile("src/main/resources/linkertest/ADMIRE_4.txt");
		
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
	
}
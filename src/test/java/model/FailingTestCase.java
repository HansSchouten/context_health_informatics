package model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class FailingTestCase {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		assertTrue(false);
	}

}

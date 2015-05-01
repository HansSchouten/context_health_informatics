import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import controller.HelloWorld;


public class HelloWorldTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		HelloWorld w1 = new HelloWorld();
		assertEquals(w1.test(), 4);
	}

	@Test
	public void test1() {
		HelloWorld w1 = new HelloWorld();
		assertNotEquals(w1.test(), 5);
	}

	@Test
	public void test2() {
		HelloWorld w1 = new HelloWorld();
		assertNotEquals(w1.test(), 5);
	}

}

package model;

import static org.junit.Assert.*;

import java.text.ParseException;

import org.junit.Test;

/**
 * This JUNit test tests all the things regarding comments in the model. 
 * @author Matthijs
 *
 */
public class CommentsTest {

	@Test
	public void addCommentTest() {
		Comments comments = new Comments();
		comments.addComments("initial comment");
		assertTrue(comments.commentlist.contains("initial comment"));
	}
	
	@Test
	public void add2CommentTest() {
		Comments comments = new Comments();
		comments.addComments("initial comment");
		comments.addComments("second comment");
		assertTrue(comments.commentlist.contains("initial comment"));
		assertTrue(comments.commentlist.contains("second comment"));
	}
	
	@Test
	public void printTest() {
		Comments comments = new Comments();
		comments.addComments("initial comment");
		assertEquals(comments.printComments(";"), "initial comment;");
	}
	
	@Test
	public void print2Test() {
		Comments comments = new Comments();
		comments.addComments("initial comment");
		comments.addComments("second comment");
		assertEquals(comments.printComments(";"), "initial comment;second comment;");
	}
	
	@Test
	public void recordcommentTest() throws ParseException {
		Record record = new Record(DateUtils.parseDate("2015-05-07", "yyyy-MM-dd"));
		record.addCommentToRecord("initial comment");
		record.addCommentToRecord("second comment");
		assertEquals(record.printComments(";"), "initial comment;second comment;");
	}

}

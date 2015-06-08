package controller;


import static org.testfx.api.FxAssert.verifyThat;
import javafx.scene.control.RadioButton;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.matcher.control.ListViewMatchers;

public class ImportControllerTest extends FxRobot  {

    @Before
    public void before() throws Exception {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(MainApp.class);
    }

    @Test
    public void testAddingGroup() throws Exception {
        clickOn("#importAnchor");
        verifyThat("#groupListView", ListViewMatchers.hasItems(1));
        clickOn("Add group");
        verifyThat("#groupListView", ListViewMatchers.hasItems(2));
    }

    @Test
    public void testAddingColumn() throws Exception {
        clickOn("#importAnchor");
        verifyThat("#columnListView", ListViewMatchers.hasItems(1));
        clickOn("Add column");
        clickOn("Add column");
        clickOn("Add column");
        verifyThat("#columnListView", ListViewMatchers.hasItems(4));
    }

    @Test
    public void testSelectingDate() throws Exception {
        clickOn("#importAnchor");
        verifyThat("#columnListView", ListViewMatchers.hasItems(1));
        clickOn("String");
        clickOn("Date");
        clickOn("Sort");
    }

    @Test
    public void testDeletingItem() throws Exception {
        clickOn("#importAnchor");
        verifyThat("#columnListView", ListViewMatchers.hasItems(1));
        clickOn("Add column");
        clickOn("Add column");
        moveBy(22, 20);
        clickOn(MouseButton.PRIMARY);
        verifyThat("#columnListView", ListViewMatchers.hasItems(2));
    }

    @Test
    public void testNotDeletingItem() throws Exception {
        clickOn("#importAnchor");
        verifyThat("#columnListView", ListViewMatchers.hasItems(1));
        moveTo("Add column");
        moveBy(22, 20);
        clickOn(MouseButton.PRIMARY);
        verifyThat("#columnListView", ListViewMatchers.hasItems(1));
    }

    @Test
    public void testNewColumnOnEnter() throws Exception {
        clickOn("#importAnchor");
        verifyThat("#columnListView", ListViewMatchers.hasItems(1));
        moveTo("String");
        moveBy(-50, 0);
        clickOn(MouseButton.PRIMARY);
        write("test");
        push(KeyCode.ENTER);
        verifyThat("#columnListView", ListViewMatchers.hasItems(2));
    }

    @Test
    public void testSelectSingleDateTime() throws Exception {
        clickOn("#importAnchor");
        verifyThat("#columnListView", ListViewMatchers.hasItems(1));
        clickOn("Add column");
        verifyThat("#columnListView", ListViewMatchers.hasItems(2));

        // When sorting two Date columns, only one can be sorted
        moveTo("String");
        moveBy(0, 30);
        clickOn(MouseButton.PRIMARY);
        clickOn("Date");
        clickOn("Sort");

        clickOn("String");
        clickOn("Date/Time");
        clickOn("Sort");

        clickOn("Date/Time");
        clickOn("String");

        verifyThat("Sort", (RadioButton b) -> !b.isSelected());
    }

    @Test
    public void testSelectSingleDateTime2() throws Exception {
        clickOn("#importAnchor");
        verifyThat("#columnListView", ListViewMatchers.hasItems(1));
        clickOn("Add column");
        verifyThat("#columnListView", ListViewMatchers.hasItems(2));

        // When sorting a Date and Time column, both can be used to sort
        moveTo("String");
        moveBy(0, 30);
        clickOn(MouseButton.PRIMARY);
        clickOn("Time");
        clickOn("Sort");

        clickOn("String");
        clickOn("Date");
        clickOn("Sort");
        verifyThat("Sort", (RadioButton b) -> b.isSelected());

        clickOn("Time");
        clickOn("String");

        verifyThat("Sort", (RadioButton b) -> b.isSelected());
    }

}

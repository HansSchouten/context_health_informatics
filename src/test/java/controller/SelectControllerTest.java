package controller;

import static org.testfx.api.FxAssert.verifyThat;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.control.TabPane;
import model.Group;
import model.Record;
import model.SequentialData;
import model.datafield.DataFieldString;

import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.matcher.control.ListViewMatchers;

public class SelectControllerTest extends FxRobot {
    private MainApp mainApp;
    private TabPane tabPane;

    @Before
    public void before() throws Exception {
        FxToolkit.registerPrimaryStage();
        mainApp = (MainApp) FxToolkit.setupApplication(MainApp.class);

        tabPane = (TabPane) mainApp.getRootLayout().getScene().lookup("#tabPane");
        tabPane.getSelectionModel().selectedIndexProperty().removeListener(mainApp.getTabChanger());

        clickOn("2. Select");
    }

    @Test
    public void checkTest() {
        ArrayList<Group> groups = new ArrayList<Group>();
        mainApp.dataflowcontroller.selectcontroller.setData(groups);

        HashMap<String, SequentialData> linkedGroups = new HashMap<String, SequentialData>();
        SequentialData sd = new SequentialData();

        Record r = new Record(LocalDateTime.now());
        r.put("Test", new DataFieldString("Test"));
        r.put("Test2", new DataFieldString("Test2"));
        linkedGroups.put("Test", sd);

        mainApp.dataflowcontroller.selectcontroller.setLinkedGroups(linkedGroups);

        assertFalse(mainApp.dataflowcontroller.selectcontroller.validateInput(false));

        moveTo("2. Select");
        doubleClickOn("Test");

        assertTrue(mainApp.dataflowcontroller.selectcontroller.validateInput(false));
    }

    @Test
    public void checkSearch() {
        ArrayList<Group> groups = new ArrayList<Group>();
        mainApp.dataflowcontroller.selectcontroller.setData(groups);

        HashMap<String, SequentialData> linkedGroups = new HashMap<String, SequentialData>();
        SequentialData sd = new SequentialData();

        Record r = new Record(LocalDateTime.now());
        r.put("Test", new DataFieldString("Test"));
        r.put("Test2", new DataFieldString("Test2"));

        linkedGroups.put("Test", sd);

        mainApp.dataflowcontroller.selectcontroller.setLinkedGroups(linkedGroups);

        clickOn("");
        write("T");

        verifyThat("#identifierListView", ListViewMatchers.hasItems(1));

        doubleClickOn("Test");
        assertTrue(mainApp.dataflowcontroller.selectcontroller.validateInput(false));

        clickOn("T");
        write("awo;ei;osf;l");

        verifyThat("#identifierListView", ListViewMatchers.hasItems(0));
    }
}

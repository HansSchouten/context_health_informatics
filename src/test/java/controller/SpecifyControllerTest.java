package controller;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import javafx.application.Platform;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseButton;
import model.Record;
import model.SequentialData;
import model.datafield.DataFieldInt;
import model.datafield.DataFieldString;

import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;

public class SpecifyControllerTest extends FxRobot {
    private MainApp mainApp;
    private TabPane tabPane;

    @Before
    public void before() throws Exception {
        FxToolkit.registerPrimaryStage();
        mainApp = (MainApp) FxToolkit.setupApplication(MainApp.class);

        tabPane = (TabPane) mainApp.getRootLayout().getScene().lookup("#tabPane");
        tabPane.getSelectionModel().selectedIndexProperty().removeListener(mainApp.getTabChanger());
        
        clickOn("3. Analyse");
        
        SequentialData sd = new SequentialData();
        Record r = new Record(LocalDateTime.now());
        r.put("Test", new DataFieldString("Test"));
        r.put("Test2", new DataFieldString("Test2"));
        r.put("Value", new DataFieldInt(3));
        sd.add(r);
        mainApp.dataflowcontroller.specifycontroller.setData(sd);
    }
    
    @Test
    public void openVariableTest() {
        clickOn("Run");

        assertTrue(mainApp.dataflowcontroller.specifycontroller.getSelectedTab().getText().equals("New file"));

        doubleClickOn("$input");
        assertTrue(mainApp.dataflowcontroller.specifycontroller.getSelectedTab().getText().equals("$input"));

        doubleClickOn("$result");
        assertTrue(mainApp.dataflowcontroller.specifycontroller.getSelectedTab().getText().equals("$result"));
    }

    @Test
    public void runFailTest() {
        clickOn("New file");
        moveBy(0, 100);
        clickOn(MouseButton.PRIMARY);
        write("Ik haat testen");

        clickOn("Run");

        doubleClickOn("$input");

        assertValidateInput(false);
    }

    @Test
    public void runSuccessTest() {
        clickOn("New file");
        moveBy(0, 100);
        clickOn(MouseButton.PRIMARY);
        write("FILTER WHERE COL(Value) > 0");

        clickOn("Run");

        doubleClickOn("$result");

        assertValidateInput(true);
    }

    @Test
    public void openTabsTest() {
       clickOn("New file");

       moveBy(0, 100);
       clickOn(MouseButton.PRIMARY);
       write("<(*_*)>");
       clickOn("Run");
       clickOn("#note-label");

       clickOn("Script");
       clickOn("New");

       moveTo("New file");
       clickOn("Run");

       doubleClickOn("$result");

       assertValidateInput(true);
    }

    /**
     * Asserts the validate input function later in this thread.
     * @param b Whether to use assertTrue of assertFalse
     */
    private void assertValidateInput(boolean b) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                assertEquals(mainApp.dataflowcontroller.specifycontroller.validateInput(true), b);
            }
        });
    }
}

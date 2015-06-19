package controller;

import static org.junit.Assert.*;
import static org.testfx.api.FxAssert.verifyThat;

import java.time.LocalDateTime;

import javafx.application.Platform;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import model.Record;
import model.SequentialData;
import model.datafield.DataFieldDate;
import model.datafield.DataFieldInt;

import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.matcher.control.ListViewMatchers;

public class ResultsControllerTest extends FxRobot {
    private MainApp mainApp;
    private TabPane tabPane;

    @Before
    public void before() throws Exception {
        FxToolkit.registerPrimaryStage();
        
        try {
            mainApp = (MainApp) FxToolkit.setupApplication(MainApp.class);
        } catch (Exception e) {
            
        }

        tabPane = (TabPane) mainApp.getRootLayout().getScene().lookup("#tabPane");
        tabPane.getSelectionModel().selectedIndexProperty().removeListener(mainApp.getTabChanger());

        SequentialData sd = new SequentialData();
        
        Record r = new Record(LocalDateTime.now());
        r.put("Date", new DataFieldDate(LocalDateTime.now().minusDays(1)));
        r.put("Value", new DataFieldInt(1));
        sd.add(r);
        
        Record r2 = new Record(LocalDateTime.now());
        r2.put("Date", new DataFieldDate(LocalDateTime.now()));
        r2.put("Value", new DataFieldInt(2));
        sd.add(r2);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                mainApp.dataflowcontroller.resultcontroller.setData(sd);
            }
        });

        clickOn("4. Results");
    }
    
    @Test
    public void generalTest() {
        clickOn("Re-sort");
        clickOn("#includeColNamesTable");
        verifyThat("#includeColNamesTable", (CheckBox b) -> !b.isSelected());
    }

    @Test
    public void textConversionTest() {       
        clickOn("Convert to text");
        clickOn("Include column names");
        verifyThat("Include column names", (CheckBox b) -> !b.isSelected());
        clickOn("Include column names");
        verifyThat("Include column names", (CheckBox b) -> b.isSelected());
    }
    
    @Test
    public void graphTest() {
        clickOn("Graphs");
        clickOn("Create Graph");
        clickOn("Clear Graph");
        clickOn("Boxplot");
        clickOn("Line Chart");
        clickOn("Create Graph");
        clickOn("+");
        clickOn("Create Graph");
        
        verifyThat("#requiredData", ListViewMatchers.hasItems(1));
    }

}

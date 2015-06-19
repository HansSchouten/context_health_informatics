package controller;

import graphs.GraphController;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import model.SequentialData;
import model.Writer;

import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

import analyze.parsing.ParseResult;
import controller.MainApp.NotificationStyle;

/**
 * This class represents a controller for the results tap of the view.
 * @author Matthijs
 *
 */
public class ResultsController extends SubController {
    /** The sequential data after applying the script. */
    private ParseResult data;

    /** The textarea to display and edit the output. */
    private CodeArea textArea;

    /** The table for viewing the data as a table. */
    @FXML
    private SeqDataTable tableView;

    /**The tab pane for selecting the output as text, table or graph. */
    @FXML
    private TabPane tabPane;

    /** A combobox for selecting an option for the graph. */
    @FXML
    private ComboBox<String> delimBox;

    /** Whether to include to column names on the first line. */
    @FXML
    private CheckBox includeColNamesText, includeColNamesTable;

    /** This variable stores the graphView of this project. */
    @FXML
    private AnchorPane graphsView;

    /** This variable stores the graphcontroller of the graphs. */
    protected GraphController graphcontroller;

    /**
     * The VBox containing the content for the Text tab.
     */
    @FXML
    private VBox textVBox;

    /** This variable stores the pipeline number of this controller. */
    private int pipelineNumber = 4;

    /**
     * This function constructs a ResultController.
     */
    public ResultsController() {  }

    @Override
    protected void initialize() {
        textArea = new CodeArea();
        textArea.setParagraphGraphicFactory(LineNumberFactory.get(textArea));
        VBox.setVgrow(textArea, Priority.ALWAYS);
        textVBox.getChildren().add(textArea);

        // Add/remove column names to text
        includeColNamesText.selectedProperty().addListener((obs, oldV, newV) -> {
            String delim = ImportController.delims[delimBox.getSelectionModel().getSelectedIndex()];

            List<String> cols = tableView.getColumns().stream().map(x -> x.getText()).collect(Collectors.toList());
            String colNames = "";
            for (String s : cols) {
                colNames += s + delim;
            }
            colNames = colNames.substring(0, colNames.length() - 1) + "\r\n";

            int endFirstLine = textArea.getText().indexOf("\n");
            if (data instanceof SequentialData && endFirstLine > 0) {
                String firstLine = textArea.getText().substring(0, endFirstLine - 1) + "\r\n";
                if (!newV && firstLine.equals(colNames)) {
                    textArea.replaceText(0, endFirstLine + 1, "");
                } else if (newV && !firstLine.equals(colNames)) {
                    textArea.insertText(0, colNames);
                    textArea.moveTo(0);
                }
            }
        });

        // Setup the delimiter chooser
        delimBox.getItems().addAll(ImportController.delimNames);
        delimBox.setValue(delimBox.getItems().get(0));
    }

    /**
     * This method sets up the graphs.
     */
    protected void setupGraphs() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(mainApp.getClass()
                    .getResource("/view/GraphView.fxml"));
            HBox importedPane = (HBox) loader.load();
            graphcontroller = loader.getController();
            graphcontroller.setMainApp(mainApp);
            graphsView.getChildren().add(importedPane);

            AnchorPane.setBottomAnchor(importedPane, 0.0);
            AnchorPane.setTopAnchor(importedPane, 0.0);
            AnchorPane.setLeftAnchor(importedPane, 0.0);
            AnchorPane.setRightAnchor(importedPane, 0.0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens a FileChooser to save the file.
     * @param text The text to write to a file.
     */
    public void saveFile(String text) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save file");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Comma delimited file (*.csv)", "*.csv"),
                new FileChooser.ExtensionFilter("Text file (*.txt)", "*.txt"));

        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        if (file != null) {
            try {
                Writer.writeFile(file, text);
                mainApp.showNotification("Succesfully saved as " + file.getName(), NotificationStyle.INFO);
            } catch (IOException e) {
                mainApp.showNotification("Cannot save. Error: " + e.getClass(), NotificationStyle.WARNING);
                e.printStackTrace();
            }
        }
    }

    /**
     * Saves the text from the textarea to a file.
     */
    @FXML
    public void saveText() {
        saveFile(textArea.getText());
    }

    /**
     * Converts the table to text and saves it to a file.
     */
    @FXML
    public void saveTable() {
        String delim = ImportController.delims[delimBox.getSelectionModel().getSelectedIndex()];
        String text = tableView.toString(delim, includeColNamesTable.isSelected());
        saveFile(text);
    }

    /**
     * Replaces the text in the Text tab to the table in String format.
     */
    @FXML
    public void tableAsText() {
        textArea.clear();
        String delim = ImportController.delims[delimBox.getSelectionModel().getSelectedIndex()];
        textArea.appendText(tableView.toString(delim, includeColNamesTable.isSelected()));
        textArea.moveTo(0);
        includeColNamesText.setSelected(includeColNamesTable.isSelected());
        tabPane.getSelectionModel().select(1);
    }

    /**
     * Resets the data so that the initial ordering is restored.
     */
    @FXML
    public void resort() {
        if (data instanceof SequentialData) {
            tableView.getSortOrder().clear();

            tableView.getItems().clear();
            tableView.getItems().addAll((SequentialData) data);
        }
    }

    @Override
    public boolean validateInput(boolean showPopup) {
        return false;
    }

    @Override
    public Object getData() {
        // Not used
        return null;
    }

    @Override
    public void setData(Object o) {
        data = (ParseResult) o;
        tableView.setData(data);
        setupGraphs();

        textArea.replaceText(tableView.toString(",", true));
        textArea.moveTo(0);

        if (data instanceof SequentialData) {
            pushDataToGraphs((SequentialData) data);
        }
    }

    /**
     * This method pushes the data to the graphs controller.
     * @param sendData          - Data to send.
     */
    protected void pushDataToGraphs(SequentialData sendData) {
        graphcontroller.updateData(sendData);
    }

    @Override
    public void setMainApp(MainApp app) {
        super.setMainApp(app);
        setupGraphs();
        graphcontroller.setMainApp(app);
    }

    @Override
    protected int getPipelineNumber() {
        return pipelineNumber;
    }
}

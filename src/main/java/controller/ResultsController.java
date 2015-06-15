package controller;

import graphs.GraphController;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import model.Column;
import model.ColumnType;
import model.Record;
import model.SequentialData;
import model.Writer;
import model.datafield.DataField;
import model.datafield.DataFieldDouble;
import model.datafield.DataFieldInt;

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
    private TableView<Record> tableView;

    /**The tab pane for selecting the output as text, table or graph. */
    @FXML
    private TabPane tabPane;

    /** The panel that contains the graph. */
    @FXML
    private AnchorPane graphAnchor;

    /** A combobox for selecting an option for the graph. */
    @FXML
    private ComboBox<String> xBox, yBox, graphType, delimBox;

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
    /**
     * This function constructs a ResultController.
     * @param table 
     */
    public ResultsController(TableView<Record> table) {
        tableView = table;
    }

    @Override
    protected void initialize() {
        textArea = new CodeArea();
        textArea.setParagraphGraphicFactory(LineNumberFactory.get(textArea));
        VBox.setVgrow(textArea, Priority.ALWAYS);
        textVBox.getChildren().add(textArea);

        // Add/remove column names to text
        includeColNamesText.selectedProperty().addListener((obs, oldV, newV) -> {
            String delim = ImportController.delims[delimBox.getSelectionModel().getSelectedIndex()];

            int endFirstLine = textArea.getText().indexOf("\n");
            if (data instanceof SequentialData && endFirstLine > 0) {
                String colNames = ((SequentialData) data).getColumnNames(delim);
                String firstLine = textArea.getText().substring(0, endFirstLine - 1) + "\r\n";
                if (!newV && firstLine.equals(colNames)) {
                    textArea.replaceText(0, endFirstLine + 1, "");
                } else if (newV) {
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
        saveFile(tableToString());
    }

    /**
     * Replaces the text in the Text tab to the table in String format.
     */
    @FXML
    public void tableAsText() {
        textArea.clear();
        textArea.appendText(tableToString());
        textArea.moveTo(0);
        includeColNamesText.setSelected(includeColNamesTable.isSelected());
        tabPane.getSelectionModel().select(1);
    }

    /**
     * Converts to table to String format.
     * @return The table in String format.
     */
    public String tableToString() {
        String delim = ImportController.delims[delimBox.getSelectionModel().getSelectedIndex()];

        StringBuilder text = new StringBuilder();
        ObservableList<TableColumn<Record, ?>> columns = tableView.getColumns();
        List<String> colNames = columns.stream().map(x -> x.getText()).collect(Collectors.toList());

        // Column names
        if (includeColNamesTable.isSelected()) {
            for (int i = 0; i < colNames.size() - 1; i++) {
                text.append(colNames.get(i) + delim);
            }
            text.append(colNames.get(columns.size() - 1) + "\r\n");
        }

        // Items
        for (Record record : tableView.getItems()) {
            for (String c : colNames) {
                if (record.containsKey(c)) {
                    text.append(record.get(c).toString() + delim);
                } else {
                    text.append(delim);
                }
            }
            text = new StringBuilder(text.substring(0, text.length() - 1) + "\r\n");
        }
        return text.toString();
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
        createTable(data);
        setupGraphs();

        try {
            if (data instanceof SequentialData) {
                textArea.replaceText(((SequentialData) data).toString(",", true));
                pushDataToGraphs((SequentialData) data);
            } else {
                textArea.replaceText(data.toString());
                textArea.moveTo(0);
            }
        } catch (IOException e) {
            mainApp.showNotification("Cannot create output: " + e.getMessage(), NotificationStyle.WARNING);
            e.printStackTrace();
        }
    }

    /**
     * This method pushes the data to the graphs controller.
     * @param sendData          - Data to send.
     */
    protected void pushDataToGraphs(SequentialData sendData) {
        graphcontroller.updateData(sendData);
    }

    /**
     * Converts the output data into a table.
     * @param parsedData The data to put in the tableview.
     */
    public void createTable(ParseResult parsedData) {
        tableView.getColumns().clear();
        tableView.getItems().clear();

        if (parsedData instanceof DataField) {
            createSingleColumn(parsedData);
        } else if (parsedData instanceof SequentialData) {
            TableColumn<Record, String> timeStamp = new TableColumn<Record, String>("Record timestamp");
            timeStamp.setCellValueFactory(p -> {
                return new SimpleStringProperty(p.getValue().getTimeStamp().toString());
            });
            tableView.getColumns().add(timeStamp);

            createMultipleColumn(parsedData);

            TableColumn<Record, String> commentCol = new TableColumn<Record, String>("Comments");
            commentCol.setCellValueFactory(p -> {
                return new SimpleStringProperty(p.getValue().printComments("-"));
            });
            tableView.getColumns().add(commentCol);

            TableColumn<Record, String> labelCol = new TableColumn<Record, String>("Labels");
            labelCol.setCellValueFactory(p -> {
                return new SimpleStringProperty(p.getValue().printLabels("-"));
            });
            tableView.getColumns().add(labelCol);

            // Setting the data in the table
            tableView.getItems().addAll((SequentialData) parsedData);
        }
    }

    /**
     * If there is a single value, create a single column for that value.
     * @param parsedData to insert in field.
     */
    private void createSingleColumn(ParseResult parsedData) {
        Record r = new Record(LocalDateTime.now());
        r.put("Data", (DataField) parsedData);
        TableColumn<Record, String> tc = new TableColumn<Record, String>("Data");
        tc.setCellValueFactory(p -> {
            return new SimpleStringProperty(p.getValue().get("Data").toString());
        });

        tableView.getColumns().add(tc);
        tableView.getItems().add(r);
    }

    /**
     * create multiple columns in table.
     * @param parsedData the parsed data
     */
    private void createMultipleColumn(ParseResult parsedData) {
        // Else, create columns for each column in the data.
        SequentialData seqData = (SequentialData) parsedData;
        // Setup the table for every column type
        Column[] columns = seqData.getColumns();
        for (int i = 0; i < columns.length; i++) {
            ColumnType ct = columns[i].getType();
            String colName = columns[i].getName();

            // Differentiate between number or string so they can be sorted correctly in the GUI
            // Dates are sorted correctly as String, so there's no need to check for Date or Time types
            if (ct == ColumnType.INT) {
                TableColumn<Record, Number> tc = new TableColumn<Record, Number>(colName);
                tc.setCellValueFactory(p -> {
                    if (p.getValue().keySet().contains(colName)) {
                        return new SimpleIntegerProperty(
                                ((DataFieldInt) p.getValue().get(colName)).getIntegerValue());
                    } else {
                        return new SimpleIntegerProperty();
                    }
                });
                tableView.getColumns().add(tc);
            } else if (ct == ColumnType.DOUBLE) {
                TableColumn<Record, Number> tc = new TableColumn<Record, Number>(colName);
                tc.setCellValueFactory(p -> {
                    if (p.getValue().keySet().contains(colName)) {
                        return new SimpleDoubleProperty(
                                ((DataFieldDouble) p.getValue().get(colName)).getDoubleValue());
                    } else {
                        return new SimpleDoubleProperty();
                    }
                });
                tableView.getColumns().add(tc);
            } else {
                TableColumn<Record, String> tc = new TableColumn<Record, String>(colName);
                tc.setCellValueFactory(p -> {
                    if (p.getValue().keySet().contains(colName)) {
                        return new SimpleStringProperty(p.getValue().get(colName).toString());
                    } else {
                        return new SimpleStringProperty("");
                    }
                });
                tableView.getColumns().add(tc);
            }
        }
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

package controller;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import model.Column;
import model.ColumnType;
import model.DateUtils;
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
    private TableView<DataField[]> tableView;

    /**The tab pane for selecting the output as text, table or graph. */
    @FXML
    private TabPane tabPane;

    /** The panel that contains the graph. */
    @FXML
    private AnchorPane graphAnchor;

    /** A combobox for selecting an option for the graph. */
    @FXML
    private ComboBox<String> xBox, yBox, graphType;

    /** Whether to include to column names on the first line. */
    @FXML
    private CheckBox includeColNamesText, includeColNamesTable;

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
    public ResultsController() { }

    @Override
    protected void initialize() {
        textArea = new CodeArea();
        textArea.setParagraphGraphicFactory(LineNumberFactory.get(textArea));
        VBox.setVgrow(textArea, Priority.ALWAYS);
        textVBox.getChildren().add(textArea);

        // Add/remove column names to text
        includeColNamesText.selectedProperty().addListener((obs, oldV, newV) -> {
            int endFirstLine = textArea.getText().indexOf("\n");
            if (data instanceof SequentialData && endFirstLine > 0) {
                String colNames = ((SequentialData) data).getColumnNames(",");
                String firstLine = textArea.getText().substring(0, endFirstLine - 1) + "\r\n";
                if (!newV && firstLine.equals(colNames)) {
                    textArea.replaceText(0, endFirstLine + 1, "");
                } else if (newV) {
                    textArea.insertText(0, colNames);
                    textArea.moveTo(0);
                }
            }
        });
    }

    /**
     * Creates a graph if the input is correct.
     */
    @FXML
    public void createGraph() {
        if (xBox.getSelectionModel().getSelectedItem() != null
                && yBox.getSelectionModel().getSelectedItem() != null
                && graphType.getSelectionModel().getSelectedItem() != null) {
            Axis<Number> x = new NumberAxis();
            Axis<Number> y = new NumberAxis();

            x.setLabel(xBox.getSelectionModel().getSelectedItem());
            y.setLabel(yBox.getSelectionModel().getSelectedItem());

            if (graphType.getSelectionModel().getSelectedIndex() == 0) {
                LineChart<Number, Number> graph = new LineChart<Number, Number>(x, y);
                Series<Number, Number> series = new XYChart.Series<>();

                for (Record r : (SequentialData) data) {
                    int xValue = -1;
                    try {
                        xValue = DateUtils.parseDate(r.get(x.getLabel()).toString(),
                            "yyMMdd").getDayOfYear();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    int yValue = Integer.parseInt(r.get(y.getLabel()).toString());
                    series.getData().add(new XYChart.Data<Number, Number>(xValue, yValue));

                    System.out.println(xValue + ", " + yValue + " - " + r.get(x.getLabel()) + ", "
                            + r.get(y.getLabel()));
                }
                graph.getData().add(series);
                graphAnchor.getChildren().clear();
                graphAnchor.getChildren().add(graph);

                AnchorPane.setBottomAnchor(graph, 0.0);
                AnchorPane.setTopAnchor(graph, 0.0);
                AnchorPane.setLeftAnchor(graph, 0.0);
                AnchorPane.setRightAnchor(graph, 0.0);
            }
        }
    }

    /**
     * Sets up the graph options, to choose the axis' and graph style.
     */
    private void setupGraphOptions() {
        if (data instanceof DataField) {
            return;
        }
        ObservableList<String> colNames = FXCollections.observableArrayList();
        for (Column c : ((SequentialData) data).getColumns()) {
            colNames.add(c.getName());
        }
        xBox.setItems(colNames);
        yBox.setItems(colNames);

        ObservableList<String> graphTypes = FXCollections.observableArrayList();
        graphTypes.addAll("Line chart", "Bar chart", "Pie chart");
        graphType.setItems(graphTypes);
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
        String text = "";
        ObservableList<TableColumn<DataField[], ?>> columns = tableView.getColumns();

        // Column names
        if (includeColNamesTable.isSelected()) {
            for (int i = 0; i < columns.size() - 1; i++) {
                text += columns.get(i).getText() + ",";
            }
            text += columns.get(columns.size() - 1).getText() + "\r\n";
        }

        // Items
        for (DataField[] item : tableView.getItems()) {
            for (int j = 0; j < columns.size() - 1; j++) {
                text += item[j] + ",";
            }
            text += item[item.length - 1] + "\r\n";
        }
        saveFile(text);
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
        createTable();
        setupGraphOptions();

        try {
            if (data instanceof SequentialData) {
                textArea.replaceText(((SequentialData) data).toString(",", true));
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
     * Converts the output data into a table.
     */
    private void createTable() {
        tableView.getColumns().clear();

        // If there is a single value, create a single column for that value.
        if (data instanceof DataField) {
            TableColumn<DataField[], String> tc = new TableColumn<DataField[], String>("Data");
            tc.setCellValueFactory(
                    new Callback<CellDataFeatures<DataField[], String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(CellDataFeatures<DataField[], String> p) {
                    return new SimpleStringProperty(p.getValue()[0].toString());
                }
            });
            tableView.getColumns().add(tc);
            tableView.getItems().add(new DataField[] {(DataField) data});
            return;
        }

        // Else, create columns for each column in the data.
        SequentialData seqData = (SequentialData) data;

        // Setup the table for every column type
        Column[] columns = seqData.getColumns();

        for (int i = 0; i < columns.length; i++) {
            final int colIdx = i;
            ColumnType ct = columns[i].getType();

            if (ct == ColumnType.INT) {
                TableColumn<DataField[], Number> tc = new TableColumn<DataField[], Number>(columns[i].getName());
                tc.setCellValueFactory(p -> {
                        return new SimpleIntegerProperty(((DataFieldInt) p.getValue()[colIdx]).getIntegerValue());
                });
                tableView.getColumns().add(tc);
            } else if (ct == ColumnType.DOUBLE) {
                TableColumn<DataField[], Number> tc = new TableColumn<DataField[], Number>(columns[i].getName());
                tc.setCellValueFactory(p -> {
                        return new SimpleDoubleProperty(((DataFieldDouble) p.getValue()[colIdx]).getDoubleValue());
                });
            } else {
                // Other values can be sorted on their String value.
                TableColumn<DataField[], String> tc = new TableColumn<DataField[], String>(columns[i].getName());
                tc.setCellValueFactory(p -> { return new SimpleStringProperty(p.getValue()[colIdx].toString()); });
                tableView.getColumns().add(tc);
            }
        }
        // Setting the data in the table
        for (Record r : seqData) {
            String[] keys = r.keySet().toArray(new String[0]);
            DataField[] row = new DataField[r.size()];
            for (int i = 0; i < keys.length; i++) {
                row[i] = r.get(keys[i]);
            }
            tableView.getItems().add(row);
        }
    }

    @Override
    protected int getPipelineNumber() {
        return pipelineNumber;
    }
}

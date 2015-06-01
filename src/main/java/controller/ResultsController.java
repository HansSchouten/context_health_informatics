package controller;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

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
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import model.Column;
import model.DateUtils;
import model.Record;
import model.SequentialData;
import model.Writer;
import controller.MainApp.NotificationStyle;

/**
 * This class represents a controller for the results tap of the view.
 * @author Matthijs
 *
 */
public class ResultsController extends SubController {
    /** The sequential data after applying the script. */
    private SequentialData data;

    /** The textarea to display and edit the output. */
    @FXML
    private TextArea textArea;

    /** The table for viewing the data as a table. */
    @FXML
    private TableView<String[]> tableView;

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
    private CheckBox includeColNames;

    /** This variable stores the pipeline number of this controller. */
    private int pipelineNumber = 4;

    /**
     * This function constructs a ResultController.
     */
    public ResultsController() { }

    @Override
    protected void initialize() {
        tabPane.getSelectionModel().selectedIndexProperty().addListener((obs, oldV, newV) -> {
            if (newV.intValue() == 0) {
                tableToText();
            } else if (newV.intValue() == 1) {
                textToTable();
            } else if (newV.intValue() == 2) {
                createGraph();
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

                for (Record r : data) {
                    int xValue = -1;
                    try {
                        xValue = DateUtils.parseDate(r.get(x.getLabel()).getStringValue(),
                            "yyMMdd").getDayOfYear();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    int yValue = Integer.parseInt(r.get(y.getLabel()).getStringValue());
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
        ObservableList<String> colNames = FXCollections.observableArrayList();
        for (Column c : data.getColumns()) {
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
     */
    @FXML
    public void saveFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save file");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Comma delimited file (*.csv)", "*.csv"),
                new FileChooser.ExtensionFilter("Text file (*.txt)", "*.txt"));

        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        try {
            Writer.writeFile(file, textArea.getText());
        } catch (IOException e) {
            e.printStackTrace();
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
        data = (SequentialData) o;
        setupGraphOptions();

        try {
            String text = data.toString(",", true);
            textArea.setText(text);
        } catch (IOException e) {
            mainApp.showNotification("Cannot create output: " + e.getMessage(), NotificationStyle.WARNING);
            e.printStackTrace();
        }
    }

    /**
     * Converts the text in the GUI to the table.
     */
    private void textToTable() {
        // Split the text and find get columns
        String text = textArea.getText();
        String[] lines = text.split("\n");
        Column[] cols = data.getColumns();
        String[] colNames = new String[cols.length];

        for (int i = 0; i < cols.length; i++) {
            colNames[i] = cols[i].getName();
        }

        tableView.getColumns().clear();

        // Setup the table so that every row is a String array
        for (int i = 0; i < cols.length; i++) {
            TableColumn<String[], String> tc = new TableColumn<String[], String>(colNames[i]);
            final int colIdx = i;
            tc.setCellValueFactory(
                    new Callback<CellDataFeatures<String[], String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(CellDataFeatures<String[], String> p) {
                    return new SimpleStringProperty(p.getValue()[colIdx]);
                }
            });
            tableView.getColumns().add(tc);
        }

        // Start at i = 1 because titles are in the first line
        ObservableList<String[]> dataList = FXCollections.observableArrayList();
        for (int i = 1; i < lines.length; i++) {
            String line = lines[i];
            dataList.add(line.split(","));
        }
        tableView.setItems(dataList);
    }

    /**
     * Converts the table in the GUI to the text.
     */
    private void tableToText() {
        String text = "";
        ObservableList<TableColumn<String[], ?>> columns = tableView.getColumns();

        // Column names
        for (int i = 0; i < columns.size() - 1; i++) {
            text += columns.get(i).getText() + ",";
        }
        text += columns.get(columns.size() - 1).getText() + "\r\n";

        // Items
        for (String[] item : tableView.getItems()) {
            for (int j = 0; j < columns.size() - 1; j++) {
                text += item[j] + ",";
            }
            text += item[item.length - 1] + "\r\n";
        }
        textArea.setText(text);
    }

    @Override
    protected int getPipelineNumber() {
        return pipelineNumber;
    }
}

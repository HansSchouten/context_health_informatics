package controller;
import graphs.BarChart;
import graphs.BoxPlot;
import graphs.Graph;
import graphs.GraphDataTransformer;
import graphs.GraphException;
import graphs.Histogram;
import graphs.InputListItem;
import graphs.InputType;
import graphs.LineChart;
import graphs.StemAndLeavePlot;

import java.util.ArrayList;

import controller.MainApp.NotificationStyle;
import model.Column;
import model.SequentialData;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.scene.web.WebView;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;

/**
 * This class controls the interface for the graphsview of the program.
 * @author Matthijs
 *
 */
public class GraphController {

    /** This variable stores the graphApplication that uses this controller. */
    protected MainApp graphApp;

    /** The web view to create and view graphs. */
    @FXML
    private WebView webView;

    /** This variable stores the combobox that selects the graph. */
    @FXML
    private ComboBox<String> graphSelector;

    /** This variable stores the listview with the required data. */
    @FXML
    private ListView requiredData;

    /** This variable stores the addbutton that adds a new input to the graph. */
    @FXML
    private Button addButton;

    /** This variable stores the textfield that stores the name. */
    @FXML
    private TextField graphName;

    /** This variable stores all the graphs that are available. */
    protected ArrayList<Graph> availableGraphs;

    /** This variable stores the dataholder, that stores the data to draw the graph with. */
    protected GraphDataTransformer dataholder;

    /**
     * This method creates a new graphController.
     */
    public GraphController() {
        availableGraphs = new ArrayList<Graph>();
        dataholder = new GraphDataTransformer();
    }

    /** This method clears the view to the homescreen. */
    @FXML
    public void clear() {
        setupWebView();
    }

    /** This method adds an input field to the required inputs. */
    @FXML
    public void addInput() {
        System.out.println("input added");

        Graph selectedGraph = availableGraphs.get(graphSelector.getSelectionModel().getSelectedIndex());
        InputType type;
        try {
            type = selectedGraph.getAddableItem();
            requiredData.getItems().add(new InputListItem(requiredData, type, dataholder.getDataColumns(), true));
        } catch (GraphException e) {
            graphApp.showNotification(e.getMessage(), NotificationStyle.WARNING);
            e.printStackTrace();
        }
    }

    /**
     * This method changes the required data based on the selected item.
     */
    @FXML
    public void graphSelected() {
        Graph selectedGraph = availableGraphs.get(graphSelector.getSelectionModel().getSelectedIndex());
        setRequiredInput(selectedGraph);
    }

    /**
     * This method sets the required input for the selected graph.
     * @param selectedGraph     - Graph that is selected.
     */
    protected void setRequiredInput(Graph selectedGraph) {
        ArrayList<InputType> inputTypes = selectedGraph.getRequiredInputs();
        requiredData.getItems().clear();
        Column[] cols = dataholder.getDataColumns();

        for (InputType type: inputTypes) {
            requiredData.getItems().add(new InputListItem(requiredData, type, cols, false));
        }

        if (selectedGraph.hasFixedSize()) {
            addButton.setDisable(true);
        } else {
            addButton.setDisable(false);
        }
    }

    /** This method exports the graph as a PDF file. */
    @FXML
    public void exportAsPDF() {
        System.out.println("pdf");
    }

    /** This method exports the graph as a JPG image. */
    @FXML
    public void exportAsJPG() {
        System.out.println("jpg");
    }

    /**This method draws the graph, when the button is pressed. */
    @FXML
    public void drawGraph() {
        System.out.println(graphName.getText());
        
        ArrayList<String> columns = new ArrayList<String>();
        ArrayList<String> inputNames = new ArrayList<String>();

        for (Object item: requiredData.getItems()) {
            InputListItem listItem = (InputListItem) item;
            inputNames.add(listItem.getinputName());
            columns.add(listItem.getSelectedColumn());
        }

        String data = dataholder.getJSONFromColumn(columns, inputNames);

        Graph selected = availableGraphs.get(graphSelector.getSelectionModel().getSelectedIndex());
        selected.drawInWebView(webView, data, graphName.getText());
    }

    /** This method initialises the controller linked with the GUI. */
    @FXML
    protected void initialize() {
        setupWebView();

        addGraph(new BoxPlot());
        addGraph(new LineChart());
        addGraph(new BarChart());
        addGraph(new StemAndLeavePlot());
        addGraph(new Histogram());

        graphSelector.setValue(graphSelector.getItems().get(0));
        graphSelected();
    }

    /**
     * Sets up the graph options, to choose the axis' and graph style.
     */
    protected void setupWebView() {
        String url = this.getClass().getResource("/graphs/index.html").toExternalForm();
        webView.getEngine().load(url);
    }

    /**
     * This method adds a graph to the graphList and to the gui.
     * @param graph     - Graph to Add.
     */
    protected void addGraph(Graph graph) {
        availableGraphs.add(graph);
        graphSelector.getItems().add(graph.toString());
    }

    /**
     * This method updates the data to draw the columns with.
     * @param data          - Data to update.
     */
    public void updateData(SequentialData data) {
        dataholder.setData(data);
        graphSelected();
    }
}

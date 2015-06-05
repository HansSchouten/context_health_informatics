package controller;
import graphs.BoxPlot;
import graphs.Graph;
import graphs.GraphDataTransformer;
import graphs.InputListItem;
import graphs.InputType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Column;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.web.WebView;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;

public class GraphController {

    /** This variable stores the graphApplication that uses this controller */
    protected GraphApp graphApp;
    
    /** The web view to create and view graphs. */
    @FXML
    private WebView webView;

    /** This variable stores the combobox that selects the graph */
    @FXML
    private ComboBox<String> graphSelector;

    /** This variable stores the listview with the required data */
    @FXML
    private ListView requiredData;
    
    /** This variable stores all the graphs that are available. */
    protected ArrayList<Graph> availableGraphs;
    
    GraphDataTransformer dataholder;

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
        System.out.println("clear view");
        setupWebView();
    }

    /** This method lets you choose which file you want to use. */
    @FXML
    public void chooseFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import files");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt"),
                new FileChooser.ExtensionFilter(
                        "Comma delimited files (*.csv)", "*.csv"));

        File file = fileChooser.showOpenDialog(graphApp.getPrimaryState());
        readfile(file);
    }
    
    /**
     * This method returns the file, in order to create a graph from it.m
     * @param file
     */
    protected void readfile(File file) {
        dataholder.readFile(file);
    }

    /**
     * This method changes the required data based on the selected item.
     */
    @FXML
    public void graphSelected() {
        System.out.println("graph selected");
        
        Graph selectedGraph = availableGraphs.get(graphSelector.getSelectionModel().getSelectedIndex());
        setRequiredInput(selectedGraph);
    }
    
    protected void setRequiredInput(Graph selectedGraph) {
        ArrayList<InputType> inputTypes = selectedGraph.getRequiredInputs();
        requiredData.getItems().clear();
        Column[] cols = dataholder.getDataColumns();
        
        for (InputType type: inputTypes) {
            requiredData.getItems().add(new InputListItem(requiredData, type, cols));
        }
    }

    @FXML
    public void exportAsPDF() {
        System.out.println("pdf");
    }
    
    @FXML
    public void exportAsJPG() {
        System.out.println("jpg");
    }
    
    @FXML
    public void drawGraph() {
        ArrayList<String> columns = new ArrayList<String>();
        ArrayList<String> inputNames = new ArrayList<String>();
        
        for (Object item: requiredData.getItems()) {
            InputListItem listItem = (InputListItem) item;
            inputNames.add(listItem.getinputName());
            columns.add(listItem.getSelectedColumn());
        }
        
        String data = dataholder.getJSONFromColumn(columns, inputNames);
        
        Graph selected = availableGraphs.get(graphSelector.getSelectionModel().getSelectedIndex());
        selected.drawInWebView(webView, data);
    }
    
    @FXML
    protected void initialize() {
        setupWebView();
        
        addGraph(new BoxPlot());
        
        graphSelector.setValue(graphSelector.getItems().get(0));
        graphSelected();
    }
    
    /**
     * Sets up the graph options, to choose the axis' and graph style.
     */
    protected void setupWebView() {
        String url = this.getClass().getResource("/graphs/homescreen.html").toExternalForm();
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
}

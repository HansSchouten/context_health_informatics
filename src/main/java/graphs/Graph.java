package graphs;

import javafx.concurrent.Worker.State;
import javafx.scene.web.WebView;

import java.util.ArrayList;

/**
 * This abstract class represents a graph.
 * @author Matthijs
 *
 */
public abstract class Graph {
    
    /** This variable stores the name of the graph. */
    protected String graphName;
    
    /** This variable stores the path to the file containing the graph */
    protected String pathToFile;

    /** This variable stores whether the graph has a fixedSize. */
    protected boolean fixedSize;
    
    /** This variable stores the number of additional inputs for this graph. */
    protected int additionalAdditions;
    
    /**
     * Construct an graphInput.
     * @param name      - Name of the graph.
     * @param path      - Path to the file the runs the graph.
     * @param fxdSize   - Indicates whether the graph has a fixed number of inputs.
     * @param requiredInput - Inputs required for this graph.
     */
    protected Graph(String name, String path, boolean fxdSize) {
        fixedSize = fxdSize;
        graphName = name;
        pathToFile = path;
    }

    /**
     * This method should draw the graph, with the given data.
     * @param webView      - webview to draw the graph in.
     * @param data         - String containing the data.
     */
    public void drawInWebView(WebView webView, String data) {
        String url = this.getClass().getResource(getURL()).toExternalForm();
        webView.getEngine().load(url);
        System.out.println("drawBarGraph(\"" + data + "\")");
        webView.getEngine().getLoadWorker().stateProperty().addListener(
                (obs, oldV, newV) -> {
                    if (newV == State.SUCCEEDED) {
                        System.out.println("done!!");
                        webView.getEngine().executeScript(getScript(data));
                    }
                }
        );
    }

    /**
     * This method should return the URL of where to find the graph.
     * @return      - The URL of where to find the graph.
     */
    public abstract String getURL();

    /**
     * This function should return the script of the program.
     * @param date      - The data to use in the script.
     * @return          - The Scripting code to execute.
     */
    public abstract String getScript(String data);

    /**
     * This method should get the requiredInputs for this table.
     * @return              - Inputs for this table.
     */
    public abstract ArrayList<InputType> getRequiredInputs();
    
    @Override
    public String toString() {
        return graphName;
    }
    
    public boolean hasFixedSize() {
        return fixedSize;
    }

    /**
     * This function should return the inputtype for the item that can be added multiple times. 
     * @return      - The InputType that is added multiple times.
     * @throws GraphException - Thrown when it is not possible to add aan item.
     */
    public InputType getAddableItem() throws GraphException {
        throw new GraphException("There is no addable item here.");
    }
}

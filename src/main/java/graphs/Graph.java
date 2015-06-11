package graphs;

import javafx.concurrent.Worker;
import javafx.scene.web.WebView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.util.ArrayList;

/**
 * This abstract class represents a graph.
 * @author Matthijs
 *
 */
public abstract class Graph {

    /** This variable stores the inputs required to draw this graph. */
    protected ArrayList<InputType> inputs;

    /** This variable stores the name of the graph. */
    protected String graphName;

    /** This variable stores the path to the file containing the graph. */
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
     */
    protected Graph(String name, String path, boolean fxdSize) {
        fixedSize = fxdSize;
        graphName = name;
        pathToFile = path;
        inputs = new ArrayList<InputType>();
    }

    /**
     * This method should draw the graph, with the given data.
     * @param webView      - webview to draw the graph in.
     * @param data         - String containing the data.
     * @param name         - Name of the graph
     */
    public void drawInWebView(WebView webView, String data, String name) {
        String url = this.getClass().getResource(getURL()).toExternalForm();
        webView.getEngine().load(url);

        ChangeListener<Worker.State> listener = null;
        listener = new ChangeListener<Worker.State>() {
            @Override
            public void changed(ObservableValue obs, Worker.State oldV, Worker.State newV) {
                if (newV.equals(Worker.State.SUCCEEDED)) {
                    webView.getEngine().executeScript(getScript(name, data));
                    webView.getEngine().getLoadWorker().stateProperty().removeListener(this);
                }
            }
        };

        webView.getEngine().getLoadWorker().stateProperty().addListener(listener);
    }

    /**
     * This method should return the URL of where to find the graph.
     * @return      - The URL of where to find the graph.
     */
    public String getURL() {
        return pathToFile;
    }

    /**
     * This function should return the script of the program.
     * @param name      - Name of the graph.
     * @param data      - The data to use in the script.
     * @return          - The Scripting code to execute.
     */
    public abstract String getScript(String name, String data);

    /**
     * This method should get the requiredInputs for this table.
     * @return              - Inputs for this table.
     */
    public ArrayList<InputType> getRequiredInputs() {
        return inputs;
    }

    @Override
    public String toString() {
        return graphName;
    }

    /**
     * This function returns whether the graph has a fixed size.
     * @return      - True if the graph has a fixed size, false otherwise.
     */
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

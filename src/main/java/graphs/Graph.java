package graphs;

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
    
    /**
     * Construct an graphInput.
     * @param name      - Name of the graph.
     * @param path      - Path to the file the runs the graph.
     * @param requiredInput - Inputs required for this graph.
     */
    protected Graph(String name, String path) {
        graphName = name;
        pathToFile = path;
    }

    /**
     * This method should draw the graph, with the given data.
     * @param webView      - webview to draw the graph in.
     * @param data         - String containing the data.
     */
    public abstract void drawInWebView(WebView webView, String data);

    /**
     * This method should get the requiredInputs for this table.
     * @return              - Inputs for this table.
     */
    public abstract ArrayList<InputType> getRequiredInputs();
    
    @Override
    public String toString() {
        return graphName;
    }
}

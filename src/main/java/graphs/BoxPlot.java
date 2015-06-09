package graphs;

import java.util.ArrayList;

import model.ColumnType;

/**
 * This class represents a boxplot, that can be drawn into a webview.
 * @author Matthijs
 *
 */
public class BoxPlot extends Graph {

    /** This variable stores the inputs required to drqw this graph. */
    protected ArrayList<InputType> inputs;

    /**
     * Construct a boxplot.
     */
    public BoxPlot() {
        super("boxplot", "/graphs/boxplot.html", true);
        inputs = new ArrayList<InputType>();
        inputs.add(new InputType("x", ColumnType.INT));
    }

    @Override
    public ArrayList<InputType> getRequiredInputs() {
        return inputs;
    }

    @Override
    public String getURL() {
        return "/graphs/boxplot.html";
    }

    @Override
    public String getScript(String data) {
        return "drawBoxPlot('" + data + "')";
    }
}

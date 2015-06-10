package graphs;

import model.ColumnType;

/**
 * This class represents a boxplot, that can be drawn into a webview.
 * @author Matthijs
 *
 */
public class BoxPlot extends Graph {

    /**
     * Construct a boxplot.
     */
    public BoxPlot() {
        super("boxplot", "/graphs/boxplot.html", true);
        inputs.add(new InputType("x", ColumnType.INT));
    }

    @Override
    public String getScript(String data) {
        return "drawBoxPlot('" + data + "')";
    }
}

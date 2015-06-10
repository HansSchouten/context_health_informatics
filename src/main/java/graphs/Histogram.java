package graphs;

import model.ColumnType;

/**
 * This class represents an histogram that can be drawn in a webview.
 * @author Matthijs
 *
 */
public class Histogram extends Graph{


    /** Construct a stem and leave plot that can be drawn in a webview. */
    public Histogram() {
        super("Histogram", "/graphs/histogram.html", true);
        inputs.add(new InputType("x", ColumnType.INT));
    }

    @Override
    public String getScript(String data) {
        return "drawHistogram('histogram drawing', '" + data + "')";
    }
    
    //TODO: Fix issue with fixed start at 0
}

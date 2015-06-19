package graphs;

import java.util.ArrayList;

import model.ColumnType;

/**
 * This class represents an histogram that can be drawn in a webview.
 * @author Matthijs
 *
 */
public class Histogram extends Graph {

    /** Construct a stem and leave plot that can be drawn in a webview. */
    public Histogram() {
        super("Histogram", "/graphs/histogram.html", true, false);

        ArrayList<ColumnType> types = new ArrayList<ColumnType>();
        types.add(ColumnType.INT);
        types.add(ColumnType.DOUBLE);

        inputs.add(new InputType("x", types, false, false));
    }

    @Override
    public String getScript(String name, String data) {
        return "drawHistogram('" + name + "', '" + data + "')";
    }

    //TODO Fix issue with fixed start at 0
}

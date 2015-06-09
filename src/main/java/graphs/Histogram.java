package graphs;

import java.util.ArrayList;

import model.ColumnType;

/**
 * This class represents an histogram that can be drawn in a webview.
 * @author Matthijs
 *
 */
public class Histogram extends Graph{

    /** This variable stores the inputs required to drqw this graph. */
    protected ArrayList<InputType> inputs;

    /** Construct a stem and leave plot that can be drawn in a webview. */
    public Histogram() {
        super("Histogram", "/graphs/histogram.html", true);
        inputs = new ArrayList<InputType>();
        inputs.add(new InputType("x", ColumnType.INT));
    }

    @Override
    public String getScript(String data) {
        return "drawHistogram('histogram drawing', '" + data + "')";
    }

    @Override
    public ArrayList<InputType> getRequiredInputs() {
        return inputs;
    }
    
    //TODO: Fix issue with fixed start at 0
}

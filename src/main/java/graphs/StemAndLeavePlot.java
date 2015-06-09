package graphs;

import java.util.ArrayList;

import model.ColumnType;
/**
 * This class represents a stem and leave plot that can be drawn in a webview.
 * @author Matthijs
 *
 */
public class StemAndLeavePlot extends Graph {

    /** This variable stores the inputs required to drqw this graph. */
    protected ArrayList<InputType> inputs;

    /** Construct a stem and leave plot that can be drawn in a webview. */
    public StemAndLeavePlot() {
        super("Stem and Leave Plot", "/graphs/stemandleave.html", true);
        inputs = new ArrayList<InputType>();
        inputs.add(new InputType("x", ColumnType.INT));
    }

    @Override
    public String getScript(String data) {
        return "drawStemAndLeave('" + data + "')";
    }

    @Override
    public ArrayList<InputType> getRequiredInputs() {
        return inputs;
    }
    
    //TODO: add name to the graph.
    //TODO: allow multipe invoervelden.

}

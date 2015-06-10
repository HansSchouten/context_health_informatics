package graphs;

import java.util.ArrayList;

import model.ColumnType;
/**
 * This class represents a stem and leave plot that can be drawn in a webview.
 * @author Matthijs
 *
 */
public class StemAndLeavePlot extends Graph {

    /** Construct a stem and leave plot that can be drawn in a webview. */
    public StemAndLeavePlot() {
        super("Stem and Leave Plot", "/graphs/stemandleave.html", true);

        ArrayList<ColumnType> types = new ArrayList<ColumnType>();
        types.add(ColumnType.INT);
        types.add(ColumnType.DOUBLE);
        
        inputs.add(new InputType("x", types));
    }

    @Override
    public String getScript(String name, String data) {
        return "drawStemAndLeave('"+ name + "', '" + data + "')";
    }
    
    //TODO: add name to the graph.
    //TODO: allow multipe invoervelden.

}

package graphs;

import java.util.ArrayList;

import model.ColumnType;

/**
 * This class represent a LineChart that can be drawn in the webview.
 * @author Matthijs
 *
 */
public class LineChart extends Graph {

    /** This variables stores the inputs that have been shown. */
    protected int inputNumber;

    /** This variable stores accepted inputtypes. */
    protected ArrayList<ColumnType> bartypes;

    /**
     * Constructs a new linechart object, that can be drawn in a webview.
     */
    public LineChart() {
        super("Line Chart", "/graphs/linechart.html", false);
        inputNumber = 0;

        bartypes = new ArrayList<ColumnType>();
        bartypes.add(ColumnType.INT);
        bartypes.add(ColumnType.DOUBLE);

        ArrayList<ColumnType> xtypes = new ArrayList<ColumnType>();
        xtypes.add(ColumnType.DATE);
        xtypes.add(ColumnType.INT);
        xtypes.add(ColumnType.DOUBLE);

        inputs.add(new InputType("date", xtypes, false, true));
        inputs.add(new InputType("line " + inputNumber, bartypes, false, false));
        inputNumber++;
    }

    @Override
    public InputType getAddableItem() {
        InputType result =  new InputType("line " + inputNumber, bartypes, false, false);
        inputNumber++;
        return result;
    }

    @Override
    public String getScript(String name, String data) {
        return "drawLineGraph('" + name + "', '" + data + "')";
    }
}


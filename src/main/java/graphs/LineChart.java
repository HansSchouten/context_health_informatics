package graphs;

import model.ColumnType;

/**
 * This class represent a LineChart that can be drawn in the webview.
 * @author Matthijs
 *
 */
public class LineChart extends Graph {

    /** This variables stores the inputs that have been shown. */
    protected int inputNumber;

    /**
     * Constructs a new linechart object, that can be drawn in a webview.
     */
    public LineChart() {
        super("Line Chart", "/graphs/linechart.html", false);
        inputNumber = 0;
        inputs.add(new InputType("date", ColumnType.DATE));
        inputs.add(new InputType("line " + inputNumber, ColumnType.INT));
        inputNumber++;
    }

    @Override
    public InputType getAddableItem() {
        InputType result =  new InputType("line " + inputNumber, ColumnType.INT);
        inputNumber++;
        return result;
    }

    @Override
    public String getScript(String name, String data) {
        return "drawLineGraph('"+ name + "', '" + data + "')";
    }
}


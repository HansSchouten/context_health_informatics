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

    /** This variable stores the inputs required to draw this graph. */
    protected ArrayList<InputType> inputs;

    /**
     * Constructs a new linechart object, that can be drawn in a webview.
     */
    public LineChart() {
        super("Line Chart", "/graphs/linechart.html", false);
        inputNumber = 0;
        inputs = new ArrayList<InputType>();
        inputs.add(new InputType("date", ColumnType.DATE));
        inputs.add(new InputType("line " + inputNumber, ColumnType.INT));
        inputNumber++;
    }

    @Override
    public ArrayList<InputType> getRequiredInputs() {
        return inputs;
    }

    @Override
    public InputType getAddableItem() {
        InputType result =  new InputType("line " + inputNumber, ColumnType.INT);
        inputNumber++;
        return result;
    }

    @Override
    public String getScript(String data) {
        return "drawLineGraph('" + data + "')";
    }
}


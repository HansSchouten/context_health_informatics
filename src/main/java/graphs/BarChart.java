package graphs;

import java.util.ArrayList;

import model.ColumnType;

/**
 * This class represents a barchart that can be viewed using a webview.
 * @author Matthijs
 *
 */
public class BarChart extends Graph {

    /** This variable stores the inputnumber of the line. */
    protected int inputNumber;

    /** This variable stores the inputs required to drqw this graph. */
    protected ArrayList<InputType> inputs;

    /**
     * Constructs a new barchart object, that can be drawn in a webview.
     */
    public BarChart() {
        super("Bar Chart", "/graphs/barchart.html", false);
        inputNumber = 0;
        inputs = new ArrayList<InputType>();
        inputs.add(new InputType("date", ColumnType.DATE));
        inputs.add(new InputType("bar " + inputNumber, ColumnType.INT));
        inputNumber++;
    }

    @Override
    public ArrayList<InputType> getRequiredInputs() {
        return inputs;
    }

    @Override
    public InputType getAddableItem() {
        InputType result =  new InputType("bar " + inputNumber, ColumnType.INT);
        inputNumber++;
        return result;
    }

    @Override
    public String getURL() {
        return "/graphs/barchart.html";
    }

    @Override
    public String getScript(String data) {
        return "drawBarChart('" + data + "')";
    }
}

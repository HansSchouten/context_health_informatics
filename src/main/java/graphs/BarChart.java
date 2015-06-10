package graphs;

import model.ColumnType;

/**
 * This class represents a barchart that can be viewed using a webview.
 * @author Matthijs
 *
 */
public class BarChart extends Graph {

    /** This variable stores the inputnumber of the line. */
    protected int inputNumber;


    /**
     * Constructs a new barchart object, that can be drawn in a webview.
     */
    public BarChart() {
        super("Bar Chart", "/graphs/barchart.html", false);
        inputNumber = 0;
        inputs.add(new InputType("date", ColumnType.DATE));
        inputs.add(new InputType("bar " + inputNumber, ColumnType.INT));
        inputNumber++;
    }

    @Override
    public InputType getAddableItem() {
        InputType result =  new InputType("bar " + inputNumber, ColumnType.INT);
        inputNumber++;
        return result;
    }

    @Override
    public String getScript(String name, String data) {
        return "drawBarChart('"+ name + "', '" + data + "')";
    }
}

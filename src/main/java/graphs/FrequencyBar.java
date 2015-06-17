package graphs;

import java.util.ArrayList;

import model.ColumnType;

/**
 * This class is used to represent a frequency bar, that can be drawn in a webview.
 * @author Matthijs
 *
 */
public class FrequencyBar extends Graph {

    /**
     * construct a new frequency bar object, that can draw in a webview.
     */
    public FrequencyBar() {
        super("Frequency Bar", "/graphs/frequency_bar.html", true);

        ArrayList<ColumnType> types = new ArrayList<ColumnType>();
        for (ColumnType coltype: ColumnType.values()) {
            types.add(coltype);
        }

        inputs.add(new InputType("x", types, true, true));
    }

    @Override
    public String getScript(String name, String data) {
        return "drawFrequencyBar('" + name + "', '" + data + "')";
    }

}

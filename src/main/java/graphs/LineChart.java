package graphs;

import java.util.ArrayList;

import model.ColumnType;
import javafx.concurrent.Worker.State;
import javafx.scene.web.WebView;

/**
 * This class represent a LineChart that can be drawn in the webview.
 * @author Matthijs
 *
 */
public class LineChart extends Graph{
    
    protected int inputNumber;
    
    /** This variable stores the inputs required to drqw this graph */
    protected ArrayList<InputType> inputs;

    /**
     * Constructs a new linechart object, that can be drawn in a webview.
     */
    public LineChart() {
        super("Line Chart", "/graphs/linechart.html", false);
        inputNumber = 0;
        inputs = new ArrayList<InputType>();
        inputs.add(new InputType("date", ColumnType.DATE));
        inputs.add(new InputType("value" + inputNumber, ColumnType.INT));
        inputNumber++;
    }

    @Override
    public void drawInWebView(WebView webView, String data) {
        String url = this.getClass().getResource("/graphs/linechart.html").toExternalForm();
        webView.getEngine().load(url);
        System.out.println("drawLineGraph(\"" + data + "\")");
        webView.getEngine().getLoadWorker().stateProperty().addListener(
                (obs, oldV, newV) -> {
                    if (newV == State.SUCCEEDED) {
                        System.out.println("done!!");
                        webView.getEngine().executeScript("drawLineGraph('" + data + "')");
                    }
                }
        );
        
    }

    @Override
    public ArrayList<InputType> getRequiredInputs() {
        return inputs;
    }
    
    @Override
    public InputType getAddableItem() {
        InputType result =  new InputType("value" + inputNumber, ColumnType.INT);
        inputNumber++;
        return result;
    }
}

package graphs;

import java.util.ArrayList;

import model.ColumnType;
import javafx.concurrent.Worker.State;
import javafx.scene.web.WebView;

public class BoxPlot extends Graph{
    
    /** This variable stores the inputs required to drqw this graph */
    protected ArrayList<InputType> inputs;

    /**
     * Construct a boxplot.
     */
    public BoxPlot() {
        super("boxplot", "/graphs/boxplot.html", true);
        inputs = new ArrayList<InputType>();
        inputs.add(new InputType("x", ColumnType.INT));
    }

    @Override
    public void drawInWebView(WebView webView, String data) {
        String url = this.getClass().getResource("/graphs/boxplot.html").toExternalForm();
        webView.getEngine().load(url);
        System.out.println("drawBoxPlot(\"" + data + "\")");
        webView.getEngine().getLoadWorker().stateProperty().addListener(
                (obs, oldV, newV) -> {
                    if (newV == State.SUCCEEDED) {
                        System.out.println("done!!");
                        webView.getEngine().executeScript("drawBoxPlot('" + data + "')");
                    }
                }
        );
    }
    
    @Override
    public ArrayList<InputType> getRequiredInputs() {
        return inputs;
    }
}

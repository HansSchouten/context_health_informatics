package controller;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.web.WebView;

public class GraphController extends Application{
    
    /**
     * Variable that stores the stage of the program.
     */
    private Stage primaryStage;
    
    /**
     * Variable that stores the root layout.
     */
    private AnchorPane rootLayout;
    
    /** The web view to create and view graphs. */
    @FXML
    private WebView webView;

    @Override
    public void start(Stage ps) throws Exception {
        this.primaryStage = ps;
        this.primaryStage.setTitle("AnalyCs GraphCreator");
        initRootLayout();
    }
    
    /**
     * This method initialises the root layout of the program.
     */
    public void initRootLayout() {
        
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource("../view/graphview.fxml"));
            rootLayout = (AnchorPane) loader.load();
            
            // Create the scene
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
            primaryStage.setMaximized(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Main method starts the application.
     * @param args        - Arguments to start the application
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    @FXML
    public void clear() {
        
    }

    @FXML
    public void chooseFile() {
        
    }
    
    /**
     * Sets up the graph options, to choose the axis' and graph style.
     */
    private void setupWebView() {
        String url = this.getClass().getResource("/graphs/GraphView.html").toExternalForm();
        webView.getEngine().load(url);
    }
}

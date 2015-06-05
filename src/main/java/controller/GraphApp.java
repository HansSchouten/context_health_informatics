package controller;

import javafx.stage.Stage;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.fxml.FXML;

/**
 * This class starts and runs the mainapp.
 * @author Matthijs
 *
 */
public class GraphApp extends Application {
    
    /**
     * Variable that stores the stage of the program.
     */
    private Stage primaryStage;
    
    /**
     * Variable that stores the root layout.
     */
    private AnchorPane rootLayout;
    
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
            GraphController controller = loader.getController();
            controller.graphApp = this;
            
            // Create the scene
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method returns the primary Stage of this appliation.
     * @return          - Primary state.
     */
    public Stage getPrimaryState() {
        return primaryStage;
    }
    
    /**
     * Main method starts the application.
     * @param args        - Arguments to start the application
     */
    public static void main(String[] args) {
        launch(args);
    }

}

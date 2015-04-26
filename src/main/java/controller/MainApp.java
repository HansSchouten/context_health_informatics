package controller;

import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.RowData;

public class MainApp extends Application {

	private Stage primaryStage;
    private AnchorPane rootLayout;
    
    /**
     * The main data of this application as an observable list
     */
    private ObservableList<RowData> rowData = FXCollections.observableArrayList();
    
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Analyzinator");
		
		initRootLayout();
	}
	
	/**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("../view/MainView.fxml"));
            rootLayout = (AnchorPane) loader.load();
            
            // Create the scene
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
            
            // Add main stylesheet
            File f = new File("src/main/java/view/MainStyle.css");
            scene.getStylesheets().clear();
            scene.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));
            
            // Set the views in the scene
            setView("../view/ImportView.fxml", 	"importAnchor");
            setView("../view/LinkView.fxml", 	"linkAnchor");
            setView("../view/SpecifyView.fxml", "specifyAnchor");
            setView("../view/AnalyzeView.fxml", "analyzeAnchor");
            setView("../view/ResultsView.fxml", "resultsAnchor");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Sets the view of an fxml file in an anchorpane inside the scene
     * @param viewPath The path to the fxml file
     * @param fxid The fxid of the pane inside the scene
     */
    private void setView(String viewPath, String fxid) {
    	try {
            // Load the view
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource(viewPath));
            Pane importedPane = (Pane) loader.load();
            
            // Add a reference of the main app to its controller
            SubController ctrl = loader.getController();
            ctrl.setMainApp(this);
            
            // Find the anchor pane in the scene and add the view there
            AnchorPane scenePane = (AnchorPane) rootLayout.getScene().lookup("#" + fxid);
            scenePane.getChildren().add(importedPane);
            
            // Set the anchor values to 0 to let the panes fill the entire window
            scenePane.setBottomAnchor(importedPane, 0.0);
            scenePane.setTopAnchor(importedPane, 0.0);
            scenePane.setLeftAnchor(importedPane, 0.0);
            scenePane.setRightAnchor(importedPane, 0.0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

	public static void main(String[] args) {
		launch(args);
	}
}

package controller;

import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Group;

public class MainApp extends Application {

	private Stage primaryStage;
    private AnchorPane rootLayout;
    
    private ArrayList<SubController> controllers;
    
    /**
     * The main data of this application as an observable list
     */
    private ArrayList<Group> groups;
    
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Analyzinator");
		
		initRootLayout();
	}
	
	/**
	 * This method initialises the root layout of the program
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
            primaryStage.setMaximized(true);
            
            // Add main stylesheet
//            File f = new File("src/main/java/view/MainStyle.css");
//            scene.getStylesheets().clear();
//            scene.getStylesheets().add(this.getClass().getResource("/src/main/java/view/MainStyle.css").toExternalForm());
            
            // Set the views in the scene
            controllers = new ArrayList<SubController>();
            setView("../view/ImportView.fxml", 	"importAnchor");
            setView("../view/LinkView.fxml", 	"linkAnchor");
            setView("../view/SpecifyView.fxml", "specifyAnchor");
            setView("../view/AnalyzeView.fxml", "analyzeAnchor");
            
            // Switching between stages
            // (To do: Could be implemented in every controller instead of here)
            TabPane tabPane = (TabPane) scene.lookup("#tabPane");
            tabPane.getSelectionModel().selectedItemProperty().addListener(
            	    new ChangeListener<Tab>() {
						public void changed(ObservableValue<? extends Tab> arg0, Tab oldTab, Tab newTab) {
							if (oldTab.getText().equals("Import") && newTab.getText().equals("Link")) {
								// Create groups:
								ImportController ic = (ImportController) controllers.get(0);
								LinkController lc = (LinkController) controllers.get(1);
								
								// Check for input errors
								if (ic.correctCheck())
									lc.setGroups(ic.getGroups());
								else
									// Don't change the tab if there are errors
									tabPane.getSelectionModel().select(0);
							}
						}
            	    }
            	);
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
            controllers.add(ctrl);
            
            // Find the anchor pane in the scene and add the view there
            AnchorPane scenePane = (AnchorPane) rootLayout.getScene().lookup("#" + fxid);
            scenePane.getChildren().add(importedPane);
            
            // Set the anchor values to 0 to let the panes fill the entire window
            AnchorPane.setBottomAnchor(importedPane, 0.0);
            AnchorPane.setTopAnchor(importedPane, 0.0);
            AnchorPane.setLeftAnchor(importedPane, 0.0);
            AnchorPane.setRightAnchor(importedPane, 0.0);
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

    /**
     * Main method starts the application
     * @param args		- Arguments to start the application
     */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * This method gets the groups of this view.
	 * @return		- List with all the groups
	 */
	public ArrayList<Group> getGroups() {
		return groups;
	}

	/**
	 * This method sets the groups of this view
	 * @param groups	- List with the groups of this view
	 */
	public void setGroups(ArrayList<Group> groups) {
		this.groups = groups;
	}
	
	
}

package controller;

import java.io.IOException;
import java.util.ArrayList;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Group;

/**
 * This class is the mainApp of the program.
 * It should be started in order to run the program.
 * @author Matthijs
 */
public class MainApp extends Application {

    /**
     * Variable that stores the stage of the program.
     */
	private Stage primaryStage;

	/**
	 * Variable that stores the root layout.
	 */
    private AnchorPane rootLayout;

    /**
     * Variable that stores all the controllers.
     */
    private ArrayList<SubController> controllers;

    /**
     * The main data of this application as an observable list.
     */
    private ArrayList<Group> groups;

	@Override
	public void start(Stage ps) {
		this.primaryStage = ps;
		this.primaryStage.setTitle("AnalyCs");
		initRootLayout();
	}

	/**
	 * This method initialises the root layout of the program.
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
            scene.getStylesheets().add(this.getClass().getResource("/view/MainStyle.css").toExternalForm());

            // Set the views in the scene
            controllers = new ArrayList<SubController>();
            setView("../view/ImportView.fxml", 	"importAnchor");
            setView("../view/LinkView.fxml", 	"linkAnchor");
            setView("../view/SpecifyView.fxml", "specifyAnchor");
            setView("../view/ResultsView.fxml", "resultsAnchor");

            // Switching between stages
            TabPane tabPane = (TabPane) scene.lookup("#tabPane");
            tabPane.getSelectionModel().selectedIndexProperty()
            	.addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> arg0, Number oldV, Number newV) {
					// If the input of the old tab is not valid, do not change tabs
					if (newV.intValue() - oldV.intValue() == 1) {
						if (!controllers.get(oldV.intValue()).validateInput())
							tabPane.getSelectionModel().select(oldV.intValue());
					}
					// When navigating to a tab which is after the next one, do not change tabs
					else if (newV.intValue() > oldV.intValue()) {
						// Check for every next tab if the input is valid
						for (int i = oldV.intValue(); i < newV.intValue(); i++) {
							if (!controllers.get(i).validateInput()) {
								tabPane.getSelectionModel().select(oldV.intValue());
								System.out.println("You can only go the the next tab");
								break;
							}
						}
					}
				}
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the view of an fxml file in an anchorpane inside the scene.
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
     * @return  - Stage of the mainApp.
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Main method starts the application.
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
	 * This method sets the groups of this view.
	 * @param grps	- List with the groups of this view.
	 */
	public void setGroups(final ArrayList<Group> grps) {
		this.groups = grps;
	}
	
	/**
	 * Shows a notification for a few seconds.
	 * @param text The message for the user
	 */
	public void showNotification(String text) {		
		Label noteLabel = (Label) rootLayout.getScene().lookup("#note-label");
		
		if (noteLabel.getOpacity() == 0) {
			FadeTransition ft = new FadeTransition(Duration.millis(600), noteLabel);
			ft.setFromValue(0);
			ft.setToValue(1);
			
			FadeTransition ftOut = new FadeTransition(Duration.millis(600), noteLabel);
			ftOut.setFromValue(1);
			ftOut.setToValue(0);
			ftOut.setDelay(Duration.seconds(3));
	
			ft.setOnFinished(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					ftOut.play();
				}
			});
			ft.play();
		}
	}
}

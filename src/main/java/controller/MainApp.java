package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.beans.Observable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
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

    /**
     * A menu item in the main menu bar.
     */
    @FXML
    private MenuItem newFile, openFile, saveFile, saveFileAs,
            newScript, openScript, saveScript, saveScriptAs, runScript;
    /**
     * A menu in the main menu bar.
     */
    @FXML
    private Menu recentFiles, recentScripts;

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
            loader.setLocation(this.getClass().getResource("/view/MainView.fxml"));
            loader.setController(this);
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
            setView("/view/ImportView.fxml",  "importAnchor");
            setView("/view/SelectView.fxml",  "linkAnchor");
            setView("/view/SpecifyView.fxml", "specifyAnchor");
            setView("/view/ResultsView.fxml", "resultsAnchor");

            // Sets up the actions for the menu bar
            setupMenuBar();

            // Hide notification when clicking
            Label noteLabel = (Label) rootLayout.getScene().lookup("#note-label");
            noteLabel.setOnMouseClicked(e -> noteLabel.setVisible(false));

            // Switching between stages
            TabPane tabPane = (TabPane) scene.lookup("#tabPane");
            tabPane.getSelectionModel().selectedIndexProperty().addListener((obs, oldV, newV) -> {
                // If the input of the old tab is not valid, do not
                // change tabs
                if (newV.intValue() - oldV.intValue() == 1) {
                    if (!controllers.get(oldV.intValue())
                            .validateInput(true)) {
                        tabPane.getSelectionModel().select(
                                oldV.intValue());
                    } else {
                        // If the input is valid, set the data in the next tab.
                        controllers.get(newV.intValue()).setData(
                                controllers.get(oldV.intValue()).getData());
                    }
                } else if (newV.intValue() > oldV.intValue()) {
                    // When navigating to a tab which is after the next
                    // one, do not change tabs

                    // Check for every next tab if the input is valid
                    for (int i = oldV.intValue(); i < newV
                            .intValue(); i++) {
                        if (!controllers.get(i).validateInput(false)) {
                            tabPane.getSelectionModel().select(
                                    oldV.intValue());
                            showNotification("You can only go to the next or any "
                                    + "of the previous tabs.",
                                    NotificationStyle.INFO);
                            break;
                        } else if (i != 0) {
                            // i != 0 because import cannot receive data.
                            // If the input is valid, set the data in the next tab.
                            controllers.get(i).setData(
                                    controllers.get(i - 1).getData());
                        }
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Changes arrow the icon of a tab.
     * @param idx The index of the tab in the tabpane.
     * @param status The status is the color of the icon; false means red and true means green.
     */
    public void changeIcon(int idx, Boolean status) {
        TabPane tabPane = (TabPane) rootLayout.getScene().lookup("#tabPane");
        Tab t = tabPane.getTabs().get(idx);

        t.getStyleClass().removeAll("red-arrow", "green-arrow");
        if (status) {
            t.getStyleClass().add("green-arrow");
        } else {
            t.getStyleClass().add("red-arrow");
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
     * @param args        - Arguments to start the application
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * This method gets the groups of this view.
     * @return        - List with all the groups
     */
    public ArrayList<Group> getGroups() {
        return groups;
    }

    /**
     * This method sets the groups of this view.
     * @param grps    - List with the groups of this view.
     */
    public void setGroups(final ArrayList<Group> grps) {
        this.groups = grps;
    }

    /**
     * Returns the rootlayout.
     * @return The rootlayout.
     */
    public AnchorPane getRootLayout() {
        return rootLayout;
    }

    /**
     * Sets up the various actions of the menu bar.
     */
    private void setupMenuBar() {
        ImportController ic = (ImportController) controllers.get(0);
        SpecifyController sc = (SpecifyController) controllers.get(2);

        // Store the recently opened files with JavaPreferences
        final RecentFilesController recFiles = new RecentFilesController("recentfile", 5);
        final RecentFilesController recScripts = new RecentFilesController("recentscript", 5);

        // Bind the menu actions to the correct functions
        newFile.setOnAction(e -> ic.reset());
        openFile.setOnAction(e -> recFiles.add(ic.chooseConfiguration()));
        saveFile.setOnAction(e -> recFiles.add(ic.saveConfiguration()));
        saveFileAs.setOnAction(e -> recFiles.add(ic.saveConfiguration()));

        newScript.setOnAction(e -> sc.addNewTab());
        openScript.setOnAction(e -> sc.chooseFiles().forEach(recScripts::add));
        saveScript.setOnAction(e -> recScripts.add(sc.saveFile()));
        saveScriptAs.setOnAction(e -> recScripts.add(sc.saveFileAs()));
        runScript.setOnAction(e -> sc.parse());

        // Bind the recent file lists to their menu lists
        recFiles.getFiles().addListener((Observable obs) -> {
            recentFiles.getItems().clear();
            for (File f : recFiles.getFiles()) {
                MenuItem mu = new MenuItem(f.getName());
                mu.setOnAction(e2 -> { ic.openConfiguration(f); recFiles.add(f); });
                recentFiles.getItems().add(mu);
            }
        });

        recScripts.getFiles().addListener((Observable obs) -> {
            recentScripts.getItems().clear();
            for (File f : recScripts.getFiles()) {
                MenuItem mu = new MenuItem(f.getName());
                mu.setOnAction(e2 -> { sc.openFiles(Arrays.asList(f)); recScripts.add(f); });
                recentScripts.getItems().add(mu);
            }
        });

        // Update list in GUI by adding and removing an item
        File f = new File("dummy");
        recFiles.getFiles().add(f);
        recFiles.getFiles().remove(f);
        recScripts.getFiles().add(f);
        recScripts.getFiles().remove(f);
    }

    /**
     * Shows a notification for a few seconds.
     * @param text The message for the user
     * @param style The style of the notification
     */
    public void showNotification(String text, NotificationStyle style) {
        Label noteLabel = (Label) rootLayout.getScene().lookup("#note-label");

        // If there was already a notification shown, overwrite it with this one.
        if (noteLabel.getOpacity() > 0) {
            noteLabel.setOpacity(0);
        }

        noteLabel.getStyleClass().removeAll("info-graphic", "remove-graphic");
        switch (style) {
        case INFO:
            noteLabel.getStyleClass().add("info-graphic");
            break;
        case WARNING:
            noteLabel.getStyleClass().add("warning-graphic");
            break;
        default:
            noteLabel.getStyleClass().add("info-graphic");
            break;
        }

        noteLabel.setVisible(true);
        noteLabel.setText(text);
        noteLabel.setMaxHeight(noteLabel.getPrefHeight());

        FadeTransition ftIn = new FadeTransition(Duration.millis(400), noteLabel);
        ftIn.setFromValue(0);
        ftIn.setToValue(1);

        PauseTransition pause = new PauseTransition(Duration.seconds(2));

        FadeTransition ftOut = new FadeTransition(Duration.millis(400), noteLabel);
        ftOut.setFromValue(1);
        ftOut.setToValue(0);

        ftIn.setOnFinished(e -> pause.play());
        pause.setOnFinished(e -> {
            if (text.equals(noteLabel.getText()) && noteLabel.getOpacity() == 1) {
                ftOut.play();
            }
        });
        ftOut.setOnFinished(e -> noteLabel.setVisible(false));

        ftIn.play();
    }

    /**
     * The style of the notification.
     * @author Remi
     *
     */
    public enum NotificationStyle {
        /**
         * Changes the graphic of the notification to the info graphic.
         */
        INFO,
        /**
         * Changes the graphic of the notification to the warning graphic.
         */
        WARNING;
    }
}

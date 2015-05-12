package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;

/**
 * This method represent a controller for the specify tab of the main view.
 * @author Matthijs
 *
 */
public class SpecifyController extends SubController {
	/**
	 * The tab pane which contains the tabs with text areas.
	 */
	@FXML
	private TabPane tabPane;

	/**
	 * Construct a SpecifyController.
	 */
	public SpecifyController() {
	}

	@Override
	protected void initialize() {
		addNewTab();
	}

	/**
	 * Adds a new tab to the tabview of scripts.
	 */
	@FXML
	public void addNewTab() {
		Tab tab = new Tab();
		tab.setText("New file");

		TextArea ta = new TextArea();
		ta.setFont(Font.font("Courier New"));
		ta.setId("script-text-area");

		TextArea lineNumbers = new TextArea();
		lineNumbers.setPrefWidth(32);
		lineNumbers.setDisable(true);
		lineNumbers.setId("line-numbers");

		// Bind scrolling to the other text area
		ta.scrollTopProperty().bindBidirectional(
				lineNumbers.scrollTopProperty());

		// Show correct amount of line numbers
		lineNumbers.setText("1");
		ta.textProperty().addListener(new ChangeListener<String>() {
			private int lines = 0;

			@Override
			public void changed(ObservableValue<? extends String> arg0,
					String oldString, String newString) {
				// Adding a space because new lines without content are not
				// counted as a new line
				String text = newString + " ";
				int newLines = text.split("\n").length;

				if (lines != newLines) {
					lines = newLines;
					lineNumbers.clear();
					for (int i = 1; i < newLines; i++)
						lineNumbers.appendText(i + "\n");
					lineNumbers.appendText((newLines) + "");
				}
			}
		});

		HBox hbox = new HBox();
		hbox.getChildren().addAll(lineNumbers, ta);
		HBox.setHgrow(ta, Priority.ALWAYS);

		tab.setContent(hbox);
		tabPane.getTabs().add(tab);
		tabPane.getSelectionModel().select(tab);

		ta.requestFocus();
	}

	/**
	 * Opens a filechooser to save to file to a location.
	 */
	@FXML
	public void saveFile() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save file");

		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("Text file (*.txt)", "*.txt"),
				new FileChooser.ExtensionFilter("AnalyCs files (*.acs)",
						"*.acs"));

		File f = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

		Tab selected = tabPane.getSelectionModel().getSelectedItem();
		TextArea ta = (TextArea) selected.getContent().lookup(
				"#script-text-area");

		writeFile(f, ta.getText());

		selected.setText(f.getName());
	}

	/**
	 * Opens a filechooser to choose files and opens them in new tabs.
	 */
	@FXML
	public void openFile() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Import files");

		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt"),
				new FileChooser.ExtensionFilter("AnalyCs files (*.acs)",
						"*.acs"));

		List<File> files = fileChooser.showOpenMultipleDialog(mainApp
				.getPrimaryStage());

		if (files != null) {
			for (File f : files) {
				// Get canonical path to file
				String path = "Path not found";
				String name = "Error";
				try {
					path = f.getCanonicalPath();
					name = f.getName();
					// Add to view
					String text = readFile(path);
					addTabWithContent(name, text);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Adds a new tab with a name and text in its text area.
	 * @param name The name of the tab
	 * @param text The content of the text area
	 */
	public void addTabWithContent(String name, String text) {
		addNewTab();
		Tab selected = tabPane.getSelectionModel().getSelectedItem();
		TextArea ta = (TextArea) selected.getContent().lookup(
				"#script-text-area");

		selected.setText(name);
		ta.setText(text);
	}

	/**
	 * Reads a text file and returns its contents using a buffered reader.
	 * @param path The path to the file
	 * @return The content of the file
	 */
	public String readFile(String path) {
		String res = "";

		try {
			FileReader fileReader = new FileReader(path);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			String line = "";
			while ((line = bufferedReader.readLine()) != null) {
				res += line + "\n";
			}

			bufferedReader.close();
		} catch (Exception e) {
			res += "Cannot read file: \n";
			res += e.getMessage();
			e.printStackTrace();
		}

		return res;
	}

	/**
	 * Writes a file to a given location with a string as content.
	 * @param file The file to be written
	 * @param text The content of the file
	 */
	public void writeFile(File file, String text) {
		try {
			FileWriter fileWriter = null;
			fileWriter = new FileWriter(file);
			fileWriter.write(text);
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Copies the currently selected text.
	 */
	@FXML
	public void copy() {
		if (getSelectedTextArea() != null)
			getSelectedTextArea().copy();
	}

	/**
	 * Cuts the currently selected text.
	 */
	@FXML
	public void cut() {
		if (getSelectedTextArea() != null)
			getSelectedTextArea().cut();
	}

	/**
	 * Pastes the currently selected text.
	 */
	@FXML
	public void paste() {
		if (getSelectedTextArea() != null)
			getSelectedTextArea().paste();
	}

	/**
	 * Returns the currently selected tab. Returns null if there is none.
	 * @return The currently selected tab
	 */
	public Tab getSelectedTab() {
		return tabPane.getSelectionModel().getSelectedItem();
	}

	/**
	 * Returns the currently selected text area. Returns null if there is none.
	 * @return The currently selected text area
	 */
	public TextArea getSelectedTextArea() {
		if (getSelectedTab() != null)
			return (TextArea) getSelectedTab().getContent().lookup(
				"#script-text-area");
		return null;
	}

	@Override
	public boolean validateInput() {
		// TODO Auto-generated method stub
		return true;
	}
}

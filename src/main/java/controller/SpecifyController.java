package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;

import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.StyleSpans;
import org.fxmisc.richtext.StyleSpansBuilder;

import controller.MainApp.NotificationStyle;

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
	 * The keywords of the scripting language.
	 */
	private static final String[] KEYWORDS = new String[] {
		"LABEL", "CHUNK", "FILTER", "COMPUTE", "CONNECT", "COMPARE", "COMMENT",
		"IF", "THEN", "DO",
		"RECORDS", "COLUMN", "COL",
		"WITH", "WHERE", "ON", "PER"};

	/** The pattern for keywords. */
	private static final String KEYWORD_PATTERN = "\\b(" + String.join("|", KEYWORDS) + ")\\b";
	/** The pattern for paren (rounded brackets). */
	private static final String PAREN_PATTERN = "\\(|\\)";
	/** The pattern for braces. */
	private static final String BRACE_PATTERN = "\\{|\\}";
	/** The pattern for square brackets. */
	private static final String BRACKET_PATTERN = "\\[|\\]";
	/** The pattern for strings. */
	private static final String STRING_PATTERN = "\"([^\"\\\\]|\\\\.)*\"";
	/** The pattern for comments (/* and //). */
	private static final String COMMENT_PATTERN = "//[^\n]*" + "|" + "/\\*(.|\\R)*?\\*/";
	/** The pattern for all of the above patterns combined. */
	private static final Pattern PATTERN = Pattern.compile(
			"(?<KEYWORD>" + KEYWORD_PATTERN + "|" + KEYWORD_PATTERN.toLowerCase() + ")"
			+ "|(?<PAREN>" + PAREN_PATTERN + ")" + "|(?<BRACE>" + BRACE_PATTERN + ")"
			+ "|(?<BRACKET>" + BRACKET_PATTERN + ")" + "|(?<STRING>" + STRING_PATTERN + ")"
			+ "|(?<COMMENT>" + COMMENT_PATTERN + ")");

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

		CodeArea codeArea = new CodeArea();
		codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
		codeArea.textProperty().addListener((obs, oldText, newText) -> {
			codeArea.setStyleSpans(0, computeHighlighting(newText));
		});

		codeArea.getStylesheets().add(this.getClass().getResource("../view/script-keywords.css")
				.toExternalForm());
		codeArea.getStyleClass().add("code-area");

		ObservableList<KeyCode> modifiers = FXCollections.observableArrayList();
		modifiers.addAll(KeyCode.CONTROL, KeyCode.ALT_GRAPH, KeyCode.ALT, KeyCode.SHIFT);

		// Enable keyboard shortcuts even when focus is on textfield
		codeArea.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				if (e.isControlDown() && !modifiers.contains(e.getCode())) {
					Runnable r = tabPane.getScene().getAccelerators().get(
						new KeyCodeCombination(e.getCode(), KeyCombination.CONTROL_DOWN));
					if (r != null) {
						r.run();
					}
				}
			}
		});

		HBox.setHgrow(codeArea, Priority.ALWAYS);
		tab.setContent(codeArea);
		tabPane.getTabs().add(tab);
		tabPane.getSelectionModel().select(tab);

		codeArea.requestFocus();
	}

	/**
	 * Computes the highlighting of the text based on the predefined patterns.
	 * @param text The text which you want to be highlighted.
	 * @return The style corresponding to the input text.
	 */
	private static StyleSpans<Collection<String>> computeHighlighting(String text) {
		Matcher matcher = PATTERN.matcher(text);
		int lastKwEnd = 0;
		StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
		while (matcher.find()) {
			String styleClass = matcher.group("KEYWORD") != null ? "keyword"
					: matcher.group("PAREN") 	!= null ? "paren" : matcher
							.group("BRACE") 	!= null ? "brace" : matcher
							.group("BRACKET") 	!= null ? "bracket" : matcher
							.group("STRING") 	!= null ? "string" : matcher
							.group("COMMENT") 	!= null ? "comment" : null;
			assert styleClass != null;
			spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
			spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
			lastKwEnd = matcher.end();
		}
		spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
		return spansBuilder.create();
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
				new FileChooser.ExtensionFilter("AnalyCs files (*.acs)", "*.acs"));

		File f = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

		if (f != null && getSelectedCodeArea() != null) {
			String text = getSelectedCodeArea().getText();

			writeFile(f, text);

			getSelectedTab().setText(f.getName());

			mainApp.showNotification("File saved succesfully as '" + f.getName()
						+ "'.", NotificationStyle.INFO);
		}
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

		List<File> files = fileChooser.showOpenMultipleDialog(mainApp.getPrimaryStage());

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
					mainApp.showNotification("File could not be opened: '" + f.getName()
							+ "'. \n" + e.getMessage(), NotificationStyle.WARNING);
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
		getSelectedTab().setText(name);
		getSelectedCodeArea().appendText(text);
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
			mainApp.showNotification("An error occurred while reading the file.",
					NotificationStyle.WARNING);
			res = e.getMessage();
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
		if (getSelectedCodeArea() != null) {
			getSelectedCodeArea().copy();
		}
	}

	/**
	 * Cuts the currently selected text.
	 */
	@FXML
	public void cut() {
		if (getSelectedCodeArea() != null) {
			getSelectedCodeArea().cut();
		}
	}

	/**
	 * Pastes the currently selected text.
	 */
	@FXML
	public void paste() {
		if (getSelectedCodeArea() != null) {
			getSelectedCodeArea().paste();
		}
	}

	/**
	 * Undoes the last action in the script editor.
	 */
	@FXML
	public void undo() {
		if (getSelectedCodeArea() != null) {
			getSelectedCodeArea().undo();
		}
	}

	/**
	 * Redoes the last action in the script editor.
	 */
	@FXML
	public void redo() {
		if (getSelectedCodeArea() != null) {
			getSelectedCodeArea().redo();
		}
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
	public CodeArea getSelectedCodeArea() {
		if (getSelectedTab() != null) {
			return (CodeArea) getSelectedTab().getContent().lookup(".code-area");
		}
		return null;
	}

	@Override
	public boolean validateInput(boolean showPopup) {
		return true;
	}
}

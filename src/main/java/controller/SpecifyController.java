package controller;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import model.Column;
import model.Reader;
import model.Record;
import model.SequentialData;
import model.Writer;

import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.StyleSpans;
import org.fxmisc.richtext.StyleSpansBuilder;

import analyze.AnalyzeException;
import analyze.parsing.ParseResult;
import analyze.parsing.Parser;
import controller.MainApp.NotificationStyle;


/**
 * This method represent a controller for the specify tab of the main view.
 * @author Matthijs
 *
 */
public class SpecifyController extends SubController {
    /** The tab pane which contains the tabs with text areas. */
    @FXML
    private TabPane tabPane;

    /** The tree view in the sidebar. */
    @FXML
    private ListView<String> columnList, varList;

    /** The accordion in de sidebar. */
    @FXML
    private Accordion accordion;

    /** The linked groups. */
    private SequentialData seqData;

    /**
     * The result after running the script.
     */
    private ParseResult result;
    
    /**
     * The parser to parse the text.
     */
    private Parser parser;

    /** The pattern of the syntax highlighting in the script. */
    private Pattern pattern;

    /** This variable stores the pipeline number of this controller. */
    private int pipelineNumber = 3;

    /**
     * Construct a SpecifyController.
     */
    public SpecifyController() {
    }

    @Override
    protected void initialize() {
        addNewTab();
        setupAccordion();

        parser = new Parser();

        varList.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                String key = varList.getSelectionModel().getSelectedItem().split(" ")[0];
                ParseResult res = parser.getVariables().get(key);

                if (res != null) {
                    Tab tab = new Tab();
                    TableView<Record> table = new TableView<Record>();

                    ResultsController.createTable(table, res);

                    tab.setContent(table);
                    tab.setText(key);

                    tabPane.getTabs().add(tab);
                    tabPane.getSelectionModel().select(tab);
                    tab.setTooltip(new Tooltip("Variable"));
                }
            }
        });
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
        codeArea.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                if (e.isControlDown() && e.isShiftDown() && !modifiers.contains(e.getCode())) {
                    Runnable r = tabPane.getScene().getAccelerators().get(
                        new KeyCodeCombination(e.getCode(), KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN));
                    if (r != null) {
                        System.out.println("hier");
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
    private StyleSpans<Collection<String>> computeHighlighting(String text) {
        Matcher matcher = pattern.matcher(text);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
        while (matcher.find()) {
            String styleClass = null;
            if (matcher.group("KEYWORD") != null) {
                styleClass = "keyword";
            } else if (matcher.group("COLUMN") != null) {
                styleClass = "column";
            } else if (matcher.group("PAREN") != null) {
                styleClass = "paren";
            } else if (matcher.group("BRACE") != null) {
                styleClass = "brace";
            } else if (matcher.group("BRACKET") != null) {
                styleClass = "bracket";
            } else if (matcher.group("STRING") != null) {
                styleClass = "string";
            } else if (matcher.group("COMMENT") != null) {
                styleClass = "comment";
            }

            assert styleClass != null;
            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
        }
        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
        return spansBuilder.create();
    }

    /**
     * Compiles the pattern of keywords, operators, columns, etc.
     * @param columns The columns used in the analysis.
     */
    public void compilePattern(String[] columns) {
        // A string containing the columns used in the analysis.
        String columnPattern = "\\b(" + String.join("|", columns) + ")\\b";

        // The keywords of the scripting language.
        String[] keywords = new String[] {
            "CHUNK ON", "PER \\d+ DAYS", "CHUNK PER \\d+ DAYS",
            "COMPUTE", "AVERAGE", "COUNT", "SUM", "MAX", "MIN", "DEVIATION", "VAR", "SQUARED",
            "LABEL", "WITH", "WHERE",
            "FILTER WHERE", "FILTER WITH",
            "COMMENT WHERE",
            "RECORDS", "COL"};
        // The pattern for keywords.
        String keywordPattern = "\\b(" + String.join("|", keywords) + ")\\b";

        // The pattern for paren (rounded brackets).
        String parenPattern = "\\(|\\)";
        // The pattern for braces.
        String bracePattern = "\\{|\\}";
        // The pattern for square brackets.
        String bracketPattern = "\\[|\\]";
        // The pattern for strings.
        String stringPattern = "\"([^\"\\\\]|\\\\.)*\"";
        // The pattern for comments (/* and //).
        String commentPattern = "//[^\n]*" + "|" + "/\\*(.|\\R)*?\\*/";

        // The pattern for all of the above patterns combined.
        pattern = Pattern.compile(
                "(?<KEYWORD>" + keywordPattern + "|" + keywordPattern.toLowerCase() + ")"
                + "|(?<PAREN>" + parenPattern + ")" + "|(?<BRACE>" + bracePattern + ")"
                + "|(?<BRACKET>" + bracketPattern + ")" + "|(?<STRING>" + stringPattern + ")"
                + "|(?<COMMENT>" + commentPattern + ")" + "|(?<COLUMN>" + columnPattern + ")");
    }

    /**
     * Sets up the operator descriptions in the accordion.
     */
    private void setupAccordion() {
        String[] resources = {"Chunk", "Comment", "Compare", "Compute", "Connect", "Convert", "Filter", "Label",
                "Operators and Variables"};

        for (String s : resources) {
            TitledPane tp = new TitledPane();
            tp.setText(s);
            WebView wv = new WebView();

            String url = this.getClass().getResource("/view/documentation/" + s + ".html").toExternalForm();
            wv.getEngine().load(url);

            tp.setContent(wv);
            accordion.getPanes().add(tp);
        }
    }

    /**
     * Opens a filechooser to save to file to a location.
     * @return The selected file.
     */
    public File saveFileAs() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save file");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text file (*.txt)", "*.txt"),
                new FileChooser.ExtensionFilter("AnalyCs files (*.acs)", "*.acs"));

        File f = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        if (f != null && getSelectedCodeArea() != null) {
            String text = getSelectedCodeArea().getText();

            try {
                Writer.writeFile(f, text);
                getSelectedTab().setText(f.getName());
                getSelectedTab().setTooltip(new Tooltip(f.getPath()));
                mainApp.showNotification("File saved succesfully as '" + f.getName()
                            + "'.", NotificationStyle.INFO);
                return f;
            } catch (IOException e) {
                mainApp.showNotification("Cannot save '" + f.getName()
                        + "': " + e.getMessage(), NotificationStyle.WARNING);
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Tries to save a file with the path defined in its tooltip, else opens a filechooser.
     * @return The saved file.
     */
    public File saveFile() {
        if (getSelectedTab() != null) {
            // The full path is saved in the tooltip of the tab.
            if (getSelectedTab().getTooltip() != null) {
                String path = getSelectedTab().getTooltip().getText();
                String text = getSelectedCodeArea().getText();
                try {
                    File f = new File(path);
                    Writer.writeFile(f, text);
                    getSelectedTab().setTooltip(new Tooltip(f.getPath()));
                    mainApp.showNotification("File saved succesfully as '" + f.getName()
                                + "'.", NotificationStyle.INFO);
                    return f;
                } catch (IOException e) {
                    return saveFileAs();
                }
            } else {
                return saveFileAs();
            }
        }
        return null;
    }

    /**
     * Opens a filechooser to choose files and opens them in new tabs.
     * @return The chosen files.
     */
    public List<File> chooseFiles() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import files");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt"),
                new FileChooser.ExtensionFilter("AnalyCs files (*.acs)",
                        "*.acs"));

        List<File> files = fileChooser.showOpenMultipleDialog(mainApp.getPrimaryStage());
        openFiles(files);
        return files;
    }

    /**
     * Opens the files in new tabs.
     * @param files The files to open.
     */
    public void openFiles(List<File> files) {
        if (files != null) {
            for (File f : files) {
                // Get canonical path to file
                String path = "Path not found";
                String name = "Error";
                try {
                    path = f.getCanonicalPath();
                    name = f.getName();
                    // Add to view
                    String text = Reader.readLimited(path, Integer.MAX_VALUE);
                    addTabWithContent(name, text);
                    getSelectedTab().setTooltip(new Tooltip(f.getPath()));
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
        if (result == null) {
            if (showPopup) {
                mainApp.showNotification("You must run the script before continuing.",
                        NotificationStyle.INFO);
            }
            return false;
        }
        return true;
    }

    /**
     * Parses the text into the result variable.
     */
    @FXML
    public void parse() {
        if (getSelectedCodeArea() != null) {

            try {
                result = parser.parse(getSelectedCodeArea().getText(), seqData);
                mainApp.showNotification("Script succesfully executed.",
                        NotificationStyle.INFO);

                // Add the variables to the list
                HashMap<String, ParseResult> vars = parser.getVariables();
                vars.put("Result", result);
                varList.getItems().clear();

                for (String s : vars.keySet()) {
                    ParseResult pr = parser.getVariables().get(s);

                    if (pr instanceof SequentialData) {
                        SequentialData sd = (SequentialData) pr;
                        varList.getItems().add(s + " (Rows: " + sd.size() + ", Columns: " + sd.getColumns().length + ")");
                    } else {
                        varList.getItems().add(s + " = " + vars.get(s).toString());
                    }
                }
            } catch (AnalyzeException e) {
                mainApp.showNotification("Cannot parse script: " + e.getMessage(),
                        NotificationStyle.WARNING);
                e.printStackTrace();
            } catch (Exception e) {
                mainApp.showNotification("An error occured: " + e.getClass().getCanonicalName(),
                        NotificationStyle.WARNING);
                e.printStackTrace();
            }
        }
    }

    @Override
    public Object getData() {
        return result;
    }

    @Override
    public void setData(Object o) {
        seqData = (SequentialData) o;

        // Create the syntax highlighting pattern with the columns of the data.
        Set<String> cols = new TreeSet<String>();

        for (Column c : seqData.getColumns()) {
            cols.add(c.getName());
        }

        String[] colNames = new String[seqData.getColumns().length];
        for (int i = 0; i < seqData.getColumns().length; i++) {
            colNames[i] = seqData.getColumns()[i].getName();
        }

        compilePattern(colNames);

        // Setup column names
        columnList.getItems().clear();
        for (Column c : seqData.getColumns()) {
            columnList.getItems().add(c.getName() + " (" + c.getType().toString() + ")");
        }

        varList.getItems().clear();
        varList.getItems().add("$input (Size: " + seqData.size() + ")");
    }

    @Override
    protected int getPipelineNumber() {
        return pipelineNumber;
    }
}

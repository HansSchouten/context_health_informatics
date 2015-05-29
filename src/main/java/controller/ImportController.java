package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;

import javax.xml.parsers.ParserConfigurationException;

import model.Column;
import model.ColumnType;
import model.DateColumn;
import model.Group;
import model.Reader;

import org.xml.sax.SAXException;

import xml.XMLhandler;
import controller.MainApp.NotificationStyle;

/**
 * This class controls the view of the import tab of the program.
 * @author Matthijs
 *
 */
public class ImportController extends SubController {

    /** Variable that stores the listview. */
    @FXML
    private ListView<GroupListItem> groupListView;

    /** Variable that stores the columview. */
    @FXML
    private ListView<ColumnListItem> columnListView;

    /** Variable that stores the file list view. */
    @FXML
    private ListView<FileListItem> fileListView;

    /** The text area to preview a selected file. */
    @FXML
    private TextArea filePreview;

    /** A combobox for choosing the primary key for the current group. */
    @FXML
    private ComboBox<String> keyBox;

    /** The current list of column names to choose the primary key. */
    private ObservableList<String> keyListItems = FXCollections.observableArrayList();

    /** The list of group list items. */
    private ObservableList<GroupListItem> groupList = FXCollections.observableArrayList();

    /** The list of delimiters to choose from. */
    private ObservableList<String> delimiterStringList = FXCollections.observableArrayList();

    /** The delimiters used for parsing a file. */
    public static String[] delims = {",", "\t", " ", ";", ":", "?"};

    /** The names of the delimiters you can choose from. */
    public static String[] delimNames = {"Comma delimiter", "Tab delimiter", "Space delimiter",
        "Semicolon delimiter", "Colon delimiter", "Excel file (.xls, .xlsx)"};

    /**
     * This function constructs an import controller.
     */
    public ImportController() { }

    /**
     * This method initializes the GUI of the import controller.
     */
    @Override
    protected void initialize() {
        // Set the delimiters
        delimiterStringList.addAll("Comma delimiter", "Tab delimiter", "Space delimiter",
                "Semicolon delimiter", "Colon delimiter", "Excel file (.xls, .xlsx)");

        // Add initial group and select it
        groupListView.setItems(groupList);
        addGroupListItem();

        // Switch to the right files and colums when selecting a group
        groupListView.getSelectionModel().selectedIndexProperty().addListener((obs, oldV, newV) -> {
            if (!groupList.isEmpty()) {
                selectGroup(groupListView.getSelectionModel().getSelectedItem());
            }
        });

        // Show the columns of the group when selecting the primary key
        keyBox.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
            groupListView.getSelectionModel().getSelectedItem().primKey = newV;
        });

        // Add all column names when selecting th primary key
        keyBox.setItems(keyListItems);
        keyBox.setValue("File name");
        keyBox.setOnMouseClicked(e -> {
            // Convert columns to list of strings
            String[] colNames = groupListView.getSelectionModel()
                    .getSelectedItem().columnList.stream()
                    .map(x -> x.txtField.getText()).collect(Collectors.toList())
                    .toArray(new String[0]);
            String primKey = keyBox.getValue();
            keyListItems.clear();
            keyListItems.add("File name");
            keyListItems.addAll(colNames);
            keyBox.valueProperty().unbind();
            keyBox.setValue(primKey);
        });

        // Update the primary key when the column name is changed
        keyBox.valueProperty().addListener((obs, oldV, newV) -> {
            int idx = keyBox.getItems().indexOf(keyBox.getValue());
            if (idx > 0) {
                keyBox.valueProperty().unbind();
                // Minus one because 'File name', the first option, is not a column
                keyBox.valueProperty().bind(columnListView.getItems().get(idx - 1)
                        .txtField.textProperty());
            }
        });

//        // When a column is deleted which was the primary key, reset the key to File name
//        columnListView.itemsProperty().get().addListener(new ListChangeListener<ColumnListItem>() {
//            @Override
//            public void onChanged(
//                    javafx.collections.ListChangeListener.Change<? extends ColumnListItem> c) {
//                GroupListItem selected = groupListView.getSelectionModel().getSelectedItem();
//
//                // Convert columns to list of strings
//                List<String> colNames = selected.columnList.stream()
//                        .map(x -> x.txtField.getText()).collect(Collectors.toList());
//
//                System.out.println(colNames.toString() + ", " + selected.primKey);
//
//                if (!colNames.contains(selected.primKey)) {
//                    System.out.println("yip");
//                    keyBox.valueProperty().unbind();
//                    keyBox.setValue("File name");
//                }
//            }
//        });

        // Preview a file when it is selected
        fileListView.selectionModelProperty().get().selectedItemProperty().addListener((obs, oldV, newV) -> {
            String text = "";
            if (newV == null) {
                text = "";
            } else if (oldV != newV) {
                try {
                    text = Reader.readLimited(newV.path, 100) + "\n...";
                } catch (IOException e) {
                    text = "Cannot open this file.";
                    e.printStackTrace();
                }
            }
            filePreview.setText(text);
        });
        filePreview.setEditable(false);

        // Enable dropping files in the file list view
        fileListView.setOnDragOver(e -> {
            Dragboard db = e.getDragboard();
            if (db.hasFiles()) {
                e.acceptTransferModes(TransferMode.COPY);
            } else {
                e.consume();
            }
        });
        fileListView.setOnDragDropped(e -> {
            Dragboard db = e.getDragboard();
            if (db.hasFiles()) {
                addFiles(db.getFiles());
            }
            e.setDropCompleted(db.hasFiles());
            e.consume();
        });
    }

    /**
     * Selects a group in the GroupListItemView and shows its files and columns.
     * @param gli The group you want to select
     */
    private void selectGroup(GroupListItem gli) {
        // Select group
        groupListView.getSelectionModel().select(gli);
        // Show its columns and files
        columnListView.setItems(gli.columnList);
        fileListView.setItems(gli.fileList);
        keyBox.setValue(gli.primKey);
    }

    /**
     * This method adds a group list item to the the group list view.
     */
    @FXML
    public void addGroupListItem() {
        GroupListItem gli = new GroupListItem(groupListView, fileListView, columnListView, delimiterStringList);
        groupList.add(gli);
        selectGroup(gli);

        addColumnListItem();
    }

    /**
     * This method adds a column to the column list view.
     */
    @FXML
    public void addColumnListItem() {
        GroupListItem gli = groupListView.getSelectionModel().getSelectedItem();
        gli.columnList.add(new ColumnListItem(columnListView, gli));
    }

    /**
     * Opens file chooser and add selected files to file list.
     */
    @FXML
    private void selectFiles() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import files");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All files (*.*)", "*.*"),
                new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt"),
                new FileChooser.ExtensionFilter(
                        "Comma delimited files (*.csv)", "*.csv"),
                new FileChooser.ExtensionFilter("Old Excel files (*.xls)",
                        "*.xls"),
                new FileChooser.ExtensionFilter("Excel files (*.xlsx)",
                        "*.xlsx"));

        List<File> files = fileChooser.showOpenMultipleDialog(mainApp
                .getPrimaryStage());
        addFiles(files);

    }

    /**
     * Adds one or more files to the currently selected group.
     * @param files Thee list of files to be added.
     */
    public void addFiles(List<File> files) {
        ObservableList<FileListItem> selected = groupListView
                .getSelectionModel().getSelectedItem().fileList;
        if (files != null) {
            for (File f : files) {
                // Get canonical path to file
                String path = "Path not found";
                try {
                    path = f.getCanonicalPath();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // No duplicate files
                if (!selected.stream().map(x -> x.path).collect(Collectors.toList()).contains(path)) {
                    // Add to view
                    selected.add(new FileListItem(fileListView, f.getName(), path));
                }
            }
            // Select first file to preview it
            fileListView.selectionModelProperty().get().select(0);
        }
    }

    /**
     * Converts the list of group, files and columns to an arraylist of Groups.
     * @return     - All the groups that are made.
     */
    public ArrayList<Group> getGroups() {
        ArrayList<Group> res = new ArrayList<Group>();
        for (GroupListItem gli : groupList) {

            Column[] colNames = gli.columnList.stream()
                    .map(x -> new Column(x.txtField.getText().toString(), ColumnType.STRING))
                    .collect(Collectors.toList())
                    .toArray(new Column[gli.columnList.size()]);

            int i = 0;
            for (ColumnListItem item: gli.columnList) {

                switch (item.comboBox.getValue()) {
                    // quick fix, maybe we will refactor the whole code
                    case "Time":
                        colNames[i] = new DateColumn(colNames[i].getName(), ColumnType.TIME,
                                item.secondBox.getValue(), item.cbSort.isSelected());
                        break;
                    case "Date":
                        colNames[i] = new DateColumn(colNames[i].getName(), ColumnType.DATE,
                                item.secondBox.getValue(), item.cbSort.isSelected());
                        break;
                    case "Date/Time":
                        colNames[i] = new DateColumn(colNames[i].getName(), ColumnType.DATEandTIME,
                                item.secondBox.getValue(), item.cbSort.isSelected());
                        break;
                    default:
                        break;
                }

                if (!item.use.isSelected()) {
                    colNames[i].setExcluded();
                }

                colNames[i].setType(ColumnType.getTypeOf(item.comboBox.getValue()));
                i++;
            }

            String dlmtr = delims[gli.box.getSelectionModel().getSelectedIndex()];

            // If the file name is the primary key, set the prim. key to null
            // which is correctly handled in the group.
            String primaryKey = null;
            if (!gli.primKey.equals("File name")) {
                primaryKey = gli.primKey;
            }

            Group g = new Group(gli.txtField.getText(), dlmtr, colNames, primaryKey);

            for (FileListItem fli : gli.fileList) {
                try {
                    g.addFile(fli.path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            res.add(g);
        }
        return res;
    }

    @Override
    public boolean validateInput(boolean showPopup) {
        for (GroupListItem gli : groupList) {
            // Check if there is an empty group name
            if (gli.txtField.getText().equals("")) {
                if (showPopup) {
                    mainApp.showNotification("There is a group with no name.",
                            NotificationStyle.WARNING);
                }
                return false;
            }
            // Check if it is a unique name
            for (GroupListItem gli2 : groupList) {
                if (gli != gli2 && gli2.txtField.getText().equals(gli.txtField.getText())) {
                    if (showPopup) {
                        mainApp.showNotification("There are multiple groups of the name '"
                            + gli.txtField.getText() + "'.", NotificationStyle.WARNING);
                    }
                    return false;
                }
            }
            // Check if every group has files
            if (gli.fileList.isEmpty()) {
                if (showPopup) {
                    mainApp.showNotification("The Group '" + gli.txtField.getText()
                            + "' doesn't contain any files.", NotificationStyle.WARNING);
                }
                return false;
            }
            // Check if every group has at least one column
            for (ColumnListItem cli : gli.columnList) {
                if (cli.txtField.getText().equals("")) {
                    if (showPopup) {
                        mainApp.showNotification("The Group '" + gli.txtField.getText()
                        + "' contains a column with no name.", NotificationStyle.WARNING);
                    }
                    return false;
                }
            }
            // Check for duplicate column names
            for (ColumnListItem cli : gli.columnList) {
                for (ColumnListItem cli2 : gli.columnList) {
                    if (cli != cli2 && cli.txtField.getText().equals(cli2.txtField.getText())) {
                        if (showPopup) {
                            mainApp.showNotification("The Group '" + gli.txtField.getText()
                            + "' contains multiple columns of the name '"
                            + cli.txtField.getText() + "'.", NotificationStyle.WARNING);
                        }
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * This method safes the configuration of the current files selected.
     */
    @FXML
    public void saveConfiguration() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save configuration");

        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("XML file (*.xml)", "*.xml"));
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        if (file != null) {
            try {
                XMLhandler writer = new XMLhandler();
                String path = file.getCanonicalPath();
                writer.writeXMLFile(path, getGroups());
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (IOException e) {
                mainApp.showNotification("Could not save at the given location, try another location."
                        , NotificationStyle.WARNING);
            } catch (ParserConfigurationException e) {
                mainApp.showNotification("Parser is not configured right, please contact your administrator."
                        , NotificationStyle.WARNING);
            } catch (SAXException e) {
                mainApp.showNotification("Something went wrong during writing to XML: " + e.getMessage()
                        , NotificationStyle.WARNING);
            }
        }
    }

    /**
     * This method safes the configuration of the current files selected.
     */
    @FXML
    public void openConfiguration() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import files");

        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("XML file (*.xml)", "*.xml"));
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        if (file != null) {
            try {
                XMLhandler writer = new XMLhandler();
                String path = file.getCanonicalPath();
                ArrayList<Group> groups = writer.readXMLFile(path);
                keyBox.valueProperty().unbind();
                groupList.clear();

                for (Group group : groups) {
                    addGroupFromXML(group);
                }
            } catch (IOException e) {
                mainApp.showNotification("Could not save at the given location, try another location."
                        , NotificationStyle.WARNING);
            } catch (ParserConfigurationException e) {
                mainApp.showNotification("Parser is not configured right, please contact your administrator."
                        , NotificationStyle.WARNING);
            } catch (SAXException e) {
                mainApp.showNotification("Something went wrong during writing to XML: " + e.getMessage()
                        , NotificationStyle.WARNING);
            }
        }
    }

    /**
     * This method adds the groups from the XML file to the GUI.
     * @param group         - Group to add to the GUI.
     */
    private void addGroupFromXML(Group group) {
        GroupListItem gli = new GroupListItem(groupListView, fileListView, columnListView, delimiterStringList);
        groupList.add(gli);
        selectGroup(gli);
        for (Column col : group.getColumns()) {
            ColumnListItem current = new ColumnListItem(columnListView, gli);
            current.txtField.setText(col.getName());
            current.comboBox.setValue(col.getType().toString());

            if (ColumnType.getDateTypes().contains(col.getType())) {
                current.addDateOptions(col.getType().toString());
                current.secondBox.setValue(((DateColumn) col).getDateFormat());
                current.cbSort.setSelected(((DateColumn) col).sortOnThisField());
            }

            gli.columnList.add(current);
        }

        for (String file : group.keySet()) {
            try {
                File ofile = new File(file);
                ofile.getCanonicalFile();
                gli.fileList.add(new FileListItem(fileListView, ofile.getName(), file));
            } catch (IOException e) {
                mainApp.showNotification("The file " + file + "is not found on your system"
                        , NotificationStyle.WARNING);
            }
        }
        gli.txtField.setText(group.getName());
        gli.box.setValue(group.getDelimiter());
        gli.primKey = group.getPrimary();
    }

    @Override
    public Object getData() {
        return getGroups();
    }

    @Override
    public void setData(Object o) {
        // Not used
    }

    /**
     * This method finds name by a delimiter.
     * @param substring    - Delimiter
     * @return            - Name of the delimiter
     */
    public static String findName(String substring) {
        for (int i = 0; i < delims.length; i++) {
            if (delims[i].equals(substring)) {
                return delimNames[i];
            }
        }
        return delimNames[0];
    }
}

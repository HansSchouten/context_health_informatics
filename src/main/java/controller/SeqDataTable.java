package controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Column;
import model.ColumnType;
import model.Record;
import model.SequentialData;
import model.datafield.DataField;
import model.datafield.DataFieldDouble;
import model.datafield.DataFieldInt;
import analyze.parsing.ParseResult;

/**
 * A TableView with convenience methods for getting and setting Sequential Data.
 * @author Remi
 *
 */
public class SeqDataTable extends TableView<Record> {
    /** The data in this table. */
    protected SequentialData data;

    /**
     * Constructs an empty table.
     */
    public SeqDataTable() {
        super();
    }

    /**
     * Constructs a table and sets the data.
     * @param pr The data to set.
     */
    public SeqDataTable(ParseResult pr) {
        super();
        setData(pr);
    }

    /**
     * Sets the data and initializes the table.
     * @param pr The sequential data to put in the table.
     */
    public void setData(ParseResult pr) {
        if (pr instanceof SequentialData) {
            data = (SequentialData) pr;
        } else {
            SequentialData sd = new SequentialData();
            Record r = new Record(LocalDateTime.now());
            r.put("Data", (DataField) pr);
            data = sd;
        }

        getColumns().clear();
        getItems().clear();

        TableColumn<Record, String> timeStamp = new TableColumn<Record, String>("Sorted timestamp");
        timeStamp.setCellValueFactory(p -> {
            return new SimpleStringProperty(p.getValue().getTimeStamp().toString());
        });
        getColumns().add(timeStamp);

        createColumns();

        TableColumn<Record, String> commentCol = new TableColumn<Record, String>("Comments");
        commentCol.setCellValueFactory(p -> {
            return new SimpleStringProperty(p.getValue().printComments("-"));
        });
        getColumns().add(commentCol);

        TableColumn<Record, String> labelCol = new TableColumn<Record, String>("Labels");
        labelCol.setCellValueFactory(p -> {
            return new SimpleStringProperty(p.getValue().printLabels("-"));
        });
        getColumns().add(labelCol);

        // Setting the data in the table
        getItems().addAll(data);
    }

    /**
     * Creates the columns for the sequential data in this table.
     */
    protected void createColumns() {
        SequentialData seqData = data;
        // Setup the table for every column type
        Column[] columns = seqData.getColumns();
        for (int i = 0; i < columns.length; i++) {
            ColumnType ct = columns[i].getType();
            String colName = columns[i].getName();

            // Differentiate between number or string so they can be sorted correctly in the GUI
            // Dates are sorted correctly as String, so there's no need to check for Date or Time types
            if (ct == ColumnType.INT) {
                TableColumn<Record, Number> tc = new TableColumn<Record, Number>(colName);
                tc.setCellValueFactory(p -> {
                    if (p.getValue().keySet().contains(colName)) {
                        return new SimpleIntegerProperty(
                                ((DataFieldInt) p.getValue().get(colName)).getIntegerValue());
                    } else {
                        return new SimpleIntegerProperty();
                    }
                });
                getColumns().add(tc);
            } else if (ct == ColumnType.DOUBLE) {
                TableColumn<Record, Number> tc = new TableColumn<Record, Number>(colName);
                tc.setCellValueFactory(p -> {
                    if (p.getValue().keySet().contains(colName)) {
                        return new SimpleDoubleProperty(
                                ((DataFieldDouble) p.getValue().get(colName)).getDoubleValue());
                    } else {
                        return new SimpleDoubleProperty();
                    }
                });
                getColumns().add(tc);
            } else {
                TableColumn<Record, String> tc = new TableColumn<Record, String>(colName);
                tc.setCellValueFactory(p -> {
                    if (p.getValue().keySet().contains(colName)) {
                        return new SimpleStringProperty(p.getValue().get(colName).toString());
                    } else {
                        return new SimpleStringProperty("");
                    }
                });
                getColumns().add(tc);
            }
        }
    }

    /**
     * Converts to table to String format.
     * @param delim The delimiter.
     * @param printColNames Wheter to include the column names.
     * @return The table in String format.
     */
    public String toString(String delim, boolean printColNames) {
        StringBuilder text = new StringBuilder();
        ObservableList<TableColumn<Record, ?>> columns = getColumns();
        List<String> colNames = columns.stream().map(x -> x.getText()).collect(Collectors.toList());

        // Column names
        if (printColNames) {
            for (int i = 0; i < colNames.size() - 1; i++) {
                text.append(colNames.get(i) + delim);
            }
            text.append(colNames.get(columns.size() - 1) + "\r\n");
        }

        // Items
        for (Record record : getItems()) {
            for (String c : colNames) {
                if (record.containsKey(c)) {
                    text.append(record.get(c).toString() + delim);
                } else if (c.equals("Comments")) {
                    text.append(record.printComments("-") + delim);
                } else if (c.equals("Labels")) {
                    text.append(record.printLabels("-") + delim);
                } else if (c.equals("Sorted timestamp")) {
                    text.append(record.getTimeStamp().toString() + delim);
                } else {
                    text.append(delim);
                }
            }
            text = new StringBuilder(text.substring(0, text.length() - 1) + "\r\n");
        }
        return text.toString();
    }
}

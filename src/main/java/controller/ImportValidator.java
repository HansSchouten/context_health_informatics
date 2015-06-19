package controller;

import java.util.List;

import javafx.scene.control.ToggleGroup;
import controller.MainApp.NotificationStyle;

/**
 * ImportValidator, this class validates the input from importcontroller.
 *
 */
public class ImportValidator {

    /**
     *
     */
    private List<GroupListItem> groupList;

    /**
     * Constructor.
     * @param mGroupList the list with all the groups.
     */
    public ImportValidator(List<GroupListItem> mGroupList) {
        this.groupList = mGroupList;
    }

    /**
     * Checks if no groupname is empty.
     * @param gli GroupListItem, the item to be checked.
     * @throws InputValidateException the exception to be thrown if the data is not valid.
     */
    private void checkEmptyGroupName(GroupListItem gli) throws InputValidateException {
        if (gli.txtField.getText().equals("")) {
            throw new InputValidateException("There is a group with no name.", NotificationStyle.WARNING);
        }
    }

    /**
     * Checks if groupname is unique.
     * @param gli GroupListItem, the item to be checked.
     * @throws InputValidateException the exception to be thrown if the data is not valid.
     */
    private void checkIfGroupNameIsUnique(GroupListItem gli) throws InputValidateException {
        for (GroupListItem gli2 : groupList) {
            if (gli != gli2 && gli2.txtField.getText().equals(gli.txtField.getText())) {
                throw new InputValidateException("There are multiple groups of the name '"
                        + gli.txtField.getText() + "'.", NotificationStyle.WARNING);
            }
        }
    }

    /**
     * Checks if a group has files.
     * @param gli GroupListItem, the item to be checked.
     * @throws InputValidateException the exception to be thrown if the data is not valid.
     */
    private void checkIfGroupHasFiles(GroupListItem gli) throws InputValidateException {
        if (gli.fileList.isEmpty()) {
            throw new InputValidateException("The Group '" + gli.txtField.getText()
                    + "' doesn't contain any files.", NotificationStyle.WARNING);
        }
    }

    /**
     * Check if all columns in a group have name.
     * @param gli GroupListItem, the item to be checked.
     * @throws InputValidateException the exception to be thrown if the data is not valid.
     */
    private void checkAllColumnsHaveAName(GroupListItem gli) throws InputValidateException {
        for (ColumnListItem cli : gli.columnList) {
            if (cli.txtField.getText().equals("")) {
                throw new InputValidateException("The Group '" + gli.txtField.getText()
                        + "' contains a column with no name.", NotificationStyle.WARNING);
            }
        }
    }

    /**
     * check if there are duplicate columnames.
     * @param gli GroupListItem, the item to be checked.
     * @throws InputValidateException the exception to be thrown if the data is not valid.
     */
    private void checkForDuplicateColumnNames(GroupListItem gli) throws InputValidateException {
        for (ColumnListItem cli : gli.columnList) {
            for (ColumnListItem cli2 : gli.columnList) {
                if (cli != cli2 && cli.txtField.getText().equals(cli2.txtField.getText())) {
                    throw new InputValidateException("The Group '" + gli.txtField.getText()
                            + "' contains multiple columns of the name '"
                            + cli.txtField.getText() + "'.", NotificationStyle.WARNING);
                }
            }
        }
    }


    /**
     * check if there is one sorted field.
     * @param gli GroupListItem, the item to be checked.
     * @throws InputValidateException the exception to be thrown if the data is not valid.
     */
    private void checkIfSorted(GroupListItem gli) throws InputValidateException {
        boolean sorted = false;
        for (ToggleGroup tg : gli.colToggleGroups) {
            if (tg.getSelectedToggle() != null) {
                sorted = true;
            }
        }
        if (!sorted) {
            throw new InputValidateException("The Group '" + gli.txtField.getText()
                    + "' needs at least one column to sort on.", NotificationStyle.WARNING);
        }
    }

    /**
     * This method checks if import input is valid.
     * @return returns true if valid.
     * @throws InputValidateException is thrown by one of checks.
     */
    public boolean validate() throws InputValidateException {
        for (GroupListItem gli : groupList) {
            checkEmptyGroupName(gli);
            checkIfGroupNameIsUnique(gli);
            checkIfGroupHasFiles(gli);
            checkAllColumnsHaveAName(gli);
            checkForDuplicateColumnNames(gli);
            checkIfSorted(gli);
        }
        return true;
    }

}

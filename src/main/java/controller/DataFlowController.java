package controller;

import java.util.ArrayList;

/**
 * This class controls the data flow between different controllers.
 * @author Matthijs
 *
 */
public class DataFlowController {

    /**
     * This variable stores the import controller of this program.
     */
    protected ImportController importcontroller;
    
    /**
     * This variable stores the select controller of this program.
     */
    protected SelectController selectcontroller;

    /**
     * This variable stores the specify controller of this program.
     */
    protected SpecifyController specifycontroller;

    /**
     * This variable stores the result controller of this program.
     */
    protected ResultsController resultcontroller;

    /**
     * Construct a DataFlowcontroller with a list of controllers.
     * @param controllers       - the controller for this app (should be labelled 1 - 4)
     * @throws ControllerSetupException - thrown when one of the controllers is not specified.
     */
    public DataFlowController(ArrayList<SubController> controllers) throws ControllerSetupException {
        for (SubController controller: controllers) {
            int controllerNumber = controller.getPipelineNumber();
            if (controllerNumber == 1) {
                importcontroller = (ImportController) controller;
            } else if (controllerNumber == 2) {
                selectcontroller = (SelectController) controller;
            } else if (controllerNumber == 3) {
                specifycontroller = (SpecifyController) controller;
            } else if (controllerNumber == 4) {
                resultcontroller = (ResultsController) controller;
            } else {
                throw new ControllerSetupException("You have entered an unregistered controller");
            }
        }
        
        if (importcontroller == null || selectcontroller == null
                || specifycontroller == null || resultcontroller == null) {
            throw new ControllerSetupException("One of the controllers has not been setup");
        }
    }

    /**
     * This method returns the import controller, if the other controller has access to it.
     * @param self          - The controller that calls this method.
     * @return              - the importcontroller of the app, null if no access.
     */
    public ImportController getImportController(SubController self) {
       if (self.getPipelineNumber() < importcontroller.getPipelineNumber()) {
           return importcontroller;
       } else {
           return null;
       }
    }

    /**
     * This method returns the select controller, if the other controller has access to it.
     * @param self          - The controller that calls this method.
     * @return              - the selectcontroller of the app, null if no access.
     */
    public SelectController getSelectController(SubController self) {
       if (self.getPipelineNumber() < selectcontroller.getPipelineNumber()) {
           return selectcontroller;
       } else {
           return null;
       }
    }
    
    /**
     * This method returns the specify controller, if the other controller has access to it.
     * @param self          - The controller that calls this method.
     * @return              - the specify controller of the app, null if no access.
     */
    public SpecifyController getSpecifyController(SubController self) {
       if (self.getPipelineNumber() < specifycontroller.getPipelineNumber()) {
           return specifycontroller;
       } else {
           return null;
       }
    }
    
    /**
     * This method returns the results controller, if the other controller has access to it.
     * @param self          - The controller that calls this method.
     * @return              - the results controller of the app, null if no access.
     */
    public ResultsController getResultController(SubController self) {
       if (self.getPipelineNumber() < resultcontroller.getPipelineNumber()) {
           return resultcontroller;
       } else {
           return null;
       }
    }
}

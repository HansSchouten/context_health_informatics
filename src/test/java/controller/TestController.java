package controller;

public class TestController extends SubController {

    protected int pipelineNumber;

    @Override
    protected void initialize() {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean validateInput(boolean showPopup) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Object getData() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setData(Object o) {
        // TODO Auto-generated method stub

    }

    @Override
    protected int getPipelineNumber() {
        return pipelineNumber;
    }

}

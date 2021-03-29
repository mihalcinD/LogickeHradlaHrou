package main.java.steakoverflow;

public class Gate extends Entity
{
    private int nmbInput;
    private String[] inputIDs;
    private boolean[] inputValues;
    private boolean outputValue;

    public Gate(int idEntity, String type, int tableX, int tableY, int nmbInput, String[] inputIDs)
    {
        super(idEntity, type, tableX, tableY);

        this.inputIDs = inputIDs;
        this.nmbInput = nmbInput;

    }

    public void check()
    {
        // just for other to override
    }

    public int getNmbInput()
    {
        return nmbInput;
    }

    public void setNmbInput(int nmbInput)
    {
        this.nmbInput = nmbInput;
    }

    public String[] getInputIDs()
    {
        return inputIDs;
    }

    public void setInputIDs(String[] inputIDs)
    {
        this.inputIDs = inputIDs;
    }

    public boolean[] getInputValues()
    {
        return inputValues;
    }

    public void setInputValues(boolean[] inputValues)
    {
        this.inputValues = inputValues;
    }

    public boolean isOutputValue()
    {
        return outputValue;
    }

    public void setOutputValue(boolean outputValue)
    {
        this.outputValue = outputValue;
    }
}

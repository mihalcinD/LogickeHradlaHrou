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
}

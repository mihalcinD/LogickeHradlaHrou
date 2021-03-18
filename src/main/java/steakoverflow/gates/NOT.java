package main.java.steakoverflow.gates;

import main.java.steakoverflow.Entity;

public class NOT extends Entity
{
    private int nmbInput;
    private boolean[] inputValues;
    private boolean outputValue;


    public NOT(String type, int x, int y, int nmbInput)
    {
        super(type, x, y);
        this.nmbInput = nmbInput;
    }
}

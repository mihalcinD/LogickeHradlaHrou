package main.java.steakoverflow.gates;

import main.java.steakoverflow.Entity;

public class NAND extends Entity
{
    private int nmbInput;
    private boolean[] inputValues;
    private boolean outputValue;

    // dedit od AND? len znegovat


    public NAND(String type, int x, int y, int nmbInput)
    {
        super(type, x, y);
        this.nmbInput = nmbInput;
    }
}

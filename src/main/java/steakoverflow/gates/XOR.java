package main.java.steakoverflow.gates;

import javafx.scene.image.Image;
import main.java.steakoverflow.Entity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class XOR extends Entity
{
    private int nmbInput;
    private String[] inputIDs;
    private boolean[] inputValues;
    private boolean outputValue;

    public XOR(int idEntity, String type, int x, int y, int nmbInput, String[] inputIDs)
    {
        super(idEntity, type, x, y);
        this.inputIDs = inputIDs;
        this.nmbInput = nmbInput;

        try
        {
            this.img.setImage(new Image(new FileInputStream("src/main/res/images/cacoPog.jpg")));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }
    public void check(){
        boolean output = inputValues[0];
        for (int i = 1; i <inputValues.length ; i++) {
            output = Boolean.logicalXor(output,inputValues[i]);

        }
        outputValue=output;

    }
}

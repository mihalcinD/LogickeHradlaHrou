package main.java.steakoverflow.gates;

import javafx.scene.image.Image;
import main.java.steakoverflow.Gate;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class OR extends Gate
{
    public OR(int idEntity, String type, int tableX, int tableY, int nmbInput, String[] inputIDs)
    {
        super(idEntity, type, tableX, tableY, nmbInput, inputIDs);

        try
        {
            this.img.setImage(new Image(new FileInputStream("src/main/res/images/Not.jpg")));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }
    public void check()
    {
        boolean output = this.getInputValues()[0];
        for (int i = 1; i < this.getInputValues().length; i++)
        {
            output = Boolean.logicalOr(output, this.getInputValues()[i]);

        }
        this.setOutputValue(output);

    }
}

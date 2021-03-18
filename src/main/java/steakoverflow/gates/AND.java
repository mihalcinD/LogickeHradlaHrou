package main.java.steakoverflow.gates;

import javafx.scene.image.Image;
import main.java.steakoverflow.Entity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class AND extends Entity
{
    private int nmbInput;
    private boolean[] inputValues;
    private boolean outputValue;


    public AND(String type, int x, int y, int nmbInput)
    {
        super(type, x, y);
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
}

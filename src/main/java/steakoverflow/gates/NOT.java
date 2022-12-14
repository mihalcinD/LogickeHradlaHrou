package main.java.steakoverflow.gates;

import javafx.scene.image.Image;
import main.java.steakoverflow.Gate;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class NOT extends Gate
{
    public NOT(int idEntity, String type, int tableX, int tableY, int nmbInput, String[] inputIDs)
    {
        super(idEntity, type, tableX, tableY, nmbInput, inputIDs);

        try
        {
            this.img.setImage(new Image(new FileInputStream("src/main/res/images/gates/Not.png")));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void check()
    {
        this.setValue(!this.getInputValues()[0]);
    }
}

package main.java.steakoverflow.gates;

import javafx.scene.image.Image;
import main.java.steakoverflow.Gate;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class XOR extends Gate
{
    public XOR(int idEntity, String type, int tableX, int tableY, int nmbInput, String[] inputIDs)
    {
        super(idEntity, type, tableX, tableY, nmbInput, inputIDs);

        try
        {
            if (getNmbInput() == 2)
                this.img.setImage(new Image(new FileInputStream("src/main/res/images/gates/XOR.png")));
            else
                this.img.setImage(new Image(new FileInputStream("src/main/res/images/gates/XOR3.png")));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void check()
    {
        boolean output = this.getInputValues()[0];
        for (int i = 1; i < this.getInputValues().length; i++)
        {
            output = Boolean.logicalXor(output, this.getInputValues()[i]);

        }
        this.setValue(output);

    }
}

package main.java.steakoverflow.gates;

import javafx.scene.image.Image;
import main.java.steakoverflow.Entity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class NOR extends Entity
{
    private int nmbInput;
    private boolean[] inputValues;
    private boolean outputValue;

    // dedit od OR? a len znegovat

    public NOR(int idEntity, String type, int x, int y, int nmbInput) {
        super(idEntity, type, x, y);
        this.nmbInput = nmbInput;

        try {
            this.img.setImage(new Image(new FileInputStream("src/main/res/images/cacoPog.jpg")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
    public void check(){
        boolean output = inputValues[0];
        for (int i = 1; i <inputValues.length ; i++) {
            output = Boolean.logicalOr(output,inputValues[i]);

        }
        outputValue=output;

    }
}

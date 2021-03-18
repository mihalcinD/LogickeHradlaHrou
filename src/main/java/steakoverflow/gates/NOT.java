package main.java.steakoverflow.gates;

import javafx.scene.image.Image;
import main.java.steakoverflow.Entity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class NOT extends Entity
{
    private int nmbInput;
    private boolean[] inputValues;
    private boolean outputValue;


    public NOT(int idEntity, String type, int x, int y, int nmbInput) {
        super(idEntity, type, x, y);
        this.nmbInput = nmbInput;

        try {
            this.img.setImage(new Image(new FileInputStream("src/main/res/images/gates/Not.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}

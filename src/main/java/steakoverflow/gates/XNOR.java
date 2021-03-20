package main.java.steakoverflow.gates;

import javafx.scene.image.Image;
import main.java.steakoverflow.Entity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class XNOR extends Entity
{
    private int nmbInput;
    private boolean[] inputValues;
    private boolean outputValue;

    // dedit od XOR? a len znegovat

    public XNOR(int idEntity, String type, int x, int y, int nmbInput) {
        super(idEntity, type, x, y);
        this.nmbInput = nmbInput;

        try {
            this.img.setImage(new Image(new FileInputStream("src/main/res/images/cacoPog.jpg")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}

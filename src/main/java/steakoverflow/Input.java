package main.java.steakoverflow;


import javafx.scene.image.Image;

public class Input extends Entity
{
    private boolean locked;
    private boolean value;
    private Image img;


    public Input(String type, double x, double y, boolean locked, boolean value)
    {
        super(type, x, y);
        this.locked = locked;
        this.value = value;
        this.img = new Image("../res/image/cacoPog.jpg");

    }
}

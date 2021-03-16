package main.java.steakoverflow;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Output extends Entity
{
    private boolean locked;
    private boolean value;

    public Output(String type, double x, double y, boolean locked, boolean value)
    {
        super(type, x, y);
        this.locked = locked;
        this.value = value;
        try
        {
            this.img.setImage(new Image(new FileInputStream("src/main/res/images/cacoPog.jpg")));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public boolean isLocked()
    {
        return locked;
    }

    public void setLocked(boolean locked)
    {
        this.locked = locked;
    }

    public boolean isValue()
    {
        return value;
    }

    public void setValue(boolean value)
    {
        this.value = value;
    }
}

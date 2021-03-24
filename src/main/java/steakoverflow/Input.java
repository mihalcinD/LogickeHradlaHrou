package main.java.steakoverflow;


import javafx.scene.image.Image;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Input extends Entity
{
    private boolean locked;
    private boolean value;


    public Input(int idEntity, String type, int x, int y, boolean locked, boolean value) {
        super(idEntity, type, x, y);
        this.locked = locked;
        this.value = value;
        changeImg();
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

    public void clicked()
    {
        if (!this.locked)
        {
            this.value = !this.value;
            changeImg();
        }
    }

    private void changeImg()
    {
        try
        {
            if (value)
            {
                if (locked)
                    this.img.setImage(new Image(new FileInputStream("src/main/res/images/input/input1_locked.png")));
                else this.img.setImage(new Image(new FileInputStream("src/main/res/images/input/input1_unlocked.png")));
            }
            else
            {
                if (locked)
                    this.img.setImage(new Image(new FileInputStream("src/main/res/images/input/input0_locked.png")));
                else this.img.setImage(new Image(new FileInputStream("src/main/res/images/input/input0_unlocked.png")));
            }

            this.img.setOnMouseClicked(mouseEvent -> clicked());

        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

}

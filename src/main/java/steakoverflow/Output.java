package main.java.steakoverflow;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Output extends Entity
{
    private boolean locked;
    private boolean value;

    public Output(int idEntity, String type, int x, int y, boolean locked, boolean value) {
        super(idEntity, type, x, y);
        this.locked = locked;
        this.value = value;
        try {
            if (value) {
                if (locked)
                    this.img.setImage(new Image(new FileInputStream("src/main/res/images/output/output1_locked.png")));
                else
                    this.img.setImage(new Image(new FileInputStream("src/main/res/images/output/output1_unlocked.png")));
            }
            else
            {
                if (locked)
                    this.img.setImage(new Image(new FileInputStream("src/main/res/images/output/output0_locked.png")));
                else
                    this.img.setImage(new Image(new FileInputStream("src/main/res/images/output/output0_unlocked.png")));
            }
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

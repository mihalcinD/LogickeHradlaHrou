package main.java.steakoverflow;

import javafx.scene.Cursor;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Output extends Entity
{
    private boolean locked;
    private String connectionID;

    public Output(int idEntity, String type, int x, int y, boolean locked, boolean value, String connectionID)
    {
        super(idEntity, type, x, y);
        this.locked = locked;
        this.setValue(value);
        this.connectionID = connectionID;
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

    public void clicked()
    {
        if (!this.locked)
        {
            this.setValue(!this.isValue());
            changeImg();
        }
    }

    private void changeImg()
    {
        try
        {
            if (isValue())
            {
                if (locked)
                {
                    this.img.setImage(new Image(new FileInputStream("src/main/res/images/output/output1_locked.png")));
                    this.img.setCursor(Cursor.CLOSED_HAND);
                }
                else
                {
                    this.img.setImage(new Image(new FileInputStream("src/main/res/images/output/output1_unlocked.png")));
                    this.img.setCursor(Cursor.HAND);
                }
            }
            else
            {
                if (locked)
                {
                    this.img.setImage(new Image(new FileInputStream("src/main/res/images/output/output0_locked.png")));
                    this.img.setCursor(Cursor.CLOSED_HAND);
                }
                else
                {
                    this.img.setImage(new Image(new FileInputStream("src/main/res/images/output/output0_unlocked.png")));
                    this.img.setCursor(Cursor.HAND);
                }
            }
            this.img.setOnMouseClicked(mouseEvent -> clicked());

        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public String getConnectionID()
    {
        return connectionID;
    }

    public void setConnectionID(String connectionID)
    {
        this.connectionID = connectionID;
    }
}

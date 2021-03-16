package main.java.steakoverflow;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Entity
{
    private String type;
    private double x;
    private double y;
    protected ImageView img;

    public Entity(String type, double x, double y)
    {
        this.type = type;
        this.x = x;
        this.y = y;

        this.img = new ImageView();
        this.img.setX(x);
        this.img.setY(y);

    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public double getX()
    {
        return x;
    }

    public void setX(double x)
    {
        this.x = x;
    }

    public double getY()
    {
        return y;
    }

    public void setY(double y)
    {
        this.y = y;
    }

    public ImageView getImg()
    {
        return img;
    }

    public void setImg(ImageView img)
    {
        this.img = img;
    }
}

package main.java.steakoverflow;

import javafx.scene.image.ImageView;

public class Entity
{
    private String type;
    private int tableX;
    private int tableY;
    protected ImageView img;

    public Entity(String type, int tableX, int tableY)
    {
        this.type = type;
        this.tableX = tableX;
        this.tableY = tableY;

        this.img = new ImageView();
        this.img.setPreserveRatio(true);
        this.img.setFitWidth(150);
        this.img.setX(tableX);
        this.img.setY(tableY);

    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public int getTableX()
    {
        return tableX;
    }

    public void setTableX(int tableX)
    {
        this.tableX = tableX;
    }

    public int getTableY()
    {
        return tableY;
    }

    public void setTableY(int tableY)
    {
        this.tableY = tableY;
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

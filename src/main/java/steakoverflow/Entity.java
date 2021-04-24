package main.java.steakoverflow;

import javafx.scene.image.ImageView;

public class Entity
{
    private int idEntity;
    private String type;
    private int x;
    private int y;
    private boolean value;
    protected ImageView img;

    public Entity(int idEntity, String type, int x, int y)
    {
        this.idEntity = idEntity;
        this.type = type;
        this.x = x;
        this.y = y;

        this.img = new ImageView();
        this.img.setPreserveRatio(true);
        this.img.setFitWidth(150);
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

    public int getX()
    {
        return x;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public int getY()
    {
        return y;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public ImageView getImg() {
        return img;
    }

    public void setImg(ImageView img) {
        this.img = img;
    }

    public int getIdEntity()
    {
        return idEntity;
    }

    public void setIdEntity(int idEntity)
    {
        this.idEntity = idEntity;
    }

    public boolean isValue()
    {
        return value;
    }

    public void setValue(boolean value)
    {
        this.value = value;
    }

    @Override
    public String toString()
    {
        return "Entity{" +
                "idEntity=" + idEntity +
                ", type='" + type + '\'' +
                ", tableX=" + x +
                ", tableY=" + y +
                ", img=" + img +
                '}';
    }
}

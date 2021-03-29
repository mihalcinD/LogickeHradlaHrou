package main.java.steakoverflow;

import javafx.scene.image.ImageView;

public class Entity
{
    private int idEntity;
    private String type;
    private int tableX;
    private int tableY;
    private boolean value;
    protected ImageView img;

    public Entity(int idEntity, String type, int tableX, int tableY) {
        this.idEntity = idEntity;
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
                ", tableX=" + tableX +
                ", tableY=" + tableY +
                ", img=" + img +
                '}';
    }
}

package main.java.steakoverflow;

public class Entity
{
    private String type;
    private double x;
    private double y;

    public Entity(String type, double x, double y)
    {
        this.type = type;
        this.x = x;
        this.y = y;
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
}

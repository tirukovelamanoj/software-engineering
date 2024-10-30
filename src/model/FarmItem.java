package model;

public abstract class FarmItem {
    private String name;
    private double locationX;
    private double locationY;
    private double length;
    private double width;
    private double height;
    private double price;

    public FarmItem(String name, double locationX, double locationY, double length, double width, double height, double price) {
        this.name = name;
        this.locationX = locationX;
        this.locationY = locationY;
        this.length = length;
        this.width = width;
        this.height = height;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLocationX() {
        return locationX;
    }

    public void setLocationX(double locationX) {
        this.locationX = locationX;
    }

    public double getLocationY() {
        return locationY;
    }

    public void setLocationY(double locationY) {
        this.locationY = locationY;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
    
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return name;
    }
}

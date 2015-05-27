package kr.ac.kaist.mobile.attable.api;

public class ApiMenuItem {

    private String name;
    private int price;
    private String picture; // will have to fll this out in the future
    private String description;

    // Empty default constructor
    public ApiMenuItem(){

    }

    @Override
    public String toString(){
        return name + " " + price + " " + picture + " " + description;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getPicture() {
        return picture;
    }

    public String getDescription() {
        return description;
    }
}
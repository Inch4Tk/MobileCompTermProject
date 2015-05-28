package kr.ac.kaist.mobile.attable.api;


public class ApiOrderItem {
    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getAmount() {
        return amount;
    }

    private String name;
    private int price;
    private int amount;

    public ApiOrderItem() {

    }

    @Override
    public String toString(){
        return name + " " + price + " " + amount;
    }
}
package kr.ac.kaist.mobile.attable.api;


public class ApiOrderItems {
    private String name;
    private int price;
    private int amount;

    public ApiOrderItems() {

    }

    @Override
    public String toString(){
        return name + " " + price + " " + amount;
    }
}
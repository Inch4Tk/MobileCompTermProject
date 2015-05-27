package kr.ac.kaist.mobile.attable.api;

public class ApiOrderPlaceItem {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    private int amount;

    public ApiOrderPlaceItem(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return name + " " + amount;
    }
}

package kr.ac.kaist.mobile.attable.api;

import java.util.List;

public class ApiOrder {

    private int status;
    private String business;
    private String table;
    private String timestamp;
    private List<ApiOrderItem> items;

    public int getStatus() {
        return status;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public List<ApiOrderItem> getItems() {
        return items;
    }


    public ApiOrder() {

    }

    @Override
    public String toString(){
        return status + " " + business + " " + table + " " + timestamp + items.toString();
    }
}

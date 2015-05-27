package kr.ac.kaist.mobile.attable.api;

import java.util.List;

public class ApiOrder {

    private int status;
    private String business;
    private String table;
    private String timestamp;
    private List<ApiOrderItems> items;

    public ApiOrder() {

    }

    @Override
    public String toString(){
        return status + " " + business + " " + table + " " + timestamp + items.toString();
    }
}

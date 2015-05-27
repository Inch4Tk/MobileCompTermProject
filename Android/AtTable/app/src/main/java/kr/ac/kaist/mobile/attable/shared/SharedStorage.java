package kr.ac.kaist.mobile.attable.shared;


import java.util.List;

import kr.ac.kaist.mobile.attable.api.ApiMenuItem;
import kr.ac.kaist.mobile.attable.api.ApiOrderPlaceItem;

public class SharedStorage {

    // Singleton setup
    private static SharedStorage instance = null;
    static {
        setupSharedStorage();
    }
    private SharedStorage() {}
    private static void setupSharedStorage()
    {
        instance = new SharedStorage();
    }
    public static SharedStorage get() {
        return instance;
    }

    // Singleton variables
    // Cur tableId
    public String getTableId() {
        return tableId;
    }
    public void setTableId(String tableId) {
        if (this.tableId.compareTo(tableId) != 0)
        {
            // Rest values of other storage items, because they are no longer valid
            menu = null;
            orderItems = null;
            this.tableId = tableId;
        }
    }
    private String tableId;

    // Menu
    public List<ApiMenuItem> getMenu() {
        return menu;
    }
    public void setMenu(List<ApiMenuItem> menu) {
        this.menu = menu;
    }
    private List<ApiMenuItem> menu = null;

    // Order place items
    public List<ApiOrderPlaceItem> getOrderItems() {
        return orderItems;
    }
    public void setOrderItems(List<ApiOrderPlaceItem> orderItems) {
        this.orderItems = orderItems;
    }
    private List<ApiOrderPlaceItem> orderItems = null;


}

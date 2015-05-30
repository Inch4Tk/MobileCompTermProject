package kr.ac.kaist.mobile.attable.shared;


import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

import kr.ac.kaist.mobile.attable.api.ApiMenuItem;
import kr.ac.kaist.mobile.attable.api.ApiOrder;
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
        if (!tableId.isEmpty() && this.tableId.compareTo(tableId) != 0)
        {
            // Rest values of other storage items, because they are no longer valid
            menu = null;
            orderItems = null;
            filteredOrderItems = null;
            filteredMenu = null;
            this.tableId = tableId;
            fetchMenu = true;
        }
    }
    private String tableId = "";

    // Don't make fetches if we still have the menu checkers
    public boolean getFetchMenu() {
        return fetchMenu;
    }
    private boolean fetchMenu = true;

    // Menu
    public List<ApiMenuItem> getMenu() {
        return menu;
    }
    public void setMenu(List<ApiMenuItem> menu) {
        this.menu = menu;
        filteredOrderItems = null;
        filteredMenu = null;
        orderItems = null;
        fetchMenu = false;
    }
    private List<ApiMenuItem> menu = null;

    // Previous orders
    public List<ApiOrder> getPrevOrders() {
        return prevOrders;
    }
    public void setPrevOrders(List<ApiOrder> prevOrderItems) {
        this.prevOrders = prevOrderItems;
    }
    private List<ApiOrder> prevOrders = null;

    // Order place items
    public List<ApiOrderPlaceItem> getOrderItems() {
        return orderItems;
    }
    public void setOrderItems(List<ApiOrderPlaceItem> orderItems) {
        this.orderItems = orderItems;
    }
    private List<ApiOrderPlaceItem> orderItems = null;

    public List<ApiOrderPlaceItem> getFilteredOrderItems() {
        return filteredOrderItems;
    }
    public void setFilteredOrderItems(List<ApiOrderPlaceItem> filteredOrderItems) {
        this.filteredOrderItems = filteredOrderItems;
    }
    // Act temporary as intermediate between adapters, no safe sharing between different activities
    private List<ApiOrderPlaceItem> filteredOrderItems = null;

    public List<ApiMenuItem> getFilteredMenu() {
        return filteredMenu;
    }
    public void setFilteredMenu(List<ApiMenuItem> filteredMenu) {
        this.filteredMenu = filteredMenu;
    }
    // Act temporary as intermediate between adapters, no safe sharing between different activities
    private List<ApiMenuItem> filteredMenu = null; // Act kind of temporary

    // Picture storage
    public List<Bitmap> getMenuPictures() {
        return menuPictures;
    }
    public List<String> getMenuPictureKeys() {
        return menuPictureKeys;
    }
    public void resetMenuPictures() {
        this.menuPictures.clear();
        this.menuPictureKeys.clear();
    }
    public void addMenuPictures(String pictureId, Bitmap menuPicture) {
        this.menuPictures.add(menuPicture);
        this.menuPictureKeys.add(pictureId);
    }
    private List<Bitmap> menuPictures = new ArrayList<Bitmap>();
    private List<String> menuPictureKeys = new ArrayList<String>();
}

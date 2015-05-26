package kr.ac.kaist.mobile.attable.shared;


import java.util.List;

import kr.ac.kaist.mobile.attable.api.ApiMenuItem;

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
    private List<ApiMenuItem> menu = null;
    public List<ApiMenuItem> getMenu() {
        return menu;
    }
    public void setMenu(List<ApiMenuItem> menu) {
        this.menu = menu;
    }

}

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

    }
    public static SharedStorage get() {
        return instance;
    }

    // Singleton variables
    private List<ApiMenuItem> menu;

    public List<ApiMenuItem> getMenu() {
        return menu;
    }
    public void setMenu(List<ApiMenuItem> menu) {
        this.menu = menu;
    }
}

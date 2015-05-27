package kr.ac.kaist.mobile.attable.api;

import java.util.List;

public class ApiOrderPlace {

    private List<ApiOrderPlaceItem> items;

    public ApiOrderPlace(List<ApiOrderPlaceItem> items) {
        this.items = items;
    }
}

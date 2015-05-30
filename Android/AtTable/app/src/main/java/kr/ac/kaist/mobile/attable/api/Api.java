package kr.ac.kaist.mobile.attable.api;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public interface Api {

    @GET("/business/menu/{id}")
    void getMenu(@Path("id") String tableId, Callback<List<ApiMenuItem>> callback);

    @GET("/business/menupic/{id}")
    void getMenuPicture(@Path("id") String pictureId, Callback<ApiPicture> callback);

    @GET("/order/table/{id}")
    void getOrders(@Path("id") String tableId, Callback<List<ApiOrder>> callback);

    @POST("/order/{id}")
    void order(@Path("id") String tableId, @Body ApiOrderPlace order, Callback<ApiOrder> callback);

}

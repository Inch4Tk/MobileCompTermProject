package kr.ac.kaist.mobile.attable.api;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public interface Api {

    @GET("/business/menu/{id}")
    void getMenu(@Path("id") String tableId, Callback<List<ApiMenuItem>> callback);
}

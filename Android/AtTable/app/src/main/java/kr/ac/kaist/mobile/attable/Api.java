package kr.ac.kaist.mobile.attable;

public interface Api {

    @GET("/weather")
    void getWeather(Callback<WeatherResponse> callback);
}

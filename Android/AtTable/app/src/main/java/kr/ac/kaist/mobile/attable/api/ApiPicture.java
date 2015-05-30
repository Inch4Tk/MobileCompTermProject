package kr.ac.kaist.mobile.attable.api;


public class ApiPicture {

    private String _id;
    private ApiBuffer data;

    public ApiBuffer getData() {
        return data;
    }
    public String get_id() {
        return _id;
    }


    @Override
    public String toString() {
        return data.toString();
    }
}

package kr.ac.kaist.mobile.attable.api;


import java.util.List;

public class ApiPicture {

    private String _id;
    private List<Byte> data;

    public List<Byte> getData() {
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

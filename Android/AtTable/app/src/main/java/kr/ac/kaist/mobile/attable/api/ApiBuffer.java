package kr.ac.kaist.mobile.attable.api;

import java.util.List;

public class ApiBuffer {
    private List<Byte> data;

    public List<Byte> getData() {
        return data;
    }

    @Override
    public String toString() {
        return data.toString();
    }
}

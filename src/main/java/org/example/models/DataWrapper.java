package org.example.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataWrapper <T> {

    private T[] data;

    public ArrayList<T> getData() {
        return new ArrayList<>(Arrays.asList(data));
    }

    public void setData(T[] data) {
        this.data = data;
    }


}

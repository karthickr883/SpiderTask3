package com.example.karthik.spider3;

/**
 * Created by karthik on 09-06-2018.
 */

public class Data {
    int id;
    String string;
    Data(int id,String string){
        this.id = id;
        this.string = string;
    }

    public String getString() {
        return string;
    }

    public int getId() {
        return id;
    }
}

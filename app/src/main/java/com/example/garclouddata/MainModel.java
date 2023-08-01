package com.example.garclouddata;

import java.util.Date;

public class MainModel {
    private String name,date,img;

    private MainModel(){

    }
    private MainModel(String name,String date,String img){
        this.name = name;
        this.date = date;
        this.img = img;


    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImg() { return img; }

    public void setImg(String img) {
        this.img = img;
    }
}


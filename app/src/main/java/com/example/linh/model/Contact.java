package com.example.linh.model;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Contact implements Serializable {
    private int id;
    private String phone;
    private String name;
    private Bitmap img;
    private String timelastuse, datelastuse, isLove;

    public Contact(String phone, String name, Bitmap img, String timelastuse, String datelastuse, String isLove) {
        this.phone = phone;
        this.name = name;
        this.img = img;
        this.timelastuse = timelastuse;
        this.datelastuse = datelastuse;
        this.isLove = isLove;
    }

    public Contact() {
    }

    public Contact(int id, String phone, String name, Bitmap img, String timelastuse, String datelastuse, String isLove) {
        this.id = id;
        this.phone = phone;
        this.name = name;
        this.img = img;
        this.timelastuse = timelastuse;
        this.datelastuse = datelastuse;
        this.isLove = isLove;
    }

    public int getId() {
        return id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }


    public String getTimelastuse() {
        return timelastuse;
    }

    public void setTimelastuse(String timelastuse) {
        this.timelastuse = timelastuse;
    }

    public String getDatelastuse() {
        return datelastuse;
    }

    public void setDatelastuse(String datelastuse) {
        this.datelastuse = datelastuse;
    }

    public String getIsLove() {
        return isLove;
    }

    public void setIsLove(String isLove) {
        this.isLove = isLove;
    }
}

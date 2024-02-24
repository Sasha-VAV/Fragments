package com.example.fragments;

import android.graphics.Bitmap;

public class Valute {
    private String Name;
    private String Value;
    private Bitmap Picture;

    public Valute(String name, String value, Bitmap picture) {
        Name = name;
        Value = value;
        Picture = picture;
    }

    public String getName() {
        return Name;
    }

    public String getValue() {
        return Value;
    }

    public Bitmap getPicture() {
        return Picture;
    }
}

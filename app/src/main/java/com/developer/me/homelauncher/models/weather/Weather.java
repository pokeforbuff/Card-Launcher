package com.developer.me.homelauncher.models.weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Sanidhya on 7/8/2017.
 */

public class Weather {

    @SerializedName("weather")
    @Expose
    private List<Type> type;

    @SerializedName("main")
    @Expose
    private MainDetails main;

    @SerializedName("name")
    @Expose
    private String name;

    public List<Type> getType() {
        return type;
    }

    public void setType(List<Type> type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MainDetails getMainDetails() {
        return main;
    }

    public void setMainDetails(MainDetails main) {
        this.main = main;
    }

}

package com.developer.me.homelauncher.models.weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Sanidhya on 7/8/2017.
 */

public class Type {

    @SerializedName("main")
    @Expose
    private String main;

    @SerializedName("icon")
    @Expose
    private String icon;

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

}

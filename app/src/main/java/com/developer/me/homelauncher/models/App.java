package com.developer.me.homelauncher.models;

import android.graphics.drawable.Drawable;

/**
 * Created by Sanidhya on 7/5/2017.
 */

public class App {
    public CharSequence label;

    public CharSequence getLabel() {
        return label;
    }

    public void setLabel(CharSequence label) {
        this.label = label;
    }

    public CharSequence getName() {
        return name;
    }

    public void setName(CharSequence name) {
        this.name = name;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public CharSequence name;
    public Drawable icon;
}

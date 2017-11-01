package com.developer.me.homelauncher.utils;

import com.developer.me.homelauncher.models.App;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sanidhya on 8/29/2017.
 */

public class HiddenAppsMgr {

    public static List<App> hiddenApps=new ArrayList<>();

    public void addToHiddenApps(List<App> toHide){
        hiddenApps.addAll(toHide);
    }

    public List<App> getHiddenApps(){
        return hiddenApps;
    }

}

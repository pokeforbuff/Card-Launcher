package com.developer.me.homelauncher.utils;

import com.developer.me.homelauncher.models.App;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sanidhya on 11/1/2017.
 */

public class AppListMgr {

    public static List<App> apps=new ArrayList<>();

    public void setAppList(List<App> apps){
        AppListMgr.apps =apps;
    }

}

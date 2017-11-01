package com.developer.me.homelauncher.utils;

import com.developer.me.homelauncher.models.App;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Sanidhya on 7/5/2017.
 */

public class ArrayListSorter {

    public List<App> sortByLabel(List<App> appList){
        Collections.sort(appList, new Comparator<App>() {
            @Override
            public int compare(App app1, App app2) {
                return app1.getLabel().toString().compareTo(app2.getLabel().toString());
            }
        });
        return appList;
    }

}

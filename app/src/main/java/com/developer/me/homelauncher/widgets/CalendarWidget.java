package com.developer.me.homelauncher.widgets;

import android.appwidget.AppWidgetHost;
import android.appwidget.AppWidgetHostView;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.developer.me.homelauncher.R;

import java.util.List;

/**
 * Created by Sanidhya on 7/28/2017.
 */

public class CalendarWidget extends Fragment {

    private View widgetView;
    private AppWidgetManager appWidgetManager;
    private AppWidgetHost appWidgetHost;
    private AppWidgetProviderInfo selectedAppWidget;
    private LinearLayout appWidgetHolder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        widgetView=inflater.inflate(R.layout.widget_calendar,container,false);
        initViews();
        initObjects();
        addWidget();
        return widgetView;
    }

    private void initObjects() {
        appWidgetManager = AppWidgetManager.getInstance(getActivity());
        appWidgetHost = new AppWidgetHost(getActivity(), 2);
        selectedAppWidget = new AppWidgetProviderInfo();
    }

    private void addWidget() {
        int appWidgetId = appWidgetHost.allocateAppWidgetId();
        List<AppWidgetProviderInfo> appWidgets;
        appWidgets = appWidgetManager.getInstalledProviders();
        for (int j = 0; j < appWidgets.size(); j++) {
            if (appWidgets.get(j).provider.getClassName().equals("com.android.calendar.widget.CalendarAppWidgetProvider")) {
                selectedAppWidget = appWidgets.get(j);
                break;
            }
        }
        if(!appWidgetManager.bindAppWidgetIdIfAllowed(appWidgetId,selectedAppWidget.provider)) {
            Intent bindIntent = new Intent(AppWidgetManager.ACTION_APPWIDGET_BIND);
            bindIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            bindIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_PROVIDER, selectedAppWidget.provider);
            startActivityForResult(bindIntent, 1);
        }
        appWidgetManager.bindAppWidgetIdIfAllowed(appWidgetId,selectedAppWidget.provider);
        AppWidgetHostView hostView = appWidgetHost.createView(getActivity(), appWidgetId, selectedAppWidget);
        hostView.setAppWidget(appWidgetId, selectedAppWidget);
        appWidgetHolder.addView(hostView);
    }

    private void initViews() {
        appWidgetHolder=widgetView.findViewById(R.id.cal_widget_container);
    }

}

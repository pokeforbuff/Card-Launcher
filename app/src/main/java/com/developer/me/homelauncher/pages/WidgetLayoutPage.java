package com.developer.me.homelauncher.pages;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.developer.me.homelauncher.R;
import com.developer.me.homelauncher.widgets.ClockWidget;
import com.developer.me.homelauncher.widgets.MusicWidget;
import com.developer.me.homelauncher.widgets.ShazamWidget;
import com.developer.me.homelauncher.widgets.TogglesWidget;
import com.developer.me.homelauncher.widgets.WeatherWidget;

/**
 * Created by Sanidhya on 7/12/2017.
 */

public class WidgetLayoutPage extends Fragment {

    private View page;
    private FragmentManager fragmentManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        page = inflater.inflate(R.layout.page_widgetlayout, container, false);
        initObjects();
        loadWidgets();
        return page;
    }

    private void initObjects() {
        fragmentManager=getActivity().getSupportFragmentManager();
    }

    private void loadWidgets() {
        fragmentManager.beginTransaction().add(R.id.widget_frame_1, new ClockWidget()).commit();
        fragmentManager.beginTransaction().add(R.id.widget_frame_2, new WeatherWidget()).commit();
        fragmentManager.beginTransaction().add(R.id.widget_frame_3, new MusicWidget()).commit();
        fragmentManager.beginTransaction().add(R.id.widget_frame_4, new TogglesWidget()).commit();
        fragmentManager.beginTransaction().add(R.id.widget_frame_5, new ShazamWidget()).commit();
    }

}

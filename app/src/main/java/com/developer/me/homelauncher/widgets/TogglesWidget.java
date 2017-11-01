package com.developer.me.homelauncher.widgets;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.developer.me.homelauncher.R;

/**
 * Created by Sanidhya on 7/25/2017.
 */

public class TogglesWidget extends Fragment implements CompoundButton.OnCheckedChangeListener {

    private View widgetView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        widgetView = inflater.inflate(R.layout.widget_toggles, container, false);
        initViews();
        return widgetView;
    }

    private void initViews() {
        ToggleButton bluetoothToggle = widgetView.findViewById(R.id.toggle_btn_bluetooth);
        bluetoothToggle.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

    }
}

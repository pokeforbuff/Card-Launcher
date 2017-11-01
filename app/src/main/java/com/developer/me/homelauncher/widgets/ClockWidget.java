package com.developer.me.homelauncher.widgets;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextClock;

import com.developer.me.homelauncher.R;

/**
 * Created by Sanidhya on 7/13/2017.
 */

public class ClockWidget extends Fragment {

    private View widget;
    private TextClock clock2,clock3,clock4;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        widget=inflater.inflate(R.layout.widget_clock,container,false);
        initViews();
        return widget;
    }

    private void initViews() {
        clock2=widget.findViewById(R.id.clock2);
        clock3=widget.findViewById(R.id.clock3);
        clock4=widget.findViewById(R.id.clock4);

        clock2.setTimeZone("America/New_York");
        clock3.setTimeZone("Europe/London");
        clock4.setTimeZone("Asia/Tokyo");
    }

}

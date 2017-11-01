package com.developer.me.homelauncher.pages;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.developer.me.homelauncher.R;
import com.developer.me.homelauncher.constants.Packages;

/**
 * Created by Sanidhya on 7/8/2017.
 */

public class HomeLayoutPage extends Fragment implements View.OnClickListener {

    private View layout;
    private String currentPackage = "";
    private Animation btnScale;
    private PackageManager packageManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.page_homelayout, container, false);
        packageManager=getActivity().getPackageManager();
        initViews();
        initAnimations();
        return layout;
    }

    private void initViews() {
        layout.findViewById(R.id.shortcut_open_store).setOnClickListener(this);
        layout.findViewById(R.id.shortcut_open_newsstand).setOnClickListener(this);
        layout.findViewById(R.id.shortcut_open_instagram).setOnClickListener(this);
        layout.findViewById(R.id.shortcut_open_whatsapp).setOnClickListener(this);
    }

    private void initAnimations() {
        btnScale = AnimationUtils.loadAnimation(getActivity(), R.anim.btn_scale);
    }

    private boolean isPackageInstalled(String packagename) {
        try {
            packageManager.getPackageInfo(packagename, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.shortcut_open_dialer:
                currentPackage = Packages.PHONE;
                break;
            case R.id.shortcut_open_messages:
                currentPackage = Packages.MESSAGES;
                break;
            case R.id.shortcut_open_camera:
                currentPackage = Packages.CAMERA;
                break;
            case R.id.shortcut_open_chrome:
                currentPackage = Packages.CHROME;
                break;
            case R.id.shortcut_open_whatsapp:
                currentPackage = Packages.WHATSAPP;
                break;
            case R.id.shortcut_open_instagram:
                currentPackage = Packages.INSTAGRAM;
                break;
            case R.id.shortcut_open_store:
                currentPackage = Packages.PLAY_STORE;
                break;
            case R.id.shortcut_open_newsstand:
                currentPackage = Packages.PLAY_NEWSSTAND;
                break;
        }
        Intent i;
        if(isPackageInstalled(currentPackage)) {
            i = getActivity().getPackageManager().getLaunchIntentForPackage(currentPackage);
            startActivity(i);
        }
        else{
            Toast.makeText(getActivity(), "App not installed", Toast.LENGTH_SHORT).show();
        }
    }

}

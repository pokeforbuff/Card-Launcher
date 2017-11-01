package com.developer.me.homelauncher.pages;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.ToggleButton;

import com.developer.me.homelauncher.R;
import com.developer.me.homelauncher.adapters.AppLayoutAdapter;
import com.developer.me.homelauncher.models.App;
import com.developer.me.homelauncher.utils.AppListMgr;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sanidhya on 8/26/2017.
 */

public class ControlCenter extends Fragment implements SeekBar.OnSeekBarChangeListener, CompoundButton.OnCheckedChangeListener, TextWatcher, AppLayoutAdapter.ItemClickListener {

    private View page, viewgrp1, viewgrp2;
    private WifiManager wifiManager;
    private boolean isFlashOn, isRotOn, isBTOn;
    private SeekBar brightnessSlider;
    private BluetoothAdapter bluetoothAdapter;
    private List<App> searchedApps, apps;
    private PackageManager manager;
    private GridLayoutManager horizontalLayoutManager;
    private CameraManager cameraManager;
    private RecyclerView searchedAppsRecylerView;
    private String cameraId;
    private AppLayoutAdapter adapter;
    private EditText seacrhApps;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        page = inflater.inflate(R.layout.page_control_center, container, false);
        initListeners();
        if (!isWifiTogglePermitted()) {
            requestWifiAccessPermission();
            requestWifiTogglePermission();
        }
        requestScreenRotTogglePermission();
        try {
            initObjects();
            initVariables();
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        initViews();
        return page;
    }

    private void initViews() {
        page.findViewById(R.id.control_center_parent).setOnClickListener(null);
        viewgrp1=page.findViewById(R.id.cc_viewgroup_1);
        viewgrp2=page.findViewById(R.id.cc_viewgroup_2);
        ToggleButton wifiToggle = page.findViewById(R.id.toggle_btn_wifi);
        ToggleButton flashLightToggle = page.findViewById(R.id.toggle_btn_flashlight);
        ToggleButton screenRotToggle = page.findViewById(R.id.toggle_btn_screenrot);
        ToggleButton btToggle = page.findViewById(R.id.toggle_btn_bt);
        searchedAppsRecylerView=page.findViewById(R.id.searched_apps_recyclerview);
        brightnessSlider = page.findViewById(R.id.brightness_slider);
        seacrhApps = page.findViewById(R.id.search_apps);
        seacrhApps.addTextChangedListener(this);
        wifiToggle.setOnCheckedChangeListener(this);
        flashLightToggle.setOnCheckedChangeListener(this);
        screenRotToggle.setOnCheckedChangeListener(this);
        btToggle.setOnCheckedChangeListener(this);
        brightnessSlider.setMax(255);
        brightnessSlider.setOnSeekBarChangeListener(this);
        int curBrightnessValue = Settings.System.getInt(getActivity().getContentResolver(), android.provider.Settings.System.SCREEN_BRIGHTNESS, -1);
        brightnessSlider.setProgress(curBrightnessValue);
        wifiToggle.setChecked(wifiManager.isWifiEnabled());
        searchedAppsRecylerView.setLayoutManager(horizontalLayoutManager);
        screenRotToggle.setChecked(isRotOn);
        btToggle.setChecked(isBTOn);
    }

    private void initVariables() throws CameraAccessException {
        isFlashOn = false;
        cameraId = cameraManager.getCameraIdList()[0];
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        isBTOn = bluetoothAdapter.isEnabled();
        isRotOn = Settings.System.getInt(getActivity().getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 1) == 1;
    }

    private boolean isWifiTogglePermitted() {
        return (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CHANGE_WIFI_STATE) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestWifiTogglePermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CHANGE_WIFI_STATE}, 1);
    }

    private void requestWifiAccessPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_WIFI_STATE}, 2);
    }

    private void requestScreenRotTogglePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.System.canWrite(getActivity())) {
                Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + getActivity().getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
    }

    private void initListeners() {

    }

    private void initObjects() throws CameraAccessException {
        manager = getActivity().getPackageManager();
        wifiManager = (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        cameraManager = (CameraManager) getActivity().getApplicationContext().getSystemService(Context.CAMERA_SERVICE);
        searchedApps = new ArrayList<>();
        apps = AppListMgr.apps;
        horizontalLayoutManager = new GridLayoutManager(getActivity(), 1, LinearLayoutManager.HORIZONTAL, false);
        adapter = new AppLayoutAdapter(getActivity(), apps, 0);
    }

    private void launchApp(String packageName) {
        Intent i;
        i = manager.getLaunchIntentForPackage(packageName);
        startActivity(i);
    }

    /*@Override
    public void onClick(View view) {
        Intent i = new Intent(getActivity(), HiddenOrFolderAppsActivity.class);
        switch (view.getId()) {
            case R.id.btn_show_folders:
                i.putExtra("decider", "folders");
                break;
            case R.id.btn_show_hidden_apps:
                i.putExtra("decider", "hidden_apps");
                break;
            case R.id.btn_show_settings:
                break;
        }
        startActivity(i);
    }*/

    private void setScreenBrightness(int i) {
        if (i >= 0 && i <= 255) {
            Settings.System.putInt(getActivity().getContentResolver(), android.provider.Settings.System.SCREEN_BRIGHTNESS, i);
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        setScreenBrightness(i);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.toggle_btn_wifi:
                wifiManager.setWifiEnabled(b);
                break;
            case R.id.toggle_btn_flashlight:
                try {
                    toggleFlash();
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.toggle_btn_screenrot:
                setAutoOrientationEnabled(b);
                break;
            case R.id.toggle_btn_bt:
                setBluetoothEnabled(b);
                break;
        }
    }

    private void toggleFlash() throws CameraAccessException {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cameraManager.setTorchMode(cameraId, !isFlashOn);
        }
        isFlashOn = !isFlashOn;
    }

    private void setAutoOrientationEnabled(boolean enabled) {
        Settings.System.putInt(getActivity().getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, enabled ? 1 : 0);
    }

    private boolean setBluetoothEnabled(boolean enable) {
        if (enable)
            return bluetoothAdapter.enable();
        else
            return bluetoothAdapter.disable();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (charSequence.length() != 0) {
            searchedApps.clear();
            for (int count = 0; count < apps.size(); count++) {
                if ((apps.get(count).getLabel().toString().toLowerCase()).startsWith(charSequence.toString().toLowerCase())) {
                    searchedApps.add(apps.get(count));
                }
            }
            adapter = new AppLayoutAdapter(getActivity(), searchedApps, 0);
            adapter.setClickListener(this);
            viewgrp1.setVisibility(View.GONE);
            viewgrp2.setVisibility(View.GONE);
            searchedAppsRecylerView.setAdapter(adapter);
        } else {
            viewgrp1.setVisibility(View.VISIBLE);
            viewgrp2.setVisibility(View.VISIBLE);
            searchedAppsRecylerView.setAdapter(null);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onItemClick(View view, App app) {
        launchApp(app.getName().toString());
    }
}

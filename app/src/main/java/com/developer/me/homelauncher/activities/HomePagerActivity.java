package com.developer.me.homelauncher.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.developer.me.homelauncher.R;
import com.developer.me.homelauncher.constants.Packages;
import com.developer.me.homelauncher.pages.AppLayoutPage;
import com.developer.me.homelauncher.pages.WidgetLayoutPage;

public class HomePagerActivity extends AppCompatActivity implements View.OnClickListener {

    ViewPager homeLayoutPager;
    private PackageManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_pager_layout);
        initViews();
        initObjects();
        initPager();
    }

    private void initObjects() {
        manager = getPackageManager();
    }

    private void initPager() {
        homeLayoutPager.setAdapter(new PagerAdaper(getSupportFragmentManager()));
        homeLayoutPager.setCurrentItem(1);
    }

    private void initViews() {
        homeLayoutPager = (ViewPager) findViewById(R.id.id_home_viewpager);
        findViewById(R.id.shortcut_open_dialer).setOnClickListener(this);
        findViewById(R.id.shortcut_open_messages).setOnClickListener(this);
        findViewById(R.id.shortcut_open_camera).setOnClickListener(this);
        findViewById(R.id.shortcut_open_chrome).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.shortcut_open_dialer:
                launchApp(Packages.PHONE);
                break;
            case R.id.shortcut_open_messages:
                launchApp(Packages.MESSAGES);
                break;
            case R.id.shortcut_open_camera:
                launchApp(Packages.CAMERA);
                break;
            case R.id.shortcut_open_chrome:
                launchApp(Packages.CHROME);
                break;
        }
    }

    private boolean isPackageInstalled(String packagename) {
        try {
            manager.getPackageInfo(packagename, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    private void launchApp(String packageName){
        Intent i;
        if(isPackageInstalled(packageName)) {
            i = manager.getLaunchIntentForPackage(packageName);
            startActivity(i);
        }
        else{
            Toast.makeText(this, "App not installed", Toast.LENGTH_SHORT).show();
        }
    }

    private class PagerAdaper extends FragmentPagerAdapter {
        PagerAdaper(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new WidgetLayoutPage();
                case 1:
                    return new AppLayoutPage();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    @Override
    public void onBackPressed() {

    }
}

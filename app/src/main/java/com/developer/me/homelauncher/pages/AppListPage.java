package com.developer.me.homelauncher.pages;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.developer.me.homelauncher.R;
import com.developer.me.homelauncher.adapters.AppLayoutAdapter;
import com.developer.me.homelauncher.constants.Packages;
import com.developer.me.homelauncher.models.App;
import com.developer.me.homelauncher.utils.AppListMgr;
import com.developer.me.homelauncher.utils.ArrayListSorter;
import com.developer.me.homelauncher.utils.HiddenAppsMgr;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sanidhya on 7/6/2017.
 */

public class AppListPage extends Fragment implements AppLayoutAdapter.ItemClickListener, View.OnClickListener, AppLayoutAdapter.ItemLongClickListener {

    private View page, searchCard, uninstallCard, uninstallViewGroup, hideCard, openGoogleApp, openPlayStore;
    private PackageManager manager;
    private boolean isMultiSelect;
    private List<App> apps, selectedApps;
    private RecyclerView recyclerView;
    private ArrayListSorter arrayListSorter;
    private AppLayoutAdapter adapter;
    public HiddenAppsMgr hiddenApps;
    private AppListMgr appListMgrManager;
    private GridLayoutManager horizontalLayoutManager;
    private Animation btnScale, slideToLeft, slideToLeftOffScreen, slideToRight, slideToRightOffScreen;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        page = inflater.inflate(R.layout.page_applist, container, false);
        initObjects();
        initViews();
        initAnimations();
        loadApps();
        loadGridView();
        initListeners();
        initCleanReceiver();
        return page;
    }

    private void initCleanReceiver() {
        BroadcastReceiver br = new AppListChangedReciever();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
        intentFilter.addDataScheme("package");
        getActivity().registerReceiver(br, intentFilter);
    }

    private void initListeners() {
        uninstallCard.setOnClickListener(this);
        hideCard.setOnClickListener(this);
        page.findViewById(R.id.cancel_uninstall_btn).setOnClickListener(this);
        openGoogleApp.setOnClickListener(this);
        openPlayStore.setOnClickListener(this);
    }

    private void initAnimations() {
        btnScale = AnimationUtils.loadAnimation(getActivity(), R.anim.btn_scale);
        slideToLeft = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_to_left);
        slideToLeftOffScreen = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_to_left_off_screen);
        slideToRight = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_to_right);
        slideToRightOffScreen = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_to_right_off_screen);
    }

    private void initObjects() {
        isMultiSelect = false;
        arrayListSorter = new ArrayListSorter();
        hiddenApps = new HiddenAppsMgr();
        appListMgrManager =new AppListMgr();
        horizontalLayoutManager = new GridLayoutManager(getActivity(), 4, LinearLayoutManager.HORIZONTAL, false);
    }

    private void initViews() {
        recyclerView = page.findViewById(R.id.app_grid_recyclerview);
        searchCard = page.findViewById(R.id.search_apps_card);
        uninstallViewGroup = page.findViewById(R.id.uninstall_viewgroup);
        uninstallCard = page.findViewById(R.id.uninstall_card);
        openGoogleApp = page.findViewById(R.id.shortcut_open_google_app);
        openPlayStore = page.findViewById(R.id.shortcut_open_store);
        hideCard = page.findViewById(R.id.hide_card);
    }

    private void loadApps() {
        manager = getActivity().getPackageManager();
        apps = new ArrayList<>();
        selectedApps = new ArrayList<>();

        Intent i = new Intent(Intent.ACTION_MAIN, null);
        i.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> availableActivities = manager.queryIntentActivities(i, 0);
        for (ResolveInfo ri : availableActivities) {
            if (!ri.activityInfo.packageName.equals(getActivity().getPackageName())) {
                App app = new App();
                app.setLabel(ri.loadLabel(manager));
                app.setName(ri.activityInfo.packageName);
                app.setIcon(ri.activityInfo.loadIcon(manager));
                apps.add(app);
            }
        }
        apps = arrayListSorter.sortByLabel(apps);
        appListMgrManager.setAppList(apps);
    }

    private void loadGridView() {
        recyclerView.setLayoutManager(horizontalLayoutManager);
        adapter = new AppLayoutAdapter(getActivity(), apps, 0);
        adapter.setClickListener(this);
        adapter.setLongClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, App app) {
        if (isMultiSelect)
            multiSelect(app);
        else {
            view.startAnimation(btnScale);
            launchApp(app.getName().toString());
        }
    }

    private void launchApp(String packageName) {
        Intent i;
        i = manager.getLaunchIntentForPackage(packageName);
        startActivity(i);
    }

    private void enableMultiSelect() {
        isMultiSelect = true;
        searchCard.startAnimation(slideToLeftOffScreen);
        searchCard.setVisibility(View.GONE);
        uninstallViewGroup.setVisibility(View.VISIBLE);
        uninstallViewGroup.startAnimation(slideToLeft);
    }

    private void disableMultiSelect() {
        selectedApps.clear();
        adapter.selectedAppList = selectedApps;
        adapter.notifyDataSetChanged();
        uninstallViewGroup.startAnimation(slideToRight);
        uninstallViewGroup.setVisibility(View.GONE);
        searchCard.startAnimation(slideToRightOffScreen);
        searchCard.setVisibility(View.VISIBLE);
        isMultiSelect = false;
    }

    private void multiSelect(App app) {
        if (selectedApps.contains(app)) {
            selectedApps.remove(app);
            if (selectedApps.isEmpty()) {
                disableMultiSelect();
            }
        } else
            selectedApps.add(app);
        refreshAdapter();
    }

    @Override
    public void onItemLongClick(View view, App app) {
        if (!isMultiSelect) {
            enableMultiSelect();
            multiSelect(app);
        }
    }

    private void refreshAdapter() {
        adapter.selectedAppList = selectedApps;
        adapter.notifyDataSetChanged();
    }

    private void uninstallPackage(String packageName) {
        Uri packageURI = Uri.parse("package:" + packageName);
        Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
        startActivity(uninstallIntent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.uninstall_card:
                if (selectedApps.size() == 1) {
                    for (App selectedApp : selectedApps) {
                        for (App app : apps) {
                            if (selectedApp == app) {
                                uninstallPackage(app.getName().toString());
                                disableMultiSelect();
                                break;
                            }
                        }
                    }
                    disableMultiSelect();
                } else
                    Toast.makeText(getActivity(), "Select only one app to uninstall", Toast.LENGTH_SHORT).show();
                break;
            case R.id.cancel_uninstall_btn:
                disableMultiSelect();
                break;
            case R.id.hide_card:
                hideApps(selectedApps);
                break;
            case R.id.shortcut_open_google_app:
                launchApp(Packages.GOOGLE_APP);
                break;
            case R.id.shortcut_open_store:
                launchApp(Packages.PLAY_STORE);
                break;
        }
    }

    private void hideApps(List<App> toHide) {
        hiddenApps.addToHiddenApps(toHide);
        apps.removeAll(toHide);
        appListMgrManager.setAppList(apps);
        disableMultiSelect();
    }

    public class AppListChangedReciever extends BroadcastReceiver {

        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_PACKAGE_REMOVED)) {
                String uninstalledAppName = intent.getData().getSchemeSpecificPart();
                for (int i = 0; i < apps.size(); i++) {
                    if (apps.get(i).getName().toString().equals(uninstalledAppName)) {
                        apps.remove(i);
                        appListMgrManager.setAppList(apps);
                        adapter.notifyItemRemoved(i);
                        break;
                    }
                }
            } else if (intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED)) {
                String installedAppName = intent.getData().getSchemeSpecificPart();
                try {
                    ApplicationInfo app = manager.getApplicationInfo(installedAppName, 0);
                    Drawable icon = manager.getApplicationIcon(app);
                    CharSequence label = manager.getApplicationLabel(app);
                    App ap = new App();
                    ap.setLabel(label);
                    ap.setName(installedAppName);
                    ap.setIcon(icon);
                    apps.add(ap);
                    appListMgrManager.setAppList(apps);
                    apps = arrayListSorter.sortByLabel(apps);
                    adapter.notifyItemInserted(apps.indexOf(ap));
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}

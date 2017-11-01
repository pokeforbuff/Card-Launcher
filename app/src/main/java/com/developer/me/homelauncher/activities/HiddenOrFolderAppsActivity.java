package com.developer.me.homelauncher.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.developer.me.homelauncher.R;
import com.developer.me.homelauncher.adapters.AppLayoutAdapter;
import com.developer.me.homelauncher.models.App;
import com.developer.me.homelauncher.utils.HiddenAppsMgr;

import java.util.List;

/**
 * Created by Sanidhya on 8/28/2017.
 */

public class HiddenOrFolderAppsActivity extends AppCompatActivity implements AppLayoutAdapter.ItemClickListener{

    private TextView titleText;
    private String title;
    private List<App> hiddenOrFolders;
    private PackageManager manager;
    private AppLayoutAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hidden_and_folders);
        receiveIntent();
        initViews();
        setValues();
    }

    private void setValues() {
        manager=getPackageManager();
        if (title.equals("folders")) {
            titleText.setText("Folders");
        } else if (title.equals("hidden_apps")) {
            titleText.setText("Hidden Apps");
            hiddenOrFolders= HiddenAppsMgr.hiddenApps;
            adapter=new AppLayoutAdapter(this,hiddenOrFolders,1);
            adapter.setClickListener(this);
            recyclerView.setLayoutManager(new GridLayoutManager(this, 4, LinearLayoutManager.VERTICAL, false));
            recyclerView.setAdapter(adapter);
        }
    }

    private void receiveIntent() {
        Intent intent = getIntent();
        title = intent.getExtras().getString("decider");
    }

    private void initViews() {
        titleText = (TextView) findViewById(R.id.hidden_folder_title_textview);
        recyclerView=(RecyclerView) findViewById(R.id.hidden_folder_recyclerview);
    }

    @Override
    public void onItemClick(View view, App app) {
        startActivity(manager.getLaunchIntentForPackage(app.getName().toString()));
    }
}

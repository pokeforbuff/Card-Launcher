package com.developer.me.homelauncher.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.developer.me.homelauncher.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sanidhya on 7/12/2017.
 */

public class WidgetLayoutAdapter extends RecyclerView.Adapter<WidgetLayoutAdapter.ViewHolder> {

    private List<Fragment> widgetList = new ArrayList<>();
    private LayoutInflater inflater;
    private FragmentManager fragmentManager;

    public WidgetLayoutAdapter(Context context, List<Fragment> widgetList) {
        this.widgetList = widgetList;
        this.inflater = LayoutInflater.from(context);
        this.fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.viewholder_widget, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        fragmentManager.beginTransaction().add(holder.widgetFrame.getId(), widgetList.get(position)).commit();
    }

    @Override
    public int getItemCount() {
        return widgetList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View widgetFrame;

        public ViewHolder(View itemView) {
            super(itemView);
            widgetFrame = itemView.findViewById(R.id.widget_frame);
        }

    }

}

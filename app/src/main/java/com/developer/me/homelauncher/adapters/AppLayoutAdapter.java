package com.developer.me.homelauncher.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.developer.me.homelauncher.R;
import com.developer.me.homelauncher.models.App;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sanidhya on 7/5/2017.
 */

public class AppLayoutAdapter extends RecyclerView.Adapter<AppLayoutAdapter.ViewHolder> {

    private List<App> appList = new ArrayList<>();
    public List<App> selectedAppList = new ArrayList<>();
    private LayoutInflater inflater;
    private ItemClickListener clickListener;
    private Context context;
    private int orientation; //0=horizontal, 1=vertical
    private ItemLongClickListener longClickListener;

    public AppLayoutAdapter(Context context, List<App> appList, int orientation) {
        this.context = context;
        this.appList = appList;
        this.orientation=orientation;
        this.inflater = LayoutInflater.from(context);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(orientation==0)
            view = inflater.inflate(R.layout.viewholder_app_horizontal, parent, false);
        else
            view= inflater.inflate(R.layout.viewholder_app_vertical, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (selectedAppList.contains(appList.get(position))) {
            holder.parent.setCardBackgroundColor(ContextCompat.getColor(context, R.color.RedThemeColorAccent));
            holder.appContainer.setCardBackgroundColor(ContextCompat.getColor(context, R.color.RedThemeColorPrimary));
            holder.appLabel.setTextColor(ContextCompat.getColor(context, R.color.RedThemeColorPrimary));
            holder.parent.setScaleX(0.9f);
            holder.parent.setScaleY(0.9f);
        } else {
            holder.parent.setCardBackgroundColor(ContextCompat.getColor(context, R.color.RedThemeColorPrimary));
            holder.appContainer.setCardBackgroundColor(ContextCompat.getColor(context, R.color.RedThemeColorAccent));
            holder.appLabel.setTextColor(ContextCompat.getColor(context, R.color.RedThemeColorAccent));
            holder.parent.setScaleX(1f);
            holder.parent.setScaleY(1f);
        }
        holder.appLabel.setText(appList.get(position).getLabel());
        holder.appIcon.setImageDrawable(appList.get(position).getIcon());
    }

    @Override
    public int getItemCount() {
        return appList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        ImageView appIcon;
        TextView appLabel;
        CardView parent, appContainer;

        ViewHolder(View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.card_parent);
            appIcon = itemView.findViewById(R.id.app_icon_imageview);
            appLabel = itemView.findViewById(R.id.app_label_textview);
            appContainer = itemView.findViewById(R.id.app_card);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null)
                clickListener.onItemClick(itemView, appList.get(getAdapterPosition()));
        }

        @Override
        public boolean onLongClick(View view) {
            if (longClickListener != null)
                longClickListener.onItemLongClick(itemView, appList.get(getAdapterPosition()));
            return true;
        }
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }


    public interface ItemClickListener {
        void onItemClick(View view, App app);
    }

    public void setLongClickListener(ItemLongClickListener itemLongClickListener) {
        this.longClickListener = itemLongClickListener;
    }

    public interface ItemLongClickListener {
        void onItemLongClick(View view, App app);
    }

}

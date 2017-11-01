package com.developer.me.homelauncher.pages;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.developer.me.homelauncher.R;
import com.developer.me.homelauncher.utils.DepthPage;

import fr.castorflex.android.verticalviewpager.VerticalViewPager;

/**
 * Created by Sanidhya on 8/26/2017.
 */

public class AppLayoutPage extends Fragment {

    private View page;
    private VerticalViewPager appPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        page = inflater.inflate(R.layout.page_applayout, container, false);
        initViews();
        initPager();
        return page;
    }

    private void initPager() {
        appPager.setAdapter(new PagerAdaper(getActivity().getSupportFragmentManager()));
        appPager.setPageTransformer(true, new DepthPage());
        appPager.setCurrentItem(1);
    }

    private void initViews() {
        appPager = page.findViewById(R.id.app_layout_pager);
    }

    private class PagerAdaper extends FragmentStatePagerAdapter {

        PagerAdaper(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new ControlCenter();
                case 1:
                    return new AppListPage();
            }
            return null;
        }

        @Override
        public float getPageWidth(int position) {
            if (position == 1)
                return 1f;
            else return 0.5f;
        }

        @Override
        public int getCount() {
            return 2;
        }

    }

}

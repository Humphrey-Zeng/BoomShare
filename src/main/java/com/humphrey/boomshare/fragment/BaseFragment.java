package com.humphrey.boomshare.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.humphrey.boomshare.activity.HomeActivity;

/**
 * Created by Humphrey on 2016/3/23.
 */
public abstract class BaseFragment extends Fragment {

    public HomeActivity mActivity;
    public Toolbar mToolBar;
    public ActionBarDrawerToggle mToggle;
    public DrawerLayout mDrawer;
    public Menu mMenu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = (HomeActivity) getActivity();
        mToolBar = mActivity.getToolBar();
        mMenu = mActivity.getMenu();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        return initViews();
    }

    public abstract View initViews();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initData();
    }

    public void initData() {

    }

}

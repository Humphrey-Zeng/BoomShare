package com.humphrey.boomshare.fragment;

import android.view.View;

import com.humphrey.boomshare.R;

/**
 * Created by Humphrey on 2016/3/23.
 */
public class PickMeUpFragment extends BaseFragment {
    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.fragment_pick_me_up, null);

        mToolBar.setTitle("带我飞");

        return view;
    }
}

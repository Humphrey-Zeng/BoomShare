package com.humphrey.boomshare.fragment;

import android.view.View;

import com.humphrey.boomshare.R;

/**
 * Created by Humphrey on 2016/3/23.
 */
public class ErrorCollectionFragment extends BaseFragment {
    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.fragment_error_collection, null);

        mToolBar.setTitle("错题本");

        return view;
    }
}

package com.humphrey.boomshare.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.humphrey.boomshare.R;
import com.humphrey.boomshare.activity.SelectFolderActivity;

/**
 * Created by Humphrey on 2016/3/23.
 */
public class NotesFragment extends BaseFragment{

    private Button btnVisitCamera;

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.fragment_notes, null);

        mToolBar.setTitle("笔记");
        mActivity.isOptionMenuShown = true;
        mActivity.onPrepareOptionsMenu(mMenu);

        return view;
    }

    @Override
    public void initData() {

    }
}

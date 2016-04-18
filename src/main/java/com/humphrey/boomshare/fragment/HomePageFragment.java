package com.humphrey.boomshare.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.humphrey.boomshare.R;

/**
 * Created by Humphrey on 2016/3/23.
 */
public class HomePageFragment extends BaseFragment implements View.OnClickListener {

    private TextView btnNotes;
    private TextView btnCommunity;
    private TextView btnErrCollection;
    private TextView btnWorkAndRest;

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.fragment_home_page, null);

        mToolBar.setTitle("首页");


        btnNotes = (TextView) view.findViewById(R.id.btn_home_notes);
        btnCommunity = (TextView) view.findViewById(R.id.btn_home_community);
        btnErrCollection = (TextView) view.findViewById(R.id.btn_home_err_collection);
        btnWorkAndRest = (TextView) view. findViewById(R.id.btn_home_work_and_rest);

        btnNotes.setOnClickListener(this);
        btnCommunity.setOnClickListener(this);
        btnErrCollection.setOnClickListener(this);
        btnWorkAndRest.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_home_notes:
                mActivity.initFragment(mActivity.NAV_NOTES);
                mActivity.isHomepage = false;
                mActivity.isNotesFragment = true;
                break;
            case R.id.btn_home_community:
                mActivity.initFragment(mActivity.NAV_COMMUNITY);
                mActivity.isHomepage = false;
                mActivity.isNotesFragment = false;
                break;
            case R.id.btn_home_err_collection:
                mActivity.initFragment(mActivity.NAV_ERRORS_COLLECTION);
                mActivity.isHomepage = false;
                mActivity.isNotesFragment = false;
                break;
            case R.id.btn_home_work_and_rest:
                mActivity.initFragment(mActivity.NAV_WORK_AND_REST);
                mActivity.isHomepage = false;
                mActivity.isNotesFragment = false;
                break;
        }
    }

    @Override
    public void initData() {

    }
}

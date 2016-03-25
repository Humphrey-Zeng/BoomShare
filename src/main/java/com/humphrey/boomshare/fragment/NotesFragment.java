package com.humphrey.boomshare.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.humphrey.boomshare.R;
import com.humphrey.boomshare.activity.SelectFolderActivity;

/**
 * Created by Humphrey on 2016/3/23.
 */
public class NotesFragment extends BaseFragment implements View.OnClickListener {

    private Button btnVisitCamera;
    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.fragment_notes, null);

        btnVisitCamera = (Button) view.findViewById(R.id.btn_visit_camera);
        btnVisitCamera.setOnClickListener(this);

        return view;
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_visit_camera:
                Intent intent = new Intent(mActivity, SelectFolderActivity.class);
                startActivity(intent);
                break;
        }
    }
}

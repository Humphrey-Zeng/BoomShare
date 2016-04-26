package com.humphrey.boomshare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.humphrey.boomshare.R;
import com.humphrey.boomshare.fragment.CommunityFragment;
import com.humphrey.boomshare.fragment.ErrorCollectionFragment;
import com.humphrey.boomshare.fragment.ExperienceExchangeFragment;
import com.humphrey.boomshare.fragment.HomePageFragment;
import com.humphrey.boomshare.fragment.NotesFragment;
import com.humphrey.boomshare.fragment.PickMeUpFragment;
import com.humphrey.boomshare.fragment.WorkAndRestFragment;

import static com.humphrey.boomshare.utils.GlobalUtils.SELECT_COVER;
import static com.humphrey.boomshare.utils.GlobalUtils.SELECT_PICTURE;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int NAV_NOTES = 0;
    public static final int NAV_COMMUNITY = 1;
    public static final int NAV_ERRORS_COLLECTION = 2;
    public static final int NAV_WORK_AND_REST = 3;
    public static final int NAV_EXPERIENCE_EXCHANGE = 4;
    public static final int NAV_PICK_ME_UP = 5;
    public static final int NAV_HOME_PAGE = 6;
    public static final String FRAGMENT_NOTES = "fragment_notes";
    public static final String FRAGMENT_COMMUNITY = "fragment_community";
    public static final String FRAGMENT_ERRORS_COLLECTION = "fragment_errors_collection";
    public static final String FRAGMENT_WORK_AND_REST = "fragment_work_and_rest";
    public static final String FRAGMENT_EXPERIENCE_EXCHANGE = "fragment_experience_exchange";
    public static final String FRAGMENT_PICK_ME_UP = "fragment_pick_me_up";
    public static final String FRAGMENT_HOME_PAGE = "fragment_home_page";

    private ImageView btnTabHome;
    private ImageView btnTabSend;
    private ImageView btnTabPersonal;
    private ImageView btnTabSetting;

    private Toolbar toolbar;
    private Menu mMenu;
    public boolean isOptionMenuShown = true;
    public boolean isHomepage = true;
    public boolean isNotesFragment = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnTabHome = (ImageView) findViewById(R.id.btn_tab_home);
        btnTabSend = (ImageView) findViewById(R.id.btn_tab_send);
        btnTabPersonal = (ImageView) findViewById(R.id.btn_tab_personal);
        btnTabSetting = (ImageView) findViewById(R.id.btn_tab_setting);

        btnTabHome.setOnClickListener(this);
        btnTabSend.setOnClickListener(this);
        btnTabSetting.setOnClickListener(this);
        btnTabPersonal.setOnClickListener(this);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        initFragment(NAV_HOME_PAGE);
        isHomepage = true;
        isNotesFragment = false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.notes_option, menu);
        mMenu = menu;
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.option_visit_camera) {
            Intent intent = new Intent(this, SelectFolderActivity.class);
            intent.putExtra("selectType", SELECT_PICTURE);
            startActivity(intent);
            return true;
        }
        if (id == R.id.option_edit){
            NotesFragment fragment = getNotesFragment();
            fragment.editNotes();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//
//        switch (id) {
//            case R.id.nav_notes:
//                initFragment(NAV_NOTES);
//                break;
//            case R.id.nav_community:
//                initFragment(NAV_COMMUNITY);
//                break;
//            case R.id.nav_errors_collection:
//                initFragment(NAV_ERRORS_COLLECTION);
//                break;
//            case R.id.nav_work_and_rest:
//                initFragment(NAV_WORK_AND_REST);
//                break;
//            case R.id.nav_experience_exchange:
//                initFragment(NAV_EXPERIENCE_EXCHANGE);
//                break;
//            case R.id.nav_pick_me_up:
//                initFragment(NAV_PICK_ME_UP);
//                break;
//            case R.id.nav_home_page:
//                initFragment(NAV_HOME_PAGE);
//        }
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }

    public NotesFragment getNotesFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        NotesFragment fragment = (NotesFragment) fragmentManager.findFragmentByTag(FRAGMENT_NOTES);

        return fragment;
    }

    public void initFragment(int factorID) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        switch (factorID) {
            case NAV_NOTES:
                transaction.replace(R.id.fl_content, new NotesFragment(), FRAGMENT_NOTES);
                break;
            case NAV_COMMUNITY:
                transaction.replace(R.id.fl_content, new CommunityFragment(), FRAGMENT_COMMUNITY);
                break;
            case NAV_ERRORS_COLLECTION:
                transaction.replace(R.id.fl_content, new ErrorCollectionFragment(),
                        FRAGMENT_ERRORS_COLLECTION);
                break;
            case NAV_WORK_AND_REST:
                transaction.replace(R.id.fl_content, new WorkAndRestFragment(),
                        FRAGMENT_WORK_AND_REST);
                break;
            case NAV_EXPERIENCE_EXCHANGE:
                transaction.replace(R.id.fl_content, new ExperienceExchangeFragment(),
                        FRAGMENT_EXPERIENCE_EXCHANGE);
                break;
            case NAV_PICK_ME_UP:
                transaction.replace(R.id.fl_content, new PickMeUpFragment(), FRAGMENT_PICK_ME_UP);
                break;
            case NAV_HOME_PAGE:
                transaction.replace(R.id.fl_content, new HomePageFragment(), FRAGMENT_HOME_PAGE);
        }

        transaction.commit();
    }


    public Toolbar getToolBar() {
        return toolbar;
    }

    public Menu getMenu() {
        return mMenu;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        menu.clear();
        if (!isOptionMenuShown) {
            return false;
        } else {
            getMenuInflater().inflate(R.menu.notes_option, menu);
            return true;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_tab_home:
                initFragment(NAV_HOME_PAGE);

                isOptionMenuShown = false;
                onPrepareOptionsMenu(mMenu);

                isHomepage = true;
                break;
            case R.id.btn_tab_send:
                break;
            case R.id.btn_tab_personal:
                break;
            case R.id.btn_tab_setting:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (isHomepage) {
            super.onBackPressed();
        } else {
            initFragment(NAV_HOME_PAGE);

            isOptionMenuShown = false;
            onPrepareOptionsMenu(mMenu);

            isHomepage = true;
            isNotesFragment = false;
        }
    }

    @Override
    protected void onResume() {
        if (isNotesFragment == false) {
            super.onResume();
        }else{
            super.onResume();
            initFragment(NAV_NOTES);
            isHomepage = false;
        }
    }

}

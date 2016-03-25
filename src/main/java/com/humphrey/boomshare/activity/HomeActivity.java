package com.humphrey.boomshare.activity;

import android.os.Bundle;
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

import com.humphrey.boomshare.R;
import com.humphrey.boomshare.fragment.CommunityFragment;
import com.humphrey.boomshare.fragment.ErrorCollectionFragment;
import com.humphrey.boomshare.fragment.ExperienceExchangeFragment;
import com.humphrey.boomshare.fragment.NotesFragment;
import com.humphrey.boomshare.fragment.PickMeUpFragment;
import com.humphrey.boomshare.fragment.WorkAndRestFragment;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int NAV_NOTES = 0;
    private static final int NAV_COMMUNITY = 1;
    private static final int NAV_ERRORS_COLLECTION = 2;
    private static final int NAV_WORK_AND_REST = 3;
    private static final int NAV_EXPERIENCE_EXCHANGE = 4;
    private static final int NAV_PICK_ME_UP = 5;
    private static final String FRAGMENT_NOTES = "fragment_notes";
    private static final String FRAGMENT_COMMUNITY = "fragment_community";
    private static final String FRAGMENT_ERRORS_COLLECTION = "fragment_errors_collection";
    private static final String FRAGMENT_WORK_AND_REST = "fragment_work_and_rest";
    private static final String FRAGMENT_EXPERIENCE_EXCHANGE = "fragment_experience_exchange";
    private static final String FRAGMENT_PICK_ME_UP = "fragment_pick_me_up";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string
                .navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_notes:
                initFragment(NAV_NOTES);
                break;
            case R.id.nav_community:
                initFragment(NAV_COMMUNITY);
                break;
            case R.id.nav_errors_collection:
                initFragment(NAV_ERRORS_COLLECTION);
                break;
            case R.id.nav_work_and_rest:
                initFragment(NAV_WORK_AND_REST);
                break;
            case R.id.nav_experience_exchange:
                initFragment(NAV_EXPERIENCE_EXCHANGE);
                break;
            case R.id.nav_pick_me_up:
                initFragment(NAV_PICK_ME_UP);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initFragment(int factorID) {
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
        }

        transaction.commit();
    }

}

package com.codepath.apps.learnfitness.activities;

import com.codepath.apps.learnfitness.R;
import com.codepath.apps.learnfitness.fragments.WeekFragment;
import com.codepath.apps.learnfitness.fragments.WeeksListFragment;
import com.codepath.apps.learnfitness.models.Week;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LessonListActivity extends AppCompatActivity
            implements WeeksListFragment.OnItemSelectedListener {

    public static final String MY_SHARED_PREFS = "MY_SHARED_PREFS4";
    public static final String CURRENT_WEEK_NUMBER = "CURRENT_WEEK_NUMBER";

    @Bind(R.id.drawer_layout) DrawerLayout mDrawer;

    @Bind(R.id.nvView) NavigationView mNavigation;

    private ActionBarDrawerToggle drawerToggle;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_list);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        drawerToggle = setUpDrawerToggle();

        mDrawer.setDrawerListener(drawerToggle);

        setUpDrawerContent(mNavigation);

        if (savedInstanceState == null) {
            // TODO: Is this duplicate of below?
            WeeksListFragment fragment = new WeeksListFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flContent, fragment);
            ft.commit();

        }

        mNavigation.getMenu().getItem(0).setChecked(true);
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction().replace(R.id.flContent, new WeeksListFragment()).commit();
        setTitle(R.string.title_activity_lesson_list);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private ActionBarDrawerToggle setUpDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open,
                R.string.drawer_close);
    }

    private void setUpDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the planet to show based on
        // position
        Fragment fragment = null;

        Class fragmentClass;
        switch(menuItem.getItemId()) {
            case R.id.nav_first_fragment:
                fragmentClass = WeeksListFragment.class;
                break;
            case R.id.nav_second_fragment:
                fragmentClass = WeeksListFragment.class;
//                fragmentClass = .class;
                break;
            case R.id.nav_third_fragment:
                fragmentClass = WeeksListFragment.class;
//                fragmentClass = SimpsonsFragment.class;
                break;
            default:
                fragmentClass = WeeksListFragment.class;
                break;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        // Highlight the selected item, update the title, and close the drawer
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        mDrawer.closeDrawers();
    }

    @Override
    public void onWeekSelected(View itemView, Week week) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, WeekFragment.newInstance(week)).commit();
    }
}

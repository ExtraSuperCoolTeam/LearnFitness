package com.codepath.apps.learnfitness.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.learnfitness.R;
import com.codepath.apps.learnfitness.fragments.CheckMyFormFragment;
import com.codepath.apps.learnfitness.fragments.FindTrainerFragment;
import com.codepath.apps.learnfitness.fragments.WeekFragment;
import com.codepath.apps.learnfitness.fragments.WeeksListFragment;
import com.codepath.apps.learnfitness.models.Trainer;
import com.codepath.apps.learnfitness.models.Week;
import com.facebook.appevents.AppEventsLogger;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LessonListActivity extends AppCompatActivity implements WeeksListFragment.OnItemSelectedListener {
    private static final String TAG = "LessonListActivity";

    public static final String MY_SHARED_PREFS = "MY_SHARED_PREFS4";
    public static final String CURRENT_WEEK_NUMBER = "CURRENT_WEEK_NUMBER";

    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawer;

    @Bind(R.id.nvView)
    NavigationView mNavigation;

    @Bind(R.id.fab)
    FloatingActionButton mFab;

    @Bind(R.id.bottom_sheet)
    View bottomSheet;

    @Bind(R.id.ivTrainerPhoto)
    ImageView mImageViewTrainerPhoto;
    @Bind(R.id.tvTrainerSpeciality)
    TextView mTextViewTrainerSpeciality;
    @Bind(R.id.tvTrainerExperience)
    TextView mTextViewTrainerExperience;
    @Bind(R.id.tvTrainerWeight)
    TextView mTextViewTrainerWeight;
    @Bind(R.id.tvTrainerHeight)
    TextView mTextViewTrainerHeight;
    @Bind(R.id.tvTrainerAddress)
    TextView mTextViewTrainerAddress;
    @Bind(R.id.tvTrainerCall)
    TextView mTextViewTrainerCall;

    private ActionBarDrawerToggle drawerToggle;
    private Toolbar toolbar;
    public static FragmentManager fragmentManager;
    private Menu mMenu;
    private FindTrainerFragment mFindTrainerFragment;
    private CheckMyFormFragment mCheckMyFormFragment;
    private BottomSheetBehavior  mBehavior;

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
//            FindTrainerFragment fragment = new FindTrainerFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flContent, fragment);
            ft.commit();

        }

        mNavigation.getMenu().getItem(0).setChecked(true);
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction().replace(R.id.flContent, new WeeksListFragment()).commit();
        setTitle(R.string.title_activity_lesson_list);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCheckMyFormFragment.showCreationDialog();

            }
        });

        setUpBottomSheet();
    }

    private void setUpBottomSheet() {

        mBehavior = BottomSheetBehavior.from(bottomSheet);
        mBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(View bottomSheet, int newState) {
                Log.d(TAG, "State changing");
            }

            @Override
            public void onSlide(View bottomSheet, float slideOffset) {
                Log.d(TAG, "onSlide");
            }
        });
//        behavior.setPeekHeight(50);
    }

    private void setupTrainerDetailView(Trainer trainer) {

        String speciality = mTextViewTrainerSpeciality.getText() + " " +
                trainer.getTrainerParams().getSpeciality();
        String experience = mTextViewTrainerExperience.getText() + " " +
                trainer.getTrainerParams().getYrsOfTraining();
        String weight = mTextViewTrainerWeight.getText() + " " +
                trainer.getTrainerParams().getWeight();
        String height = mTextViewTrainerHeight.getText() + " " +
                trainer.getTrainerParams().getHeight();

        mTextViewTrainerSpeciality.setText(speciality);
        mTextViewTrainerExperience.setText(experience);
        mTextViewTrainerWeight.setText(weight);
        mTextViewTrainerHeight.setText(height);

        Glide.with(LessonListActivity.this)
                .load(trainer.getProfileUrl()).placeholder(R.mipmap.ic_wifi)
                .into(mImageViewTrainerPhoto);

        mTextViewTrainerAddress.setText(trainer.getAddress());
        mTextViewTrainerCall.setText(trainer.getPhone());

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

    public void showTrainerInfo(Trainer trainer) {
        if (trainer == null) {
            bottomSheet.setVisibility(View.GONE);
            return;
        }

        setupTrainerDetailView(trainer);
        bottomSheet.setVisibility(View.VISIBLE);
        mBehavior.setPeekHeight(100);

    }

    public void selectDrawerItem(MenuItem currentMenuItem) {
        // Create a new fragment and specify the planet to show based on
        // position
        Fragment fragment = null;

        Class fragmentClass;
        switch(currentMenuItem.getItemId()) {
            case R.id.nav_first_fragment:
                fragmentClass = WeeksListFragment.class;
                break;
            case R.id.nav_second_fragment:
                fragmentClass = FindTrainerFragment.class;
                break;
            case R.id.nav_third_fragment:
                fragmentClass = CheckMyFormFragment.class;
                break;

            default:
                fragmentClass = WeeksListFragment.class;
                break;
        }

        showSearch(currentMenuItem.getItemId() == R.id.nav_second_fragment);
        showFab(currentMenuItem.getItemId() == R.id.nav_third_fragment);


        try {
            fragment = (Fragment) fragmentClass.newInstance();
            if (fragmentClass == FindTrainerFragment.class) {
                mFindTrainerFragment = (FindTrainerFragment) fragment;
                showTrainerInfo(null);
            } else if (fragmentClass == CheckMyFormFragment.class) {
                mCheckMyFormFragment = (CheckMyFormFragment) fragment;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        // Highlight the selected item, update the title, and close the drawer
        Menu menu = mNavigation.getMenu();
        for (int i = 0; i < menu.size(); i++) {
            menu.getItem(i).setChecked(false);
        }
        currentMenuItem.setChecked(true);

        setTitle(currentMenuItem.getTitle());
        mDrawer.closeDrawers();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        mMenu = menu;
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchItem.setVisible(false);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Todo need to refresh the trainer fragment
                mFindTrainerFragment.populateMapWithSearchQuery(query);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public void showSearch(Boolean show) {
        MenuItem search = mMenu.findItem(R.id.action_search);
        search.setVisible(show);

        if (show) {
            bottomSheet.setVisibility(View.VISIBLE);
        } else {
            bottomSheet.setVisibility(View.GONE);
        }
    }

    public void showFab(Boolean show) {
        if (show) {
            mFab.setVisibility(View.VISIBLE);
        } else {
            mFab.setVisibility(View.GONE);
        }

    }

    @Override
    public void onWeekSelected(View itemView, Week week) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, WeekFragment.newInstance(week)).commit();
    }

    protected void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppEventsLogger.deactivateApp(this);
    }
}

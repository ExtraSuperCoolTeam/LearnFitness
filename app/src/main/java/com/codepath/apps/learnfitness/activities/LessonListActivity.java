package com.codepath.apps.learnfitness.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

    @Bind(R.id.tvTrainerName)
    TextView mTrainerName;

    @Bind(R.id.rlTrainerCall)
    RelativeLayout rlTrainerCall;

    @Bind(R.id.llTrainerPeakInfo)
    LinearLayout mTrainerPeakInfo;

    private ActionBarDrawerToggle drawerToggle;
    private Toolbar toolbar;
    public static FragmentManager fragmentManager;
    private Menu mMenu;
    private FindTrainerFragment mFindTrainerFragment;
    private CheckMyFormFragment mCheckMyFormFragment;
    private BottomSheetBehavior  mBehavior;
    private Trainer mTrainer;
    private WeeksListFragment mWeeksListFragment;

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

        mFindTrainerFragment = new FindTrainerFragment();
        mCheckMyFormFragment = new CheckMyFormFragment();
        mWeeksListFragment = new WeeksListFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flContent, mWeeksListFragment)
                .commit();
        Log.d(TAG, "Should have just instantiated the WeeksListFragment");


        mNavigation.getMenu().getItem(0).setChecked(true);

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
        Log.d(TAG, "setUpBottomSheet");
        mBehavior = BottomSheetBehavior.from(bottomSheet);
        mBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(View bottomSheet, int newState) {
                Log.d(TAG, "State changing");
                int color;
                int textColor;
                switch (newState) {
                    case BottomSheetBehavior.STATE_DRAGGING:
                    case BottomSheetBehavior.STATE_EXPANDED:
                        color = R.color.primary_dark;
                        textColor = R.color.white;
                    default:
                        color = R.color.white;
                        textColor = R.color.text_dark;

                }
                mTrainerPeakInfo.setBackgroundColor(ContextCompat.
                        getColor(LessonListActivity.this, color));

               //Put into styles the different text color
            }

            @Override
            public void onSlide(View bottomSheet, float slideOffset) {



                Log.d(TAG, "onSlide");
            }
        });

        rlTrainerCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTrainer != null) {
                    Intent i = new Intent(Intent.ACTION_CALL);
                    i.setData(Uri.parse("tel:" + mTrainer.getPhone()));
                    startActivity(i);
                }
            }
        });
    }

    private void setupTrainerDetailView(Trainer trainer) {

        String speciality =  "Specialty: " +
                trainer.getTrainerParams().getSpeciality();
        String experience = "Years of Experience: " +
                trainer.getTrainerParams().getYrsOfTraining();
        String weight = "Weight: " +
                trainer.getTrainerParams().getWeight();
        String height = "Height: " +
                trainer.getTrainerParams().getHeight();
        String name = "Name: " + trainer.getName();

        mTextViewTrainerSpeciality.setText(speciality);
        mTextViewTrainerExperience.setText(experience);
        mTextViewTrainerWeight.setText(weight);
        mTextViewTrainerHeight.setText(height);
        mTrainerName.setText(name);


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
        mTrainer = trainer;

        // If there is no current trainer, hide the bottom sheet.
        if (trainer == null) {
            bottomSheet.setVisibility(View.GONE);
            return;
        }

        // Set up the details in the sheet.
        setupTrainerDetailView(trainer);

        // Initialize to collapsed state and make it visible.
        mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        bottomSheet.setVisibility(View.VISIBLE);
    }

    public void selectDrawerItem(MenuItem currentMenuItem) {
        // Create a new fragment and specify the planet to show based on
        // position
        Fragment fragment = null;

        Class fragmentClass;
        switch(currentMenuItem.getItemId()) {
            case R.id.nav_first_fragment:
                fragmentClass = WeeksListFragment.class;
                fragment = mWeeksListFragment;
                break;
            case R.id.nav_second_fragment:
                fragmentClass = FindTrainerFragment.class;
                fragment = mFindTrainerFragment;
                bottomSheet.setVisibility(View.GONE);
                break;
            case R.id.nav_third_fragment:
                fragmentClass = CheckMyFormFragment.class;
                fragment = mCheckMyFormFragment;
                break;

            default:
                fragmentClass = WeeksListFragment.class;
                fragment = mWeeksListFragment;
                break;
        }

        showMapSpecificElements(currentMenuItem.getItemId() == R.id.nav_second_fragment);
        showFab(currentMenuItem.getItemId() == R.id.nav_third_fragment);

        // Insert the fragment by replacing any existing fragment
        fragmentManager = getSupportFragmentManager();
        if (fragmentClass == WeekFragment.class) {
            fragmentManager.beginTransaction().add(R.id.flContent, fragment).addToBackStack("week").commit();
        } else {
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        }

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

    public void showMapSpecificElements(Boolean show) {

        // Show or hide the search bar.
        MenuItem search = mMenu.findItem(R.id.action_search);
        search.setVisible(show);

        // Show or hide the bottomSheet.
        if (!show) {
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
        fragmentManager.beginTransaction().add(R.id.flContent, WeekFragment.newInstance(week)).addToBackStack("week").commit();
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

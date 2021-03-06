package com.codepath.apps.learnfitness.activities;


import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.util.ExponentialBackOff;

import com.bumptech.glide.Glide;
import com.codepath.apps.learnfitness.R;
import com.codepath.apps.learnfitness.fragments.CheckMyFormFragment;
import com.codepath.apps.learnfitness.fragments.ComposeFormMessageFragment;
import com.codepath.apps.learnfitness.fragments.FABHideBehavior;
import com.codepath.apps.learnfitness.fragments.FindTrainerFragment;
import com.codepath.apps.learnfitness.fragments.MyFormMessageListFragment;
import com.codepath.apps.learnfitness.fragments.WeekFragment;
import com.codepath.apps.learnfitness.fragments.WeeksListFragment;
import com.codepath.apps.learnfitness.gcm.RegistrationIntentService;
import com.codepath.apps.learnfitness.models.Lesson;
import com.codepath.apps.learnfitness.models.MyFormMessage;
import com.codepath.apps.learnfitness.models.Trainer;
import com.codepath.apps.learnfitness.models.Week;
import com.codepath.apps.learnfitness.rest.MediaStoreService;
import com.codepath.apps.learnfitness.util.Utils;
import com.codepath.apps.learnfitness.util.VideoUtility;
import com.codepath.apps.learnfitness.youtubeupload.Auth;
import com.daimajia.numberprogressbar.NumberProgressBar;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelSlideListener;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState;

import android.Manifest;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
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
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LessonListActivity extends AppCompatActivity
        implements WeeksListFragment.OnItemSelectedListener,
        MyFormMessageListFragment.OnMyFormMessagesListener,
        ComposeFormMessageFragment.OnFormMessageListener {

    private static final String TAG = "LessonListActivity";

    public static final String MY_SHARED_PREFS = "MY_SHARED_PREFS4";
    public static final String CURRENT_WEEK_NUMBER = "CURRENT_WEEK_NUMBER";
    public static final int BOTTOM_SHEET_IMAGE_MARGIN = 700;

    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawer;

    @Bind(R.id.nvView)
    NavigationView mNavigation;

    @Bind(R.id.rlBottomSheet)
    RelativeLayout rlBottomSheet;

    @Bind(R.id.ivTrainerPhoto)
    ImageView mImageViewTrainerPhoto;

    @Bind(R.id.tvTrainerSpeciality)
    TextView mTextViewTrainerSpeciality;

    @Bind(R.id.tvTrainerExperience)
    TextView mTextViewTrainerExperience;

    @Bind(R.id.tvTrainerAddressLine1)
    TextView mTextViewTrainerAddressLine1;

    @Bind(R.id.tvTrainerAddressLine2)
    TextView mTextViewTrainerAddressLine2;

    @Bind(R.id.tvTrainerCall)
    TextView mTextViewTrainerCall;

    @Bind(R.id.tvTrainerName)
    TextView mTrainerName;

    @Bind(R.id.rlTrainerCall)
    RelativeLayout rlTrainerCall;

    @Bind(R.id.llTrainerPeakInfo)
    RelativeLayout mTrainerPeakInfo;

    @Bind(R.id.plSlidingPanel)
    SlidingUpPanelLayout plSlidingPanel;

    @Bind(R.id.ivSpecialtyIcon)
    ImageView mSpecialtyIcon;

    @Bind(R.id.ivWhiteSpecialtyIcon)
    ImageView mWhiteSpecialtyIcon;

    MenuItem miActionProgressItem;
    public static FragmentManager mFragmentManager;
    private ActionBarDrawerToggle drawerToggle;
    private Toolbar toolbar;
    private Menu mMenu;
    private FindTrainerFragment mFindTrainerFragment;
    private CheckMyFormFragment mCheckMyFormFragment;
    private MyFormMessageListFragment mMyFormMessageListFragment;
    private ComposeFormMessageFragment mComposeFormMessageFragment;
    private Trainer mTrainer;
    private WeeksListFragment mWeeksListFragment;
    private List<Week> mWeeks;


    private Uri mVideoRecordFileURI = null;
    public static final String ACCOUNT_KEY = "accountName";
    public static final String FORM_INFO = "formInfo";
    public static final String RECEIVER = "receiver";
    public static final String FORM_ID = "formId";
    private static final String HEADER_CONTENT_TYPE_JSON = "application/json";

    static final String REQUEST_AUTHORIZATION_INTENT = "com.google.example.yt.RequestAuth";
    static final String REQUEST_AUTHORIZATION_INTENT_PARAM = "com.google.example.yt.RequestAuth.param";
    private static final int REQUEST_GOOGLE_PLAY_SERVICES = 0;
    private static final int REQUEST_GMS_ERROR_DIALOG = 1;
    private static final int REQUEST_ACCOUNT_PICKER = 2;
    private static final int REQUEST_ACCOUNT_PICKER_FROM_WEEK_DETAILS = 22;
    private static final int REQUEST_AUTHORIZATION = 3;
    private static final int RESULT_VIDEO_CAP = 5;
    private static final int REQUEST_TAKE_GALLERY_VIDEO = 7;
    GoogleAccountCredential credential;
    private UploadBroadcastReceiver broadcastReceiver;
    private String mChosenAccountName;
    LessonListActivityReceiver mLessonListActivityReceiver;

    NumberProgressBar numberProgressBar;
    private final int UPDATE_CODE_PROGRESSING = 99;
    private final int UPDATE_CODE_DONE = 100;
    private final int JUST_HIDE = 101;
    private static final int LESSON_GROUP = 0;
    private static final int FIND_TRAINER_GROUP = 1;
    private static final int CHECK_FORM_GROUP = 2;

    private static final int LESSON_LIST_ID = 5;
    private static final int FIND_TRAINER_ID = 7;
    private static final int CHECK_MY_FORM_ID = 8;
    private static final int FEEDBACK_ID = 9;
    private static final int REQUEST_FEEDBACK_ID = 10;

    private static final int LESSON_MIN_INDEX = 1000;

    AppBarLayout.LayoutParams mAppBarLayoutParams;
    FloatingActionButton mFab;
    Week currentSelectedWeek;
    boolean chooseAccountFromMessageBoard = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_list);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        mAppBarLayoutParams = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
        getSupportFragmentManager().addOnBackStackChangedListener(getListener());

        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setVisibility(View.GONE);
        mFab.setVisibility(View.GONE);
        CoordinatorLayout.LayoutParams p = (CoordinatorLayout.LayoutParams) mFab.getLayoutParams();
        p.setBehavior(new FABHideBehavior(getApplicationContext(), null));
        mFab.setLayoutParams(p);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCheckMyFormDialog();

            }
        });

        //gcm
        launchClientRegistrationService();
        // youtube account info for upload service
        setupServiceReceiver();
        credential = GoogleAccountCredential.usingOAuth2(
                getApplicationContext(), Arrays.asList(Auth.SCOPES));

        mFragmentManager = getSupportFragmentManager();

        //set exponential backoff policy
        credential.setBackOff(new ExponentialBackOff());

        if (savedInstanceState != null) {
            mChosenAccountName = savedInstanceState.getString(ACCOUNT_KEY);
        } else {
            loadAccount();
        }

        credential.setSelectedAccountName(mChosenAccountName);

        drawerToggle = setUpDrawerToggle();

        mDrawer.addDrawerListener(drawerToggle);

//        setUpDrawerContent(mNavigation);

        mFindTrainerFragment = new FindTrainerFragment();
        mMyFormMessageListFragment = new MyFormMessageListFragment();
        mWeeksListFragment = new WeeksListFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flContent, mWeeksListFragment)
                .commit();
        Log.d(TAG, "Should have just instantiated the WeeksListFragment");

        setTitle(R.string.title_activity_lesson_list);

        setUpBottomSheet();

        setUpUploadProgressBar();
        fetchWeekContent();
    }

    private void setUpUploadProgressBar() {
        numberProgressBar = (NumberProgressBar) findViewById(R.id.number_progress_bar);
    }

    public List<Week> getWeeks() {
        return mWeeks;
    }

    private void fetchWeekContent() {
        Log.d(TAG, "fetching week content");
        mWeeks = new LinkedList<>();

        mWeeks.clear();
        showProgressBar(true);

        final Observable<Lesson> call = MediaStoreService.contentStore.fetchContent();

        call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<Lesson>() {
                @Override
                public void onCompleted() {
                    Log.d(TAG, "Api call success");
                }

                @Override
                public void onError(Throwable e) {
                    // cast to retrofit.HttpException to get the response code
                    Log.d(TAG, "in error");
                    Log.d(TAG, e.toString());

                    if (e instanceof HttpException) {
                        HttpException response = (HttpException) e;
                        int code = response.code();
                    }

                    call.retry();
                }

                @Override
                public void onNext(Lesson lesson) {
                    Log.d(TAG, "adding all the weeks now");
                    mWeeks.addAll(lesson.getWeeks());
                    SharedPreferences sharedPreferences =
                            getSharedPreferences(LessonListActivity.MY_SHARED_PREFS,
                                    Context.MODE_PRIVATE);

                    int currentWeekNumber =
                            Integer.parseInt(sharedPreferences.getString(
                                    LessonListActivity.CURRENT_WEEK_NUMBER, "2")) - 1;
                    if (currentWeekNumber >= 0 && currentWeekNumber < mWeeks.size()) {
                        Week currentWeek = mWeeks.get(currentWeekNumber);
                        currentWeek.setIsCurrent(true);
                    } else {
                        Week currentWeek = mWeeks.get(0);
                        currentWeek.setIsCurrent(true);
                    }

                    mWeeksListFragment.notifyWeeksChanged();
                    showProgressBar(false);

                    setUpDrawerContent(mNavigation);

                }
            });

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        // Store instance of the menu item containing progress
        miActionProgressItem = menu.findItem(R.id.miActionProgress);
        // Extract the action-view from the menu item
        ProgressBar v = (ProgressBar) MenuItemCompat.getActionView(miActionProgressItem);
        // Return to finish
        return super.onPrepareOptionsMenu(menu);
    }

    private void setUpBottomSheet() {
        Log.d(TAG, "setUpBottomSheet");

        // Indicate that the bottom panel should be completely on top of the background.
        // This gets rid of the initial wiggle of the map.
        plSlidingPanel.setOverlayed(true);

        // Add a listen for position changes of the bottom panel.
        plSlidingPanel.addPanelSlideListener(new PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                int color;
                int textColor;
                if (slideOffset <= 0.005) {
                    color = R.color.white;
                    textColor = R.color.text_dark;
                } else {
                    color = R.color.primary;
                    textColor = R.color.white;
                }

                if (slideOffset >= 0) {
                    RelativeLayout.LayoutParams boxMargins = new RelativeLayout
                            .LayoutParams(mTrainerPeakInfo.getLayoutParams());
                    boxMargins.topMargin = (int) (BOTTOM_SHEET_IMAGE_MARGIN * slideOffset);
                    mTrainerPeakInfo.setLayoutParams(boxMargins);
                    mWhiteSpecialtyIcon.setVisibility(View.VISIBLE);
                    mSpecialtyIcon.setVisibility(View.GONE);
                }

                mTrainerPeakInfo.setBackgroundColor(ContextCompat.
                        getColor(LessonListActivity.this, color));
                int parsedTextColor = ContextCompat.
                        getColor(LessonListActivity.this, textColor);
                mTrainerName.setTextColor(parsedTextColor);
                mTextViewTrainerSpeciality.setTextColor(parsedTextColor);
                mTextViewTrainerExperience.setTextColor(parsedTextColor);
            }

            @Override
            public void onPanelStateChanged(View panel,
                                            SlidingUpPanelLayout.PanelState previousState,
                                            SlidingUpPanelLayout.PanelState newState) {
                Log.d(TAG, "Bottom panel state changing");
                int color;
                int textColor;

                if (newState == PanelState.COLLAPSED) {
                    color = R.color.white;
                    textColor = R.color.text_dark;
                    mTrainerPeakInfo.setBackgroundColor(ContextCompat.
                            getColor(LessonListActivity.this, color));
                    int parsedTextColor = ContextCompat.
                            getColor(LessonListActivity.this, textColor);
                    mTrainerName.setTextColor(parsedTextColor);
                    mTextViewTrainerSpeciality.setTextColor(parsedTextColor);
                    mWhiteSpecialtyIcon.setVisibility(View.GONE);
                    mSpecialtyIcon.setVisibility(View.VISIBLE);
                }
            }
        });

        rlTrainerCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTrainer != null) {
                    Intent i = new Intent(Intent.ACTION_CALL);
                    i.setData(Uri.parse("tel:" + mTrainer.getPhone()));
                    if (ActivityCompat.checkSelfPermission(LessonListActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    startActivity(i);

                }
            }
        });
    }

    private void setupTrainerDetailView(Trainer trainer) {

        String speciality =  "Specialty: " +
                trainer.getTrainerParams().getSpeciality();
        String experience = "Years Experience: " +
                trainer.getTrainerParams().getYrsOfTraining();

        String name = trainer.getName();

        mTextViewTrainerSpeciality.setText(speciality);
        mTextViewTrainerExperience.setText(experience);
        mTrainerName.setText(name);

        switch (trainer.getTrainerParams().getSpeciality()) {
            case "Cardio":
                mWhiteSpecialtyIcon.setImageResource(R.drawable.cardio_white_icon);
                mSpecialtyIcon.setImageResource(R.drawable.cardio_icon);
                break;
            case "Cross Fit":
                mWhiteSpecialtyIcon.setImageResource(R.drawable.yoga_white_icon);
                mSpecialtyIcon.setImageResource(R.drawable.yoga_icon);
                break;
            case "Lifting":
                mWhiteSpecialtyIcon.setImageResource(R.drawable.lifting_white_icon);
                mSpecialtyIcon.setImageResource(R.drawable.lifting_icon);
                break;
            case "Yoga":
                mWhiteSpecialtyIcon.setImageResource(R.drawable.yoga_white_icon);
                mSpecialtyIcon.setImageResource(R.drawable.yoga_icon);
                break;
            default:
                mWhiteSpecialtyIcon.setImageResource(R.drawable.cardio_white_icon);
                mSpecialtyIcon.setImageResource(R.drawable.cardio_icon);
                break;

        }

        mWhiteSpecialtyIcon.setVisibility(View.GONE);

        Glide.with(LessonListActivity.this)
                .load(trainer.getProfileUrl())
                .placeholder(R.drawable.fitness_placeholder)
                .into(mImageViewTrainerPhoto);

        mTextViewTrainerAddressLine1.setText(trainer.getAddress().getFirstAddressLine());
        mTextViewTrainerAddressLine2.setText(trainer.getAddress().getSecondAddressLine());
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

    public void showProgressBar(boolean show) {
        if (miActionProgressItem != null) {
            miActionProgressItem.setVisible(show);
        }
    }

    private ActionBarDrawerToggle setUpDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open,
                R.string.drawer_close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                // When the drawer is opened, hide the bottom panel.
                plSlidingPanel.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
                Utils.hideKeyboard(LessonListActivity.this);
            }
        };
    }

    private void setUpDrawerContent(NavigationView navigationView) {
        Menu menu = navigationView.getMenu();


        // Add the Lesson group and each lesson.
        MenuItem lessons = menu.add(LESSON_GROUP, LESSON_LIST_ID, 0, "Lessons");
        lessons.setIcon(R.drawable.lessons_icon);
        lessons.setChecked(true);

        for (int i = 0; i < mWeeks.size(); i++) {
            Week week = mWeeks.get(i);
            menu.add(LESSON_GROUP, i + LESSON_MIN_INDEX, 0, week.getWeekTitle());
        }

        // Add the Find a Trainer link
        MenuItem trainerItem = menu.add(FIND_TRAINER_GROUP, FIND_TRAINER_ID, 0, "Find a Trainer");
        trainerItem.setIcon(R.drawable.trainer_icon);

        // Add the check my form links.
        MenuItem checkForm = menu.add(CHECK_FORM_GROUP, FEEDBACK_ID, 0, "Check My Form");
        checkForm.setIcon(R.drawable.lifting_icon);

//        menu.add(CHECK_FORM_GROUP, FEEDBACK_ID, 1, "Message Board");
        menu.add(CHECK_FORM_GROUP, REQUEST_FEEDBACK_ID, 2, "Request Feedback");

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return false;
                    }
                });
    }

    public void showTrainerInfo(Trainer trainer) {
        mTrainer = trainer;

        // If there is no current trainer, hide the bottom sheet.
        if (trainer == null) {
            plSlidingPanel.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
            return;
        }

        // Set up the details in the sheet.
        setupTrainerDetailView(trainer);

        // Initialize to collapsed state and make it visible.
        plSlidingPanel.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
    }

    public void selectDrawerItem(MenuItem currentMenuItem) {
        // Create a new fragment and specify the planet to show based on
        // position
        Fragment fragment = null;
        String fragmentTag = "";
        Class fragmentClass;
        switch(currentMenuItem.getItemId()) {
            case LESSON_LIST_ID:
                fragment = mWeeksListFragment;
                fragmentTag = "WeeksListFragment";
                break;
            case FIND_TRAINER_ID:
                fragment = mFindTrainerFragment;
                fragmentTag = "FindTrainerFragment";
                break;
            case FEEDBACK_ID:
                fragment = mMyFormMessageListFragment;
                fragmentTag = "MyFormMessageListFragment";
                break;

            case REQUEST_FEEDBACK_ID:
                fragment = mComposeFormMessageFragment;
                fragmentTag = "ComposeFormMessageFragment";
                onCheckMyFormDialog();
//                onFormMessageSelected(null, new MyFormMessage());
                checkCorrectMenuItem(currentMenuItem);
                mDrawer.closeDrawers();
                return;
//                break;
            default:
                int weekIndex = currentMenuItem.getItemId() - LESSON_MIN_INDEX;
                if (weekIndex >= mWeeks.size()) {
                    return;
                }
                onWeekSelected(null, mWeeks.get(weekIndex));
                checkCorrectMenuItem(currentMenuItem);
                mDrawer.closeDrawers();
                return;
        }

        showMapSpecificElements(currentMenuItem.getItemId() == FIND_TRAINER_ID);

        // Insert the fragment by replacing any existing fragment
        mFragmentManager.beginTransaction().replace(R.id.flContent, fragment).addToBackStack(fragmentTag).commit();

        checkCorrectMenuItem(currentMenuItem);

        setTitle(currentMenuItem.getTitle());
        mDrawer.closeDrawers();
    }

    private void checkCorrectMenuItem(MenuItem currentMenuItem) {
        // Highlight the selected item, update the title, and close the drawer
        Menu menu = mNavigation.getMenu();
        for (int i = 0; i < menu.size(); i++) {
            menu.getItem(i).setChecked(false);
        }
        currentMenuItem.setChecked(true);
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
            plSlidingPanel.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
        }
    }

    @Override
    public void onWeekSelected(View itemView, Week week) {
        currentSelectedWeek = week;
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.flContent, WeekFragment.newInstance(week)).addToBackStack("week").commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (broadcastReceiver == null)
            broadcastReceiver = new UploadBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter(
                REQUEST_AUTHORIZATION_INTENT);
        LocalBroadcastManager.getInstance(this).registerReceiver(
                broadcastReceiver, intentFilter);
    }


    @Override
    public void onCheckMyFormDialog() {
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.flContent);
        if (f.getClass().toString().contains("WeekFragment")) {
            checkLoginWeekDetails();
            mComposeFormMessageFragment = ComposeFormMessageFragment.newInstance(currentSelectedWeek);
        } else {
            mComposeFormMessageFragment = ComposeFormMessageFragment.newInstance(null);
        }
        //mComposeFormMessageFragment = ComposeFormMessageFragment.newInstance(currentSelectedWeek);
        mFragmentManager.beginTransaction().add(R.id.flContent, mComposeFormMessageFragment).addToBackStack("ComposeFormMessageFragment").commit();
    }

    @Override
    public void onFormMessageSelected(View itemView, MyFormMessage myFormMessage) {
        CheckMyFormFragment mCheckMyFormFragment = CheckMyFormFragment.newInstance(myFormMessage);
        mFragmentManager.beginTransaction().add(R.id.flContent, mCheckMyFormFragment).addToBackStack("CheckMyFormFragment").commit();
    }

    @Override
    public void onRecordVideo(View view) {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        // start the Video Capture Intent
        startActivityForResult(intent, RESULT_VIDEO_CAP);
    }

    @Override
    public void startUpload(MyFormMessage myFormMessage) {
        if (mChosenAccountName == null) {
            chooseAccount();
        }

        if (mVideoRecordFileURI != null) {
            showUploadProgressBar();
            Intent uploadIntent = new Intent(this, UploadService.class);
            uploadIntent.setData(mVideoRecordFileURI);
            uploadIntent.putExtra(ACCOUNT_KEY, mChosenAccountName);
            uploadIntent.putExtra(FORM_INFO, myFormMessage);
            uploadIntent.putExtra(RECEIVER, mLessonListActivityReceiver);
            startService(uploadIntent);
            Toast.makeText(this, R.string.youtube_upload_started,
                    Toast.LENGTH_LONG).show();
        }

        mFragmentManager.beginTransaction().remove(mComposeFormMessageFragment).commit();
//        mFragmentManager.beginTransaction().replace(R.id.flContent, mMyFormMessageListFragment)
//                .addToBackStack("MyFormMessageListFragment").commit();
        mFragmentManager.popBackStack();
        coordinatorReConfig();
    }

    @Override
    public void startPostWithoutVideo(MyFormMessage myFormMessage) {
        Observable<MyFormMessage> call =
            MediaStoreService.formsMessagesStore.postMyFormMessage(HEADER_CONTENT_TYPE_JSON,
                    myFormMessage);

            call
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<MyFormMessage>() {
                        @Override
                        public void onCompleted() {
                            Log.i(TAG, "POST form call success");
                        }

                        @Override
                        public void onError(Throwable e) {
                            // cast to retrofit.HttpException to get the response code
                            Log.i(TAG, "in error");
                            Log.i(TAG, e.toString());

                            if (e instanceof HttpException) {
                                HttpException response = (HttpException) e;
                                int code = response.code();
                                Log.i(TAG, "Http error code: " + code);
                            }
                        }

                        @Override
                        public void onNext(MyFormMessage myFormMessage) {
                            Log.i(TAG, myFormMessage.getId());
                        }
                    });

        mFragmentManager.beginTransaction().remove(mComposeFormMessageFragment).commit();
//        mFragmentManager.beginTransaction().replace(R.id.flContent, mMyFormMessageListFragment)
//                .addToBackStack("MyFormMessageListFragment").commit();
        mFragmentManager.popBackStack();
        coordinatorReConfig();
    }

    @Override
    public void composeMessageCancel() {
        mFragmentManager.beginTransaction().remove(mComposeFormMessageFragment).commit();
//        mFragmentManager.beginTransaction().replace(R.id.flContent, mMyFormMessageListFragment)
//                .addToBackStack("MyFormMessageListFragment").commit();
        mFragmentManager.popBackStack();
        coordinatorReConfig();
    }

    @Override
    public void launchVideoGallery() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Video"), REQUEST_TAKE_GALLERY_VIDEO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, "onActivityResult");
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_GMS_ERROR_DIALOG:
                break;
            case RESULT_VIDEO_CAP:
                if (resultCode == RESULT_OK) {
                    Log.i(TAG, "back in onActivityResult");
                    mVideoRecordFileURI = data.getData();
                    if (mVideoRecordFileURI != null) {
                        Log.i(TAG, "Video path: " +
                                VideoUtility.getRealPathFromURI(getApplicationContext(), mVideoRecordFileURI));
                        mComposeFormMessageFragment.setRecordedVideoUrl(
                                VideoUtility.getRealPathFromURI(getApplicationContext(),
                                        mVideoRecordFileURI));

                        mComposeFormMessageFragment.showVideo(mVideoRecordFileURI);
                    }
                }
                break;

            case REQUEST_GOOGLE_PLAY_SERVICES:
                if (resultCode == Activity.RESULT_OK) {
                    haveGooglePlayServices();
                } else {
                    checkGooglePlayServicesAvailable();
                }
                break;

            case REQUEST_AUTHORIZATION:
                if (resultCode != Activity.RESULT_OK) {
                    chooseAccount();
                }
                break;

            case REQUEST_ACCOUNT_PICKER:
                if (resultCode == Activity.RESULT_OK && data != null
                        && data.getExtras() != null) {
//                    String accountName = data.getExtras().getString(
//                            AccountManager.KEY_ACCOUNT_NAME);
//                    if (accountName != null) {
//                        mChosenAccountName = accountName;
//                        credential.setSelectedAccountName(accountName);
//                        saveAccount();
//                    }
                    saveAccount(resultCode, data);
                    mMyFormMessageListFragment.showData();
                }
                break;

            case REQUEST_ACCOUNT_PICKER_FROM_WEEK_DETAILS:
                if (resultCode == Activity.RESULT_OK && data != null
                        && data.getExtras() != null) {
                    saveAccount(resultCode, data);
                    //mMyFormMessageListFragment.showData();
                }
                break;

            case REQUEST_TAKE_GALLERY_VIDEO:
                if (resultCode == RESULT_OK) {
                    mVideoRecordFileURI = data.getData();
                    // OI FILE Manager
                    if (mVideoRecordFileURI != null) {
                        Log.i(TAG, "Selected video path" +
                                VideoUtility.getRealPathFromURI(getApplicationContext(), mVideoRecordFileURI));
                        // MEDIA GALLERY
                        mComposeFormMessageFragment.setRecordedVideoUrl(
                                VideoUtility.getRealPathFromURI(getApplicationContext(), mVideoRecordFileURI));
                        mComposeFormMessageFragment.showVideo(mVideoRecordFileURI);
                    }
                }
                break;
            }
    }
    /**
     * Store the account name that was chosen as a login.
     */
    private void saveAccount(int resultCode, Intent data){

        if (resultCode == Activity.RESULT_OK && data != null
                && data.getExtras() != null) {
            String accountName = data.getExtras().getString(
                    AccountManager.KEY_ACCOUNT_NAME);
            if (accountName != null) {
                mChosenAccountName = accountName;
                credential.setSelectedAccountName(accountName);
            }
            SharedPreferences sp = PreferenceManager
                    .getDefaultSharedPreferences(this);
            sp.edit().putString(ACCOUNT_KEY, mChosenAccountName).commit();
        }
    }

    private void loadAccount() {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(this);
        mChosenAccountName = sp.getString(ACCOUNT_KEY, null);
        invalidateOptionsMenu();
    }

    /**
     * Check if an account has been chosen, and launch the account picker if not.
     */
    public void checkLogin() {
        if (mChosenAccountName == null) {
            chooseAccount();
        } else {
            Log.d(TAG, "Don't need to choose an account");
            mMyFormMessageListFragment.showData();
        }
    }

    public void checkLoginWeekDetails() {
        if (mChosenAccountName == null) {
            startActivityForResult(credential.newChooseAccountIntent(),
                    REQUEST_ACCOUNT_PICKER_FROM_WEEK_DETAILS);
        }
    }

    /**
     * Store the account name that was chosen as a login.
     */
//    private void saveAccount() {
//        SharedPreferences sp = PreferenceManager
//                .getDefaultSharedPreferences(this);
//        sp.edit().putString(ACCOUNT_KEY, mChosenAccountName).commit();
//    }

    /**
     * Launch the google account picker.
     */
    private void chooseAccount() {
        startActivityForResult(credential.newChooseAccountIntent(),
                REQUEST_ACCOUNT_PICKER);
    }


    /**
     * Check that Google Play services APK is installed and up to date.
     */
    private boolean checkGooglePlayServicesAvailable() {
        final int connectionStatusCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (GooglePlayServicesUtil.isUserRecoverableError(connectionStatusCode)) {
            showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode);
            return false;
        }
        return true;
    }
    public void showGooglePlayServicesAvailabilityErrorDialog(
            final int connectionStatusCode) {
        runOnUiThread(new Runnable() {
            public void run() {
                Dialog dialog = GooglePlayServicesUtil.getErrorDialog(
                        connectionStatusCode, LessonListActivity.this,
                        REQUEST_GOOGLE_PLAY_SERVICES);
                dialog.show();
            }
        });
    }

    private void haveGooglePlayServices() {
        // check if there is already an account selected
        if (credential.getSelectedAccountName() == null) {
            // ask user to choose account
            chooseAccount();
        }
    }

    private class UploadBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(REQUEST_AUTHORIZATION_INTENT)) {
                Log.d(TAG, "Request auth received - executing the intent");
                Intent toRun = intent
                        .getParcelableExtra(REQUEST_AUTHORIZATION_INTENT_PARAM);
                startActivityForResult(toRun, REQUEST_AUTHORIZATION);
            }
        }
    }

    // Setup the callback for when data is received from the upload service
    public void setupServiceReceiver() {
        mLessonListActivityReceiver = new LessonListActivityReceiver(new Handler());
        // This is where we specify what happens when data is received from the service
        mLessonListActivityReceiver.setReceiver(new LessonListActivityReceiver.Receiver() {
            @Override
            public void onReceiveResult(int resultCode, Bundle resultData) {
                if (resultCode == RESULT_OK) {
                    String resultValue = resultData.getString("resultValue");
                    //Toast.makeText(LessonListActivity.this, resultValue, Toast.LENGTH_SHORT).show();
                    if (numberProgressBar != null) {
                        for(int i = 0; i <= 100; i++) {
                            if (numberProgressBar.getProgress() >= numberProgressBar.getMax()) {
                                numberProgressBar.setVisibility(View.GONE);
                                Snackbar.make(findViewById(R.id.cl_lessonlist), R.string.snackbar_form_post_complete,
                                        Snackbar.LENGTH_LONG)
                                        .show(); // Don’t forget to show!
                                break;
                            }
                            numberProgressBar.incrementProgressBy(1);
                        }
                    }
                }
            }

            @Override
            public void onProgressUpdate(int resultCode, Bundle resultData) {
                if (resultCode == UPDATE_CODE_PROGRESSING) {
                    String resultValue = resultData.getString("resultValue");
                    Log.i(TAG, "Update progress to " + resultValue);
                    updateVideoUploadProgress(Integer.parseInt(resultValue));

                } else if (resultCode == UPDATE_CODE_DONE) {
                    doneUploadProgress();
                } else if (resultCode == JUST_HIDE) {
                    hideUploadProgressBar();
                }
            }
        });
    }

    private void launchClientRegistrationService() {
        Intent gcmRegIntent = new Intent(this, RegistrationIntentService.class);
        startService(gcmRegIntent);
    }

    public static class MapViewHolder {
        static View mView;
        public static void StoreMapView(View view) {
            mView = view;
        }
        public static View getView() {
            return mView;
        }
    }

    private FragmentManager.OnBackStackChangedListener getListener()
    {
        FragmentManager.OnBackStackChangedListener result = new FragmentManager.OnBackStackChangedListener()
        {
            public void onBackStackChanged()
            {
                coordinatorReConfig();
            }
        };

        return result;
    }

    public void updateVideoUploadProgress(int progressUnit) {
        if (numberProgressBar != null && progressUnit < 100) {
            Log.i(TAG, "Progress unit: " + progressUnit);

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (numberProgressBar.getProgress() >= numberProgressBar.getMax()) {
                                numberProgressBar.setVisibility(View.GONE);
                            }
                            numberProgressBar.incrementProgressBy(1);
                        }
                    });
                }
            }, 1000, progressUnit);
        }
    }

    public void doneUploadProgress() {
        if (numberProgressBar != null) {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (numberProgressBar.getProgress() >= numberProgressBar.getMax()) {
                                numberProgressBar.setVisibility(View.GONE);
                            }
                            numberProgressBar.incrementProgressBy(1);
                        }
                    });
                }
            }, 1000, 100);
        }
    }

    public void showUploadProgressBar() {
        if (numberProgressBar != null) {

            for (int i = 0; i < 5; i++) {
                numberProgressBar.incrementProgressBy(1);
            }
            numberProgressBar.setVisibility(View.VISIBLE);
        }
    }

    public void hideUploadProgressBar() {
        if (numberProgressBar != null) {
            numberProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.i(TAG, "Inside onNewIntent");
        MyFormMessage myFormMessage = intent.getParcelableExtra("formMessage");
        CheckMyFormFragment mCheckMyFormFragment = CheckMyFormFragment.newInstance(myFormMessage);
        mFragmentManager.beginTransaction().add(R.id.flContent, mCheckMyFormFragment).addToBackStack("CheckMyFormFragment").commit();
    }

    private void coordinatorReConfig() {
        FragmentManager manager = getSupportFragmentManager();

        if (manager != null) {
            Fragment f = getSupportFragmentManager().findFragmentById(R.id.flContent);
            Log.i(TAG, "Current fragment is: " + f.getClass());

            if (f.getClass().toString().contains("WeeksListFragment") ||
                    f.getClass().toString().contains("MyFormMessageListFragment")) {
                mAppBarLayoutParams.setScrollFlags(
                        AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS |
                                AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL |
                                AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP);
                toolbar.setLayoutParams(mAppBarLayoutParams);
                Log.i(TAG, "Toolbar scrolling: " + f.getClass());

            } else if (f.getClass().toString().contains("CheckMyFormFragment") ||
                    f.getClass().toString().contains("ComposeFormMessageFragment") ||
                    f.getClass().toString().contains("WeekFragment") ||
                    f.getClass().toString().contains("FindTrainerFragment")) {
                mAppBarLayoutParams.setScrollFlags(
                        AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
                toolbar.setLayoutParams(mAppBarLayoutParams);

                Log.i(TAG, "Toolbar stop scrolling: " + f.getClass());
            }

            if (f.getClass().toString().contains("MyFormMessageListFragment") ||
                    f.getClass().toString().contains("WeekFragment")) {
                mFab.setVisibility(View.VISIBLE);
            } else {
                mFab.setVisibility(View.GONE);
                CoordinatorLayout.LayoutParams p = (CoordinatorLayout.LayoutParams) mFab.getLayoutParams();
                p.setBehavior(new FABHideBehavior(getApplicationContext(), null));
                mFab.setLayoutParams(p);
            }

            f.onResume();
        }
    }
}
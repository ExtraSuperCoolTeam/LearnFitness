package com.codepath.apps.learnfitness.activities;


import android.accounts.AccountManager;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
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
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codepath.apps.learnfitness.R;
import com.codepath.apps.learnfitness.fragments.CheckMyFormFragment;
import com.codepath.apps.learnfitness.fragments.ComposeFormMessageFragment;
import com.codepath.apps.learnfitness.fragments.FindTrainerFragment;
import com.codepath.apps.learnfitness.fragments.WeekFragment;
import com.codepath.apps.learnfitness.fragments.WeeksListFragment;
import com.codepath.apps.learnfitness.models.Form;
import com.codepath.apps.learnfitness.models.Trainer;
import com.codepath.apps.learnfitness.models.Week;
import com.codepath.apps.learnfitness.rest.MediaStoreService;
import com.codepath.apps.learnfitness.util.VideoUtility;
import com.codepath.apps.learnfitness.youtubeupload.Auth;
import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.ExponentialBackOff;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelSlideListener;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LessonListActivity extends AppCompatActivity
        implements WeeksListFragment.OnItemSelectedListener,
        CheckMyFormFragment.OnCheckMyFormListener,
        ComposeFormMessageFragment.OnFormMessageListener {
    private static final String TAG = "LessonListActivity";

    public static final String MY_SHARED_PREFS = "MY_SHARED_PREFS4";
    public static final String CURRENT_WEEK_NUMBER = "CURRENT_WEEK_NUMBER";

    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawer;

    @Bind(R.id.nvView)
    NavigationView mNavigation;

    @Bind(R.id.fab)
    FloatingActionButton mFab;

    @Bind(R.id.rlBottomSheet)
    RelativeLayout rlBottomSheet;

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

    @Bind(R.id.plSlidingPanel)
    SlidingUpPanelLayout plSlidingPanel;

    private ActionBarDrawerToggle drawerToggle;
    private Toolbar toolbar;
    public static FragmentManager fragmentManager;
    private Menu mMenu;
    private FindTrainerFragment mFindTrainerFragment;
    private CheckMyFormFragment mCheckMyFormFragment;
    ComposeFormMessageFragment mComposeFormMessageFragment;
    private Trainer mTrainer;
    private WeeksListFragment mWeeksListFragment;

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
    private static final int REQUEST_AUTHORIZATION = 3;
    private static final int RESULT_PICK_IMAGE_CROP = 4;
    private static final int RESULT_VIDEO_CAP = 5;
    private static final int REQUEST_DIRECT_TAG = 6;
    final HttpTransport transport = AndroidHttp.newCompatibleTransport();
    final JsonFactory jsonFactory = new GsonFactory();
    GoogleAccountCredential credential;
    private UploadBroadcastReceiver broadcastReceiver;
    private String mChosenAccountName;
    private Uri mFileURI = null;
    LessonListActivityReceiver mLessonListActivityReceiver;
    private Subscription subscription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_list);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        // youtube account info for upload service
        setupServiceReceiver();
        credential = GoogleAccountCredential.usingOAuth2(
                getApplicationContext(), Arrays.asList(Auth.SCOPES));

        // set exponential backoff policy
        credential.setBackOff(new ExponentialBackOff());

        if (savedInstanceState != null) {
            mChosenAccountName = savedInstanceState.getString(ACCOUNT_KEY);
        } else {
            loadAccount();
        }

        credential.setSelectedAccountName(mChosenAccountName);

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

        // Indicate that the bottom panel should be completely on top of the background.
        // This gets rid of the initial wiggle of the map.
        plSlidingPanel.setOverlayed(true);

        // Add a listen for position changes of the bottom panel.
        plSlidingPanel.addPanelSlideListener(new PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
//                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams();
//
//                mImageViewTrainerPhoto.setLayoutParams(layoutParams);
//                mImageViewTrainerPhoto.
                mImageViewTrainerPhoto.setVisibility(View.GONE);
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                Log.d(TAG, "State changing");
                int color;
                int textColor;
                if (newState == PanelState.DRAGGING || newState == PanelState.EXPANDED) {
                    color = R.color.primary_dark;
                    textColor = R.color.white;
                } else {
                    color = R.color.white;
                    textColor = R.color.text_dark;

                }
                mTrainerPeakInfo.setBackgroundColor(ContextCompat.
                        getColor(LessonListActivity.this, color));
                int parsedTextColor = ContextCompat.
                        getColor(LessonListActivity.this, textColor);
                mTrainerName.setTextColor(parsedTextColor);
                mTextViewTrainerSpeciality.setTextColor(parsedTextColor);

                //Put into styles the different text color
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
                R.string.drawer_close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                // When the drawer is opened, hide the bottom panel.
                plSlidingPanel.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
            }
        };
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

        Class fragmentClass;
        switch(currentMenuItem.getItemId()) {
            case R.id.nav_first_fragment:
                fragmentClass = WeeksListFragment.class;
                fragment = mWeeksListFragment;
                break;
            case R.id.nav_second_fragment:
                fragmentClass = FindTrainerFragment.class;
                fragment = mFindTrainerFragment;
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
            plSlidingPanel.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
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

        if (broadcastReceiver == null)
            broadcastReceiver = new UploadBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter(
                REQUEST_AUTHORIZATION_INTENT);
        LocalBroadcastManager.getInstance(this).registerReceiver(
                broadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppEventsLogger.deactivateApp(this);
    }

    @Override
    public void onCheckMyFormDialog() {
        mFab.setVisibility(View.GONE);
        mComposeFormMessageFragment = ComposeFormMessageFragment.newInstance();
        //mComposeFormMessageFragment.show(getSupportFragmentManager(), ComposeFormMessageFragment.TAG);
        fragmentManager.beginTransaction().replace(R.id.flContent, mComposeFormMessageFragment).commit();
    }

    @Override
    public void onRecordVideo(View view) {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        // start the Video Capture Intent
        startActivityForResult(intent, RESULT_VIDEO_CAP);
    }

    @Override
    public void startUpload(Form form) {
        if (mVideoRecordFileURI != null) {
            Intent uploadIntent = new Intent(this, UploadService.class);
            uploadIntent.setData(mVideoRecordFileURI);
            uploadIntent.putExtra(ACCOUNT_KEY, mChosenAccountName);
            uploadIntent.putExtra(FORM_INFO, form);
            uploadIntent.putExtra(RECEIVER, mLessonListActivityReceiver);
            startService(uploadIntent);
            Toast.makeText(this, R.string.youtube_upload_started,
                    Toast.LENGTH_LONG).show();
        }

        fragmentManager.beginTransaction().remove(mComposeFormMessageFragment).commit();
        fragmentManager.beginTransaction().replace(R.id.flContent, mCheckMyFormFragment).commit();
        mFab.setVisibility(View.VISIBLE);
    }

    @Override
    public void startPostWithoutVideo(Form form) {
        Observable<Form> call =
            MediaStoreService.formsStore.postFormMessages(HEADER_CONTENT_TYPE_JSON,
                    form);
            subscription = call
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<Form>() {
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
                public void onNext(Form form) {
                    Log.i(TAG, form.getFeedback());
                }
            });

        fragmentManager.beginTransaction().remove(mComposeFormMessageFragment).commit();
        fragmentManager.beginTransaction().replace(R.id.flContent, mCheckMyFormFragment).commit();
        mFab.setVisibility(View.VISIBLE);
    }

    @Override
    public void composeMessageCancel() {
        fragmentManager.beginTransaction().remove(mComposeFormMessageFragment).commit();
        fragmentManager.beginTransaction().replace(R.id.flContent, mCheckMyFormFragment).commit();
        mFab.setVisibility(View.VISIBLE);
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
                    String accountName = data.getExtras().getString(
                            AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null) {
                        mChosenAccountName = accountName;
                        credential.setSelectedAccountName(accountName);
                        saveAccount();
                    }
                }
                break;
            }
    }

    private void loadAccount() {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(this);
        mChosenAccountName = sp.getString(ACCOUNT_KEY, null);
        if (mChosenAccountName == null) {
            chooseAccount();
        }
        invalidateOptionsMenu();
    }

    private void saveAccount() {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(this);
        sp.edit().putString(ACCOUNT_KEY, mChosenAccountName).commit();
    }
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
                    Snackbar.make(findViewById(android.R.id.content), R.string.snackbar_form_post_complete,
                            Snackbar.LENGTH_SHORT)
                            .show(); // Don’t forget to show!
                }
            }
        });
    }
}

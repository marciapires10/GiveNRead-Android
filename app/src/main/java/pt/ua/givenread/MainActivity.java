package pt.ua.givenread;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private static MainActivity instance;
    BookViewModel viewModel;
    final Handler handler = new Handler();
    public Fragment previous_fragment;
    public boolean last_fragment = true;
    BottomNavigationView bottomNavView;
    ActionBar actionBar;

    private static final String[] CAMERA_PERMISSION = new String[]{Manifest.permission.CAMERA};
    private static final int CAMERA_REQUEST_CODE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;
        viewModel = ViewModelProviders.of(this).get(BookViewModel.class);
        viewModel.init();

        bottomNavView = findViewById(R.id.bottomNavigationView);

        bottomNavView.setOnItemSelectedListener(this);
        bottomNavView.setSelectedItemId(R.id.home_opt);

        createNotificationChannel();
        handler.post(runnableCode);

        String menuFragment = getIntent().getStringExtra("menuFragment");
        String bookstop = getIntent().getStringExtra("Bookstop");

        actionBar = getSupportActionBar();

        if(menuFragment != null && bookstop != null){
            if (menuFragment.equals("MapsFragment")){
                Log.d("//////////////////", bookstop);
                Bundle args = new Bundle();
                args.putString("Bookstop", bookstop);
                Log.d("MAINACTIVITY", bookstop);
                mapsFragment.setArguments(args);
                getSupportFragmentManager().beginTransaction().replace(R.id.container, mapsFragment).commit();
            }
            else {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, homepageFragment).commit();

            }
        }

    }

    private final Runnable runnableCode = new Runnable() {
        @Override
        public void run() {
            DataFromFirebase.getAllDataFromFirebase(viewModel, getApplicationContext());

            handler.postDelayed(this, 2000);
        }
    };

    private void createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            String name = "channel_1";
            String description = "notification_channel";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("channel_id_1", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(
                this,
                CAMERA_PERMISSION,
                CAMERA_REQUEST_CODE
        );


    }

    private void enableCamera() {
        BarcodeScannerFragment barcodeScannerFragment = new BarcodeScannerFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, barcodeScannerFragment).commit();

    }

    final HomepageFragment homepageFragment = new HomepageFragment();
    final BooksListFragment booksListFragment = new BooksListFragment();
    final MapsFragment mapsFragment = new MapsFragment();
    final NotificationsFragment notificationsFragment = new NotificationsFragment();


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item){

        switch (item.getItemId()){
            case R.id.home_opt:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, homepageFragment).commit();
                return true;
            case R.id.book_opt:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, booksListFragment).commit();
                return true;
            case R.id.camera_opt:
                requestPermission();
                enableCamera();
                return true;
            case R.id.map_opt:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, mapsFragment).commit();
                return true;
            case R.id.notify_opt:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, notificationsFragment).commit();
                return true;
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();  return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        if(previous_fragment != null)
        {
            Log.d("Previous fragment", previous_fragment.toString());
            getSupportFragmentManager().beginTransaction().replace(R.id.container, previous_fragment).commit();
            if(!last_fragment)
            {
                Log.d("Last fragment","Was not last fragment");
                last_fragment = true;
                previous_fragment = booksListFragment;
            }
            else
            {

                actionBar.setDisplayHomeAsUpEnabled(false);
                previous_fragment = null;
            }
        }
        else if(bottomNavView.getSelectedItemId () != R.id.home_opt)
        {
            Log.d("Previous Fragment","WAS NULL");
            bottomNavView.setSelectedItemId(R.id.home_opt);
        }
        else
        {
            Log.d("Previous Fragment","WAS NULL 2");
            super.onBackPressed();
        }
    }

    public BottomNavigationView getBottomNavView() {
        return bottomNavView;
    }

    public static MainActivity getInstance() {
        return instance;
    }

    public void updateNotificationsNumber(int number)
    {
        bottomNavView.getOrCreateBadge(R.id.notify_opt).getNumber();
        bottomNavView.getOrCreateBadge(R.id.notify_opt).setNumber(bottomNavView.getOrCreateBadge(R.id.notify_opt).getNumber() + number);
    }

    public void showBackButton() {
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
}
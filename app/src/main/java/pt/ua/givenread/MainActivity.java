package pt.ua.givenread;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.nearby.connection.Strategy;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private static MainActivity instance;
    BookViewModel viewModel;
    Handler handler = new Handler();

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

    }

    private Runnable runnableCode = new Runnable() {
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

    private boolean hasCameraPermission() {
        return ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(
                this,
                CAMERA_PERMISSION,
                CAMERA_REQUEST_CODE
        );
    }

    private void enableCamera() {
        //Intent intent = new Intent(this, BarcodeScannerActivity.class);
        //startActivity(intent);
        BarcodeScannerFragment barcodeScannerFragment = new BarcodeScannerFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, barcodeScannerFragment).commit();

    }

    HomepageFragment homepageFragment = new HomepageFragment();
    BooksListFragment booksListFragment = new BooksListFragment();
    MapsFragment mapsFragment = new MapsFragment();
    NotificationsFragment notificationsFragment = new NotificationsFragment();


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
                if (hasCameraPermission()) {
                    enableCamera();
                } else {
                    requestPermission();
                }
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
    public void onBackPressed() {
        if(bottomNavView.getSelectedItemId () != R.id.home_opt)
        {
            bottomNavView.setSelectedItemId(R.id.home_opt);
        }
        else
        {
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

    public void setNotification(String menuFragment, String bookstop){
        Log.d("menu", menuFragment);
        Log.d("mainBookstop", bookstop);
        if(menuFragment != null && bookstop != null){
            if (menuFragment.equals("MapsFragment")){
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

}
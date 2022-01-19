package pt.ua.givenread;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.nearby.connection.Strategy;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavView;
    FloatingActionButton fabNav;

    private static final String[] CAMERA_PERMISSION = new String[]{Manifest.permission.CAMERA};
    private static final int CAMERA_REQUEST_CODE = 10;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavView = findViewById(R.id.bottomNavigationView);

        bottomNavView.setOnItemSelectedListener(this);
        bottomNavView.setSelectedItemId(R.id.home);
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
                return true;
        }
        return false;
    }


}
package pt.ua.givenread;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private static RecyclerView booksRecyclerView;

    private ProgressBar progressBar;
    private EditText searchEdt;
    private ImageButton searchBtn;

    BottomNavigationView bottomNavView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavView = findViewById(R.id.bottomNavigationView);

        bottomNavView.setOnItemSelectedListener(this);
        bottomNavView.setSelectedItemId(R.id.home);
    }

    BookSearchFragment bookSearchFragment = new BookSearchFragment();
    BooksListFragment booksListFragment = new BooksListFragment();
    MapsFragment mapsFragment = new MapsFragment();


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item){
        switch (item.getItemId()){
            case R.id.home_opt:
                return true;
            case R.id.book_opt:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, booksListFragment).commit();
                return true;
            case R.id.camera_opt:
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
package pt.ua.givenread;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private static RecyclerView booksRecyclerView;
    private static BookAdapter booksAdapter;
    private static Context mainContext;

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
    MapsFragment mapsFragment = new MapsFragment();


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item){
        switch (item.getItemId()){
            case R.id.book_opt:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, bookSearchFragment).commit();
                return true;
            case R.id.map_opt:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, mapsFragment).commit();
                return true;
        }
        return false;
    }

}
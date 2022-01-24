package pt.ua.givenread;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

/**public class BookSearchActivity extends AppCompatActivity {

    private static String type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_search);

        if (getIntent().getExtras() != null){
            type = (String) getIntent().getExtras().get("type");
            Log.d("Type", type);
        }

        // Get the FragmentManager and start a transaction.
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        BookSearchFragment fragment = BookSearchFragment.newInstance(type);

        // Add the fragment.
        fragmentTransaction.add(R.id.activity_book_search, fragment).addToBackStack(null).commit();
    }
}**/
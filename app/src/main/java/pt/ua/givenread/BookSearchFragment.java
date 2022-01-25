package pt.ua.givenread;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

public class BookSearchFragment extends Fragment {

    private BookViewModel viewModel;
    private BookAdapter adapter;
    private Context context;

    private TextInputEditText keywordEditText;

    private String type = "";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.getInstance().showBackButton();
        if (getArguments() != null && getArguments().containsKey("type")) {
            type = getArguments().getString("type");
        }

        viewModel = ViewModelProviders.of(this).get(BookViewModel.class);
        adapter = new BookAdapter(viewModel, type, "", "");
        viewModel.init();
        viewModel.getVolumeResponseLiveData().observe(this, volumesResponse -> {
            if (volumesResponse != null) {
                adapter.setBookResults(volumesResponse.getItems());
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booksearch, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.fragment_booksearch_searchResultsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        keywordEditText = view.findViewById(R.id.fragment_booksearch_keyword);
        Button searchButton = view.findViewById(R.id.fragment_booksearch_search);

        searchButton.setOnClickListener(v -> performSearch());


        return view;
    }

    public void performSearch() {
        String keyword = keywordEditText.getEditableText().toString();

        if(keyword.isEmpty()){
            keywordEditText.setError("Please insert a book title");
        }
        else{
            viewModel.searchBooks(keyword);
        }
    }
}
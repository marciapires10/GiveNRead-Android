package pt.ua.givenread;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ISBNResultFragment extends Fragment {

    private String isbnResult = "";
    private TextView isbnTV;

    private BookSearchViewModel viewModel;
    private BookAdapter adapter;
    private Context context;


    public ISBNResultFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey("ISBNResult")) {
            isbnResult = getArguments().getString("ISBNResult");
        }

        viewModel = ViewModelProviders.of(this).get(BookSearchViewModel.class);
        adapter = new BookAdapter(context, viewModel, isbnResult);
        viewModel.init();
        viewModel.getVolumeResponseLiveData().observe(this, volumesResponse -> {
            if (volumesResponse != null) {
                adapter.setBookResults(volumesResponse.getItems());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_isbn_result, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.isbn_result_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        isbnTV = view.findViewById(R.id.isbn_result);
        isbnTV.setText(isbnResult);

        performSearchByISBN(isbnResult);

        return view;
    }

    public void performSearchByISBN(String isbn){
        //String isbn = isbnEditText.getEditableText().toString();

        viewModel.searchBookByISBN("isbn:" + isbn);
    }
}
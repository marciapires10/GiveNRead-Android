package pt.ua.givenread;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BookListCompleteFragment extends Fragment {

    private String type = "";
    private BookListAdapter adapter;
    private BookSearchViewModel viewModel;

    public BookListCompleteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey("Type")) {
            type = getArguments().getString("Type");
        }

        viewModel = ViewModelProviders.of(this).get(BookSearchViewModel.class);
        viewModel.init();
        adapter = new BookListAdapter(new BookListAdapter.BookDiff());

        if (type.equals("ToGive")){
            viewModel.getBooksToGive().observe(this, books -> adapter.submitList(books));
        }
        else {
            viewModel.getBooksToRead().observe(this, books -> adapter.submitList(books));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_list_complete, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.list_complete_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }
}
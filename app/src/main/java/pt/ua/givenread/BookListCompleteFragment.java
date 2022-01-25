package pt.ua.givenread;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class BookListCompleteFragment extends Fragment {

    private String type = "";
    private BookListCompleteAdapter adapter;
    private BookViewModel viewModel;
    private FloatingActionButton addBooks;

    public BookListCompleteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey("Type")) {
            type = getArguments().getString("Type");
        }

        viewModel = ViewModelProviders.of(this).get(BookViewModel.class);
        viewModel.init();
        adapter = new BookListCompleteAdapter(new BookListCompleteAdapter.BookDiff(), viewModel);

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

        MainActivity.getInstance().showBackButton();

        RecyclerView recyclerView = view.findViewById(R.id.list_complete_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        addBooks = view.findViewById(R.id.fab_addbooks);

        addBooks.setOnClickListener(v -> {
            BookSearchFragment fragment = new BookSearchFragment();
            Bundle args = new Bundle();
            args.putString("type", type);
            fragment.setArguments(args);
            MainActivity.getInstance().previous_fragment = this;
            MainActivity.getInstance().last_fragment = false;
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
        });

        return view;
    }
}
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
import android.widget.Button;


import java.util.concurrent.ExecutionException;


public class BooksListFragment extends Fragment {

    private BookViewModel viewModel;
    private BookListAdapter adapter_list1, adapter_list2;
    private Context context;


    public BooksListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter_list1 = new BookListAdapter(new BookListAdapter.BookDiff());
        adapter_list2 = new BookListAdapter(new BookListAdapter.BookDiff());

        viewModel = ViewModelProviders.of(this).get(BookViewModel.class);
        viewModel.init();


        viewModel.getBooksToGive().observe(this, books -> adapter_list1.submitList(books));

        viewModel.getBooksToRead().observe(this, books -> adapter_list2.submitList(books));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bookslist, container, false);


        RecyclerView recyclerView1 = view.findViewById(R.id.fragment_bookList1_RecyclerView);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));
        LinearLayoutManager horizontalLayout = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recyclerView1.setLayoutManager(horizontalLayout);
        recyclerView1.setAdapter(adapter_list1);


        RecyclerView recyclerView2 = view.findViewById(R.id.fragment_bookList2_RecyclerView);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));
        horizontalLayout = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recyclerView2.setLayoutManager(horizontalLayout);
        recyclerView2.setAdapter(adapter_list2);

        Button addTGButton = view.findViewById(R.id.addBookToGive);
        Button addTRButton = view.findViewById(R.id.addBookToRead);

        try {
            if(viewModel.getBooksToGiveList().isEmpty()){
                // Button to add book to To Give list
                addTGButton.setVisibility(View.VISIBLE);

                addTGButton.setOnClickListener(v -> {
                    BookSearchFragment fragment = new BookSearchFragment();
                    Bundle args = new Bundle();
                    args.putString("type", "ToGive");
                    fragment.setArguments(args);
                    MainActivity.getInstance().previous_fragment = MainActivity.getInstance().booksListFragment;
                    MainActivity.getInstance().last_fragment = true;
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                });
            }
            else {
                addTGButton.setVisibility(View.INVISIBLE);
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Button to see all list of To Give books
        Button seeAllTGButton = view.findViewById(R.id.seeAllToGive);

        seeAllTGButton.setOnClickListener(v -> {
            BookListCompleteFragment fragment_tg = new BookListCompleteFragment();
            Bundle type_tg = new Bundle();
            type_tg.putString("Type", "ToGive");
            fragment_tg.setArguments(type_tg);
            MainActivity.getInstance().previous_fragment = MainActivity.getInstance().booksListFragment;
            MainActivity.getInstance().last_fragment = true;
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment_tg).commit();


        });


        try {
            if (viewModel.getBooksToReadList().isEmpty()){
                // Button to add book to To Read list
                addTRButton.setVisibility(View.VISIBLE);

                addTRButton.setOnClickListener(v -> {
                    BookSearchFragment fragment = new BookSearchFragment();
                    Bundle args = new Bundle();
                    args.putString("type", "ToRead");
                    fragment.setArguments(args);
                    MainActivity.getInstance().previous_fragment = MainActivity.getInstance().booksListFragment;
                    MainActivity.getInstance().last_fragment = true;
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                });
            }
            else {
                addTRButton.setVisibility(View.INVISIBLE);
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        // Button to see all list of To Read books
        Button seeAllTRButton = view.findViewById(R.id.seeAllToRead);

        seeAllTRButton.setOnClickListener(v -> {
            BookListCompleteFragment fragment_tr = new BookListCompleteFragment();
            Bundle type_tr = new Bundle();
            type_tr.putString("Type", "ToRead");
            fragment_tr.setArguments(type_tr);
            MainActivity.getInstance().previous_fragment = MainActivity.getInstance().booksListFragment;
            MainActivity.getInstance().last_fragment = true;
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment_tr).commit();
        });

        return view;
    }

}
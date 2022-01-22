package pt.ua.givenread;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import java.util.ArrayList;

public class BookListCompleteAdapter extends ListAdapter<Book, BookListCompleteViewHolder> {

    BookViewModel bookViewModel;

    public BookListCompleteAdapter(DiffUtil.ItemCallback<Book> diffCallback, BookViewModel viewModel){
        super(diffCallback);
        this.bookViewModel = viewModel;
    }

    @NonNull
    @Override
    public BookListCompleteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return BookListCompleteViewHolder.create(parent);
    }


    @Override
    public void onBindViewHolder(BookListCompleteViewHolder holder, int position) {
        Book current = getItem(position);
        holder.bind(current, bookViewModel);
    }

    static class BookDiff extends DiffUtil.ItemCallback<Book> {

        @Override
        public boolean areItemsTheSame(@NonNull Book oldItem, @NonNull Book newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Book oldItem, @NonNull Book newItem) {
            return oldItem.getBook_title().equals(newItem.getBook_title());
        }
    }

}

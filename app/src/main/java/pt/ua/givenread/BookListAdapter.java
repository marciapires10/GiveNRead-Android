package pt.ua.givenread;

import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

public class BookListAdapter extends ListAdapter<Book, BookListViewHolder> {

    public BookListAdapter(DiffUtil.ItemCallback<Book> diffCallback){
        super(diffCallback);
    }

    @NonNull
    @Override
    public BookListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
       return BookListViewHolder.create(parent);
    }


    @Override
    public void onBindViewHolder(BookListViewHolder holder, int position) {
        Book current = getItem(position);
        Log.d("book", current.toString());
        Log.d("current", current.getBook_title());
        holder.bind(current.getBook_title(), current.getImage());
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
package pt.ua.givenread;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class BookListCompleteViewHolder extends RecyclerView.ViewHolder{
    private final TextView bookTVtitle, publisherTV, pageCountTV, remove_book_to_list;
    private final ImageView imageIV;

    private BookListCompleteViewHolder(View itemView) {
        super(itemView);
        bookTVtitle = itemView.findViewById(R.id.idTVBookTitle);
        publisherTV = itemView.findViewById(R.id.idTVpublisher);
        pageCountTV = itemView.findViewById(R.id.idTVPageCount);
        imageIV = itemView.findViewById(R.id.idIVbook);

        remove_book_to_list = itemView.findViewById(R.id.add_book_to_list);
    }

    public void bind(Book book, BookViewModel viewModel) {

        bookTVtitle.setText(book.getBook_title());
        publisherTV.setText("");
        pageCountTV.setText("");
        if (book.getImage() != null){
            String imageUrl = book.getImage().replace("http://", "https://");
            Picasso.get().load(imageUrl).into(imageIV);
        }
        remove_book_to_list.setId(getAdapterPosition());
        remove_book_to_list.setText("remove");

        remove_book_to_list.setOnClickListener(v -> {
            Log.d("o book", book.toString());
            viewModel.delete(book);
        });
    }

    static BookListCompleteViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_item, parent, false);
        return new BookListCompleteViewHolder(view);
    }
}

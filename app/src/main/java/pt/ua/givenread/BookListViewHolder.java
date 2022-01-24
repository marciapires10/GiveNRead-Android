package pt.ua.givenread;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

class BookListViewHolder extends RecyclerView.ViewHolder {
    private final TextView bookTVtitle;
    private final ImageView imageIV;

    private BookListViewHolder(View itemView) {
        super(itemView);
        bookTVtitle = itemView.findViewById(R.id.book_prev_title);
        imageIV = itemView.findViewById(R.id.book_prev_image);
    }

    public void bind(String bookTitle, String image) {
        bookTVtitle.setText(bookTitle);
        if (!image.equals("")){
            String imageUrl = image.replace("http://", "https://");
            Picasso.get().load(imageUrl).into(imageIV);
        }
    }

    static BookListViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_item_prev, parent, false);
        return new BookListViewHolder(view);
    }
}


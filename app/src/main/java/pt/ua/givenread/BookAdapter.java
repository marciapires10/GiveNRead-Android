package pt.ua.givenread;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private BookSearchViewModel viewModel;
    private ArrayList<Volume> bookResults = new ArrayList<>();
    private Context context;

    public BookAdapter(Context context, BookSearchViewModel viewModel){

        this.context = context;
        this.viewModel = viewModel;

    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int pos){
        Log.d("pos", String.valueOf(pos));
        Volume volume = bookResults.get(pos);
        holder.nameTV.setText(volume.getBookInfo().getTitle());
        holder.publisherTV.setText(volume.getBookInfo().getPublisher());
        holder.pageCountTV.setText("No of pages: " + volume.getBookInfo().getPageCount());
        holder.add_book_to_list.setId(pos);
        holder.add_book_to_list.setText(String.valueOf(pos));


        if (volume.getBookInfo().getThumbnail() != null){
            String imageUrl = volume.getBookInfo().getThumbnail().getSmallThumbnail().replace("http://", "https://");
            Picasso.get().load(imageUrl).into(holder.bookIV);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Context mcontext = v.getContext();
                Intent intent = new Intent(mcontext, BookDetails.class);
                intent.putExtra("title", volume.getBookInfo().getTitle());
                intent.putExtra("subtitle", volume.getBookInfo().getSubtitle());
                intent.putExtra("authors", volume.getBookInfo().getAuthors());
                intent.putExtra("publisher", volume.getBookInfo().getPublisher());
                intent.putExtra("publishedDate", volume.getBookInfo().getPublishedDate());
                intent.putExtra("description", volume.getBookInfo().getDescription());
                intent.putExtra("pageCount", volume.getBookInfo().getPageCount());
                intent.putExtra("thumbnail", volume.getBookInfo().getThumbnail().getSmallThumbnail());
                intent.putExtra("previewLink", volume.getBookInfo().getPreviewLink());
                intent.putExtra("infoLink", volume.getBookInfo().getInfoLink());
                intent.putExtra("buyLink", volume.getBookInfo().getBuyLink());

                mcontext.startActivity(intent);
            }
        });

        holder.add_book_to_list.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Book book = new Book(volume.getBookInfo().getTitle(), "", volume.getBookInfo().getThumbnail().getSmallThumbnail());
                viewModel.insert(book);
            }
        });
    }

    public int getItemCount(){
        return bookResults.size();
    }

    public void setBookResults(ArrayList<Volume> bookResults){
        this.bookResults = bookResults;
        notifyDataSetChanged();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder{
        TextView nameTV, publisherTV, pageCountTV;
        ImageView bookIV;
        Button add_book_to_list;

        public BookViewHolder(View itemView) {
            super(itemView);
            nameTV = itemView.findViewById(R.id.idTVBookTitle);
            publisherTV = itemView.findViewById(R.id.idTVpublisher);
            pageCountTV = itemView.findViewById(R.id.idTVPageCount);
            bookIV = itemView.findViewById(R.id.idIVbook);
            add_book_to_list = itemView.findViewById(R.id.add_book_to_list);
        }
    }
}

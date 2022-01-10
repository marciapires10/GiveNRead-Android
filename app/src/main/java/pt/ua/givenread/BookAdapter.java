package pt.ua.givenread;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private ArrayList<Volume> bookResults = new ArrayList<>();
    private Context context;

    public BookAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int pos){
        Volume volume = bookResults.get(pos);
        holder.nameTV.setText(volume.getBookInfo().getTitle());
        holder.publisherTV.setText(volume.getBookInfo().getPublisher());
        holder.pageCountTV.setText("No of pages: " + volume.getBookInfo().getPageCount());
        holder.dateTV.setText(volume.getBookInfo().getPublishedDate());


        if (volume.getBookInfo().getThumbnail() != null){
            String imageUrl = volume.getBookInfo().getThumbnail().getSmallThumbnail().replace("http://", "https://");
            Picasso.get().load(imageUrl).into(holder.bookIV);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(context, BookDetails.class);
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

                context.startActivity(intent);
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
        TextView nameTV, publisherTV, pageCountTV, dateTV;
        ImageView bookIV;

        public BookViewHolder(View itemView) {
            super(itemView);
            nameTV = itemView.findViewById(R.id.idTVBookTitle);
            publisherTV = itemView.findViewById(R.id.idTVpublisher);
            pageCountTV = itemView.findViewById(R.id.idTVPageCount);
            dateTV = itemView.findViewById(R.id.idTVDate);
            bookIV = itemView.findViewById(R.id.idIVbook);
        }
    }
}

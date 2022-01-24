package pt.ua.givenread;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private BookViewModel viewModel;
    private String type; // or isbn
    private String bookstop;
    private String check_type;
    private ArrayList<Volume> bookResults = new ArrayList<>();
    private Context context;

    public BookAdapter(Context context, BookViewModel viewModel, String type, String bookstop, String check_type){

        this.context = context;
        this.viewModel = viewModel;
        this.type = type;
        this.bookstop = bookstop;
        this.check_type = check_type;

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
        holder.add_book_to_list.setId(pos);
        holder.add_book_to_list.setText("add");

        if (volume.getBookInfo().getThumbnail() != null){
            String imageUrl = volume.getBookInfo().getThumbnail().getSmallThumbnail().replace("http://", "https://");
            Picasso.get().load(imageUrl).into(holder.bookIV);
        }
        else {
            Log.d("no image", "no image");
            Drawable d = ContextCompat.getDrawable(viewModel.getApplication().getApplicationContext(), R.drawable.ic_baseline_image_not_supported_24);
            //Drawable d = viewModel.getApplication().getApplicationContext().getDrawable(R.drawable.ic_baseline_image_not_supported_24);
            holder.bookIV.setImageDrawable(d);
        }

        holder.itemView.setOnClickListener(v -> {
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
        });

        if(check_type.equals("check-out")){
            holder.add_book_to_list.setText("remove");
        }

        holder.add_book_to_list.setOnClickListener(v -> {
            Book book;
            if (type.equals("ToGive")){
                if(volume.getBookInfo().getThumbnail() != null){
                    book = new Book(volume.getBookInfo().getTitle(), volume.getBookInfo().getAuthors().get(0), volume.getBookInfo().getIndustryIdentifiers().get(0).getIdentifier(), volume.getBookInfo().getThumbnail().getSmallThumbnail(), type);
                }
                else {
                    book = new Book(volume.getBookInfo().getTitle(), volume.getBookInfo().getAuthors().get(0), volume.getBookInfo().getIndustryIdentifiers().get(0).getIdentifier(), "", type);
                }
                viewModel.insert(book);
                Toast.makeText(viewModel.getApplication().getApplicationContext(), book.getBook_title() + " was added to your list!", Toast.LENGTH_LONG).show();
            }
            else if (type.equals("ToRead")) {
                if(volume.getBookInfo().getThumbnail() != null){
                    book = new Book(volume.getBookInfo().getTitle(), volume.getBookInfo().getAuthors().get(0), volume.getBookInfo().getIndustryIdentifiers().get(0).getIdentifier(), volume.getBookInfo().getThumbnail().getSmallThumbnail(), type);
                }
                else {
                    book = new Book(volume.getBookInfo().getTitle(), volume.getBookInfo().getAuthors().get(0), volume.getBookInfo().getIndustryIdentifiers().get(0).getIdentifier(), "", type);
                }
                viewModel.insert(book);
                Toast.makeText(viewModel.getApplication().getApplicationContext(), book.getBook_title() + " was added to your list!", Toast.LENGTH_LONG).show();
            }
            else {
                if (check_type.equals("check-in")){
                    DataFromFirebase.addDatatoFirebase(volume.getBookInfo().getTitle(), volume.getBookInfo().getAuthors(), type, bookstop);
                    Toast.makeText(viewModel.getApplication().getApplicationContext(), volume.getBookInfo().getTitle() + " was added to " + bookstop, Toast.LENGTH_LONG).show();
                }
                else{
                    DataFromFirebase.removeDataFromFirebase(type, bookstop);
                    Toast.makeText(viewModel.getApplication().getApplicationContext(), volume.getBookInfo().getTitle() + " was removed from " + bookstop, Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public int getItemCount(){
        if (bookResults == null){
            return 0;
        }
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    static void sendNotification(String title, String messageBody, Context applicationContext){
        Intent intent = new Intent(applicationContext, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(applicationContext, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);


        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(applicationContext, "channel_id_1")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(messageBody))
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);


        NotificationManager notificationManager =
                (NotificationManager) applicationContext.getSystemService(Context.NOTIFICATION_SERVICE);


        int random = (int)System.currentTimeMillis();
        notificationManager.notify(random, notificationBuilder.build());
    }
}

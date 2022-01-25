package pt.ua.givenread;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MatchesAdapter extends RecyclerView.Adapter<MatchesAdapter.MatchesViewHolder> {

    private List<Book> matchesList;

    public MatchesAdapter(List<Book> matchesList){
        this.matchesList = matchesList;
    }

    @NonNull
    @Override
    public MatchesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.matches_row, parent, false);
        return new MatchesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchesViewHolder holder, int position) {
        String bookTitle = matchesList.get(position).getBook_title();
        String bookAuthor = matchesList.get(position).getAuthor();

        Log.d("booktit", matchesList.get(position).toString());

        holder.matchBookTitleTV.setText(bookTitle);
        holder.matchBookAuthorTV.setText(bookAuthor);


    }

    @Override
    public int getItemCount() {
        return matchesList == null ? 0 : matchesList.size();
    }

    public void setData(List<Book> matchesList){
        this.matchesList = matchesList;
        notifyDataSetChanged();
    }

    public static class MatchesViewHolder extends RecyclerView.ViewHolder {

        final TextView matchBookTitleTV;
        final TextView matchBookAuthorTV;

        public MatchesViewHolder(@NonNull View itemView) {
            super(itemView);

            matchBookTitleTV = itemView.findViewById(R.id.match_bookTitle);
            matchBookAuthorTV = itemView.findViewById(R.id.match_bookAuthor);
        }
    }
}

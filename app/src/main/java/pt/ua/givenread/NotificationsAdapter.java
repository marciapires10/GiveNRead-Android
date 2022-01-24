package pt.ua.givenread;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.NotificationsViewHolder> {

    Context context;
    private List<String> notifications;

    public NotificationsAdapter(Context context, List<String> notifications) {
        this.context = context;
        this.notifications = notifications;
    }

    @NonNull
    @Override
    public NotificationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.matches_row, parent, false);
        return new NotificationsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationsViewHolder holder, int position) {
        String notification = notifications.get(position);

        holder.titleTV.setText("You have a new match!");
        holder.messageBodyTV.setText(notification);

    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public class NotificationsViewHolder extends RecyclerView.ViewHolder {

        TextView titleTV, messageBodyTV;

        public NotificationsViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTV = itemView.findViewById(R.id.match_bookTitle);
            messageBodyTV = itemView.findViewById(R.id.match_bookAuthor);
        }
    }
}

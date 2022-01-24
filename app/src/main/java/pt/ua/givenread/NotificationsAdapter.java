package pt.ua.givenread;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.NotificationsViewHolder> {

    Context context;
    private List<List<String>> notifications;

    public NotificationsAdapter(Context context, List<List<String>> notifications) {
        this.context = context;
        this.notifications = notifications;
    }

    @NonNull
    @Override
    public NotificationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notifications_row, parent, false);
        return new NotificationsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationsViewHolder holder, int position) {
        String bookstop = notifications.get(position).get(0);
        String notification = notifications.get(position).get(1);

        //String notification = notifications.get(position);

        holder.titleTV.setText("You have a new match!");
        holder.messageBodyTV.setText(notification);

        holder.itemView.setOnClickListener(v -> {
            MapsFragment fragment = new MapsFragment();
            Bundle args = new Bundle();
            args.putString("Bookstop", bookstop);
            fragment.setArguments(args);
            ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
        });


    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public void deleteItem(int position) {
        notifications.remove(position);
        notifyItemRemoved(position);
    }

    public class NotificationsViewHolder extends RecyclerView.ViewHolder {

        TextView titleTV, messageBodyTV;

        public NotificationsViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTV = itemView.findViewById(R.id.notification_title);
            messageBodyTV = itemView.findViewById(R.id.notification_body);


        }
    }
}

package pt.ua.givenread;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class NotificationsFragment extends Fragment {

    NotificationsAdapter adapter;
    List<String> notificationsList = new ArrayList<>();

    public NotificationsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.notificationsList = DataFromFirebase.getNotifications();
        adapter = new NotificationsAdapter(getContext(), notificationsList);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_notifications, container, false);


        RecyclerView recyclerView = view.findViewById(R.id.notifications_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        Log.d("adap", String.valueOf(adapter.getItemCount()));

        return view;
    }
}
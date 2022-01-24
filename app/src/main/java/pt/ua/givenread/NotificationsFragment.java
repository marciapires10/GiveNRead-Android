package pt.ua.givenread;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;


public class NotificationsFragment extends Fragment {

    BottomNavigationView bottomNavView;

    NotificationsAdapter adapter;
    List<List<String>> notificationsList = new ArrayList<>();

    public NotificationsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bottomNavView = ((MainActivity)getActivity()).getBottomNavView();
        Log.d("notfica", String.valueOf(DataFromFirebase.getNotifications().size()));
        bottomNavView.getOrCreateBadge(R.id.notify_opt).setNumber(0);

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
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        return view;
    }

}
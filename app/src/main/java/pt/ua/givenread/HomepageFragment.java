package pt.ua.givenread;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.connection.AdvertisingOptions;
import com.google.android.gms.nearby.connection.ConnectionInfo;
import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback;
import com.google.android.gms.nearby.connection.ConnectionResolution;
import com.google.android.gms.nearby.connection.ConnectionsStatusCodes;
import com.google.android.gms.nearby.connection.DiscoveredEndpointInfo;
import com.google.android.gms.nearby.connection.DiscoveryOptions;
import com.google.android.gms.nearby.connection.EndpointDiscoveryCallback;
import com.google.android.gms.nearby.connection.Payload;
import com.google.android.gms.nearby.connection.PayloadCallback;
import com.google.android.gms.nearby.connection.PayloadTransferUpdate;
import com.google.android.gms.nearby.connection.Strategy;
import com.google.gson.Gson;


import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class HomepageFragment extends Fragment {

    private Button advertiseBtn, discoveryBtn, stopBtn, requestBooksBtn;
    private TextView connectionsTV, matchCounterTV;
    private String username = String.valueOf(new Random().nextInt(10000));

    private static final String[] LOCATION_PERMISSION = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
    private static final int LOCATION_REQUEST_CODE = 20;

    private BookViewModel viewModel;
    private HashMap<String, ConnectionInfo> endpointMap = new HashMap<>();

    private MatchesAdapter adapter;
    private List<Book> booksList = new ArrayList<>();

    public HomepageFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(BookViewModel.class);
        viewModel.init();
        adapter = new MatchesAdapter(getContext(), booksList);

        if (hasCoarseLocationPermission() && hasFineLocationPermission()) {
           Log.d("Location permission", "Has location permission");
        } else {
            requestPermission();
        }

    }

    private boolean hasCoarseLocationPermission() {
        return ContextCompat.checkSelfPermission(
                getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED;
    }

    private boolean hasFineLocationPermission() {
        return ContextCompat.checkSelfPermission(
                getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(
                getActivity(),
                LOCATION_PERMISSION,
                LOCATION_REQUEST_CODE
        );
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_homepage, container, false);


        advertiseBtn = view.findViewById(R.id.advertise_btn);
        discoveryBtn = view.findViewById(R.id.discovery_btn);
        stopBtn = view.findViewById(R.id.stop_connecton_btn);
        requestBooksBtn = view.findViewById(R.id.request_books_btn);
        connectionsTV = view.findViewById(R.id.connections_id);
        matchCounterTV = view.findViewById(R.id.match_counter);

        RecyclerView recyclerView = view.findViewById(R.id.matches_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        advertiseBtn.setOnClickListener(v -> startAdvertising());

        discoveryBtn.setOnClickListener(v -> startDiscovery());

        stopBtn.setOnClickListener(v -> {
            Nearby.getConnectionsClient(getContext()).stopAllEndpoints();
            endpointMap.clear();
        });

        requestBooksBtn.setOnClickListener(v -> endpointMap.forEach((key, value) -> {
            Toast.makeText(getActivity().getApplicationContext(), "Requesting books...", Toast.LENGTH_SHORT).show();
            send_signal(key);
        }));

        matchCounterTV.setText("Number of matches: 0");

        return view;
    }

    private void startAdvertising(){
        AdvertisingOptions advertisingOptions = new AdvertisingOptions.Builder().setStrategy(Strategy.P2P_STAR).build();
        Nearby.getConnectionsClient(getContext()).startAdvertising(
                username, "pt.ua.givenread", connectionLifecycleCallback, advertisingOptions)
                .addOnSuccessListener(
                        (Void unused) -> {
                            // We're advertising!
                            Log.d("Success", "Advertising with success!");
                        }
                ).addOnFailureListener(
                    (Exception e) -> {
                        // We were unable to start advertising.
                        Log.e("Advertising error", e.toString());
                    }
                );
    }

    private void startDiscovery(){
        DiscoveryOptions discoveryOptions = new DiscoveryOptions.Builder().setStrategy(Strategy.P2P_STAR).build();
        Nearby.getConnectionsClient(getContext()).startDiscovery("pt.ua.givenread", endpointDiscoveryCallback, discoveryOptions)
                .addOnSuccessListener(
                        (Void unused) -> {
                            // We're discovering!
                            Log.d("Success", "Discovering with success!");
                        }
                ).addOnFailureListener(
                    (Exception e) -> {
                        // We're unable to start discovering.
                        Log.e("Discovery error", e.toString());
                    }
                );
    }

    private final ConnectionLifecycleCallback connectionLifecycleCallback =
            new ConnectionLifecycleCallback() {

                @Override
                public void onConnectionInitiated(String endpointId, ConnectionInfo info) {
                    endpointMap.put(endpointId, info);
                    new AlertDialog.Builder(getContext())
                            .setTitle("Accept connection to " + info.getEndpointName())
                            .setMessage("Confirm the code matches on both devices: " + info.getAuthenticationDigits())
                            .setPositiveButton(
                                    "Accept",
                                    (DialogInterface dialog, int which) ->
                                            // The user confirmed, so we can accept the connection.
                                            Nearby.getConnectionsClient(getContext())
                                                    .acceptConnection(endpointId, payloadCallback))
                            .setNegativeButton(
                                    android.R.string.cancel,
                                    (DialogInterface dialog, int which) ->
                                            // The user canceled, so we should reject the connection.
                                            Nearby.getConnectionsClient(getContext()).rejectConnection(endpointId))
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }

                @Override
                public void onConnectionResult(String endpointId, ConnectionResolution result) {
                    switch (result.getStatus().getStatusCode()) {
                        case ConnectionsStatusCodes.STATUS_OK:
                            // We're connected! Can now start sending and receiving data.
                            Log.d("Connection result", "status ok");
                            sendPayload(endpointId, "");
                            break;
                        case ConnectionsStatusCodes.STATUS_CONNECTION_REJECTED:
                            // The connection was rejected by one or both sides.
                            Log.d("Connection result", "status connection rejected");
                            break;
                        case ConnectionsStatusCodes.STATUS_ERROR:
                            // The connection broke before it was able to be accepted.
                            Log.d("Connection result", "status error");
                            break;
                        default:
                            // Unknown status code
                    }
                }

                @Override
                public void onDisconnected(String endpointId) {
                    // We've been disconnected from this endpoint. No more data can be
                    // sent or received.
                    endpointMap.remove(endpointId);
                }
            };

    private final EndpointDiscoveryCallback endpointDiscoveryCallback =
            new EndpointDiscoveryCallback() {
                @Override
                public void onEndpointFound(String endpointId, DiscoveredEndpointInfo info) {
                    // An endpoint was found. We request a connection to it.
                    Nearby.getConnectionsClient(getContext())
                            .requestConnection(username, endpointId, connectionLifecycleCallback)
                            .addOnSuccessListener(
                                    (Void unused) -> {
                                        // We successfully requested a connection. Now both sides
                                        // must accept before the connection is established.
                                        Log.d("Asking for connection", "Asking for connection");
                                    })
                            .addOnFailureListener(
                                    (Exception e) -> {
                                        // Nearby Connections failed to request the connection.
                                        Log.e("Failure", "Failed to request the connection");
                                    });
                }

                @Override
                public void onEndpointLost(String endpointId) {
                    // A previously discovered endpoint has gone away.
                    Log.d("Endpoint lost", "Previously discovered endpoint has gone away");
                }
            };

    private void sendPayload(String id, String message){
        Payload bytesPayload = Payload.fromBytes(message.getBytes());
        Nearby.getConnectionsClient(getContext()).sendPayload(id, bytesPayload)
                .addOnSuccessListener(unused -> {
                    Log.d("Success", "Payload sent with success");
                })
                .addOnFailureListener(e -> {
                    Log.e("Error", "Payload failure");
                });
    }

    private final PayloadCallback payloadCallback = new PayloadCallback() {
        @Override
        public void onPayloadReceived(@NonNull String endpointId, @NonNull Payload payload) {
            final byte[] receivedBytes = payload.asBytes();
            String message = new String(receivedBytes, StandardCharsets.UTF_8);

            if (message.equals("send_books")){
                sendMyToReadList(endpointId);
            }
            else {
                Gson gson = new Gson();
                Book _book = gson.fromJson(message, Book.class);


                List<Book> booksToGive = null;
                try {
                    booksToGive = viewModel.getBooksToGiveList();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                for(Book book : booksToGive){
                    Log.d("book in bookstogive", "booktogive");
                    Log.d("give", book.toString());
                    Log.d("_book", _book.toString());
                    if(booksList.isEmpty()){
                        if(book.getBook_title().equals(_book.getBook_title()) && book.getAuthor().equals(_book.getAuthor())){
                            booksList.add(_book);
                            adapter.notifyDataSetChanged();
                        }
                    }
                    else {
                        for (Book b : booksList
                        ) {
                            Log.d("b", b.toString());
                            if (!(b.getBook_title().equals(_book.getBook_title())) && !(b.getAuthor().equals(_book.getAuthor()))
                                    && book.getBook_title().equals(_book.getBook_title()) && book.getAuthor().equals(_book.getAuthor())){
                                booksList.add(_book);
                                adapter.notifyDataSetChanged();
                                Log.d("deu match", "match: " + _book.getBook_title());
                            }
                        }
                    }
                    /**if (book.getBook_title().equals(_book.getBook_title()) && book.getAuthor().equals(_book.getAuthor())
                            && !(booksList.containsKey(_book.getBook_title())) && !(booksList.containsValue(_book.getAuthor()))) {

                        booksList.put(_book.getBook_title(), _book.getAuthor());
                        Log.d("deu match", "match: " + _book.getBook_title());
                    }**/
                }

                connectionsTV.setText(booksList.toString());
                matchCounterTV.setText("Number of matches: " + booksList.size());


            }
        }

        @Override
        public void onPayloadTransferUpdate(@NonNull String s, @NonNull PayloadTransferUpdate payloadTransferUpdate) {
            if (payloadTransferUpdate.getStatus() == PayloadTransferUpdate.Status.SUCCESS) {
                // Do something with is here...
            }
        }
    };

    private void sendMyToReadList(String id){

        List<Book> booksToRead = null;
        try {
            booksToRead = viewModel.getBooksToReadList();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (Book book : booksToRead){
            Gson gson = new Gson();
            String _json = gson.toJson(book);
            sendPayload(id, _json);
        }
    }

    private void send_signal(String id){
        String signal = "send_books";
        sendPayload(id, signal);
    }



}
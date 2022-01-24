package pt.ua.givenread;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsFragment extends Fragment {

    String bookstop;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @SuppressLint("MissingPermission")
        @Override
        public void onMapReady(GoogleMap googleMap) {


            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

            if ((ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED)){
                googleMap.setMyLocationEnabled(true);
            }

            LatLng centerCamera = new LatLng(40.6333308, -8.6499974);

            CameraPosition cameraPosition;

            LatLng bookstop1 = new LatLng(40.63318631270549, -8.659459114357666);
            googleMap.addMarker(new MarkerOptions().position(bookstop1).title("Bookstop 1"));
            //googleMap.moveCamera(CameraUpdateFactory.newLatLng(bookstop1));

            LatLng bookstop2 = new LatLng(40.63067854192939, -8.65347069632065);
            googleMap.addMarker(new MarkerOptions().position(bookstop2).title("Bookstop 2"));
            //googleMap.moveCamera(CameraUpdateFactory.newLatLng(bookstop2));

            Log.d("bookstop", bookstop);
            if(bookstop.equals("BookStop1")){
                Log.d("entrou 1", "book1");
                cameraPosition = new CameraPosition.Builder().target(bookstop1).zoom(18).build();
            }
            else if (bookstop.equals("BookStop2")){
                Log.d("entrou 2", "book2");
                cameraPosition = new CameraPosition.Builder().target(bookstop2).zoom(18).build();
            }
            else {
                cameraPosition = new CameraPosition.Builder().target(centerCamera).zoom(14).build();
            }
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_maps, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        if(getArguments() != null){
            Log.d("get", String.valueOf(getArguments().containsKey("Bookstop")));
        }
        if(getArguments() != null && getArguments().containsKey("Bookstop")){
            bookstop = getArguments().getString("Bookstop");
        }

        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

}
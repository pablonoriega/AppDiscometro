package com.discometro.map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.discometro.MainActivity;
import com.discometro.PerfilDisco.PerfilDiscoPachaActivity;
import com.discometro.PerfilDisco.PerfilDiscoShokoActivity;
import com.discometro.R;
import com.discometro.User;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsFragment extends Fragment  implements GoogleMap.OnMarkerClickListener {
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private View view;
    private Button button;
    private String nameDisco;
    private Intent intent;
    private Class claseDisco;
    private User u;




    // below are the latitude and longitude
    LatLng shoko = new LatLng(41.38561716518406, 2.196849116060127);
    LatLng pacha = new LatLng(41.3858729724748, 2.1970634975427625);
    LatLng downtown = new LatLng(41.38125363306423, 2.114597924767517);
    LatLng ottoZutz = new LatLng(41.40052854343992, 2.1500308266135137);
    LatLng titus = new LatLng(41.45794352445815, 2.263403171770759);

    // two array list for our lat long and location Name;
    private ArrayList<LatLng> latLngArrayList;
    private ArrayList<String> locationNameArraylist;


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
        @Override
        public void onMapReady(GoogleMap googleMap) {
            /**
             //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in barcelona"));
             mMap.moveCamera(CameraUpdateFactory.newLatLng(barcelona));
             **/
            mMap = googleMap;
            u=((MainActivity)getActivity()).getUser();

            // below line is to add marker to google maps
            for (int i = 0; i < latLngArrayList.size(); i++) {

                // adding marker to each location on google maps
                mMap.addMarker(new MarkerOptions().position(latLngArrayList.get(i)).title(locationNameArraylist.get(i)));

                //Build camera position
                CameraPosition cameraPosition = new CameraPosition.Builder().target(shoko).zoom(11).build();
                //Zoom in and animate the camera.
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }

            // adding on click listener to marker of google maps.
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    // on marker click we are getting the title of our marker
                    // which is clicked and displaying it in a toast message.
                    //String markerName = marker.getTitle();
                    //Toast.makeText(getActivity(), "Clicked location is " + markerName, Toast.LENGTH_SHORT).show();
                    //Fragment perfil = new PerfilUserFragment();
                    //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.map,perfil).addToBackStack(null).commit();
                    button.setVisibility(view.VISIBLE);
                    nameDisco=marker.getTitle();
                    //Obtenir nom de l'activity corresponent
                    nameDisco="com.discometro.PerfilDisco.PerfilDisco"+marker.getTitle()+"Activity";


                    return false;
                }
            });

        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_maps, container, false);
        button = (Button) view.findViewById(R.id.botonMapa);
        button.setVisibility(view.INVISIBLE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    claseDisco = Class.forName(nameDisco);
                }
                catch(Exception e) {
                    //  Block of code to handle errors
                }
                intent = new Intent(getContext(), claseDisco);
                intent.putExtra("usuario",u);
                startActivity(intent);
            }
        });

        return view;


    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }

        // initializing our array lists.
        latLngArrayList = new ArrayList<>();
        locationNameArraylist = new ArrayList<>();

        // on below line we are adding
        // data to our array list.

        latLngArrayList.add(shoko);
        latLngArrayList.add(pacha);
        latLngArrayList.add(downtown);
        latLngArrayList.add(ottoZutz);
        latLngArrayList.add(titus);


        locationNameArraylist.add(("Shoko"));
        locationNameArraylist.add(("Pacha"));
        locationNameArraylist.add(("Downtown"));
        locationNameArraylist.add(("OttoZutz"));
        locationNameArraylist.add(("Titus"));



    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        return false;
    }
}




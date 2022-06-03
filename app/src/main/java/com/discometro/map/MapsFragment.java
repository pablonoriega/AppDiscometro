package com.discometro.map;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.discometro.Discos.PerfilDisco;


import com.discometro.Discos.PerfilDiscoActivity;
import com.discometro.MainActivity.MainActivity;
import com.discometro.R;
import com.discometro.User.User;

import com.discometro.ViewModels.ViewModelMapFragment;
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
    private String correo;
    PerfilDisco disco;
    private ViewModelMapFragment vm;

    // below are the latitude and longitude
    LatLng centre = new LatLng(41.38561716518406, 2.196849116060127);


    // two array list for our lat long and location Name;
    private ArrayList<LatLng> latLngArrayList;
    private ArrayList<String> locationNameArraylist;
    private ArrayList<PerfilDisco> listDiscos;


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

            setLiveDataObservers();
            vm.iniUser(((MainActivity)getActivity()).getCorreo());

            //Build camera position
            CameraPosition cameraPosition = new CameraPosition.Builder().target(centre).zoom(11).build();
            //Zoom in and animate the camera.
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            // adding on click listener to marker of google maps.
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    // on marker click we are getting the title of our marker
                    //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.map,perfil).addToBackStack(null).commit();
                    button.setVisibility(view.VISIBLE);
                    nameDisco = marker.getTitle();

                    return false;
                }
            });

            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(@NonNull LatLng latLng) {
                    button.setVisibility(View.INVISIBLE);
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
        button = (Button) view.findViewById(R.id.btn_fragmentmap_gotoprofile);
        button.setVisibility(view.INVISIBLE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    claseDisco = Class.forName(nameDisco);
                } catch (Exception e) {
                    //  Block of code to handle errors
                }

                intent = new Intent(getContext(), PerfilDiscoActivity.class);
                Bundle extras = new Bundle();
                extras.putString("usuario",u.getCorreo());
                extras.putString("disco",nameDisco);
                intent.putExtras(extras);
                startActivity(intent);


            }
        });

        return view;


    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fragment_fragmentmap_map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        return false;
    }

    public void inicializarMapa() {
        // initializing our array lists.
        latLngArrayList = new ArrayList<>();
        locationNameArraylist = new ArrayList<>();
        if (!listDiscos.isEmpty()) {
            for (int i = 0; i < listDiscos.size(); i++) {
                PerfilDisco disco = listDiscos.get(i);
                LatLng ubicacio = new LatLng(Double.parseDouble(disco.getLatitud()), Double.parseDouble(disco.getLongitud()));
                latLngArrayList.add(ubicacio);
                locationNameArraylist.add(disco.getNameDisco());
            }
        }
        // below line is to add marker to google maps
        for (int i = 0; i < latLngArrayList.size(); i++) {
            // adding marker to each location on google maps
            mMap.addMarker(new MarkerOptions().position(latLngArrayList.get(i)).title(locationNameArraylist.get(i)));
        }

    }

    public void setLiveDataObservers() {
        //Subscribe the activity to the observable
        vm = new ViewModelProvider(this).get(ViewModelMapFragment.class);

        final Observer<User> observerUsuario = new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (vm.getUser().getValue().equals(null)) {
                    Toast.makeText(getActivity(), "El usuario o la contraseÃ±a son incorrectos", Toast.LENGTH_SHORT).show();
                } else {
                    MapsFragment.this.u = (User) vm.getUser().getValue();

                }
            }
        };
        final Observer<ArrayList<PerfilDisco>> observerDiscos = new Observer<ArrayList<PerfilDisco>>() {
            @Override
            public void onChanged(ArrayList<PerfilDisco> perfilDiscos) {
                if (vm.getDiscos().getValue().equals(null)) {

                } else {
                    listDiscos = vm.getDiscos().getValue();
                    inicializarMapa();
                }
            }
        };

        final Observer<String> observerToast = new Observer<String>() {
            @Override
            public void onChanged(String t) {
                Toast.makeText(getActivity(), t, Toast.LENGTH_SHORT).show();
            }
        };

        vm.getToast().observe(getViewLifecycleOwner(), observerToast);
        vm.getUser().observe(getViewLifecycleOwner(), observerUsuario);
        vm.getDiscos().observe(getViewLifecycleOwner(),observerDiscos);

    }
}







package com.discometro.VueltaSegura;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.discometro.Discos.PerfilDisco;
import com.discometro.MainActivity.MainActivity;
import com.discometro.R;
import com.discometro.User.User;
import com.discometro.ViewModels.ViewModelAddUbicacionVueltaActivity;
import com.discometro.ViewModels.ViewModelMapFragment;
import com.discometro.map.MapsFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapSeleccionarUbiVuelta extends Fragment {

    private MarkerOptions marker;
    private VueltaSeguraCardItem card;
    private ViewModelAddUbicacionVueltaActivity vm;
    private String correo;
    private Button button;
    private View view;

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

            setLiveDataObservers();
            correo =((SeleccionarUbicacionVueltaActivity)getActivity()).getCorreo();
            vm.iniCard(correo);

            LatLng centre = new LatLng(41.38561716518406, 2.196849116060127);
            CameraPosition cameraPosition = new CameraPosition.Builder().target(centre).zoom(11).build();
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(@NonNull LatLng latLng) {
                    button.setVisibility(view.VISIBLE);
                    googleMap.clear();
                    marker= new MarkerOptions().position(latLng).title("Destinació");
                    googleMap.addMarker(marker);
                }
            });

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    card.setLocation(marker.getPosition().latitude+""+marker.getPosition().longitude);
                    Intent vuelta = new Intent(getContext(), MainActivity.class);
                    vuelta.putExtra("usuario",correo);
                    vm.saveVueltaSeguraCard(card);
                    startActivity(vuelta);
                }
            });

        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_ubi_vuelta_segura, container, false);
        button = (Button) view.findViewById(R.id.botonMapaUbiVuelta);
        button.setVisibility(view.INVISIBLE);
        button.setClickable(false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }


    public void setLiveDataObservers() {
        //Subscribe the activity to the observable
        vm = new ViewModelProvider(this).get(ViewModelAddUbicacionVueltaActivity.class);

        final Observer<VueltaSeguraCardItem> observerCard = new Observer<VueltaSeguraCardItem>() {
            @Override
            public void onChanged(VueltaSeguraCardItem vueltaSeguraCardItem) {
                if(vm.getVueltaSeguraCard().getValue().equals(null)){

                }
                else{
                    card= vm.getVueltaSeguraCard().getValue();
                    button.setClickable(true);

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
        vm.getVueltaSeguraCard().observe(getViewLifecycleOwner(), observerCard);
    }

}
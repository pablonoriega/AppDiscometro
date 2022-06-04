package com.discometro.VueltaSegura;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.discometro.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MostrarVueltaFragment extends Fragment {

    private String ubiCasa,ubiDisco,fotoLogo;

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

            ubiCasa =((MostrarVueltaSeguraActivity)getActivity()).getUbiCasa();
            ubiDisco =((MostrarVueltaSeguraActivity)getActivity()).getUbiDisco();

            String[] ubiCasaSplit = ubiCasa.split(" ");
            LatLng casa = new LatLng(Double.parseDouble(ubiCasaSplit[0]),Double.parseDouble(ubiCasaSplit[1]));
            String[] ubiDiscoSplit = ubiDisco.split(" ");

            LatLng disco = new LatLng(Double.parseDouble(ubiDiscoSplit[0]),Double.parseDouble(ubiDiscoSplit[1]));

            MarkerOptions markerDisco = new MarkerOptions().position(disco).title("Discoteca");
            MarkerOptions markerCasa = new MarkerOptions().position(casa).title("Casa");


            googleMap.addMarker(markerCasa);
            googleMap.addMarker(markerDisco);



            LatLng centre = new LatLng(41.38561716518406, 2.196849116060127);

            CameraPosition cameraPosition = new CameraPosition.Builder().target(centre).zoom(11).build();
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mostrar_vuelta_segura, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fragment_fragmentmap_map4);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}
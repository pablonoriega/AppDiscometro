package com.discometro.VueltaSegura;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.discometro.R;

public class SeleccionarUbicacionVueltaActivity extends AppCompatActivity {

    private String correo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_ubicacion_vuelta);

        Bundle extras= getIntent().getExtras();
        if (!extras.getString("usuario").equals(null)){
            correo = extras.getString("usuario");
        }
    }

    public String getCorreo(){return  correo; }
}
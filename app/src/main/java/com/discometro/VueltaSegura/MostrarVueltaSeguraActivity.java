package com.discometro.VueltaSegura;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.discometro.Discos.PerfilDisco;
import com.discometro.R;

public class MostrarVueltaSeguraActivity extends AppCompatActivity {
    private String ubiDisco,ubiCasa,fotoLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_vuelta_segura);


        Bundle extras = getIntent().getExtras();

        if (!extras.getParcelable("disco").equals(null)) {
            PerfilDisco disco = extras.getParcelable("disco");
            ubiDisco= disco.getLatitud()+" "+disco.getLongitud();
            fotoLogo= disco.getLogoCoded();
        }
        if (!extras.getString("ubiCasa").equals(null)){
        ubiCasa = extras.getString("ubiCasa");

        }



    }

    public String getFotoLogo() {
        return fotoLogo;
    }

    public String getUbiCasa() {
        return ubiCasa;
    }

    public String getUbiDisco() {
        return ubiDisco;
    }
}


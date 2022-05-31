package com.discometro;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.discometro.Login.LoginActivity;

public class PantallaInicioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_inicio);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent ventanaLogin = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(ventanaLogin);
                finish();
            }
        },2000); //tiempo duración 2s
    }
}
package com.discometro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.navigation.ui.AppBarConfiguration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.discometro.Suscripciones.SuscripcionesFragment;
import com.discometro.VueltaSegura.AgregarVueltaSeguraActivity;
import com.discometro.VueltaSegura.VueltaSeguraFragment;
import com.discometro.favoritos.FavoritosFragment;
import com.discometro.map.MapsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private MapsFragment mapsFragment;
    private VueltaSeguraFragment vueltaSeguraFragment;
    private SuscripcionesFragment suscripcionesFragment;
    private FavoritosFragment favoritosFragment;
    private PerfilUserFragment perfilUserFragment;
    private static User u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavController navController = Navigation.findNavController(this, R.id.fragment);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.mapsFragment, R.id.vueltaSeguraFragment, R.id.suscripcionesFragment, R.id.favoritosFragment, R.id.perfilUserFragment).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        Intent intent = getIntent();

        if (intent.getParcelableExtra("usuario") != null) {
            u= intent.getParcelableExtra("usuario");
        }

    }

    public void intentToAgregarVueltaSegura(View view) {
        Intent intent = new Intent(this, AgregarVueltaSeguraActivity.class);
        startActivity(intent);
    }

    public User getUser(){
        return this.u;
    }
}
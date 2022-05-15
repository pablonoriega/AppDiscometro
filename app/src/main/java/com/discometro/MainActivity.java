package com.discometro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.navigation.ui.AppBarConfiguration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.discometro.PerfilDisco.PerfilDisco;
import com.discometro.Suscripciones.SuscripcionesFragment;
import com.discometro.ViewModel.ViewModelMain;
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
    private User user;
    private static String correo;
    private ViewModelMain vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vm = new ViewModelMain(getApplication());
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavController navController = Navigation.findNavController(this, R.id.fragment);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.mapsFragment, R.id.vueltaSeguraFragment, R.id.suscripcionesFragment, R.id.favoritosFragment, R.id.perfilUserFragment).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        Bundle extras = getIntent().getExtras();

        if (extras.getString("usuario") != null) {
            correo = extras.getString("usuario");
        }
        System.out.println(correo + "HOLAAA");
        user = vm.getUserById(correo);
    }

    public void intentToAgregarVueltaSegura(View view) {
        Intent intent = new Intent(this, AgregarVueltaSeguraActivity.class);
        startActivity(intent);
    }

    public User getUser(){
        return user;
    }

    public String getUserCorreo() {
        return user.getCorreo();
    }
}
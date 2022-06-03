package com.discometro.MainActivity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import com.discometro.ViewModels.ViewModelMainActivity;
import com.discometro.map.MapsFragment;
import com.discometro.R;
import com.discometro.User.User;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private MapsFragment mapsFragment;

    private User user;
    private String correo;
    private ViewModelMainActivity vm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bnv_main_bottomNavigationView);
        NavController navController = Navigation.findNavController(this, R.id.fragment_main_fragment);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.mapsFragment, R.id.vueltaSeguraFragment, R.id.suscripcionesFragment, R.id.favoritosFragment, R.id.perfilUserFragment).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        Bundle extras = getIntent().getExtras();

        if (extras.getString("usuario") != null) {
            correo = extras.getString("usuario");
        }







    }

    public void setLiveDataObservers() {
        //Subscribe the activity to the observable
        vm= new ViewModelProvider(this).get(ViewModelMainActivity.class);

        final Observer<User> observerUsuario = new Observer<User>() {
            @Override
            public void onChanged(User u) {
                if (vm.getUser().getValue().equals(null)){
                    Toast.makeText(MainActivity.this, "El usuario o la contraseÃ±a son incorrectos", Toast.LENGTH_SHORT).show();
                } else{
                    user= (User) vm.getUser().getValue();
                    Toast.makeText(MainActivity.this, user.getCorreo(), Toast.LENGTH_SHORT).show();
                }

            }
        };

        final Observer<String> observerToast = new Observer<String>() {
            @Override
            public void onChanged(String t) {
                Toast.makeText(MainActivity.this, t, Toast.LENGTH_SHORT).show();
            }
        };

        vm.getToast().observe(this, observerToast);
        vm.getUser().observe(this, observerUsuario);
    }

    /*public void intentToAgregarVueltaSegura(View view) {
        Intent intent = new Intent(this, AgregarVueltaSeguraActivity.class);
        startActivity(intent);
    }

     */


    public String getCorreo() {
        return this.correo;
    }
    public User getUser(){ return this.user;}



}
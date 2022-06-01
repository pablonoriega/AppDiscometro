package com.discometro.VueltaSegura;

import android.content.Intent;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import com.discometro.MainActivity.MainActivity;
import com.discometro.R;
import com.discometro.User.User;
import com.discometro.ViewModels.ViewModelVueltaSeguraFragment;

import java.util.ArrayList;

public class AgregarVueltaSeguraActivity extends AppCompatActivity {

    private Spinner spinner;
    private TextView number_amount;
    private EditText number, origen;
    private ViewGroup container;
    private ViewModelVueltaSeguraFragment vm;
    private User u;
    private String correo;
    VueltaSeguraCardItem card;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_vuelta_segura);

        Intent intent = getIntent();
        correo =intent.getStringExtra("usuario");
        setLiveDataObservers();
        vm.iniUser(correo);

        spinner = findViewById(R.id.spinner_vehicles);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.vehicles, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        number_amount = findViewById(R.id.tv_amount);
        number = findViewById(R.id.et_number);
        origen=findViewById(R.id.edit_origen);
        container = findViewById(R.id.tr_amount);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String text = spinner.getSelectedItem().toString();
                if (text.equals("A pie")) {
                    TransitionManager.beginDelayedTransition(container);
                    number_amount.setVisibility(View.GONE);
                    number.setVisibility(View.GONE);
                }
                else {
                    TransitionManager.beginDelayedTransition(container);
                    number_amount.setVisibility(View.VISIBLE);
                    number.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void intentToAddUbicacion(View view) {
        Intent ubicacion = new Intent(getApplicationContext(), SeleccionarUbicacionVueltaActivity.class);
        ubicacion.putExtra("usuario",correo);
        if(comprobar()){
            vm.saveVueltaSeguraCard(card);
            startActivity(ubicacion);

        }

    }

    public void setLiveDataObservers() {
        //Subscribe the activity to the observable
        vm = new ViewModelProvider(this).get(ViewModelVueltaSeguraFragment.class);

        final Observer<User> observerUsuario = new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (vm.getUser().getValue().equals(null)) {
                    Toast.makeText(AgregarVueltaSeguraActivity.this, "El usuario o la contraseÃ±a son incorrectos", Toast.LENGTH_SHORT).show();
                } else {
                    u=(User) vm.getUser().getValue();
                }
            }
        };

        final Observer<String> observerToast = new Observer<String>() {
            @Override
            public void onChanged(String t) {
                Toast.makeText(AgregarVueltaSeguraActivity.this, t, Toast.LENGTH_SHORT).show();
            }
        };

        vm.getToast().observe(this, observerToast);
        vm.getUser().observe(this, observerUsuario);

    }

    public boolean comprobar(){
        String txt_number = number.getText().toString();
        String txt_vehicle = spinner.getSelectedItem().toString();
        String txt_origen =origen.getText().toString();
        ArrayList<String> listNombres = vm.getNameDiscos();

        String inicial= txt_origen.substring(0,1);
        inicial=inicial.toUpperCase();
        String resto = txt_origen.substring(1,txt_origen.length());
        resto=resto.toLowerCase();
        txt_origen= inicial+resto;

        if(txt_origen.equals("")){
            Toast.makeText(getApplicationContext(), "No se ha rellenado el origen", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!listNombres.contains(txt_origen)){
            Toast.makeText(getApplicationContext(), "La discoteca no se encuentra en la lista", Toast.LENGTH_SHORT).show();
            return false;

        }
        if(txt_vehicle.equals("pie")){
            card = new VueltaSeguraCardItem(u.getName(),u.getCorreo(),txt_vehicle,"","Indefinido",txt_origen,vm.getDiscoByName(txt_origen).getLogo());
        }
        else{
            card = new VueltaSeguraCardItem(u.getName(),u.getCorreo(),txt_vehicle,"",txt_number,txt_origen,vm.getDiscoByName(txt_origen).getLogo());
        }
        return true;
    }
}
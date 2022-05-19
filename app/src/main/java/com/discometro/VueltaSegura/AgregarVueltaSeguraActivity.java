package com.discometro.VueltaSegura;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

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

import com.discometro.MainActivity;
import com.discometro.R;
import com.discometro.User;
import com.discometro.ViewModel.ViewModelMain;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AgregarVueltaSeguraActivity extends AppCompatActivity {

    private Spinner spinner;
    private TextView number_amount;
    private EditText location, number, origen;
    private ViewGroup container;
    private ViewModelMain vm;
    private static User u;
    private VueltaSeguraCardItem card;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_vuelta_segura);
        vm = new ViewModelProvider(this).get(ViewModelMain.class);
        Intent intent = getIntent();
        if (intent.getParcelableExtra("usuario")!= null) {
            u=intent.getParcelableExtra("usuario");
        }

        spinner = findViewById(R.id.spn_addsafereturn_spinnervehicle);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.vehicles, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        container = findViewById(R.id.cl_addsafereturn_constraintlayout);
        number_amount = findViewById(R.id.tv_addsafereturn_passengers);
        number = findViewById(R.id.et_addsafereturn_passengers);
        location= findViewById(R.id.et_addsafereturn_dest);
        origen=findViewById(R.id.et_addsafereturn_discoorigin);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String text = spinner.getSelectedItem().toString();
                if (text.equals("A pie") || text.equals("Bicicleta") ||text.equals("Autobus") || text.equals("Moto")) {
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

    public void intentToVueltaSegura(View view) {
        Intent vuelta = new Intent(getApplicationContext(), MainActivity.class);
        if(comprobar()){
            vm.saveVueltaSeguraCard(card);
            vuelta.putExtra("usuario",u.getCorreo());
            startActivity(vuelta);
        }


    }
    public boolean comprobar() {
        int num = 0;
        String txt_location = location.getText().toString();
        String txt_number = number.getText().toString();
        String txt_vehicle = spinner.getSelectedItem().toString();
        String txt_origen = origen.getText().toString();
        ArrayList<String> listNombres = vm.getNameDiscos();
        ArrayList<String> userList = new ArrayList<>();
        userList.add(u.getCorreo());

        if (!txt_number.equals("")) {
            num = Integer.parseInt(txt_number);
        }

        if (!txt_origen.equals("")) {
            String inicial = txt_origen.substring(0, 1);
            inicial = inicial.toUpperCase();
            String resto = txt_origen.substring(1, txt_origen.length());
            resto = resto.toLowerCase();
            txt_origen = inicial + resto;
        }
        if(txt_location.equals("")){
            Toast.makeText(getApplicationContext(), "No se ha rellenado la localización", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(txt_origen.equals("")){
            Toast.makeText(getApplicationContext(), "No se ha rellenado el origen", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!listNombres.contains(txt_origen)){
            Toast.makeText(getApplicationContext(), "La discoteca no se encuentra en la lista", Toast.LENGTH_SHORT).show();
            return false;

        }
        if(txt_vehicle.equals("A pie") || txt_vehicle.equals("Bicicleta") ||txt_vehicle.equals("Autobus")){
            card = new VueltaSeguraCardItem(u.getName(),u.getCorreo(),txt_vehicle,txt_location,"10",txt_origen,vm.getDiscoByName(txt_origen).getLogo(), userList);
        }
        else if (txt_vehicle.equals("Moto")) {
            card = new VueltaSeguraCardItem(u.getName(),u.getCorreo(),txt_vehicle,txt_location,"1",txt_origen,vm.getDiscoByName(txt_origen).getLogo(), userList);
        }
        else{
            if (num <= 0 || num >= 5) {
                return false;
            }
            card = new VueltaSeguraCardItem(u.getName(),u.getCorreo(),txt_vehicle,txt_location,txt_number,txt_origen,vm.getDiscoByName(txt_origen).getLogo(), userList);
        }
        return true;
    }
}
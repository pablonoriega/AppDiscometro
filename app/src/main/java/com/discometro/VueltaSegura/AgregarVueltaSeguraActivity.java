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
import com.discometro.PerfilDisco.PerfilDisco;
import com.discometro.R;
import com.discometro.User;
import com.discometro.ViewModel.ViewModelMain;

import java.util.ArrayList;

public class AgregarVueltaSeguraActivity extends AppCompatActivity {

    private Spinner spinner;
    private TextView number_amount;
    private EditText location, number, origen;
    private ViewGroup container;
    private ViewModelMain vm;
    private User u;
    VueltaSeguraCardItem card;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_vuelta_segura);
        vm = new ViewModelProvider(this).get(ViewModelMain.class);
        Intent intent = getIntent();
        u=intent.getParcelableExtra("usuario");

        spinner = findViewById(R.id.spinner_vehicles);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.vehicles, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        number_amount = findViewById(R.id.tv_amount);
        number = findViewById(R.id.et_number);
        location= findViewById(R.id.et_location);
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

    public void intentToVueltaSegura(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        if(comprobar()){
            vm.saveVueltaSeguraCard(card);
            startActivity(intent);
        }


    }
    public boolean comprobar(){
        String txt_location= location.getText().toString();
        String txt_number = number.getText().toString();
        String txt_vehicle = spinner.getSelectedItem().toString();
        String txt_origen =origen.getText().toString();

        ArrayList<String> listNombres = vm.getNameDiscos();


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
        if(txt_vehicle.equals("pie")){
            card = new VueltaSeguraCardItem(u.getName(),u.getCorreo(),txt_vehicle,txt_location,"Indefinido",txt_origen,vm.getDiscoByName(txt_origen).getLogo());
        }
        else{
            card = new VueltaSeguraCardItem(u.getName(),u.getCorreo(),txt_vehicle,txt_location,txt_number,txt_origen,vm.getDiscoByName(txt_origen).getLogo());
        }
        return true;
    }
}
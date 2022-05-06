package com.discometro.objetosPerdidos;

import android.content.Intent;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.discometro.MainActivity;
import com.discometro.R;
import com.discometro.User;
import com.discometro.ViewModel.ViewModelMain;
import com.discometro.VueltaSegura.VueltaSeguraCardItem;

import java.util.ArrayList;

public class AgregarObjetoPerdidoActivity extends AppCompatActivity {


    private EditText nombre, descripcion;
    private ViewModelMain vm;
    private String nameDisco;
    private Button subirObjeto;
    private User u;
    private ObjetosPerdidosCardItem card;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_objeto_perdido);
        vm = new ViewModelProvider(this).get(ViewModelMain.class);



        Intent getDatos = getIntent();
        Bundle extras = getDatos.getExtras();
        u= extras.getParcelable("usuario");
        nameDisco= extras.getString("nameDisco");



        nombre = findViewById(R.id.eTxtObjectName);
        descripcion= findViewById(R.id.eTxtAddDescription);
        subirObjeto =findViewById(R.id.subirObjBtn);


        subirObjeto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(comprobar()){
                    vm.saveObjetoPerdidoCard(card);
                    Intent vuelta = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(vuelta);

                }
            }
        });






    }
    public boolean comprobar(){
        String txt_nombre = nombre.getText().toString();
        String txt_descripcion = descripcion.getText().toString();
        String txt_usuario = u.getCorreo();
        String txt_fotoLogo = vm.getDiscoByName(nameDisco).getLogo();


        if(txt_descripcion.equals("")){
            Toast.makeText(getApplicationContext(), "No se ha rellenado la descripción", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(txt_nombre.equals("")){
            Toast.makeText(getApplicationContext(), "No se ha rellenado el nombre del objeto", Toast.LENGTH_SHORT).show();
            return false;
        }


        card= new ObjetosPerdidosCardItem(txt_nombre,txt_usuario,txt_descripcion,txt_fotoLogo,nameDisco);

        return true;
    }

}
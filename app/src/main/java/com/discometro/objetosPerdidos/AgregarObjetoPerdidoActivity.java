package com.discometro.objetosPerdidos;

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
import androidx.lifecycle.ViewModelProvider;

import com.discometro.MainActivity;
import com.discometro.R;
import com.discometro.User;
import com.discometro.ViewModel.ViewModelMain;
import com.discometro.VueltaSegura.VueltaSeguraCardItem;

import java.util.ArrayList;

public class AgregarObjetoPerdidoActivity extends AppCompatActivity {

    private Spinner spinner;
    private TextView number_amount;
    private EditText location, number, origen;
    private ViewGroup container;
    private ViewModelMain vm;
    private String nameDisco;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objetos_perdidos);
        vm = new ViewModelProvider(this).get(ViewModelMain.class);
        Intent intent = getIntent();
        nameDisco=intent.getParcelableExtra("nameDisco");

        number_amount = findViewById(R.id.tv_amount);
        number = findViewById(R.id.et_number);
        location= findViewById(R.id.et_location);
        origen=findViewById(R.id.edit_origen);
        container = findViewById(R.id.tr_amount);



    }
    public void comprobar(){

    }

}
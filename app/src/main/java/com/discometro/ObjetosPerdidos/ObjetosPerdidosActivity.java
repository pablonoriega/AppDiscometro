package com.discometro.ObjetosPerdidos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.discometro.Discos.PerfilDisco;
import com.discometro.R;
import com.discometro.User.User;
import com.discometro.ViewModels.ViewModelObjetosPerdidosActivity;

import java.util.ArrayList;

public class ObjetosPerdidosActivity extends AppCompatActivity {

    private ImageButton addObject;
    private ArrayList<ObjetosPerdidosCardItem> listCards;
    private RecyclerView recyclerView;
    private ViewModelObjetosPerdidosActivity vm;
    private String nameDisco;
    private String correo;
    private PerfilDisco disco;
    private User u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objetos_perdidos);

        Intent getDatos = getIntent();
        Bundle extras = getDatos.getExtras();
        correo= extras.getString("usuario");
        nameDisco = extras.getString("nameDisco");
        setLiveDataObservers();
        vm.iniUser(correo);
        listCards = new ArrayList<ObjetosPerdidosCardItem>();


        addObject = findViewById(R.id.ib_object_addobject);

        recyclerView = (RecyclerView) findViewById(R.id.rv_object_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));



        addObject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentToAddLostObjects(view);
            }
        });
    }





        public void setLiveDataObservers() {
            //Subscribe the activity to the observable
            vm = new ViewModelProvider(this).get(ViewModelObjetosPerdidosActivity.class);

            final Observer<User> observerUsuario = new Observer<User>() {
                @Override
                public void onChanged(User user) {
                    if (vm.getUser().getValue().equals(null)) {
                        Toast.makeText(ObjetosPerdidosActivity.this, "El usuario o la contraseÃ±a son incorrectos", Toast.LENGTH_SHORT).show();
                    } else {
                        u=(User) vm.getUser().getValue();
                    }
                }
            };
            final Observer<ArrayList<ObjetosPerdidosCardItem>> observerCards = new Observer<ArrayList<ObjetosPerdidosCardItem>>() {
                @Override
                public void onChanged(ArrayList<ObjetosPerdidosCardItem> objetosPerdidosCardItems) {
                    if(vm.getAllObjetos().getValue().equals(null)){
                        }
                    else{
                        listCards = (ArrayList<ObjetosPerdidosCardItem>) vm.getObjetosPerdidosCards(nameDisco);
                        ObjetosPerdidosItemAdapter adapter = new ObjetosPerdidosItemAdapter(listCards,ObjetosPerdidosActivity.this);
                        recyclerView.setAdapter(adapter);

                    }
                }
            };


            final Observer<String> observerToast = new Observer<String>() {
                @Override
                public void onChanged(String t) {
                    Toast.makeText(ObjetosPerdidosActivity.this, t, Toast.LENGTH_SHORT).show();
                }
            };

            vm.getToast().observe(this, observerToast);
            vm.getUser().observe(this, observerUsuario);
            vm.getAllObjetos().observe(this,observerCards);

        }


    public void intentToAddLostObjects(View view){

        Intent intent = new Intent(getApplicationContext(), AgregarObjetoPerdidoActivity.class);
        Bundle extras = new Bundle();
        extras.putString("nameDisco",nameDisco);
        extras.putString("usuario",u.getCorreo());
        intent.putExtras(extras);
        startActivity(intent);


    }


}
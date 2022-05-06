package com.discometro.objetosPerdidos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.discometro.MainActivity;
import com.discometro.PerfilDisco.PerfilDisco;
import com.discometro.PerfilDisco.PerfilDiscoActivity;
import com.discometro.R;
import com.discometro.User;
import com.discometro.ViewModel.ViewModelMain;
import com.discometro.VueltaSegura.VueltaSeguraCardItem;
import com.discometro.VueltaSegura.VueltaSeguraItemAdapter;
import com.discometro.favoritos.FavoritosCardItem;
import com.discometro.favoritos.FavoritosItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class ObjetosPerdidosActivity extends AppCompatActivity {

    private ImageButton addObject;
    private ArrayList<ObjetosPerdidosCardItem> listCards;
    private RecyclerView recyclerView;
    private ViewModelMain vm;
    private String nameDisco;
    private PerfilDisco disco; ;
    private User u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objetos_perdidos);
        vm = new ViewModelProvider(this).get(ViewModelMain.class);

        addObject = findViewById(R.id.addObjectBtn);


        recyclerView = (RecyclerView) findViewById(R.id.objPerdidosRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        Intent getDatos = getIntent();
        Bundle extras = getDatos.getExtras();
        u= extras.getParcelable("usuario");
        nameDisco= extras.getString("nameDisco");

        /*

        listCards= new ArrayList<ObjetosPerdidosCardItem>();
        listCards = vm.getObjetosPerdidosCards(nameDisco);
        if(!listCards.isEmpty()){
            ObjetosPerdidosItemAdapter adapter = new ObjetosPerdidosItemAdapter(listCards);
            recyclerView.setAdapter(adapter);
        }

         */


    }

    public void intentToAddLostObjects(View view){
        Intent intent = new Intent(getApplicationContext(), AgregarObjetoPerdidoActivity.class);
        Bundle extras = new Bundle();
        extras.putString("nameDisco",nameDisco);
        extras.putParcelable("usuario",u);
        intent.putExtras(extras);
        startActivity(intent);
    }
}
package com.discometro.objetosPerdidos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.discometro.MainActivity;
import com.discometro.PerfilDisco.PerfilDisco;
import com.discometro.PerfilDisco.PerfilDiscoActivity;
import com.discometro.R;
import com.discometro.User;
import com.discometro.ViewModel.ViewModelMain;
import com.discometro.VueltaSegura.VueltaSeguraCardItem;
import com.discometro.favoritos.FavoritosCardItem;
import com.discometro.favoritos.FavoritosItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class ObjetosPerdidosActivity extends AppCompatActivity {

    private ImageButton addObject;
    private List<ObjetosPerdidosCardItem> listCards;
    private RecyclerView recyclerView;
    private ViewModelMain vm;
    private String nameDisco;
    private PerfilDisco disco; ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }

    public void intentToAddLostObjects(View view){
        Intent intent = new Intent(getApplicationContext(), AgregarObjetoPerdidoActivity.class);
        intent.putExtra("nameDisco",nameDisco);
        startActivity(intent);
    }
}
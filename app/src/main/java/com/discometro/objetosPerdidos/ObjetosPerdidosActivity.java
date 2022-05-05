package com.discometro.objetosPerdidos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.discometro.R;
import com.discometro.VueltaSeguraCardItem;

import java.util.List;

public class ObjetosPerdidosActivity extends AppCompatActivity {

    private ImageButton addObject;
    private List<VueltaSeguraCardItem> listItems;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objetos_perdidos);
        addObject = findViewById(R.id.addObjectBtn);

        addObject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentToAddLostObjects(view);
            }
        });
    }

    public void intentToAddLostObjects(View view){
        //Intent intent = new Intent(getApplicationContext(), AgregarObjetoPerdidoActivity.class);
        //startActivity(intent);
    }
}
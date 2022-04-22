package com.discometro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

public class AgregarObjetoPerdidoActivity extends AppCompatActivity {

    private EditText objectName;
    private EditText objectDescription;
    private ImageButton addObjectPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_objeto_perdido);
        objectName = findViewById(R.id.eTxtObjectName);
        objectDescription = findViewById(R.id.eTxtAddDescription);
        addObjectPhoto = findViewById(R.id.addImageBtn);
    }
}
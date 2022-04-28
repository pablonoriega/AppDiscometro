package com.discometro.objetosPerdidos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.discometro.R;
import com.discometro.objetosPerdidos.ObjetosPerdidosActivity;

public class AgregarObjetoPerdidoActivity extends AppCompatActivity {

    private EditText objectName;
    private EditText objectDescription;
    private ImageButton addObjectPhoto;
    private Button subirObjeto;
    private Uri objectImagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_objeto_perdido);
        objectName = findViewById(R.id.eTxtObjectName);
        objectDescription = findViewById(R.id.eTxtAddDescription);
        addObjectPhoto = findViewById(R.id.addImageBtn);
        subirObjeto = findViewById(R.id.subirObjBtn);

        addObjectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarImagen();
            }
        });

        subirObjeto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentToObjetosPerdidos(view);
            }
        });
    }

    public void cargarImagen() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent,"Seleccione la aplicación"),10);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            Uri path = data.getData();
            objectImagePath = path;
        }
    }

    public void intentToObjetosPerdidos(View view){
        String txt_name = objectName.getText().toString();
        String txt_description = objectDescription.getText().toString();

        if (txt_name.equals("") || txt_description.equals("")) {
            Toast.makeText(this, "Faltan campos por rellenar", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(getApplicationContext(), ObjetosPerdidosActivity.class);
            startActivity(intent);
        }

    }

}
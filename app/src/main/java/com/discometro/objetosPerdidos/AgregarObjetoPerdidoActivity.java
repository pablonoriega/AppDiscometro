package com.discometro.objetosPerdidos;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.discometro.R;
import com.discometro.User;
import com.discometro.ViewModel.ViewModelMain;

public class AgregarObjetoPerdidoActivity extends AppCompatActivity {


    private EditText nombre, descripcion;
    private ViewModelMain vm;
    private String nameDisco;
    private Button subirObjeto;
    private ImageView objectImage;
    private ImageButton addImageBtn;
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



        nombre = findViewById(R.id.tv_addobject_objectname);
        descripcion= findViewById(R.id.tv_addobject_objectdescr);
        subirObjeto = findViewById(R.id.btn_addobject_addobject);
        objectImage = findViewById(R.id.iv_addobject_objectimg);
        addImageBtn = findViewById(R.id.ib_addobject_addobjectimg);

        addImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarImagen();
            }
        });

        subirObjeto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(comprobar()){
                    vm.saveObjetoPerdidoCard(card);
                    Intent vuelta = new Intent(getApplicationContext(),ObjetosPerdidosActivity.class);
                    startActivity(vuelta);

                }
            }
        });


    }

    public void cargarImagen() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent, "Seleccione la aplicación"), 10);
    }

    public void onActivityResult ( int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            Uri path = data.getData();
            objectImage.setImageURI(path);
        }
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
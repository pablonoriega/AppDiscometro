package com.discometro.ObjetosPerdidos;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.discometro.R;
import com.discometro.User.User;
import com.discometro.ViewModels.ViewModelObjetosPerdidosActivity;

import java.io.IOException;

public class AgregarObjetoPerdidoActivity extends AppCompatActivity {


    private EditText nombre, descripcion;
    private ViewModelObjetosPerdidosActivity vmObjetos;


    private String nameDisco,correo;
    private Button subirObjeto;
    private ImageView objectImage;
    private ImageButton addImageBtn;
    private User u;
    private ObjetosPerdidosCardItem card;
    private Uri imgUri;
    private final int IMG_REQUEST_ID = 10;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_objeto_perdido);
        setLiveDataObservers();

        Intent getDatos = getIntent();
        Bundle extras = getDatos.getExtras();
        correo = extras.getString("usuario");
        nameDisco= extras.getString("nameDisco");
        vmObjetos.iniUser(correo);
        card = new ObjetosPerdidosCardItem("","","","","","");



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
                    guardarImagen();
                    Intent vuelta = new Intent(getApplicationContext(),ObjetosPerdidosActivity.class);
                    Bundle extras = new Bundle();
                    extras.putString("nameDisco",nameDisco);
                    extras.putString("usuario",correo);
                    vuelta.putExtras(extras);
                    vmObjetos.saveObjetoPerdidoCard(card);
                    startActivity(vuelta);

                }
            }
        });


    }

    public void cargarImagen() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Selecciona una imagen"),IMG_REQUEST_ID);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == IMG_REQUEST_ID && resultCode == Activity.RESULT_OK && data!= null && data.getData()!= null){

            imgUri = data.getData();
            try {
                Bitmap bitmapImg = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imgUri);
                objectImage.setImageBitmap(bitmapImg);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public  void guardarImagen(){
        if (imgUri != null){
            vmObjetos.SaveImage(imgUri,"fotosObjetosPerdidos/"+u.getCorreo());
            card.setImagenObjeto("fotosObjetosPerdidos/"+u.getCorreo());


        }
    }

    public boolean comprobar(){
        String txt_nombre = nombre.getText().toString();
        String txt_descripcion = descripcion.getText().toString();
        String txt_usuario = u.getCorreo();
        String txt_fotoLogo = vmObjetos.getDiscoByName(nameDisco).getLogo();


        if(txt_descripcion.equals("")){
            Toast.makeText(getApplicationContext(), "No se ha rellenado la descripción", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(txt_nombre.equals("")){
            Toast.makeText(getApplicationContext(), "No se ha rellenado el nombre del objeto", Toast.LENGTH_SHORT).show();
            return false;
        }
        card.setNombreObj(txt_nombre);
        card.setDescripcion(txt_descripcion);
        card.setUsuario(txt_usuario);
        card.setNameDisco(nameDisco);
        card.setImagenLogo(txt_fotoLogo);
        return true;
    }

    public void setLiveDataObservers() {
        //Subscribe the activity to the observable
        vmObjetos = new ViewModelProvider(this).get(ViewModelObjetosPerdidosActivity.class);

        final Observer<User> observerUsuario = new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (vmObjetos.getUser().getValue().equals(null)) {
                    Toast.makeText(AgregarObjetoPerdidoActivity.this, "El usuario o la contraseÃ±a son incorrectos", Toast.LENGTH_SHORT).show();
                } else {
                    u=(User) vmObjetos.getUser().getValue();
                }
            }
        };

        final Observer<String> observerToast = new Observer<String>() {
            @Override
            public void onChanged(String t) {
                Toast.makeText(AgregarObjetoPerdidoActivity.this, t, Toast.LENGTH_SHORT).show();
            }
        };

        vmObjetos.getToast().observe(this, observerToast);
        vmObjetos.getUser().observe(this, observerUsuario);


    }

}
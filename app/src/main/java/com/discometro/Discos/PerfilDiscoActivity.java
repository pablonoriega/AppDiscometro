package com.discometro.Discos;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.discometro.MainActivity.MainActivity;
import com.discometro.ObjetosPerdidos.ObjetosPerdidosActivity;
import com.discometro.ObjetosPerdidos.ObjetosPerdidosItemAdapter;
import com.discometro.Pair;
import com.discometro.R;
import com.discometro.User.User;

import com.discometro.ViewModels.ViewModelPerfilDiscoActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;

public class PerfilDiscoActivity extends AppCompatActivity implements BotonesPerfilDisco {

    private ImageButton ib_1, ib_2, ib_3, ib_4;
    private ImageView logo, banner;
    private FloatingActionButton fab_msg, fab_items, fab_favs, fab_subs;
    private TextView descripcion;
    private ViewModelPerfilDiscoActivity vm;

    Intent intent;
    private User u;
    private String correo;
    private String nameDisco;

    private ArrayList<String> positionImages;
    private int count=0;

    private PerfilDisco disco;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_disco);




        intent=getIntent();
        Bundle extras = intent.getExtras();
        correo = extras.getString("usuario");
        nameDisco= extras.getString("disco");

        setLiveDataObservers();
        vm.iniUser(correo);

        positionImages=new ArrayList<>();
        positionImages.add("banner");
        positionImages.add("foto1");
        positionImages.add("foto2");
        positionImages.add("foto3");
        positionImages.add("foto4");
        positionImages.add("logo");


        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        ib_1 = findViewById(R.id.ib_discoprofile_pic1);
        ib_2 = findViewById(R.id.ib_discoprofile_pic2);
        ib_3 = findViewById(R.id.ib_discoprofile_pic3);
        ib_4 = findViewById(R.id.ib_discoprofile_event1);
        fab_msg = findViewById(R.id.FAB_discoprofile_message);
        fab_items = findViewById(R.id.FAB_discoprofile_object);
        fab_favs = findViewById(R.id.FAB_discoprofile_favs);
        fab_subs = findViewById(R.id.FAB_discoprofile_subscribe);
        logo= findViewById(R.id.iv_discoprofile_logo);
        banner=findViewById(R.id.iv_discoprofile_banner);
        descripcion= findViewById(R.id.tv__discoprofile_descr);

        changeVisibility(4);








        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent vuelta = new Intent(getApplicationContext(),MainActivity.class);
                 vuelta.putExtra("usuario",u.getCorreo());
                startActivity(vuelta);

            }
        };
        getOnBackPressedDispatcher().addCallback(this,callback);

    }

    @Override
    public void showImage(View view) {
        Dialog builder = new Dialog(this);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {

            }
        });

        ImageView imageView = new ImageView(this);
        if (view.equals(ib_1)) {

        }
        else if (view.equals(ib_2)) {

        }
        else if (view.equals(ib_3)) {

        }
        else if (view.equals(ib_4)) {

        }
        builder.addContentView(imageView, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        builder.show();
    }

    public void inicializarFotos(){

        String banner_text =disco.getBanner();
        String descripcion_text=disco.getDescripcion();
        vm.iniBitmap(banner_text,"banner");
        changeVisibility(4);

        descripcion.setText(descripcion_text);

    }

    public void setLiveDataObservers() {
        //Subscribe the activity to the observable
        vm = new ViewModelProvider(this).get(ViewModelPerfilDiscoActivity.class);
        final Observer<User> observerUsuario = new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (vm.getUser().getValue().equals(null)) {
                    Toast.makeText(PerfilDiscoActivity.this, "Se ha producido un error", Toast.LENGTH_SHORT).show();
                } else {
                    u= vm.getUser().getValue();
                }
            }
        };

        final Observer<Pair> hashMapObserver = new Observer<Pair>() {
            @Override
            public void onChanged(Pair pair) {
                if(vm.getMapImagesObjects().getValue().equals(null)){

                }
                else{
                    String position= vm.getMapImagesObjects().getValue().getIdentificador();
                    Bitmap bm = vm.getMapImagesObjects().getValue().getBitmap();

                    if(position.equals("banner")){
                        banner.setImageBitmap(bm);
                        vm.iniBitmap(disco.getFoto1(),"foto1");
                    }
                    else if(position.equals("foto1")){
                        ib_1.setImageBitmap(bm);
                        vm.iniBitmap(disco.getFoto2(),"foto2");
                    }
                    else if(position.equals("foto2")){
                        ib_2.setImageBitmap(bm);
                        vm.iniBitmap(disco.getFoto3(),"foto3");
                    }
                    else if(position.equals("foto3")){
                        ib_3.setImageBitmap(bm);
                        vm.iniBitmap(disco.getFoto4(),"foto4");
                    }
                    else if(position.equals("foto4")){
                        ib_4.setImageBitmap(bm);
                        vm.iniBitmap(disco.getLogo(),"logo");
                    }
                    else if(position.equals("logo")){
                        logo.setImageBitmap(bm);
                        changeVisibility(0);
                    }
                }
            }
        };
        final Observer<ArrayList<PerfilDisco>> observerDiscos = new Observer<ArrayList<PerfilDisco>>() {
            @Override
            public void onChanged(ArrayList<PerfilDisco> perfilDiscos) {
                if (vm.getDiscos().getValue().equals(null)) {

                } else {
                    disco = vm.getDiscoByName(nameDisco);
                    inicializarFotos();
                }
            }
        };

        final Observer<String> observerToast = new Observer<String>() {
            @Override
            public void onChanged(String t) {
                Toast.makeText(PerfilDiscoActivity.this, t, Toast.LENGTH_SHORT).show();
            }
        };

        vm.getToast().observe(this, observerToast);
        vm.getUser().observe(this, observerUsuario);
        vm.getDiscos().observe(this,observerDiscos);
        vm.getMapImagesObjects().observe(this,hashMapObserver);
    }





    @Override
    public void intentToEmail(View view) {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto",disco.getCorreo(), null));
        startActivity(Intent.createChooser(intent, "Send email..."));
    }

    @Override
    public void intentToObjetosPerdidos(View view) {

        Intent enviarDatos = new Intent(this, ObjetosPerdidosActivity.class);
        Bundle extras = new Bundle();
        extras.putString("nameDisco",disco.getNameDisco());
        extras.putString("usuario",correo);
        enviarDatos.putExtras(extras);
        startActivity(enviarDatos);



    }

    public void añadirFavorito(View view){
        if(!u.getListFavoritos().contains(disco.getLogoCoded())){
            u.añadirFavorito(disco.getLogoCoded());
            vm.saveUser(u);
            Toast.makeText(getApplicationContext(),"Se ha añadido "+disco.getNameDisco()+" a favoritos", Toast.LENGTH_SHORT).show();

        }
        else if(u.getListFavoritos().contains(disco.getLogoCoded())){
            Toast.makeText(getApplicationContext(),disco.getNameDisco() + " ya se encuentra en favoritos", Toast.LENGTH_SHORT).show();
        }
    }

    public void añadirSuscripción(View view){

        if(!u.getListSuscripciones().contains(disco.getLogoCoded())){
            u.añadirSuscripcion(disco.getLogoCoded());
            vm.saveUser(u);
            Toast.makeText(getApplicationContext(),"Se ha añadido "+disco.getNameDisco()+" a suscripciones", Toast.LENGTH_SHORT).show();
        }
        else if(u.getListSuscripciones().contains(disco.getLogoCoded())){
            Toast.makeText(getApplicationContext(),disco.getNameDisco() + " ya se encuentra en suscripciones", Toast.LENGTH_SHORT).show();
        }
    }

    public void changeVisibility(int visibility){
        banner.setVisibility(visibility);
        ib_1.setVisibility(visibility);
        ib_2.setVisibility(visibility);
        ib_3.setVisibility(visibility);
        ib_4.setVisibility(visibility);
        descripcion.setVisibility(visibility);
    }
}
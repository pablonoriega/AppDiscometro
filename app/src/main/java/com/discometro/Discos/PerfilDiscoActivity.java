package com.discometro.Discos;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.discometro.R;
import com.discometro.User.User;

import com.discometro.ViewModels.ViewModelPerfilDiscoActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

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
            imageView.setImageResource(Integer.parseInt(disco.getFoto1()));
        }
        else if (view.equals(ib_2)) {
            imageView.setImageResource(Integer.parseInt(disco.getFoto2()));
        }
        else if (view.equals(ib_3)) {
            imageView.setImageResource(Integer.parseInt(disco.getFoto3()));
        }
        else if (view.equals(ib_4)) {
            imageView.setImageResource(Integer.parseInt(disco.getFoto4()));
        }
        builder.addContentView(imageView, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        builder.show();
    }

    public void inicializarFotos(){

        String foto1 = disco.getFoto1();
        String foto2 = disco.getFoto2();
        String foto3 = disco.getFoto3();
        String foto4 = disco.getFoto4();
        String logo_text = disco.getLogo();
        String banner_text =disco.getBanner();
        String descripcion_text=disco.getDescripcion();



        ib_1.setImageResource(Integer.parseInt(foto1));
        ib_2.setImageResource(Integer.parseInt(foto2));
        ib_3.setImageResource(Integer.parseInt(foto3));
        ib_4.setImageResource(Integer.parseInt(foto4));
        logo.setImageResource(Integer.parseInt(logo_text));
        banner.setImageResource(Integer.parseInt(banner_text));
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
        if(!u.getListFavoritos().contains(disco.getLogo())){
            u.añadirFavorito(disco.getLogo());
            vm.saveUser(u);
            Toast.makeText(getApplicationContext(),"Se ha añadido "+disco.getNameDisco()+" a favoritos", Toast.LENGTH_SHORT).show();

        }
        else if(u.getListFavoritos().contains(disco.getLogo())){
            Toast.makeText(getApplicationContext(),disco.getNameDisco() + " ya se encuentra en favoritos", Toast.LENGTH_SHORT).show();
        }
    }

    public void añadirSuscripción(View view){

        if(!u.getListSuscripciones().contains(disco.getLogo())){
            u.añadirSuscripcion(disco.getLogo());
            vm.saveUser(u);
            Toast.makeText(getApplicationContext(),"Se ha añadido "+disco.getNameDisco()+" a suscripciones", Toast.LENGTH_SHORT).show();
        }
        else if(u.getListSuscripciones().contains(disco.getLogo())){
            Toast.makeText(getApplicationContext(),disco.getNameDisco() + " ya se encuentra en suscripciones", Toast.LENGTH_SHORT).show();
        }
    }
}
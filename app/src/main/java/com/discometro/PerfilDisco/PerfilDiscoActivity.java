package com.discometro.PerfilDisco;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

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

import com.discometro.MainActivity;
import com.discometro.R;
import com.discometro.User;
import com.discometro.ViewModel.ViewModelMain;
import com.discometro.objetosPerdidos.ObjetosPerdidosActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PerfilDiscoActivity extends AppCompatActivity implements BotonesPerfilDisco {

    private ImageButton ib_1, ib_2, ib_3, ib_4;
    private ImageView logo, banner;
    private FloatingActionButton fab_msg, fab_items, fab_favs, fab_subs;
    private TextView descripcion;
    private ViewModelMain vm;

    Intent intent;
    private User u;

    private PerfilDisco disco;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vm = new ViewModelProvider(this).get(ViewModelMain.class);
        intent=getIntent();
        Bundle extras = intent.getExtras();
        u= extras.getParcelable("usuario");
        disco= extras.getParcelable("disco");

        setContentView(R.layout.activity_perfil_disco);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        ib_1 = findViewById(R.id.ib_foto1_disco);
        ib_2 = findViewById(R.id.ib_foto2_disco);
        ib_3 = findViewById(R.id.ib_foto3_disco);
        ib_4 = findViewById(R.id.ib_event1_disco);
        fab_msg = findViewById(R.id.FAB_msg_disco);
        fab_items = findViewById(R.id.FAB_items_disco);
        fab_favs = findViewById(R.id.FAB_favs_disco);
        fab_subs = findViewById(R.id.FAB_subs_disco);
        logo= findViewById(R.id.logoDisco);
        banner=findViewById(R.id.banner_disco);
        descripcion= findViewById(R.id.descripcion_disco);

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




        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent vuelta = new Intent(getApplicationContext(), MainActivity.class);
                vuelta.putExtra("usuario",u);
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
        extras.putParcelable("usuario",u);
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
            u.eliminarFavorito(disco.getLogo());
            vm.saveUser(u);
            Toast.makeText(getApplicationContext(),"Se ha eliminado "+disco.getNameDisco() + " de favoritos", Toast.LENGTH_SHORT).show();
        }
    }

    public void añadirSuscripción(View view){

        if(!u.getListSuscripciones().contains(disco.getLogo())){
            u.añadirSuscripcion(disco.getLogo());
            vm.saveUser(u);
            Toast.makeText(getApplicationContext(),"Se ha añadido "+disco.getNameDisco()+" a suscripciones", Toast.LENGTH_SHORT).show();

        }
        else if(u.getListSuscripciones().contains(disco.getLogo())){
            u.eliminarSuscripcion(disco.getLogo());
            vm.saveUser(u);
            Toast.makeText(getApplicationContext(),"Se ha eliminado "+disco.getNameDisco() + " de suscripciones", Toast.LENGTH_SHORT).show();
        }
    }
}
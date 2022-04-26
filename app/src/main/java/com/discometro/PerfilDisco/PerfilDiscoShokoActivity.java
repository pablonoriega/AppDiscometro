package com.discometro.PerfilDisco;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.Toast;

import com.discometro.MainActivity;
import com.discometro.R;
import com.discometro.User;
import com.discometro.objetosPerdidos.ObjetosPerdidosActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PerfilDiscoShokoActivity extends AppCompatActivity implements BotonesPerfilDisco {

    private ImageButton ib_1, ib_2, ib_3, ib_4;
    private FloatingActionButton fab_msg, fab_items, fab_favs, fab_subs;
    Intent intent;
    private User u;
    private String name;
    private String numLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_disco_shoko);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        ib_1 = findViewById(R.id.ib_foto1_shoko);
        ib_2 = findViewById(R.id.ib_foto2_shoko);
        ib_3 = findViewById(R.id.ib_foto3_shoko);
        ib_4 = findViewById(R.id.ib_event1_shoko);
        fab_msg = findViewById(R.id.FAB_msg_shoko);
        fab_items = findViewById(R.id.FAB_items_shoko);
        fab_favs = findViewById(R.id.FAB_favs_shoko);
        fab_subs = findViewById(R.id.FAB_subs_shoko);

        intent=getIntent();
        u= intent.getParcelableExtra("usuario");
        numLogo= R.drawable.shoko_logo+"";
        name="Shoko";

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
            imageView.setImageResource(R.drawable.shoko_foto1);
        }
        else if (view.equals(ib_2)) {
            imageView.setImageResource(R.drawable.shoko_foto2);
        }
        else if (view.equals(ib_3)) {
            imageView.setImageResource(R.drawable.shoko_foto3);
        }
        else if (view.equals(ib_4)) {
            imageView.setImageResource(R.drawable.shoko_evento1);
        }
        builder.addContentView(imageView, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        builder.show();
    }

    @Override
    public void intentToEmail(View view) {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto","shoko@shoko.biz", null));
        startActivity(Intent.createChooser(intent, "Send email..."));
    }

    @Override
    public void intentToObjetosPerdidos(View view) {
        Intent intent = new Intent(this, ObjetosPerdidosActivity.class);
        startActivity(intent);
    }

    public void añadirFavorito(View view){
        if(!u.getListFavoritos().contains(numLogo)){
            u.añadirFavorito(numLogo);
            Toast.makeText(getApplicationContext(),"Se ha añadido "+name+" a favoritos", Toast.LENGTH_SHORT).show();


        }
    }
}
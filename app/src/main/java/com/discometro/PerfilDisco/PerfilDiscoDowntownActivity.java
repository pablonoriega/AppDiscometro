package com.discometro.PerfilDisco;

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

import com.discometro.R;
import com.discometro.objetosPerdidos.ObjetosPerdidosActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PerfilDiscoDowntownActivity extends AppCompatActivity implements BotonesPerfilDisco {

    private ImageButton ib_1, ib_2, ib_3;
    private FloatingActionButton fab_msg, fab_items, fab_favs, fab_subs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_disco_downtown);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        ib_1 = findViewById(R.id.ib_foto1_downtown);
        ib_2 = findViewById(R.id.ib_foto2_downtown);
        ib_3 = findViewById(R.id.ib_event_downtown);
        fab_msg = findViewById(R.id.FAB_msg_downtown);
        fab_items = findViewById(R.id.FAB_items_downtown);
        fab_favs = findViewById(R.id.FAB_favs_downtown);
        fab_subs = findViewById(R.id.FAB_subs_downtown);

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
            imageView.setImageResource(R.drawable.downtown_cap1);
        }
        else if (view.equals(ib_2)) {
            imageView.setImageResource(R.drawable.downtown_cap2);
        }
        else if (view.equals(ib_3)) {
            imageView.setImageResource(R.drawable.downtown_evento1);
        }
        builder.addContentView(imageView, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        builder.show();
    }

    @Override
    public void intentToEmail(View view) {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto","info@downtownbarcelona.es", null));
        startActivity(Intent.createChooser(intent, "Send email..."));
    }

    @Override
    public void intentToObjetosPerdidos(View view) {
        Intent intent = new Intent(this, ObjetosPerdidosActivity.class);
        startActivity(intent);
    }

    public void addFavs(View view) {

    }

    public void subscribe(View view) {

    }
}
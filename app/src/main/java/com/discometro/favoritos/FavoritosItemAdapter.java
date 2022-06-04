package com.discometro.favoritos;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.discometro.ObjetosPerdidos.ObjetosPerdidosItemAdapter;
import com.discometro.Pair;
import com.discometro.R;
import com.discometro.Suscripciones.SuscripcionesItemAdapter;
import com.discometro.User.User;
import com.discometro.ViewModels.ViewModelFavoritosFragment;
import com.discometro.ViewModels.ViewModelObjetosPerdidosActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FavoritosItemAdapter extends RecyclerView.Adapter<FavoritosItemAdapter.ViewHolderItems> {

    private List<FavoritosCardItem> listItems;
    private Button eliminar, visitarPerfil;
    private User u;
    private ViewModelFavoritosFragment vm;
    private ArrayList<String> discosArray;
    private ArrayList<ViewHolderItems> holderItemsArrayList;
    private int count;
    private FavoritosFragment favoritosFragment;

    public FavoritosItemAdapter(List<FavoritosCardItem> listItems, User u,ViewModelFavoritosFragment vm) {

        this.listItems = listItems;
        this.u=u;
        this.favoritosFragment = favoritosFragment;
        this.vm = vm;


    }

    @Override
    public ViewHolderItems onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favoritos_item_list, null, false);

        eliminar=view.findViewById(R.id.btn_favsitemlist_delete);
        visitarPerfil= view.findViewById(R.id.btn_favsitemlist_gotoprofile);

        return new ViewHolderItems(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderItems holder, int position) {
        String logoDisco= listItems.get(position).getLogoDisco();
        ((FavoritosItemAdapter.ViewHolderItems)holder).asignarItems(logoDisco);


        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (u.getListFavoritos().contains(logoDisco)) {
                    u.eliminarFavorito(logoDisco);
                    vm.saveUser(u);
                }
            }
        });
        visitarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameDisco = vm.getNamebyLogo(logoDisco);
                vm.llamarVisitarPerfil(nameDisco);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolderItems extends RecyclerView.ViewHolder {

        private TextView usuario, usuarioid;
        private ImageView logoDisco;
        private String nameDisco;

        public ViewHolderItems(@NonNull View itemView) {
            super(itemView);

            logoDisco= (ImageView) itemView.findViewById(R.id.iv_favsitemlist_logo);

        }
        public void asignarItems(String  nameDisco) {
            this.nameDisco=nameDisco;
            this.logoDisco.setImageResource(Integer.parseInt(nameDisco));

        }

    }

}

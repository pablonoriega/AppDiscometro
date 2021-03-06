package com.discometro.Suscripciones;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.discometro.R;
import com.discometro.User.User;
import com.discometro.ViewModels.ViewModelSuscripcionesFragment;

import java.util.List;

public class SuscripcionesItemAdapter extends RecyclerView.Adapter<SuscripcionesItemAdapter.ViewHolderItems> {

    private List<SuscripcionesCardItem> listItems;
    private Button eliminar,visitarPerfil;
    private User u;
    private ViewModelSuscripcionesFragment vm;
    private ImageView iv;

    public SuscripcionesItemAdapter(List<SuscripcionesCardItem> listItems, User u, ViewModelSuscripcionesFragment vm) {

        this.listItems = listItems;
        this.u=u;
        this.vm=vm;
    }

    @Override
    public ViewHolderItems onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.suscripciones_item_list, null, false);

        eliminar=view.findViewById(R.id.btn_subsitemlist_delete);
        visitarPerfil = view.findViewById(R.id.btn_subsitemlist_gotoprofile);
        iv = view.findViewById(R.id.iv_subsitemlist_logo);

        return new ViewHolderItems(view);
    }


    @Override
    public void onBindViewHolder(ViewHolderItems holder, int position) {
        String fotoLogo= listItems.get(position).getFotoLogo();

        ((ViewHolderItems)holder).asignarItems(fotoLogo);
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (u.getListSuscripciones().contains(fotoLogo)) {
                    u.eliminarSuscripcion(fotoLogo);
                    vm.saveUser(u);
                }
            }
        });
        visitarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //hay que conseguir el nombre de  la discoteca a traves del numero del logo
                String name = vm.getNamebyLogo(fotoLogo);
                vm.llamarVisitarPerfil(name);
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

            logoDisco= (ImageView) itemView.findViewById(R.id.iv_subsitemlist_logo);

        }
        public void asignarItems(String nameDisco) {
            this.nameDisco=nameDisco;
            this.logoDisco.setImageResource(Integer.parseInt(nameDisco));

        }

    }
}

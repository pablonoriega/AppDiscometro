package com.discometro.VueltaSegura;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.discometro.Discos.PerfilDisco;
import com.discometro.R;
import com.discometro.ViewModels.ViewModelVueltaSeguraFragment;

import java.util.ArrayList;
import java.util.List;

public class VueltaSeguraItemAdapter extends RecyclerView.Adapter<VueltaSeguraItemAdapter.ViewHolderItems> {

    private List<VueltaSeguraCardItem> listItems;
    private String correo;
    private ViewModelVueltaSeguraFragment vm;
    private Button solicitar,visualizarUbicacion;
    private VueltaSeguraFragment vueltaSeguraFragment;
    private String location;

    public VueltaSeguraItemAdapter(List<VueltaSeguraCardItem> listItems, String correo, ViewModelVueltaSeguraFragment vm,VueltaSeguraFragment vueltaSeguraFragment) {
        this.listItems = listItems;
        this.correo = correo;
        this.vm = vm;
        this.vueltaSeguraFragment=vueltaSeguraFragment;
    }

    @Override
    public ViewHolderItems onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vuelta_segura_item_list, null, false);
        solicitar = view.findViewById(R.id.btn_safereturnitemlist_join);
        visualizarUbicacion = view.findViewById(R.id.visualizar_ubicacion);

        return new ViewHolderItems(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderItems holder, int position) {
        VueltaSeguraCardItem card = listItems.get(position);
        String usuario = card.getName();
        String usuarioid = card.getUsuarioid();
        String vehicle = card.getVehicle();
        location = card.getLocation();
        String fotoLogo = card.getFotoLogo();
        String number = card.getNumber();
        ArrayList<String> userList = card.getUserList();
        if (userList.contains(correo)) {
            ((ViewHolderItems) holder).asignarItems(usuario, usuarioid, vehicle, fotoLogo, true);
        } else {
            ((ViewHolderItems) holder).asignarItems(usuario, usuarioid, vehicle, fotoLogo, false);
        }
        solicitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (card.afegirUserToList(correo)) {
                    vm.saveVueltaSeguraCard(card);
                    Toast.makeText(view.getContext(), "Te has unido a la vuelta", Toast.LENGTH_SHORT).show();
                }
            }
        });
        visualizarUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent visualizar = new Intent(vueltaSeguraFragment.getContext(),MostrarVueltaSeguraActivity.class);
                PerfilDisco disco = vm.getDiscoByLogo(fotoLogo);

                Bundle extras= new Bundle();
                extras.putParcelable("disco",disco);
                extras.putString("ubiCasa",location);
                visualizar.putExtras(extras);
                vueltaSeguraFragment.startActivity(visualizar);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolderItems extends RecyclerView.ViewHolder {

        private TextView usuario, usuarioid, vehicle, location;
        private ImageView foto_logo;

        public ViewHolderItems(@NonNull View itemView) {
            super(itemView);
            usuario = (TextView) itemView.findViewById(R.id.tv_safereturnitemlist_username);
            usuarioid = (TextView) itemView.findViewById(R.id.tv_safereturnitemlist_useremail);
            vehicle = (TextView) itemView.findViewById(R.id.tv_safereturnitemlist_origin);
            foto_logo= (ImageView)  itemView.findViewById(R.id.iv_safereturnitemlist_logo);


        }

        public void asignarItems(String usuario, String usuarioid, String vehicle, String fotoLogo, boolean bool) {
            this.usuario.setText(usuario);
            this.usuarioid.setText(usuarioid);
            this.vehicle.setText(vehicle);
            this.foto_logo.setImageResource(Integer.parseInt(fotoLogo));
            if (bool) {
                solicitar.setVisibility(View.INVISIBLE);
            }
        }
    }
}

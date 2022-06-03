package com.discometro.VueltaSegura;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.discometro.R;
import com.discometro.ViewModels.ViewModelVueltaSeguraFragment;

import java.util.ArrayList;
import java.util.List;

public class VueltaSeguraItemAdapter extends RecyclerView.Adapter<VueltaSeguraItemAdapter.ViewHolderItems> {

    private List<VueltaSeguraCardItem> listItems;
    private String correo;
    private ViewModelVueltaSeguraFragment vm;
    private Button solicitar;

    public VueltaSeguraItemAdapter(List<VueltaSeguraCardItem> listItems, String correo, ViewModelVueltaSeguraFragment vm) {
        this.listItems = listItems;
        this.correo = correo;
        this.vm = vm;
    }

    @Override
    public ViewHolderItems onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vuelta_segura_item_list, null, false);
        solicitar = view.findViewById(R.id.btn_safereturnitemlist_join);
        return new ViewHolderItems(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderItems holder, int position) {
        VueltaSeguraCardItem card = listItems.get(position);
        String usuario = card.getName();
        String usuarioid = card.getUsuarioid();
        String vehicle = card.getVehicle();
        String location = card.getLocation();
        String fotoLogo = card.getFotoLogo();
        String number = card.getNumber();
        ArrayList<String> userList = card.getUserList();
        if (userList.contains(correo)) {
            ((ViewHolderItems) holder).asignarItems(usuario, usuarioid, vehicle, location, fotoLogo, true);
        } else {
            ((ViewHolderItems) holder).asignarItems(usuario, usuarioid, vehicle, location, fotoLogo, false);
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
            location = (TextView) itemView.findViewById(R.id.tv_safereturnitemlist_dest);
            foto_logo= (ImageView)  itemView.findViewById(R.id.iv_safereturnitemlist_logo);


        }

        public void asignarItems(String usuario, String usuarioid, String vehicle, String location, String fotoLogo, boolean bool) {
            this.usuario.setText(usuario);
            this.usuarioid.setText(usuarioid);
            this.vehicle.setText(vehicle);
            this.location.setText(location);
            this.foto_logo.setImageResource(Integer.parseInt(fotoLogo));
            if (bool) {
                solicitar.setVisibility(View.INVISIBLE);
            }
        }
    }
}

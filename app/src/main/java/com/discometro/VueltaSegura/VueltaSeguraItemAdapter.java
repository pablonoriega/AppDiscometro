package com.discometro.VueltaSegura;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.discometro.R;
import com.discometro.User;
import com.discometro.ViewModel.ViewModelMain;

import java.util.ArrayList;
import java.util.List;

public class VueltaSeguraItemAdapter extends RecyclerView.Adapter<VueltaSeguraItemAdapter.ViewHolderItems> {

    private List<VueltaSeguraCardItem> listItems;
    private Button solicitar;
    private User u;
    private ViewModelMain vm;

    public VueltaSeguraItemAdapter(List<VueltaSeguraCardItem> listItems, User u, ViewModelMain vm) {
        this.listItems = listItems;
        this.u = u;
        this.vm = vm;
    }

    @Override
    public VueltaSeguraItemAdapter.ViewHolderItems onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vuelta_segura_item_list, null, false);
        solicitar = view.findViewById(R.id.btn_safereturnitemlist_join);
        return new ViewHolderItems(view);
    }

    @Override
    public void onBindViewHolder(VueltaSeguraItemAdapter.ViewHolderItems holder, int position) {
        VueltaSeguraCardItem card = listItems.get(position);
        String usuario = card.getName();
        String usuarioid = card.getUsuarioid();
        String vehicle = card.getVehicle();
        String location = card.getLocation();
        String fotoLogo = card.getFotoLogo();
        ArrayList<String> userList = card.getUserList();
        if (userList.contains(u.getCorreo())) {
            ((ViewHolderItems) holder).asignarItems(usuario, usuarioid, vehicle, location, fotoLogo, true);
        }
        else {
            ((ViewHolderItems) holder).asignarItems(usuario, usuarioid, vehicle, location, fotoLogo, false);
        }

        solicitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (card.afegirUserToList(u)) {
                    vm.saveVueltaSeguraCard(card);
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
        private Button solicitar;

        public ViewHolderItems(@NonNull View itemView) {
            super(itemView);
            usuario = (TextView) itemView.findViewById(R.id.tv_safereturnitemlist_username);
            usuarioid = (TextView) itemView.findViewById(R.id.tv_safereturnitemlist_useremail);
            vehicle = (TextView) itemView.findViewById(R.id.tv_safereturnitemlist_origin);
            location = (TextView) itemView.findViewById(R.id.tv_safereturnitemlist_dest);
            foto_logo= (ImageView)  itemView.findViewById(R.id.iv_safereturnitemlist_logo);
            solicitar = (Button) itemView.findViewById(R.id.btn_safereturnitemlist_join);

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

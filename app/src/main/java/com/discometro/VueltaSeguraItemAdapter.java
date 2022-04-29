package com.discometro;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class VueltaSeguraItemAdapter extends RecyclerView.Adapter<VueltaSeguraItemAdapter.ViewHolderItems> {

    private List<VueltaSeguraCardItem> listItems;

    public VueltaSeguraItemAdapter(List<VueltaSeguraCardItem> listItems) {
        this.listItems = listItems;
    }

    @Override
    public VueltaSeguraItemAdapter.ViewHolderItems onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vuelta_segura_item_list, null, false);
        return new ViewHolderItems(view);
    }

    @Override
    public void onBindViewHolder(VueltaSeguraItemAdapter.ViewHolderItems holder, int position) {
        String usuario = listItems.get(position).getUsuario();
        String usuarioid = listItems.get(position).getUsuarioid();
        String vehicle = listItems.get(position).getVehicle();
        String location = listItems.get(position).getLocation();
        ((ViewHolderItems)holder).asignarItems(usuario, usuarioid, vehicle, location);
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolderItems extends RecyclerView.ViewHolder {

        private TextView usuario, usuarioid, vehicle, location;

        public ViewHolderItems(@NonNull View itemView) {
            super(itemView);
            usuario = (TextView) itemView.findViewById(R.id.nombreObj_objetos_perdidos_item);
            usuarioid = (TextView) itemView.findViewById(R.id.usuario_objetos_perdidos_item);
            vehicle = (TextView) itemView.findViewById(R.id.descripcion_objetos_perdidos_item);
            location = (TextView) itemView.findViewById(R.id.tv_location_vuelta_segura_item);
        }

        public void asignarItems(String usuario, String usuarioid, String vehicle, String location) {
            this.usuario.setText(usuario);
            this.usuarioid.setText(usuarioid);
            this.vehicle.setText(vehicle);
            this.location.setText(location);
        }
    }
}

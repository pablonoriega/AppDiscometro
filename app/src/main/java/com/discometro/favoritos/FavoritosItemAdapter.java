package com.discometro.favoritos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.discometro.R;
import com.discometro.User;
import com.discometro.ViewModel.ViewModelMain;

import java.util.List;

public class FavoritosItemAdapter extends RecyclerView.Adapter<FavoritosItemAdapter.ViewHolderItems> {

    private List<FavoritosCardItem> listItems;
    private Button eliminar;
    private User u;
    private ViewModelMain vm;

    public FavoritosItemAdapter(List<FavoritosCardItem> listItems, User u, ViewModelMain vm) {

        this.listItems = listItems;
        this.u=u;
        this.vm=vm;
    }

    @Override
    public FavoritosItemAdapter.ViewHolderItems onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favoritos_item_list, null, false);

        eliminar=view.findViewById(R.id.buttonEliminarSus);

        return new ViewHolderItems(view);
    }

    @Override
    public void onBindViewHolder(FavoritosItemAdapter.ViewHolderItems holder, int position) {
        String nameDisco= listItems.get(position).getNameDisco();

        ((ViewHolderItems)holder).asignarItems(nameDisco);
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

            logoDisco= (ImageView) itemView.findViewById(R.id.fotoLogoSus);

        }
        public void asignarItems(String nameDisco) {
            this.nameDisco=nameDisco;
            this.logoDisco.setImageResource(Integer.parseInt(nameDisco));

        }

    }
}

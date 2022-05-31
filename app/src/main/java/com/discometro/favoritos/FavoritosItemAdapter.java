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
import com.discometro.User.User;
import com.discometro.ViewModels.ViewModelFavoritosFragment;

import java.util.List;

public class FavoritosItemAdapter extends RecyclerView.Adapter<FavoritosItemAdapter.ViewHolderItems> {

    private List<FavoritosCardItem> listItems;
    private Button eliminar, visitarPerfil;
    private User u;
    private ViewModelFavoritosFragment vm;

    public FavoritosItemAdapter(List<FavoritosCardItem> listItems, User u, ViewModelFavoritosFragment vm) {

        this.listItems = listItems;
        this.u=u;
        this.vm=vm;
    }

    @Override
    public ViewHolderItems onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favoritos_item_list, null, false);

        eliminar=view.findViewById(R.id.eliminarFav);
        visitarPerfil= view.findViewById(R.id.visitar_perfil_favs);

        return new ViewHolderItems(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderItems holder, int position) {
        String nameDisco= listItems.get(position).getNameDisco();

        ((ViewHolderItems)holder).asignarItems(nameDisco);
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (u.getListFavoritos().contains(nameDisco)) {
                    u.eliminarFavorito(nameDisco);
                    vm.saveUser(u);
                }
            }
        });
        visitarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //hay que conseguir el nombre de  la discoteca a traves del numero del logo
                String name = vm.getNamebyLogo(nameDisco);
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

            logoDisco= (ImageView) itemView.findViewById(R.id.fotoLogoFavs);

        }
        public void asignarItems(String nameDisco) {
            this.nameDisco=nameDisco;
            this.logoDisco.setImageResource(Integer.parseInt(nameDisco));

        }

    }
}

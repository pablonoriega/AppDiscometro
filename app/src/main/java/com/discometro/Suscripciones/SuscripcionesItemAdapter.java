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
import com.discometro.User;
import com.discometro.ViewModel.ViewModelMain;

import java.util.List;

public class SuscripcionesItemAdapter extends RecyclerView.Adapter<SuscripcionesItemAdapter.ViewHolderItems> {

    private List<SuscripcionesCardItem> listItems;
    private Button eliminar;
    private User u;
    private ViewModelMain vm;
    private ImageView iv;

    public SuscripcionesItemAdapter(List<SuscripcionesCardItem> listItems, User u, ViewModelMain vm) {

        this.listItems = listItems;
        this.u=u;
        this.vm=vm;
    }

    @Override
    public SuscripcionesItemAdapter.ViewHolderItems onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.suscripciones_item_list, null, false);

        eliminar=view.findViewById(R.id.btn_subsitemlist_delete);
        iv = view.findViewById(R.id.iv_subsitemlist_logo);

        return new ViewHolderItems(view);
    }


    @Override
    public void onBindViewHolder(SuscripcionesItemAdapter.ViewHolderItems holder, int position) {
        String nameDisco= listItems.get(position).getNameDisco();

        ((ViewHolderItems)holder).asignarItems(nameDisco);
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (u.getListSuscripciones().contains(nameDisco)) {
                    u.eliminarSuscripcion(nameDisco);
                    vm.saveUser(u);
                }
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

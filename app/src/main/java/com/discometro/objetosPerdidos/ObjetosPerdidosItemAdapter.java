package com.discometro.objetosPerdidos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.discometro.R;

import java.util.List;

public class ObjetosPerdidosItemAdapter extends RecyclerView.Adapter<ObjetosPerdidosItemAdapter.ViewHolderItems> {

    private List<ObjetosPerdidosCardItem> listItems;

    public ObjetosPerdidosItemAdapter(List<ObjetosPerdidosCardItem> listItems) {
        this.listItems = listItems;
    }

    @Override
    public ObjetosPerdidosItemAdapter.ViewHolderItems onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.objetos_perdidos_item_list, null, false);
        return new ViewHolderItems(view);
    }

    @Override
    public void onBindViewHolder(ObjetosPerdidosItemAdapter.ViewHolderItems holder, int position) {
        String nombreObj = listItems.get(position).getUsuario();
        String usuario = listItems.get(position).getUsuario();
        String descripcion = listItems.get(position).getDescripcion();
        String imagen = listItems.get(position).getImagen();
        ((ViewHolderItems)holder).asignarItems(nombreObj, usuario, descripcion, imagen);
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolderItems extends RecyclerView.ViewHolder {

        private TextView nombreObj, usuario, descripcion;
        private ImageView imagen;

        public ViewHolderItems(@NonNull View itemView) {
            super(itemView);
            nombreObj = (TextView) itemView.findViewById(R.id.tv_objectitemlist_username);
            usuario = (TextView) itemView.findViewById(R.id.tv_objectitemlist_useremail);
            descripcion = (TextView) itemView.findViewById(R.id.tv_objectitemlist_descr);
            imagen = (ImageView) itemView.findViewById(R.id.iv_objectitemlist_objectimg);
        }

        public void asignarItems(String nombreObj, String usuario, String descripcion, String imagen) {
            this.nombreObj.setText(nombreObj);
            this.usuario.setText(usuario);
            this.descripcion.setText(descripcion);
            this.imagen.setImageResource(Integer.parseInt(imagen));
            //this.imagen.setImageURI(imagen);
        }
    }
}

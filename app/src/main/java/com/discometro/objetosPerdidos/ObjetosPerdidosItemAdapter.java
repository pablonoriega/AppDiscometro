package com.discometro.objetosPerdidos;

import android.media.Image;
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
        Image imagen = listItems.get(position).getImagen();
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
            nombreObj = (TextView) itemView.findViewById(R.id.nombreObj_objetos_perdidos_item);
            usuario = (TextView) itemView.findViewById(R.id.usuario_objetos_perdidos_item);
            descripcion = (TextView) itemView.findViewById(R.id.descripcion_objetos_perdidos_item);
            imagen = (ImageView) itemView.findViewById(R.id.objetoPerdidoImagen);
        }

        public void asignarItems(String nombreObj, String usuario, String descripcion, Image imagen) {
            this.nombreObj.setText(nombreObj);
            this.usuario.setText(usuario);
            this.descripcion.setText(descripcion);
            //this.imagen.setImageURI(imagen);
        }
    }
}

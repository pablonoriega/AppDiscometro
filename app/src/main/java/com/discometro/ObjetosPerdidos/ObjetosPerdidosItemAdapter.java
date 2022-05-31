package com.discometro.ObjetosPerdidos;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.discometro.R;
import com.discometro.Suscripciones.SuscripcionesItemAdapter;
import com.discometro.User.User;
import com.discometro.ViewModels.ViewModelObjetosPerdidosActivity;
import com.discometro.ViewModels.ViewModelPerfilUserFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ObjetosPerdidosItemAdapter extends RecyclerView.Adapter<ObjetosPerdidosItemAdapter.ViewHolderItems> {

    private List<ObjetosPerdidosCardItem> listItems;
    private String nombreObj,usuario,descripcion,imagenLogo;
    private Bitmap bmImagenObjeto;
    private ViewModelObjetosPerdidosActivity vm;
    private ObjetosPerdidosActivity objetosPerdidosActivity;
    private ArrayList<String> usersArray;
    private ArrayList<ViewHolderItems> holderItemsArrayList;
    private int count;

    public ObjetosPerdidosItemAdapter(List<ObjetosPerdidosCardItem> listItems,ObjetosPerdidosActivity objetosPerdidosActivity) {
        this.listItems = listItems;
        this.objetosPerdidosActivity=objetosPerdidosActivity;
        this.usersArray= new ArrayList<>();
        this.holderItemsArrayList = new ArrayList<>();
        this.count=0;
        setLiveDataObservers();
    }

    @Override
    public ViewHolderItems onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.objetos_perdidos_item_list, null, false);
        return new ViewHolderItems(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderItems holder, int position) {
        usuario = listItems.get(position).getUsuario();
        vm.iniBitmap(listItems.get(position).getImagenObjeto(),usuario);
        usersArray.add(usuario);
        holderItemsArrayList.add(holder);




    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }


    public void setLiveDataObservers() {
        //Subscribe the activity to the observable
        vm = new ViewModelProvider(objetosPerdidosActivity).get(ViewModelObjetosPerdidosActivity.class);

        final Observer<HashMap<String,Bitmap>> hashMapObserver = new Observer<HashMap<String, Bitmap>>() {
            @Override
            public void onChanged(HashMap<String, Bitmap> BitmapHashMap) {
                if(vm.getMapImagesObjects().getValue().equals(null)){

                }
                else{
                    Bitmap bm = vm.getMapImagesObjects().getValue().get(usersArray.get(count));
                    nombreObj = listItems.get(count).getNombreObj();
                    usuario = listItems.get(count).getUsuario();
                    descripcion = listItems.get(count).getDescripcion();
                    imagenLogo = listItems.get(count).getImagenLogo();
                    ViewHolderItems holder = holderItemsArrayList.get(count);
                    ((ObjetosPerdidosItemAdapter.ViewHolderItems)holder).asignarItems(nombreObj,usuario,descripcion,imagenLogo,bm);
                    count=count+1;

                }

            }
        };
        final Observer<String> observerToast = new Observer<String>() {
            @Override
            public void onChanged(String t) {
                Toast.makeText(objetosPerdidosActivity, t, Toast.LENGTH_SHORT).show();
            }
        };

        vm.getToast().observe(objetosPerdidosActivity, observerToast);
        vm.getMapImagesObjects().observe(objetosPerdidosActivity,hashMapObserver);
    }




    public class ViewHolderItems extends RecyclerView.ViewHolder {

        private TextView nombreObj, usuario, descripcion;
        private ImageView imagenLogo, imagenObjeto;

        public ViewHolderItems(@NonNull View itemView) {
            super(itemView);
            nombreObj = (TextView) itemView.findViewById(R.id.objePerdidoName);
            usuario = (TextView) itemView.findViewById(R.id.objePerdidoUser);
            descripcion = (TextView) itemView.findViewById(R.id.obje_PerdidosDesc);
            imagenLogo = (ImageView) itemView.findViewById(R.id.fotoLogoObjetosPerdidos);
            imagenObjeto = (ImageView) itemView.findViewById(R.id.objetoPerdidoImagenObjeto);
        }

        public void asignarItems(String nombreObj, String usuario, String descripcion, String imagen,Bitmap bmImagenObjeto) {
            this.nombreObj.setText(nombreObj);
            this.usuario.setText(usuario);
            this.descripcion.setText(descripcion);
            this.imagenLogo.setImageResource(Integer.parseInt(imagen));
            this.imagenObjeto.setImageBitmap(bmImagenObjeto);
        }

    }

}

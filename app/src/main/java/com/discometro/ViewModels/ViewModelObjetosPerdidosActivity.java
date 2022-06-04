package com.discometro.ViewModels;

import android.app.Application;
import android.graphics.Bitmap;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.discometro.Adapter.DataBaseAdapter;
import com.discometro.Discos.PerfilDisco;
import com.discometro.ObjetosPerdidos.ObjetosPerdidosCardItem;
import com.discometro.Pair;
import com.discometro.User.User;
import com.discometro.VueltaSegura.VueltaSeguraCardItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class ViewModelObjetosPerdidosActivity extends AndroidViewModel implements DataBaseAdapter.vmInterface{

    private MutableLiveData<ArrayList<ObjetosPerdidosCardItem>> mObjetoPerdido;
    private MutableLiveData<ArrayList<PerfilDisco>> mPerfilDisco;
    private DataBaseAdapter da;
    private MutableLiveData<User> mUser;
    private MutableLiveData<String> mToast;
    private MutableLiveData<Bitmap> mBitmap;
    private MutableLiveData<Pair> mImagenesObjetos;

    public ViewModelObjetosPerdidosActivity(@NonNull Application application) {
        super(application);
        mToast = new MutableLiveData<>();
        mUser = new MutableLiveData<>();
        mPerfilDisco = new MutableLiveData<>();
        mObjetoPerdido = new MutableLiveData<>();
        mBitmap = new MutableLiveData<>();
        mImagenesObjetos = new MutableLiveData<>();

        da = new DataBaseAdapter(this);
        da.getObjetosPerdidoCards();
        da.iniAllDiscos();
    }


    public void saveObjetoPerdidoCard(ObjetosPerdidosCardItem card){
        if(card!= null){
            mObjetoPerdido.getValue().add(card);
            mObjetoPerdido.setValue(mObjetoPerdido.getValue());
            da.saveObjetoPerdido(card);
        }
    }

    public LiveData<ArrayList<ObjetosPerdidosCardItem>> getAllObjetos(){
        return mObjetoPerdido;
    }


    public ArrayList<ObjetosPerdidosCardItem> getObjetosPerdidosCards(String nameDisco){

        ArrayList<ObjetosPerdidosCardItem> listAux = mObjetoPerdido.getValue();
        ArrayList<ObjetosPerdidosCardItem> listObjetos=new ArrayList<>();
        Iterator it = listAux.iterator();
        while (it.hasNext()){
            ObjetosPerdidosCardItem aux= (ObjetosPerdidosCardItem) it.next();
            if(aux.getNameDisco().equals(nameDisco)){
                listObjetos.add(aux);
            }
        }
        return listObjetos;
    }

    public PerfilDisco getDiscoByName(String name){
        ArrayList<PerfilDisco> listDiscos = mPerfilDisco.getValue();
        Iterator it = listDiscos.iterator();
        PerfilDisco u = null;
        while (it.hasNext()){
            PerfilDisco aux= (PerfilDisco) it.next();
            if(aux.getNameDisco().equals(name)){
                u=aux;
            }
        }
        return u;
    }

    @Override
    public void setToast(String s) {
        mToast.setValue(s);

    }

    @Override
    public void setDiscos(ArrayList<PerfilDisco> p) {
        mPerfilDisco.setValue(p);

    }

    @Override
    public void setUsuario(User u) {
        mUser.setValue(u);

    }

    @Override
    public void setObjetosPerdidos(ArrayList<ObjetosPerdidosCardItem> o) {
        mObjetoPerdido.setValue(o);


    }

    @Override
    public void setVueltaSeguraCards(ArrayList<VueltaSeguraCardItem> retrieved_s) {

    }

    @Override
    public void setVisitarPerfil(String m) {

    }

    @Override
    public void setBitmapPerfil(Bitmap p) {mBitmap.setValue(p); }

    @Override
    public void setBitmapImagenes(Pair pair) {
        mImagenesObjetos.setValue(pair);

    }


    @Override
    public void setVueltaSeguraCard(VueltaSeguraCardItem card) {

    }

    public void  SaveImage(Uri uri, String name){
        da.saveImage(uri,name);
    }


    public void iniUser(String correo){
        da.iniUser(correo);
    }

    public LiveData getUser(){
        return mUser;
    }

    public LiveData getToast(){
        return  mToast;
    }

    public void iniBitmap(String path,String user){
        da.cargarImagenesObjetos(path,user);

    }
    public LiveData<Bitmap> getBitmap(){
        return mBitmap;
    }
    public LiveData<Pair> getMapImagesObjects(){ return mImagenesObjetos;}
}

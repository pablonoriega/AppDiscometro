package com.discometro.ViewModels;

import android.app.Application;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.discometro.Adapter.DataBaseAdapter;
import com.discometro.Discos.PerfilDisco;
import com.discometro.ObjetosPerdidos.ObjetosPerdidosCardItem;
import com.discometro.User.User;
import com.discometro.VueltaSegura.VueltaSeguraCardItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class ViewModelSuscripcionesFragment extends AndroidViewModel implements DataBaseAdapter.vmInterface {

    private DataBaseAdapter da;
    private MutableLiveData<User> mUser;
    private MutableLiveData<String> mToast;
    private MutableLiveData<ArrayList<PerfilDisco>> mPerfilDisco;
    private MutableLiveData<String> mVisitarPerfil;

    public ViewModelSuscripcionesFragment(@NonNull Application application) {
        super(application);

        mToast = new MutableLiveData<>();
        mPerfilDisco = new MutableLiveData<>();
        mUser = new MutableLiveData<>();
        mVisitarPerfil = new MutableLiveData<>();
        da = new DataBaseAdapter(this);
        da.iniAllDiscos();
        mVisitarPerfil.setValue("null");

    }
    public void iniUser(String correo){
        da.iniUser(correo);
    }
    public LiveData<User> getUser(){
        return this.mUser;
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

    }

    @Override
    public void setVueltaSeguraCards(ArrayList<VueltaSeguraCardItem> retrieved_s) {

    }

    @Override
    public void setVisitarPerfil(String m) {
        mVisitarPerfil.setValue(m);

    }

    @Override
    public void setBitmapPerfil(Bitmap p) {

    }

    @Override
    public void setBitmapObjetosPerdidos(HashMap<String, Bitmap> map) {

    }


    public LiveData<String> getToast(){
        return mToast;
    }

    public void saveUser(User u){
        if(u!= null){
            mUser.setValue(u);
            da.saveUser(u);
        }
    }

    public LiveData<String> getVisitarPerfil(){ return mVisitarPerfil;}

    public void llamarVisitarPerfil(String nameDisco){
        da.cambiarVisitarPerfil(nameDisco);
    }

    public String getNamebyLogo(String logo){
        String nameDisco=null;
        ArrayList<PerfilDisco> listDiscos = mPerfilDisco.getValue();
        Iterator it = listDiscos.iterator();
        PerfilDisco u = null;
        while (it.hasNext()){
            PerfilDisco aux= (PerfilDisco) it.next();
            if(aux.getLogo().equals(logo)){
                nameDisco=aux.getNameDisco();
            }
        }
        return nameDisco;
    }

}



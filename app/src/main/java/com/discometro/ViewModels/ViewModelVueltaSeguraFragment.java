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


public class ViewModelVueltaSeguraFragment extends AndroidViewModel implements DataBaseAdapter.vmInterface {

    private MutableLiveData<ArrayList<VueltaSeguraCardItem>> mVueltaSegura;
    private MutableLiveData<ArrayList<PerfilDisco>> mPerfilDisco;
    private DataBaseAdapter da;
    private MutableLiveData<User> mUser;
    private MutableLiveData<String> mToast;

    public ViewModelVueltaSeguraFragment(@NonNull Application application) {
        super(application);
        mToast = new MutableLiveData<>();
        mUser = new MutableLiveData<>();
        mVueltaSegura = new MutableLiveData<>();
        mPerfilDisco = new MutableLiveData<>();

        da = new DataBaseAdapter(this);
        da.getVueltaSeguraCard();
        da.iniAllDiscos();
    }

    @Override
    public void setToast(String s) { mToast.setValue(s);}

    @Override
    public void setDiscos(ArrayList<PerfilDisco> p) { mPerfilDisco.setValue(p);}

    @Override
    public void setUsuario(User u) { mUser.setValue(u); }
    @Override
    public void setObjetosPerdidos(ArrayList<ObjetosPerdidosCardItem> o) { }

    @Override
    public void setVueltaSeguraCards(ArrayList<VueltaSeguraCardItem> retrieved_s) { mVueltaSegura.setValue(retrieved_s); }

    @Override
    public void setVisitarPerfil(String m) {

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
    public LiveData<User> getUser(){
        return  mUser;
    }
    public LiveData<ArrayList<VueltaSeguraCardItem>> getVueltaSeguraCards(){
        return mVueltaSegura;
    }
    public void iniUser(String correo){
        da.iniUser(correo);
    }

    public ArrayList<String> getNameDiscos(){
        ArrayList<PerfilDisco> listDiscos = mPerfilDisco.getValue();
        ArrayList<String>listNombres=new ArrayList<>();
        Iterator it = listDiscos.iterator();
        while (it.hasNext()){
            PerfilDisco aux= (PerfilDisco) it.next();
            listNombres.add(aux.getNameDisco());
        }
        return listNombres;
    }


    public void saveVueltaSeguraCard(VueltaSeguraCardItem card){
        if(card!= null){
            mVueltaSegura.getValue().add(card);
            mVueltaSegura.setValue(mVueltaSegura.getValue());
            da.saveVueltaSegura(card);
        }
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

}

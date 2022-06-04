package com.discometro.ViewModels;

import android.app.Application;
import android.graphics.Bitmap;

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

public class ViewModelRegistroActivity extends AndroidViewModel implements DataBaseAdapter.vmInterface {
    private DataBaseAdapter da;
    private MutableLiveData<User> mUser;
    private MutableLiveData<String> mToast;

    public ViewModelRegistroActivity(Application application) {
        super(application);

        mToast = new MutableLiveData<>();
        mUser = new MutableLiveData<>();
        da = new DataBaseAdapter(this);

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

    }

    @Override
    public void setBitmapPerfil(Bitmap p) {

    }

    @Override
    public void setBitmapImagenes(Pair pair) {

    }



    @Override
    public void setVueltaSeguraCard(VueltaSeguraCardItem card) {

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

}


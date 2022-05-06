package com.discometro.ViewModel;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import android.app.Application;
import android.widget.Toast;

import com.discometro.PerfilDisco.PerfilDisco;
import com.discometro.User;
import com.discometro.VueltaSegura.VueltaSeguraCardItem;
import com.discometro.dataBase.DataBaseAdapter;

import java.util.ArrayList;
import java.util.Iterator;

public class ViewModelMain extends AndroidViewModel implements DataBaseAdapter.vmInterface {



    DataBaseAdapter da;
    private MutableLiveData<ArrayList<User>> mUser;
    private MutableLiveData<String> mToast;
    private MutableLiveData<ArrayList<PerfilDisco>> mPerfilDisco;
    private MutableLiveData<ArrayList<VueltaSeguraCardItem>> mVueltaSegura;

    public ViewModelMain(Application application) {
        super(application);
        mToast = new MutableLiveData<>();
        mUser = new MutableLiveData<>();
        mVueltaSegura = new MutableLiveData<>();
        mPerfilDisco = new MutableLiveData<>();


        da = new DataBaseAdapter(this);
        da.getUsers();
        da.getDiscos();
        da.getVueltaSeguraCard();


    }





    @Override
    public void setToast(String s) {
        mToast.setValue(s);
    }

    @Override
    public void setUser(ArrayList<User> s) {mUser.setValue(s); }

    @Override
    public void setDiscos(ArrayList<PerfilDisco> p) {
        mPerfilDisco.setValue(p);
    }

    @Override
    public void setVueltaSeguraCards(ArrayList<VueltaSeguraCardItem> c) {
        mVueltaSegura.setValue(c);
    }

    public void saveUser(User u){
        if(u!= null){
            mUser.getValue().add(u);
            mUser.setValue(mUser.getValue());
            da.saveUser(u);
        }
    }

    public void saveCard(VueltaSeguraCardItem card){
        if(card!= null){
            mVueltaSegura.getValue().add(card);
            mVueltaSegura.setValue(mVueltaSegura.getValue());
            da.saveVueltaSegura(card);
        }
    }

    public ArrayList<User> getUser(){return mUser.getValue();}

    public User getUserById(String id){
        ArrayList<User> listUsers = mUser.getValue();
        Iterator it = listUsers.iterator();
        User u = null;

        while (it.hasNext()){
            User aux= (User) it.next();
            if(aux.getCorreo().equals(id)){
                u=aux;
            }
        }
        return u;
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

    public ArrayList<VueltaSeguraCardItem> getVueltaSeguraCards(){
        return mVueltaSegura.getValue();
    }




}
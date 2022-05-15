package com.discometro.ViewModel;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import android.app.Application;
import android.view.View;
import android.widget.Toast;

import com.discometro.AllDiscos;
import com.discometro.AllUsers;
import com.discometro.PerfilDisco.PerfilDisco;
import com.discometro.User;
import com.discometro.VueltaSegura.VueltaSeguraCardItem;
import com.discometro.dataBase.DataBaseAdapter;
import com.discometro.objetosPerdidos.ObjetosPerdidosCardItem;

import java.util.ArrayList;
import java.util.Iterator;

public class ViewModelMain extends AndroidViewModel implements DataBaseAdapter.vmInterface {



    private DataBaseAdapter da;
    private MutableLiveData<ArrayList<User>> mUser;
    private MutableLiveData<String> mToast;
    private MutableLiveData<ArrayList<PerfilDisco>> mPerfilDisco;
    private MutableLiveData<ArrayList<VueltaSeguraCardItem>> mVueltaSegura;
    private MutableLiveData<ArrayList<ObjetosPerdidosCardItem>> mObjetoPerdido;

    public ViewModelMain(Application application) {
        super(application);
        mToast = new MutableLiveData<>();
        mUser = new MutableLiveData<>();
        mVueltaSegura = new MutableLiveData<>();
        mPerfilDisco = new MutableLiveData<>();
        mObjetoPerdido = new MutableLiveData<>();

        da = new DataBaseAdapter(this);
        da.getUsers();
        da.getVueltaSeguraCard();
        da.getObjetosPerdidoCards();

        AllDiscos allDiscos  = AllDiscos.getInstance();
        if(allDiscos.getAllDiscos().isEmpty()){
            da.getDiscos();
            mPerfilDisco.setValue(allDiscos.getAllDiscos());
        }
        else {
            mPerfilDisco.setValue(allDiscos.getAllDiscos());
        }

        AllUsers allUsers = AllUsers.getInstance();
        if(allUsers.getAllUsers().isEmpty()) {
            da.getUsers();
            mUser.setValue(allUsers.getAllUsers());
        } else {
            mUser.setValue(allUsers.getAllUsers());
        }

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

    @Override
    public void setObjetosPerdidosCards(ArrayList<ObjetosPerdidosCardItem> c) {
        mObjetoPerdido.setValue(c);
    }

    public void saveUser(User u){
        if(u!= null){
            mUser.getValue().add(u);
            mUser.setValue(mUser.getValue());
            da.saveUser(u);
        }
    }

    public void saveVueltaSeguraCard(VueltaSeguraCardItem card){
        if(card!= null){
            mVueltaSegura.getValue().add(card);
            mVueltaSegura.setValue(mVueltaSegura.getValue());
            da.saveVueltaSegura(card);
        }
    }

    public void saveObjetoPerdidoCard(ObjetosPerdidosCardItem card){
        if(card!= null){
            mObjetoPerdido.getValue().add(card);
            mObjetoPerdido.setValue(mObjetoPerdido.getValue());
            da.saveObjetoPerdido(card);
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

    public ArrayList<PerfilDisco> getDiscos(){
        return mPerfilDisco.getValue();
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

    public ArrayList<ObjetosPerdidosCardItem> getObjetosPerdidosCards(String nameDisco){

        ArrayList<ObjetosPerdidosCardItem> listAux = mObjetoPerdido.getValue();
        ArrayList<ObjetosPerdidosCardItem>listObjetos=new ArrayList<>();
        Iterator it = listAux.iterator();
        while (it.hasNext()){
            ObjetosPerdidosCardItem aux= (ObjetosPerdidosCardItem) it.next();
            if(aux.getNameDisco().equals(nameDisco)){
                listAux.add(aux);
            }
        }
        return listAux;
    }





}
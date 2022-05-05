package com.discometro.ViewModel;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import android.app.Application;

import com.discometro.PerfilDisco.PerfilDisco;
import com.discometro.User;
import com.discometro.dataBase.DataBaseAdapter;

import java.util.ArrayList;
import java.util.Iterator;

public class ViewModelMain extends AndroidViewModel implements DataBaseAdapter.vmInterface {



    DataBaseAdapter da;
    private MutableLiveData<ArrayList<User>> mUser;
    private MutableLiveData<String> mToast;
    private MutableLiveData<ArrayList<PerfilDisco>> mPerfilDisco;

    public ViewModelMain(Application application) {
        super(application);
        mToast = new MutableLiveData<>();
        mUser = new MutableLiveData<>();
        mPerfilDisco = new MutableLiveData<>();

        da = new DataBaseAdapter(this);
        da.getUsers();
        da.getDiscos();
    }





    @Override
    public void setToast(String s) {
        mToast.setValue(s);
    }

    @Override
    public void setUser(ArrayList<User> s) {mUser.setValue(s); }

    @Override
    public void setDiscos(ArrayList<PerfilDisco> p) { mPerfilDisco.setValue(p); }

    public void addUser(User u){
        if(u!= null){
            mUser.getValue().add(u);
            mUser.setValue(mUser.getValue());
            da.saveUser(u);

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

    public String prueba(){
        return "prueba";
    }


}
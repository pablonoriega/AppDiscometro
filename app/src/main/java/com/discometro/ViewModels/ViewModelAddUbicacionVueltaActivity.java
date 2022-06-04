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
import com.discometro.Pair;
import com.discometro.User.User;
import com.discometro.VueltaSegura.VueltaSeguraCardItem;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewModelAddUbicacionVueltaActivity extends AndroidViewModel implements DataBaseAdapter.vmInterface{

    private DataBaseAdapter da;
    private MutableLiveData<String> mToast;
    private MutableLiveData<VueltaSeguraCardItem> mCard;


    public ViewModelAddUbicacionVueltaActivity(@NonNull Application application) {
        super(application);
        mCard = new MutableLiveData<>();
        mToast= new MutableLiveData<>();
        da = new DataBaseAdapter(this);
    }

    @Override
    public void setToast(String s) { mToast.setValue(s); }

    @Override
    public void setDiscos(ArrayList<PerfilDisco> p) { }

    @Override
    public void setUsuario(User u) { }

    @Override
    public void setObjetosPerdidos(ArrayList<ObjetosPerdidosCardItem> o) { }

    @Override
    public void setVueltaSeguraCards(ArrayList<VueltaSeguraCardItem> retrieved_s) { }

    @Override
    public void setVisitarPerfil(String m) { }

    @Override
    public void setBitmapPerfil(Bitmap p) { }

    @Override
    public void setBitmapImagenes(Pair pair) { }

    @Override
    public void setVueltaSeguraCard(VueltaSeguraCardItem card) { mCard.setValue(card);}

    public LiveData<VueltaSeguraCardItem> getVueltaSeguraCard(){return mCard;}
    public LiveData<String> getToast(){return mToast;}
    public void iniCard(String correoCard){
        da.iniVueltaSeguraCard(correoCard);
    }

    public void saveVueltaSeguraCard(VueltaSeguraCardItem card){
        if(card!= null){
            da.saveVueltaSegura(card);
        }
    }
}

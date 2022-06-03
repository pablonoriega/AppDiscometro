package com.discometro.Suscripciones;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.discometro.Discos.PerfilDiscoActivity;
import com.discometro.MainActivity.MainActivity;
import com.discometro.R;
import com.discometro.User.User;
import com.discometro.ViewModels.ViewModelSuscripcionesFragment;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SuscripcionesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SuscripcionesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Intent intent;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    private View view;
    private ArrayList<SuscripcionesCardItem> listItems;
    private ArrayList<String> listNombres;
    private RecyclerView recyclerView;
    private String correo;
    private User u;
    private ImageView imgLogo;
    private ViewModelSuscripcionesFragment vm;

    public SuscripcionesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SuscripcionesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SuscripcionesFragment newInstance(String param1, String param2) {
        SuscripcionesFragment fragment = new SuscripcionesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_suscripciones, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_fragmentsubs_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        setLiveDataObservers();
        correo = ((MainActivity)getActivity()).getCorreo();
        vm.iniUser(correo);
        return view;
    }

    public void setLiveDataObservers() {
        //Subscribe the activity to the observable
        vm = new ViewModelProvider(this).get(ViewModelSuscripcionesFragment.class);
        final Observer<User> observerUsuario = new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (vm.getUser().getValue().equals(null)) {
                    Toast.makeText(getActivity(), "El usuario o la contraseÃ±a son incorrectos", Toast.LENGTH_SHORT).show();
                } else {
                    listItems = new ArrayList<SuscripcionesCardItem>();
                    u=vm.getUser().getValue();
                    listNombres = u.getListSuscripciones();
                    if(!listNombres.isEmpty()){
                        for(int i=0; i<listNombres.size();i++){
                            String logoDisco=listNombres.get(i);
                            SuscripcionesCardItem card= new SuscripcionesCardItem(u.getName(), u.getCorreo(),logoDisco);
                            listItems.add(card);
                        }
                    }
                    SuscripcionesItemAdapter adapter = new SuscripcionesItemAdapter(listItems, u, vm);
                    recyclerView.setAdapter(adapter);
                }
            }
        };
        final Observer<String> observerVisitarPerfil = new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(vm.getVisitarPerfil().getValue().equals(null)){

                }
                else{
                    if(!vm.getVisitarPerfil().getValue().equals("null")){
                        String nameDisco = vm.getVisitarPerfil().getValue();
                        Intent intent = new Intent(getContext(), PerfilDiscoActivity.class);
                        Bundle extras = new Bundle();
                        extras.putString("usuario",u.getCorreo());
                        extras.putString("disco",nameDisco);
                        intent.putExtras(extras);
                        startActivity(intent);
                    }
                }
            }
        };

        final Observer<String> observerToast = new Observer<String>() {
            @Override
            public void onChanged(String t) {
                Toast.makeText(getActivity(), t, Toast.LENGTH_SHORT).show();
            }
        };

        vm.getToast().observe(getViewLifecycleOwner(), observerToast);
        vm.getUser().observe(getViewLifecycleOwner(), observerUsuario);
        vm.getVisitarPerfil().observe(getViewLifecycleOwner(),observerVisitarPerfil);
    }


}
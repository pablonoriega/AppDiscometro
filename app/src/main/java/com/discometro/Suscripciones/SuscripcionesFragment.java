package com.discometro.Suscripciones;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.discometro.MainActivity;
import com.discometro.R;
import com.discometro.User;
import com.discometro.ViewModel.ViewModelMain;

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
    private ViewModelMain vm;

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
        vm = new ViewModelProvider(getActivity()).get(ViewModelMain.class);
        listItems = new ArrayList<SuscripcionesCardItem>();

        if (((MainActivity)getActivity()).getUserCorreo() != null) {
            correo = ((MainActivity)getActivity()).getUserCorreo();
        }
        u = vm.getUserById(correo);


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

        return view;
    }


}
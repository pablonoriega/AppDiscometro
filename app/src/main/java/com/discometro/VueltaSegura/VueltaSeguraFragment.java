package com.discometro.VueltaSegura;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.discometro.MainActivity.MainActivity;
import com.discometro.R;
import com.discometro.User.User;

import com.discometro.ViewModels.ViewModelVueltaSeguraFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VueltaSeguraFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VueltaSeguraFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button btn;
    private List<VueltaSeguraCardItem> listItems;
    private RecyclerView recyclerView;
    private User u;
    private String correo;
    private ViewModelVueltaSeguraFragment vm;
    private FloatingActionButton btn_addCard;
    private View view;
    Intent intent;


    public VueltaSeguraFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static VueltaSeguraFragment newInstance(String param1, String param2) {
        VueltaSeguraFragment fragment = new VueltaSeguraFragment();
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
        view = inflater.inflate(R.layout.fragment_vuelta_segura, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewVuelta);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        setLiveDataObservers();
        correo = ((MainActivity)getActivity()).getCorreo();
        vm.iniUser(correo);


        btn_addCard= (FloatingActionButton) view.findViewById(R.id.btn_addcard);
        btn_addCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getContext(),AgregarVueltaSeguraActivity.class);
                intent.putExtra("usuario",correo);
                startActivity(intent);
            }
        });

        return view;


    }

    public void setLiveDataObservers() {
        //Subscribe the activity to the observable
        vm = new ViewModelProvider(this).get(ViewModelVueltaSeguraFragment.class);

        final Observer<User> observerUsuario = new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (vm.getUser().getValue().equals(null)) {
                    Toast.makeText(getContext(), "El usuario o la contraseÃ±a son incorrectos", Toast.LENGTH_SHORT).show();
                } else {
                    u=(User) vm.getUser().getValue();
                }
            }
        };
        final Observer<ArrayList<VueltaSeguraCardItem>> observerCards = new Observer<ArrayList<VueltaSeguraCardItem>>() {
            @Override
            public void onChanged(ArrayList<VueltaSeguraCardItem> vueltaSeguraCardItems) {
                if(vm.getVueltaSeguraCards().getValue().equals(null)){

                }
                else {
                    listItems= new ArrayList<>();
                    listItems= vm.getVueltaSeguraCards().getValue();
                    if(!listItems.isEmpty()){
                        VueltaSeguraItemAdapter adapter = new VueltaSeguraItemAdapter(listItems);
                        recyclerView.setAdapter(adapter);
                    }
                }
            }
        };


        final Observer<String> observerToast = new Observer<String>() {
            @Override
            public void onChanged(String t) {
                Toast.makeText(getContext(), t, Toast.LENGTH_SHORT).show();
            }
        };

        vm.getToast().observe(getViewLifecycleOwner(), observerToast);
        vm.getUser().observe(getViewLifecycleOwner(), observerUsuario);
        vm.getVueltaSeguraCards().observe(getViewLifecycleOwner(),observerCards);

    }









}
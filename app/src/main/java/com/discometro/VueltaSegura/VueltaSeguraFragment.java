package com.discometro.VueltaSegura;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.discometro.MainActivity;
import com.discometro.R;
import com.discometro.User;
import com.discometro.ViewModel.ViewModelMain;
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
    private static User u;
    private ViewModelMain vm;
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
        vm = new ViewModelProvider(getActivity()).get(ViewModelMain.class);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_fragmentsafereturn_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        if (u == null) {
            u = vm.getUserById(((MainActivity) getActivity()).getUser().getCorreo());
        }

        btn_addCard= (FloatingActionButton) view.findViewById(R.id.FAB_fragmentsafereturn_addsafereturn);
        btn_addCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getContext(),AgregarVueltaSeguraActivity.class);
                intent.putExtra("usuario",u);
                startActivity(intent);
            }
        });


        listItems = new ArrayList<VueltaSeguraCardItem>();
        listItems = vm.getVueltaSeguraCards();
        System.out.println(listItems.size() + "BBBB");
        if(!listItems.isEmpty()){
            VueltaSeguraItemAdapter adapter = new VueltaSeguraItemAdapter(listItems, u, vm);
            recyclerView.setAdapter(adapter);
        }

        return view;


    }









}
package com.discometro;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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

    public VueltaSeguraFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VueltaSeguraFragment.
     */
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
        View view = inflater.inflate(R.layout.fragment_vuelta_segura, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewVuelta);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        listItems = new ArrayList<VueltaSeguraCardItem>();
        VueltaSeguraCardItem cardItem = new VueltaSeguraCardItem("Adri", "@srnatsu", "Coche", "Badalona");
        listItems.add(cardItem);
        listItems.add(new VueltaSeguraCardItem("Pepe", "pepe", "bici", "bcn"));
        VueltaSeguraItemAdapter adapter = new VueltaSeguraItemAdapter(listItems);
        recyclerView.setAdapter(adapter);
        return view;
        //return inflater.inflate(R.layout.fragment_vuelta_segura, container, false);
    }



    public void intentToAgregarVueltaSegura(View view) {
        ((MainActivity)getActivity()).intentToAgregarVueltaSegura(view);
    }
}
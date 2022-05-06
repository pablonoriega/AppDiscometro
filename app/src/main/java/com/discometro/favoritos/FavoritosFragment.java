package com.discometro.favoritos;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.discometro.MainActivity;
import com.discometro.R;
import com.discometro.User;
import com.discometro.ViewModel.ViewModelMain;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoritosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoritosFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArrayList<FavoritosCardItem> listItems;
    private ArrayList<String> listNombres;
    private RecyclerView recyclerView;
    private User u;
    private ImageView imgLogo;
    private ViewModelMain vm;


    public FavoritosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavoritosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavoritosFragment newInstance(String param1, String param2) {
        FavoritosFragment fragment = new FavoritosFragment();
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
        View view = inflater.inflate(R.layout.fragment_favoritos, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewFavs);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        vm = new ViewModelProvider(getActivity()).get(ViewModelMain.class);
        listItems = new ArrayList<FavoritosCardItem>();

        u=vm.getUserById(((MainActivity)getActivity()).getUser().getCorreo());
        listNombres = u.getListFavoritos();


        if(!listNombres.isEmpty()){
            for(int i=0; i<listNombres.size();i++){
                String logoDisco=listNombres.get(i);
                FavoritosCardItem card= new FavoritosCardItem(u.getName(), u.getCorreo(),logoDisco);
                listItems.add(card);
            }

        }


        FavoritosItemAdapter adapter = new FavoritosItemAdapter(listItems,u,vm);
        recyclerView.setAdapter(adapter);
        return view;

    }

}






package com.discometro;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.discometro.ViewModel.ViewModelMain;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PerfilUserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PerfilUserFragment extends Fragment {


    private TextView name,lastName,birthday,password, dni, email;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ImageView mImageView;
    private ImageButton cambiarImagen;
    private View view;
    private ViewModelMain vm;
    private User u;

    public PerfilUserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PerfilUserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PerfilUserFragment newInstance(String param1, String param2) {
        PerfilUserFragment fragment = new PerfilUserFragment();
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
        view = inflater.inflate(R.layout.fragment_perfil_user, container, false);
        vm = new ViewModelProvider(getActivity()).get(ViewModelMain.class);
        u=vm.getUserById(((MainActivity)getActivity()).getUser().getCorreo());

        name = (TextView) view.findViewById(R.id.tv_username);
        birthday = (TextView) view.findViewById(R.id.tv_bday_user);
        password = (TextView) view.findViewById(R.id.tv_password_user);
        dni = (TextView) view.findViewById(R.id.tv_DNI_user);
        email = (TextView) view.findViewById(R.id.tv_email_user);


        name.setText(u.getName());
        birthday.setText(u.getBirthday());
        password.setText(u.getContra());
        dni.setText(u.getDni());
        email.setText(u.getCorreo());


        mImageView = (ImageView) view.findViewById(R.id.imageView2);
        mImageView.setImageResource(R.drawable.ottozutz1);
        cambiarImagen = (ImageButton) view.findViewById(R.id.changePhotoBtn);
        cambiarImagen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                cargarImagen();
            }
        });
        return view;
    }


    public void cargarImagen() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent, "Seleccione la aplicación"), 10);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == -1){
            Uri path = data.getData();
            mImageView.setImageURI(path);
        }
    }





}
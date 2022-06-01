package com.discometro.User;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import com.discometro.Login.LoginActivity;
import com.discometro.MainActivity.MainActivity;
import com.discometro.R;
import com.discometro.User.User;
import com.discometro.ViewModels.ViewModelLoginActivity;
import com.discometro.ViewModels.ViewModelPerfilUserFragment;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;


public class PerfilUserFragment extends Fragment {


    private TextView name,lastName,birthday,password, dni, email;
    private ImageView mImageView;
    private View view;
    private Button establecerBtn,cambiarImagen;
    private ViewModelPerfilUserFragment vm;
    private User u;
    private final int IMG_REQUEST_ID = 10;
    private Uri imgUri;
    private String correo;

    public PerfilUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_perfil_user, container, false);
        name = (TextView) view.findViewById(R.id.tv_username);
        birthday = (TextView) view.findViewById(R.id.tv_bday_user);
        password = (TextView) view.findViewById(R.id.tv_password_user);
        dni = (TextView) view.findViewById(R.id.tv_DNI_user);
        email = (TextView) view.findViewById(R.id.tv_email_user);
        mImageView = (ImageView) view.findViewById(R.id.imageView2);
        cambiarImagen = (Button) view.findViewById(R.id.changeUserImage);
        cambiarImagen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                cargarImagen();

            }
        });

        setLiveDataObservers();
        correo = ((MainActivity)getActivity()).getCorreo();
        vm.iniUser(correo);

        return view;
    }


    public void cargarImagen() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Selecciona una imagen"),IMG_REQUEST_ID);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == IMG_REQUEST_ID && resultCode == Activity.RESULT_OK && data!= null && data.getData()!= null){

            imgUri = data.getData();
            try {
                Bitmap bitmapImg = MediaStore.Images.Media.getBitmap(this.getActivity().getContentResolver(), imgUri);
                guardarImagen();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public  void guardarImagen(){
        if (imgUri != null){
            vm.SaveImage(imgUri,"fotosPerfil/"+u.getCorreo());
            u.setUri("fotosPerfil/"+u.getCorreo());
            vm.saveUser(u);
            vm.iniBitmap(u.getUrl());
        }
    }

    public void setLiveDataObservers() {
        //Subscribe the activity to the observable
        vm = new ViewModelProvider(this).get(ViewModelPerfilUserFragment.class);
        final Observer<User> observerUsuario = new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (vm.getUser().getValue().equals(null)) {
                    Toast.makeText(getActivity(), "El usuario o la contraseÃ±a son incorrectos", Toast.LENGTH_SHORT).show();
                } else {

                    u = vm.getUser().getValue();
                    name.setText(u.getName());
                    birthday.setText(u.getBirthday());
                    password.setText(u.getContra());
                    dni.setText(u.getDni());
                    email.setText(u.getCorreo());
                    if(u.getUrl().equals("")){
                        vm.iniBitmap("fotosPerfil/defaultUser.jpg");

                    }
                    else {
                        vm.iniBitmap(u.getUrl());
                    }


                }
            }
        };

        final Observer<Bitmap> bitmapObserver = new Observer<Bitmap>() {
            @Override
            public void onChanged(Bitmap bitmap) {
              if(vm.getBitmap().getValue().equals(null)){

              }
              else {
                  Bitmap bm = vm.getBitmap().getValue();
                  mImageView.setImageBitmap(bm);
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
        vm.getBitmap().observe(getViewLifecycleOwner(),bitmapObserver);
    }

}
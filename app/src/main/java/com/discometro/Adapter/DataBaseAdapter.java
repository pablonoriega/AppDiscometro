package com.discometro.Adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.discometro.Discos.PerfilDisco;
import com.discometro.ObjetosPerdidos.ObjetosPerdidosCardItem;
import com.discometro.User.User;
import com.discometro.VueltaSegura.VueltaSeguraCardItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataBaseAdapter extends Activity {
    public static final String TAG = "DatabaseAdapter";

    public static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser user;

    public static DataBaseAdapter databaseAdapter;
    public static vmInterface listener;

    public DataBaseAdapter(vmInterface listener) {
        this.listener = listener;
        databaseAdapter = this;
        FirebaseFirestore.setLoggingEnabled(true);
        initFirebase();
    }

    public interface vmInterface {

        void setToast(String s);
        void setDiscos(ArrayList<PerfilDisco> p);
        void  setUsuario(User u);
        void setObjetosPerdidos(ArrayList<ObjetosPerdidosCardItem> o);
        void setVueltaSeguraCards(ArrayList<VueltaSeguraCardItem> retrieved_s);
        void setVisitarPerfil(String m);
        void setBitmapPerfil(Bitmap p);
        void setBitmapObjetosPerdidos(HashMap<String,Bitmap> map);
        void setVueltaSeguraCard(VueltaSeguraCardItem card);
    }

    public void initFirebase() {
        user = mAuth.getCurrentUser();
        if (user == null) {
            mAuth.signInAnonymously()
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInAnonymously:success");
                                listener.setToast("Authentication successful.");
                                user = mAuth.getCurrentUser();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInAnonymously:failure", task.getException());

                            }
                        }
                    });
        } else {
            listener.setToast("Authentication with current user.");

        }
    }


    public void saveUser (User u) {

        // Create a new user with a first and last name
        Map<String, Object> usuario = new HashMap<>();
        usuario.put("correo", u.getCorreo());
        usuario.put("contra",u.getContra());
        usuario.put("name", u.getName());
        usuario.put("birthday",u.getBirthday());
        usuario.put("surname",u.getSurname());
        usuario.put("dni",u.getDni());
        usuario.put("listFavoritos",u.getListFavoritos());
        usuario.put("url",u.getUrl());
        usuario.put("listSuscripciones",u.getListSuscripciones());

        Log.d(TAG, "saveUser");
        // Add a new document with a generated ID
        db.collection("users").document(u.getCorreo())
                .set(usuario)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }

    public void saveObjetoPerdido (ObjetosPerdidosCardItem card) {

        // Create a new user with a first and last name
        Map<String, Object> usuario = new HashMap<>();
        usuario.put("nombreObj", card.getNombreObj());
        usuario.put("usuario", card.getUsuario());
        usuario.put("descripcion",card.getDescripcion());
        usuario.put("imagenLogo",card.getImagenLogo());
        usuario.put("nameDisco",card.getNameDisco());
        usuario.put("imagenObjeto",card.getImagenObjeto());



        Log.d(TAG, "saveObjetoPerdido");
        // Add a new document with a generated ID
        db.collection("objetosPerdidos").document(card.getUsuario())
                .set(usuario)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }

    public void getObjetosPerdidoCards() {
        Log.d(TAG, "updateObjetosPerdidosCards");
        DataBaseAdapter.db.collection("objetosPerdidos")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            ArrayList<ObjetosPerdidosCardItem> retrieved_s = new ArrayList<ObjetosPerdidosCardItem>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                retrieved_s.add(new ObjetosPerdidosCardItem(document.getString("nombreObj"),document.getString("usuario"), document.getString("descripcion"),document.getString("imagenLogo"),document.getString("nameDisco"),document.getString("imagenObjeto")));
                            }

                            listener.setObjetosPerdidos(retrieved_s);





                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }





    public void iniUser(String correo) {
        Log.d(TAG, "updateUsers");
        DataBaseAdapter.db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            User u = null;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                if(document.getString("correo").equals(correo)) {
                                    u= new User(document.getString("correo"), document.getString("contra"), document.getString("name"), document.getString("birthday"), document.getString("surname"), document.getString("dni"), (ArrayList<String>) document.get("listFavoritos"), document.getString("url"), (ArrayList<String>) document.get("listSuscripciones"));
                                }
                            }
                            listener.setUsuario(u);

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }

    public void iniAllDiscos() {

        Log.d(TAG, "updateDiscos");
        DataBaseAdapter.db.collection("discos")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            ArrayList<PerfilDisco> retrieved_s = new ArrayList<PerfilDisco>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                retrieved_s.add(new PerfilDisco(document.getString("nameDisco"),document.getString("logo"),document.getString("foto1"),document.getString("foto2"),document.getString("foto3"),document.getString("foto4"),document.getString("correo"),document.getString("banner"),document.getString("descripcion"),document.getString("latitud"),document.getString("longitud")));
                            }
                            listener.setDiscos(retrieved_s);

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void getVueltaSeguraCard() {
        Log.d(TAG, "updateVueltaSeguraCards");
        DataBaseAdapter.db.collection("vueltaSegura")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            ArrayList<VueltaSeguraCardItem> retrieved_s = new ArrayList<VueltaSeguraCardItem>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                retrieved_s.add(new VueltaSeguraCardItem(document.getString("name"),document.getString("usuarioid"), document.getString("vehicle"),document.getString("location"),document.getString("number"),document.getString("origen"),document.getString("fotoLogo")));
                            }
                            listener.setVueltaSeguraCards(retrieved_s);

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }


    public void iniVueltaSeguraCard(String user) {
        Log.d(TAG, "updateVueltaSeguraCards");
        DataBaseAdapter.db.collection("vueltaSegura")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            VueltaSeguraCardItem card = null;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                if(document.getString("usuarioid").equals(user)){
                                    card=new VueltaSeguraCardItem(document.getString("name"),document.getString("usuarioid"), document.getString("vehicle"),document.getString("location"),document.getString("number"),document.getString("origen"),document.getString("fotoLogo"));
                                }
                            }
                            listener.setVueltaSeguraCard(card);

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void saveVueltaSegura (VueltaSeguraCardItem card) {

        // Create a new user with a first and last name
        Map<String, Object> usuario = new HashMap<>();
        usuario.put("name", card.getName());
        usuario.put("usuarioid",card.getUsuarioid());
        usuario.put("vehicle", card.getVehicle());
        usuario.put("location",card.getLocation());
        usuario.put("number",card.getNumber());
        usuario.put("origen",card.getOrigen());
        usuario.put("fotoLogo",card.getFotoLogo());


        Log.d(TAG, "saveVueltaSegura");
        // Add a new document with a generated ID
        db.collection("vueltaSegura").document(card.getUsuarioid())
                .set(usuario)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }

    public void saveImage(Uri uri, String name) {

        StorageReference reference = storageRef.child(name);
        reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                cargarFotoPerfil(name);
            }
        });
    }


    public void cargarFotoPerfil(String path){

        StorageReference photoReference= storageRef.child(path);

        final long ONE_MEGABYTE = 1024 * 1024;
        photoReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bmp =BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                listener.setBitmapPerfil(bmp);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(getApplicationContext(), "No Such file or Path found!!", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void cargarImagenesObjetos(String path,String usuario){

        StorageReference photoReference= storageRef.child(path);

        final long ONE_MEGABYTE = 1024 * 1024;
        photoReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bmp =BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                HashMap<String,Bitmap> map = new HashMap<>();
                map.put(usuario,bmp);
                listener.setBitmapObjetosPerdidos(map);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(getApplicationContext(), "No Such file or Path found!!", Toast.LENGTH_LONG).show();
            }
        });

    }


    public void cambiarVisitarPerfil(String nameDisco){
        listener.setVisitarPerfil(nameDisco);
    }



    /*
    public Uri getImage(String path){
        StorageReference reference = storageRef.child(path);
        final long megabyte = 1024*1024;
        reference.getBytes(megabyte).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {

            }
        });

    }

     */

/*

 public void saveDisco () {

        // Create a new user with a first and last name
        Map<String, Object> perfilDisco = new HashMap<>();

        perfilDisco.put("banner", R.drawable.downtown2+"");
        perfilDisco.put("correo", "info@downtownbarcelona.es");
        perfilDisco.put("descripcion", "     Edad mínima de 18 ");
        perfilDisco.put("foto1", R.drawable.downtown_cap1+"");
        perfilDisco.put("foto2", R.drawable.downtown_cap2+"");
        perfilDisco.put("foto3", R.drawable.downtown_evento1+"");
        perfilDisco.put("foto4", R.drawable.otto_evento2+"");
        perfilDisco.put("logo",R.drawable.downtownlogocrop+"");
        perfilDisco.put("nameDisco","Downtown");
        perfilDisco.put("latitud",...);
        perfilDisco.put("longitud",...);


        Log.d(TAG, "saveUser");
        // Add a new document with a generated ID
        db.collection("discos").document("Downtown")
                .set(perfilDisco)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }


 */








}

package com.discometro.dataBase;

import androidx.annotation.NonNull;

import android.app.Activity;
import android.net.Uri;
import android.util.Log;

import com.discometro.PerfilDisco.PerfilDisco;
import com.discometro.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.android.gms.tasks.Continuation;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataBaseAdapter extends Activity {

    public static final String TAG = "DatabaseAdapter";

    public static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseStorage storage = FirebaseStorage.getInstance();
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
        void setUser(ArrayList<User> s);
        void setDiscos(ArrayList<PerfilDisco> p);
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
                                listener.setToast("Authentication failed.");

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

    public void getUsers() {
        Log.d(TAG, "updateUsers");
        DataBaseAdapter.db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            ArrayList<User> retrieved_s = new ArrayList<User>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                retrieved_s.add(new User(document.getString("correo"),document.getString("contra"), document.getString("name"),document.getString("birthday"),document.getString("surname"),document.getString("dni"),(ArrayList<String>) document.get("listFavoritos"),document.getString("url")));
                            }
                            listener.setUser(retrieved_s);

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
    public void getDiscos() {
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
                                retrieved_s.add(new PerfilDisco(document.getString("nameDisco"),document.getString("logo"),document.getString("activity_perfil"),document.getString("ib_1"),document.getString("ib_2"),document.getString("ib_3"),document.getString("ib_4"),document.getString("fab_msg"),document.getString("fab_items"),document.getString("fab_favs"),document.getString("fab_subs"),document.getString("foto1"),document.getString("foto2"),document.getString("foto3"),document.getString("foto4"),document.getString("correo")));
                            }
                            listener.setDiscos(retrieved_s);

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

}








package com.discometro.dataBase;

import androidx.annotation.NonNull;

import android.app.Activity;
import android.net.Uri;
import android.util.Log;

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

    public static final String TAG ="DataBaseAdapter";

    public static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseStorage storage= FirebaseStorage.getInstance();
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private  FirebaseUser user;

    public static  DataBaseAdapter dataBaseAdapter;
    public static vmInterface listener;

    public DataBaseAdapter(vmInterface listener){
        this.listener = listener;
        dataBaseAdapter = this;
        FirebaseFirestore.setLoggingEnabled(true);
        initFirebase();
    }

    public interface vmInterface{
        void setCollection(ArrayList<User> usersArray);
        void setToast(String s);
    }


    public void initFirebase(){

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
        }
        else{
            listener.setToast("Authentication with current user.");

        }
    }


    public void getCollection(){
        Log.d(TAG,"updateUsers");
        DataBaseAdapter.db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            ArrayList<User> usersArray = new ArrayList<User>() ;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                usersArray.add(new User( document.getString("correo"), document.getString("contraseña")));
                            }
                            listener.setCollection(usersArray);

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }


    public void saveDocument (String correo, String contraseña, ArrayList<String> listFavoritos,String url) {

        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("correo", correo);
        user.put("contraseña", contraseña);
        user.put("listFavoritos", listFavoritos);
        user.put("url", url);


        Log.d(TAG, "saveDocument");
        // Add a new document with a generated ID
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }


    public void saveDocumentWithFile (String correo, String contraseña, ArrayList<String> listFavoritos) {

        //Uri file = Uri.fromFile(new File(path));
        StorageReference storageRef = storage.getReference();
        StorageReference userRef = storageRef.child("user"+File.separator+file.getLastPathSegment());
        UploadTask uploadTask = userRef.putFile(file);

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return userRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    saveDocument(correo, contraseña, listFavoritos, downloadUri.toString());
                } else {
                    // Handle failures
                    // ...
                }
            }
        });


        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                Log.d(TAG, "Upload is " + progress + "% done");
            }
        });
    }

    public HashMap<String, String> getDocuments () {
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

        return new HashMap<>();
    }

}








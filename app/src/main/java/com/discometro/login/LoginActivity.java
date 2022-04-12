package com.discometro.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.discometro.CarteraUser;
import com.discometro.MainActivity;
import com.discometro.R;
import com.discometro.User;
import com.discometro.resources.service.AbstractFactoryData;
import com.discometro.resources.service.DataService;
import com.discometro.resources.service.FactoryMOCK;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private CheckBox remember;
    private Button login;
    private CarteraUser carteraUser;
    private AbstractFactoryData factory;
    private DataService dataService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        email = findViewById(R.id.et_correo);
        password = findViewById(R.id.et_contra);
        remember = findViewById(R.id.cb_recuerdame);
        login = findViewById(R.id.btn_login);
        factory = new FactoryMOCK();
        dataService = new DataService(factory);
        try {
            iniCarteraUser();
        } catch (Exception e) {

        }
        loadPreferences();
    }

    public boolean iniCarteraUser() throws Exception {
        List<User> list = dataService.getAllUsers();
        if (list != null) {
            carteraUser = new CarteraUser(list);
            return true;
        }
        else {
            return false;
        }
    }

    public void intentToMap(View view) {
        String txt_email = email.getText().toString();
        String txt_pwd = password.getText().toString();
        if (txt_email.equals("") || txt_pwd.equals("")) {
            Toast.makeText(this, "Rellena los espacios", Toast.LENGTH_SHORT).show();
        }
        else {
            User u = carteraUser.find(txt_email);
            if (u == null) {
                Toast.makeText(this, "Correo incorrecto", Toast.LENGTH_SHORT).show();
            }
            else {
                if (!u.getContra().equals(txt_pwd)) {
                    Toast.makeText(this, "La contraseña no es correcta", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (remember.isChecked()) {
                        savePreferences();
                    }
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
            }
        }
    }

    public void savePreferences() {
        SharedPreferences preferences = getSharedPreferences("password", Context.MODE_PRIVATE);
        String correo = email.getText().toString();
        String pwd = password.getText().toString();

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("email", correo);
        editor.putString("password", pwd);

        email.setText(correo);
        password.setText(pwd);
        editor.commit();
    }

    public void loadPreferences() {
        SharedPreferences preferences = getSharedPreferences("password", Context.MODE_PRIVATE);
        String correo = preferences.getString("email", "");
        String pwd = preferences.getString("password", "");
        email.setText(correo);
        password.setText(pwd);
    }
}
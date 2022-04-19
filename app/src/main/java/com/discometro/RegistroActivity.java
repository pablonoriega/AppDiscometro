package com.discometro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.discometro.resources.service.DataService;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistroActivity extends AppCompatActivity {

    private EditText name;
    private EditText surname;
    private EditText email;
    private EditText dni;
    private EditText birthday;
    private EditText password;
    private EditText passwordRepeat;
    private CheckBox acceptTerms;
    private Button register;
    private CarteraUser carteraUser;
    private DataService dataService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        email = findViewById(R.id.email);
        surname = findViewById(R.id.surname);
        name = findViewById(R.id.name);
        dni = findViewById(R.id.dni);
        birthday = findViewById(R.id.birthDate);
        password = findViewById(R.id.password);
        passwordRepeat = findViewById(R.id.password2);
        acceptTerms = findViewById(R.id.acceptTerms);
        register = findViewById(R.id.registerButton);

        try {
            iniCarteraUser();
        } catch (Exception e) {

        }
    }

    public boolean isPasswordSafe(String password) {
        Pattern pattern = Pattern.compile("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,}$");
        Matcher matcher = pattern.matcher(password);
        return matcher.find();
    }

    public boolean isEmailSafe(String email) {
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }

    public String validatePassword(String password) {
        if (!isPasswordSafe(password)) {
            return "Contraseña no segura";
        } else
            return "Contraseña segura";
    }

    public String validateUsername(String email) {
        if (!isEmailSafe(email))
            return "Correo en formato incorrecto";
        else
            return "Correo en formato correcto";
    }

    public String registerUser(String email, String password) {
        if (isEmailSafe(email) && isPasswordSafe(password)) {
            User user = carteraUser.find(email);
            if (user != null) {
                return "Usuario Duplicado";
            } else return "Usuario Validado";
        } else return "Formato incorrecto";
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


}
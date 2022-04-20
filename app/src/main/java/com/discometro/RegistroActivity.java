package com.discometro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

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

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentToMapReg(view);
            }

        });
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

    public void registerUser(String email, String password, String password2, String name, String surname, String birthday, String dni, Boolean acceptedTerms) {

        if (email.equals("") || password.equals("") || password2.equals("") || name.equals("") || surname.equals("") || dni.equals("") || birthday.equals("")) {
            Toast.makeText(this, "Faltan campos por rellenar", Toast.LENGTH_SHORT).show();
        }

        if(acceptedTerms == Boolean.FALSE){
            Toast.makeText(this, "Debes aceptar los términos y condiciones", Toast.LENGTH_SHORT).show();
        }

        if(isEmailSafe(email) && isPasswordSafe(password)){
            User user = carteraUser.find(email);
            if(user != null){
                Toast.makeText(this, "Este usuario ya existe", Toast.LENGTH_SHORT).show();
            }
            if(password.equals(password2)){
                User u = new User(email, password);
                carteraUser.addUser(u);
            } else{
                Toast.makeText(this, "Asegúrate de introducir la misma contraseña", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "Formato incorrecto", Toast.LENGTH_SHORT).show();
        }

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

    public void intentToMapReg(View view) {
        String txt_email = email.getText().toString();
        String txt_pwd = password.getText().toString();
        String txt_pwd2 = passwordRepeat.getText().toString();
        String txt_name = name.getText().toString();
        String txt_surname = surname.getText().toString();
        String txt_dni = dni.getText().toString();
        String txt_birthday = birthday.getText().toString();
        Boolean acceptedTerms = acceptTerms.isChecked();

        registerUser(txt_email,txt_pwd,txt_pwd2,txt_name,txt_surname,txt_birthday,txt_dni,acceptedTerms);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        }
    }



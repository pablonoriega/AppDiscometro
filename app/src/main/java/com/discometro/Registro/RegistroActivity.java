package com.discometro.Registro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import com.discometro.Login.LoginActivity;
import com.discometro.MainActivity.MainActivity;
import com.discometro.R;
import com.discometro.User.User;

import com.discometro.ViewModels.ViewModelRegistroActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    private ViewModelRegistroActivity vm;
    private User u;
    private String txt_pwd,txt_pwd2,txt_email, txt_name, txt_birthday, txt_surname, txt_dni;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        email = findViewById(R.id.tv_register_email);
        surname = findViewById(R.id.tv_register_surname);
        name = findViewById(R.id.tv_register_name);
        dni = findViewById(R.id.tv_register_dni);
        birthday = findViewById(R.id.tv_register_birthday);
        password = findViewById(R.id.tv_register_password);
        passwordRepeat = findViewById(R.id.tv_register_passwordconfirm);
        acceptTerms = findViewById(R.id.cb_register_acceptterms);
        register = findViewById(R.id.btn_register_register);

        setLiveDataObservers();


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentToMapReg(view);
            }

        });

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent vuelta = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(vuelta);

            }
        };
        getOnBackPressedDispatcher().addCallback(this,callback);

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

    public boolean isBirthdayCorrect(String bday) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        try {
            Date date = sdf.parse(bday);
        } catch (Exception e) {
            Toast.makeText(this, "Fecha incorrecta\nDebe ser formato dd/mm/yyyy", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public boolean isDNICorrect(String dni) {
        String letra = "";
        if (dni.length() != 9 || Character.isLetter(dni.charAt(8)) == false) {
            return false;
        }

        letra = (dni.substring(8)).toUpperCase();
        if (onlyNumber(dni) && letraDNI(dni).equals(letra)) {
            return true;
        }
        return false;
    }

    public boolean onlyNumber(String dni) {
        String num = "";
        String miDNI = "";
        String[] digitos = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

        for (int i = 0; i < dni.length() - 1; i++) {
            num = dni.substring(i, i + 1);
            for (int j = 0; j < digitos.length; j++) {
                if (num.equals(digitos[j])) {
                    miDNI += digitos[j];
                }
            }
        }
        if (miDNI.length() != 8) {
            return false;
        }
        return true;
    }

    public String letraDNI(String dni) {
        int miDNI = Integer.parseInt(dni.substring(0, 8));
        int resto = 0;
        String miLetra = "";
        String[] letras = {"T", "R", "W", "A", "G", "M", "Y", "F", "P", "D", "X", "B", "N", "J", "Z",
                "S", "Q", "V", "H", "L", "C", "K", "E"};
        resto = miDNI % 23;
        miLetra = letras[resto];
        return miLetra;
    }


    public void intentToMapReg(View view) {
         txt_email = email.getText().toString();
         txt_pwd = password.getText().toString();
         txt_pwd2 = passwordRepeat.getText().toString();
         txt_name = name.getText().toString();
         txt_surname = surname.getText().toString();
         txt_dni = dni.getText().toString();
         txt_birthday = birthday.getText().toString();
        Boolean acceptedTerms = acceptTerms.isChecked();

        if (txt_email.equals("") || txt_pwd.equals("") || txt_pwd2.equals("") || txt_name.equals("") || txt_surname.equals("") || txt_dni.equals("") || txt_birthday.equals("")) {
            Toast.makeText(this, "Faltan campos por rellenar", Toast.LENGTH_SHORT).show();
        } else {
            if (!isEmailSafe(txt_email)) {
                Toast.makeText(this, "Correo inválido", Toast.LENGTH_SHORT).show();
            } else {
                if (!isPasswordSafe(txt_pwd)) {
                    Toast.makeText(this, "La contraseña debe contener mayúsculas, minúsculas y números", Toast.LENGTH_SHORT).show();
                } else {
                    if (isBirthdayCorrect(txt_birthday)) {
                        if (!isDNICorrect(txt_dni)) {
                            Toast.makeText(this, "DNI no válido", Toast.LENGTH_SHORT).show();
                        } else {
                            if (acceptedTerms == Boolean.FALSE) {
                                Toast.makeText(this, "Debes aceptar los términos y condiciones", Toast.LENGTH_SHORT).show();
                            } else {
                                vm.iniUser(txt_email);


                            }
                        }
                    }
                }

            }

        }
    }

    public void setLiveDataObservers() {
        //Subscribe the activity to the observable
        vm = new ViewModelProvider(this).get(ViewModelRegistroActivity.class);
        final Observer<User> observerUsuario = new Observer<User>() {
            @Override
            public void onChanged(User user) {
                u= vm.getUser().getValue();
                validacionFinal();
            }
        };

        final Observer<String> observerToast = new Observer<String>() {
            @Override
            public void onChanged(String t) {
                Toast.makeText(RegistroActivity.this, t, Toast.LENGTH_SHORT).show();
            }
        };

        vm.getToast().observe(this, observerToast);
        vm.getUser().observe(this, observerUsuario);
    }

    public void validacionFinal(){
        if (u != null) {
            //Toast.makeText(this, "Este usuario ya existe", Toast.LENGTH_SHORT).show();
        } else {
            if (txt_pwd.equals(txt_pwd2)) {
                User u = new User(txt_email, txt_pwd, txt_name, txt_birthday, txt_surname, txt_dni, new ArrayList<String>(), "", new ArrayList<String>());
                vm.saveUser(u);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("usuario", u.getCorreo());
                startActivity(intent);
            } else {
                Toast.makeText(this, "Asegúrate de introducir la misma contraseña", Toast.LENGTH_SHORT).show();
            }

        }
    }
}




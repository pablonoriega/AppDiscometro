package com.discometro.registro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.discometro.CarteraUser;
import com.discometro.MainActivity;
import com.discometro.R;
import com.discometro.User;
import com.discometro.ViewModel.ViewModelMain;
import com.discometro.map.MainMapActivity;
import com.discometro.map.MapsFragment;
import com.discometro.resources.service.DataService;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.text.SimpleDateFormat;

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
    private ViewModelMain vm;


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
        vm = new ViewModelProvider(this).get(ViewModelMain.class);


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


    public boolean iniCarteraUser() throws Exception {
        List<User> list = dataService.getAllUsers();
        if (list != null) {
            carteraUser = new CarteraUser(list);
            return true;
        } else {
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
                                User user = vm.getUserById(txt_email);
                                if (user != null) {
                                    Toast.makeText(this, "Este usuario ya existe", Toast.LENGTH_SHORT).show();
                                } else {
                                    if (txt_pwd.equals(txt_pwd2)) {
                                        User u = new User(txt_email, txt_pwd, txt_name, txt_birthday, txt_surname, txt_dni, new ArrayList<String>(), "", new ArrayList<String>());
                                        vm.saveUser(u);
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(this, "Asegúrate de introducir la misma contraseña", Toast.LENGTH_SHORT).show();
                                    }

                                }

                            }
                        }
                    }
                }

            }

        }
    }
}




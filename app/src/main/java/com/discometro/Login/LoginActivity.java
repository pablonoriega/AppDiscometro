package com.discometro.Login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.discometro.MainActivity.MainActivity;
import com.discometro.R;
import com.discometro.Registro.RegistroActivity;
import com.discometro.User.User;
import com.discometro.ViewModels.ViewModelLoginActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private CheckBox remember;
    private Button login;
    private ImageButton img_btn;
    private User u;
    private static int num;
    private ViewModelLoginActivity vm;
    private String txt_email;
    private String txt_pwd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.et_login_email);
        password = findViewById(R.id.et_login_password);
        remember = findViewById(R.id.cb_login_remember);
        login = findViewById(R.id.btn_login_login);
        img_btn = findViewById(R.id.ibtn_login_showpassword);
        num = 0;
        setLiveDataObservers();
        loadPreferences();
        img_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                num++;
                if (num % 2 != 0) {
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else {
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }

        });
    }

    public void intentToRegister(View view) {
        Intent intent = new Intent(getApplicationContext(), RegistroActivity.class);
        startActivity(intent);
    }

    public void intentToMap(View view) {
        txt_email = email.getText().toString();
        txt_pwd = password.getText().toString();
        if (txt_email.equals("") || txt_pwd.equals("")) {
            Toast.makeText(this, "Rellena los espacios", Toast.LENGTH_SHORT).show();
        }
        else {
            vm.iniUser(txt_email);

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

    public void setLiveDataObservers() {
        //Subscribe the activity to the observable
        vm = new ViewModelProvider(this).get(ViewModelLoginActivity.class);
        final Observer<User> observerUsuario = new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (vm.getUser().getValue().equals(null)) {
                    Toast.makeText(LoginActivity.this, "El usuario o la contraseÃ±a son incorrectos", Toast.LENGTH_SHORT).show();
                } else {
                    validacionFinal();
                }
            }
        };

        final Observer<String> observerToast = new Observer<String>() {
            @Override
            public void onChanged(String t) {
                Toast.makeText(LoginActivity.this, t, Toast.LENGTH_SHORT).show();
            }
        };

        vm.getToast().observe(this, observerToast);
        vm.getUser().observe(this, observerUsuario);
    }

    public void validacionFinal(){
        u=vm.getUser().getValue();
        if (u == null) {
            Toast.makeText(LoginActivity.this, "Correo incorrecto", Toast.LENGTH_SHORT).show();
        }
        else {
            if (!u.getContra().equals(txt_pwd)) {
                Toast.makeText(LoginActivity.this, "La contraseña no es correcta", Toast.LENGTH_SHORT).show();
            }
            else {
                if (remember.isChecked()) {
                    savePreferences();
                }
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("usuario", u.getCorreo());
                startActivity(intent);


            }
        }

    }



}

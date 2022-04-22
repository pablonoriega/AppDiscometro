package com.discometro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AgregarVueltaSeguraActivity extends AppCompatActivity {

    private Spinner spinner;
    private TextView number_amount;
    private EditText location, number;
    private ViewGroup container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_vuelta_segura);

        spinner = findViewById(R.id.spinner_vehicles);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.vehicles, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        number_amount = findViewById(R.id.tv_amount);
        number = findViewById(R.id.et_number);
        container = findViewById(R.id.tr_amount);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String text = spinner.getSelectedItem().toString();
                if (text.equals("A pie")) {
                    TransitionManager.beginDelayedTransition(container);
                    number_amount.setVisibility(View.GONE);
                    number.setVisibility(View.GONE);
                }
                else {
                    TransitionManager.beginDelayedTransition(container);
                    number_amount.setVisibility(View.VISIBLE);
                    number.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void intentToVueltaSegura(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
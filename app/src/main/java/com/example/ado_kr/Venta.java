package com.example.ado_kr;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Venta extends AppCompatActivity {

    TextView Id, Fecha, Hora, Total;
    Spinner origen, destino;
    Button Efecha, Ehora, Cancelar, Adquirir;
    int b = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venta);

        Id = findViewById(R.id.ID);
        Fecha = findViewById(R.id.Fecha);
        Hora = findViewById(R.id.Hora);
        Total = findViewById(R.id.Total);
        origen = findViewById(R.id.Origen);
        destino = findViewById(R.id.Destino);
        Efecha = findViewById(R.id.EFecha);
        Ehora = findViewById(R.id.EHora);
        Cancelar = findViewById(R.id.Cancelar);
        Adquirir = findViewById(R.id.Adquirir);

        final String[] lugares = {"Mérida", "CDMX", "Villahermosa", "Cancún", "Córdoba"};
        origen.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,lugares));
        destino.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,lugares));
//        String[] Precios = {"100", "200", "150", "300", "150"};
//
//        int a = origen.getSelectedItemPosition();
//        int c = destino.getSelectedItemPosition();
//        int Tot = Integer.parseInt(Precios[a] + Precios[c]);

        origen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        destino.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Efecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogofecha();
            }
        });

        Ehora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogohora();
            }
        });

//        if(origen.equals("")||destino.equals("")||Fecha.equals("")||Hora.equals("")){
//            Total.setText("0");
//        }else{
//            Total.setText(Tot);
//        }



        Cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Menu.class);
                Toast.makeText(getApplicationContext(), "Evento cancelado", Toast.LENGTH_LONG).show();
                startActivity(i);
            }
        });

        Adquirir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String origin = origen.getSelectedItem().toString();
                String destiny = destino.getSelectedItem().toString();
                String date = Fecha.getText().toString();
                String time = Hora.getText().toString();
                if(origin.equals("")||destiny.equals("")||date.equals("")||time.equals("")){
                    Toast.makeText(getApplicationContext(),"Debes llenar todos los campos",Toast.LENGTH_LONG).show();
                }else{
                    String response = insertar(origin,destiny,date,time);
                    String[] res = response.split("-");
                    if(res[0].equals("Exitoso"))
                        Toast.makeText(getApplicationContext(),res[1],Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(getApplicationContext(),res[1],Toast.LENGTH_LONG).show();
                }

            }
        });


    }

    private String insertar(String origin, String destiny, String date, String time) {
        ClaseBD admin = new ClaseBD(getApplicationContext(),"BoletosADO",null,1);
        ContentValues registro =new ContentValues();
        SQLiteDatabase reader = admin.getReadableDatabase();
        try {
            registro.put("origen",origin);
            registro.put("destino",destiny);
            registro.put("fecha", date);
            registro.put("hora", time);
            reader.insert("boletos",null,registro);
            reader.close();
            return "Exitoso-Registro insertado con exito!!!!";

        }catch (Exception e){
            return "Error-"+e.getMessage();
        }

    }

    private void dialogohora() {
        TimePickerDialog dialog2 = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Hora.setText(String.valueOf(hourOfDay)+":"+String.valueOf(minute));
            }
        }, 15, 00, true);
        dialog2.show();
    }

    private void dialogofecha() {
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Fecha.setText(year+"."+(month+1)+"."+dayOfMonth);
            }
        }, 2024, 01, 15);
        dialog.show();
    }
}
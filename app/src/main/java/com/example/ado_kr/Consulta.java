package com.example.ado_kr;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Consulta extends AppCompatActivity {

    TextView Consul;
    Button Menu, consulta;
    EditText Id;

    String idbol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);

        Id = findViewById(R.id.ID);
        Menu = findViewById(R.id.Menu);
        consulta = findViewById(R.id.Consulta);
        Consul = findViewById(R.id.Consul);

        consulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idbol = String.valueOf(Integer.parseInt(Id.getText().toString()));
                String response = Consultar();
                String[] res = response.split("&");
                if(res[0].equals("success"))
                    Consul.setText(res[1]);
                else
                    Consul.setText(res[1]);
            }
        });



        Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Menu.class);
                startActivity(i);
            }
        });

    }

    private String Consultar() {
        ClaseBD admin = new ClaseBD(getApplicationContext(),"BoletosADO",null,1);
        SQLiteDatabase reader = admin.getReadableDatabase();
        try {
            Cursor cursor = reader.rawQuery("Select * from boletos where idBoleto = ?",new String[]{idbol});
            String response = "Datos del Boleto \n\n";

            while(cursor.moveToNext()) {
                response +=  "ID: "+cursor.getString(0)+"\nOrigen: "+cursor.getString(1)+"\nDestino: "+cursor.getString(2)+"\nFecha: "+cursor.getString(3)+"\nHora: "+cursor.getString(4)+"\nTotal: "+cursor.getString(5)+"\nFecha de Registro: "+cursor.getString(6);
            }
            cursor.close();
            return "success&"+response;
        }catch (Exception e){
            return "error&"+e.getMessage();
        }
    }
}
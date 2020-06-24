package com.example.ejemplologin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ejemplologin.Model.Persona;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Driver_editor extends AppCompatActivity {

    EditText txt_nombrePersona, txt_apellidoPersona, txt_cedulaPersona, txt_edadPersona, txtLicensePlatePersona;
    ImageView img_profilePersona;
    Button btn_delete, btn_update;
    Persona persona;

    private DatabaseReference databaseReference;
    private SharedPreferences sharedPreferences;
    private FirebaseDatabase firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_driver_editor);
        super.onCreate(savedInstanceState);

        txt_nombrePersona = findViewById(R.id.txt_nombrePersona);
        txt_apellidoPersona = findViewById(R.id.txt_apellidoPersona);
        txt_cedulaPersona = findViewById(R.id.txt_cedulaPersona);
        txt_edadPersona = findViewById(R.id.txt_edadPersona);
        txtLicensePlatePersona = findViewById(R.id.txt_LicensePlatePersona);
        img_profilePersona = findViewById(R.id.img_Driver);
        btn_delete = findViewById(R.id.icon_delete);
        btn_update = findViewById(R.id.icon_save);

        SharedPreferences result = getSharedPreferences("save data", Context.MODE_PRIVATE);
        String correo = result.getString("correo", "data no found");

        SharedPreferences result2 = getSharedPreferences("save data2", Context.MODE_PRIVATE);
        String nombreChofer = result2.getString("nombreChofer", "data no found");

        SharedPreferences result3 = getSharedPreferences("save data2", Context.MODE_PRIVATE);
        String apellidoChofer = result3.getString("apellidoChofer", "data no found");

        SharedPreferences result4 = getSharedPreferences("save data2", Context.MODE_PRIVATE);
        String cedulaChofer = result4.getString("cedulaChofer", "data no found");

        SharedPreferences result5 = getSharedPreferences("save data2", Context.MODE_PRIVATE);
        String placaChofer = result5.getString("placaChofer", "data no found");

        SharedPreferences result6 = getSharedPreferences("save data2", Context.MODE_PRIVATE);
        String edadChofer = result6.getString("edadChofer", "data no found");

        SharedPreferences result7 = getSharedPreferences("save data2", Context.MODE_PRIVATE);
        String fotoChofer = result7.getString("fotoChofer", "data no found");


        initializarFirebase();

        if (correo == null) {

            startActivity(new Intent(getApplicationContext(), Login.class));
            Toast.makeText(this, "No CORREO", Toast.LENGTH_SHORT).show();
            finish();

        } else {

            //databaseReference = FirebaseDatabase.getInstance().getReference().child("email").child(correo).child(nombreChofer).child("profilePicture");

            //databaseReference.keepSynced(true);


            txt_nombrePersona.setText(nombreChofer.toString().trim());
            txt_apellidoPersona.setText(apellidoChofer.toString().trim());
            txt_cedulaPersona.setText(cedulaChofer.toString().trim());
            txt_edadPersona.setText(edadChofer.toString().trim());
            txtLicensePlatePersona.setText(placaChofer.toString().trim());
            Picasso.get().load(fotoChofer).into(img_profilePersona);



        }
    }

    private void initializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_update_delete_driver, menu);
        return super.onCreateOptionsMenu(menu);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.icon_delete) {

            sharedPreferences = getSharedPreferences("save data2", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("nombreChofer");
            editor.remove("apellidoChofer");
            editor.remove("cedulaChofer");
            editor.remove("placaChofer");
            editor.remove("edadChofer");
            editor.clear();
            editor.apply();
            editor.commit();


            startActivity(new Intent(getApplicationContext(), RecycleView_Drivers.class));
            finish();
        }
        return true;

    }
}

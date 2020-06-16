package com.example.ejemplologin;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
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

import java.util.UUID;

public class DriverRegistration extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    EditText txt_nombrePersona, txt_apellidoPersona, txt_cedulaPersona, txt_edadPersona, txtLicensePlatePersona;
    ImageView img_profilePersona;
    Button btn_add_new_driver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_registration);

        txt_nombrePersona = findViewById(R.id.txt_nombrePersona);
        txt_apellidoPersona = findViewById(R.id.txt_apellidoPersona);
        txt_cedulaPersona = findViewById(R.id.txt_cedulaPersona);
        txt_edadPersona = findViewById(R.id.txt_edadPersona);
        txtLicensePlatePersona = findViewById(R.id.txt_LicensePlatePersona);
        img_profilePersona = findViewById(R.id.img_Driver);
        btn_add_new_driver = findViewById(R.id.btn_add_new_driver);

         initializarFirebase(); //always on top to reach all data bellow.
    }

    private void initializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        //firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();
    }

    //agregar foto haciendo click en la imagen de ANDROID
    public void DriverRegistration(View view) {
        Toast.makeText(this, "Image Clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        String nombre =txt_nombrePersona.getText().toString().trim();
        String apellido = txt_apellidoPersona.getText().toString().trim();
        String cedula = txt_cedulaPersona.getText().toString().trim();
        String edad = txt_edadPersona.getText().toString().trim();
        String placaVehiculo = txtLicensePlatePersona.getText().toString().trim();


        if (item.getItemId() == R.id.btn_add_new_driver){
            Persona p = new Persona();
            p.setUid(UUID.randomUUID().toString());
            p.setNombre(nombre);
            p.setApellido(apellido);
            p.setCedula(cedula);
            p.setEdad(edad);
            p.setCarLicensePlate(placaVehiculo);
            databaseReference.child("Administrador").child(p.getUid()).child(p.getNombre()).setValue(p);

        }
        return super.onOptionsItemSelected(item);
    }
}
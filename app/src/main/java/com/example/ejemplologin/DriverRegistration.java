package com.example.ejemplologin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ejemplologin.Model.Persona;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class DriverRegistration extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth fAuth;

    EditText txt_nombrePersona, txt_apellidoPersona, txt_cedulaPersona, txt_edadPersona, txtLicensePlatePersona;
    ImageView img_profilePersona;
    Button btn_add_new_driver;

    public static final int GOOGLE_SIGN_IN_CODE = 0;
    SignInButton signIn;
    GoogleSignInOptions gso;
    GoogleSignInClient signInClient;

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
         fAuth = FirebaseAuth.getInstance();
    }

    private void initializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        //firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();

        btn_add_new_driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombre = txt_nombrePersona.getText().toString().trim();
                String apellido = txt_apellidoPersona.getText().toString().trim();
                String cedula = txt_cedulaPersona.getText().toString().trim();
                String edad = txt_edadPersona.getText().toString().trim();
                String placaVehiculo = txtLicensePlatePersona.getText().toString().trim();

                if (fAuth != null) {

                    String correo = fAuth.getUid().toString().trim();

                    Persona p = new Persona();
                    p.setUid(UUID.randomUUID().toString());
                    p.setNombre(nombre);
                    p.setApellido(apellido);
                    p.setCedula(cedula);
                    p.setEdad(edad);
                    p.setCarLicensePlate(placaVehiculo);
                    p.setEmail(correo);

                    databaseReference.child("email").child(p.getEmail()).child(p.getNombre()).setValue(p);

                    startActivity(new Intent(getApplicationContext(), RecycleView_Drivers.class)); //pasar a un activity diferente

                    Toast.makeText(DriverRegistration.this, "Driver Added", Toast.LENGTH_SHORT).show();

                }
            }

        });

    }

}
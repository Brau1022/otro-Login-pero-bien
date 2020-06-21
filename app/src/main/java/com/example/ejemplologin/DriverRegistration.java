package com.example.ejemplologin;

import android.content.Context;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ejemplologin.Model.Persona;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class DriverRegistration extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth fAuth;

    EditText txt_nombrePersona, txt_apellidoPersona, txt_cedulaPersona, txt_edadPersona, txtLicensePlatePersona;
    ImageView img_profilePersona;
    Button btn_add_new_driver;

    // instance for firebase storage and StorageReference
    FirebaseStorage storage;
    StorageReference storageReference;

    // request code
    private final int PICK_IMAGE_REQUEST = 22;
    // Uri indicates, where the image will be picked from
    private Uri filePath;

    public String url22;



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

         initializarFirebase();

        // get the Firebase  storage reference
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        img_profilePersona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage();
            }

            });

    }

    private void SelectImage() {
        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            filePath = data.getData();
            try {

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                filePath);
                img_profilePersona.setImageBitmap(bitmap);

                uploadImage();

            }

            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }

}

    private void uploadImage() {

        if (filePath != null) {

            // Code for showing progressDialog while uploading
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            final StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());


            // adding listeners on upload
            // or failure of image
            ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                                {

                                       ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                           @Override
                                           public void onSuccess(Uri uri) {
                                               Log.e("Tuts+", "uri: " + uri.toString());
                                               //Handle whatever you're going to do with the URL here
                                               url22 = uri.toString();

                                           }

                                       });

                                       // Image uploaded successfully
                                       // Dismiss dialog
                                       progressDialog.dismiss();
                                       Toast
                                               .makeText(DriverRegistration.this, "Image Uploaded!!", Toast.LENGTH_SHORT)
                                               .show();

                                   }



                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {

                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast
                                    .makeText(DriverRegistration.this,
                                            "Failed " + e.getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {

                                // Progress Listener for loading
                                // percentage on the dialog box
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    double progress
                                            = (100.0
                                            * taskSnapshot.getBytesTransferred()
                                            / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage(
                                            "Uploaded "
                                                    + (int)progress + "%");
                                }
                            });
        }


    }


    private void initializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        btn_add_new_driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                String nombre = txt_nombrePersona.getText().toString().trim();
                String apellido = txt_apellidoPersona.getText().toString().trim();
                String cedula = txt_cedulaPersona.getText().toString().trim();
                String edad = txt_edadPersona.getText().toString().trim();
                String placaVehiculo = txtLicensePlatePersona.getText().toString().trim();
                // subir la foto




        if (fAuth != null){

            String correo = fAuth.getUid();

            Persona p = new Persona();
            p.setProfilePicture(url22);
            p.setUid(UUID.randomUUID().toString());
            p.setNombre(nombre);
            p.setApellido(apellido);
            p.setCedula(cedula);
            p.setEdad(edad);
            p.setCarLicensePlate(placaVehiculo);
            p.setPersonId(correo);

            databaseReference.child("email").child(p.getPersonId()).child(p.getNombre()).setValue(p);

            fAuth = FirebaseAuth.getInstance();
        }

                SharedPreferences result = getSharedPreferences("save data", Context.MODE_PRIVATE );
                String correo = result.getString("correo", "data no found");

                Persona p = new Persona();
                p.setProfilePicture(url22);
                p.setUid(UUID.randomUUID().toString());
                p.setNombre(nombre);
                p.setApellido(apellido);
                p.setCedula(cedula);
                p.setEdad(edad);
                p.setCarLicensePlate(placaVehiculo);
                p.setPersonId(correo);

                databaseReference.child("email").child(p.getPersonId()).child(p.getNombre()).setValue(p);

                fAuth = FirebaseAuth.getInstance();

                startActivity(new Intent(getApplicationContext(), RecycleView_Drivers.class)); //pasar a un activity diferente

                Toast.makeText(DriverRegistration.this, "Driver Added", Toast.LENGTH_SHORT).show();
            }




        });

    }

}
package com.example.ejemplologin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    EditText mFullName, mEmail, mPassword, mPhone;
    Button mRegister;
    TextView mlogin;
   FirebaseAuth fAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFullName = findViewById(R.id.txt_name);
        mEmail = findViewById(R.id.txt_email);
        mPassword = findViewById(R.id.txt_password_1);
        mPhone = findViewById(R.id.txt_phone);
        mRegister = findViewById(R.id.btn_register);
        mlogin = findViewById(R.id.tv3_login);

        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);


        //if already login go to main menu or whatever you want
        if(fAuth.getCurrentUser() != null){

            //go to any other activity
            startActivity(new Intent(getApplicationContext(),MainActivity.class));

        }

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //these variables are used to see if something is written in a textbox
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String nombre = mFullName.getText().toString().trim();
                String phone = mPhone.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    mEmail.setError("Email is required.");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    mPassword.setError("Password is required.");
                    return;
                }
                if (password.length() < 6){
                    mPassword.setError("Password must have at least 6 characters.");
                    return;
                }
                if (TextUtils.isEmpty(nombre)){
                    mFullName.setError("Name is required.");
                    return;
                }
                if (TextUtils.isEmpty(phone)){
                    mPhone.setError("Phone is required.");
                    return;
                }


                progressBar.setVisibility(View.VISIBLE);

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Register.this, "User Created", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);

                            SharedPreferences result = getSharedPreferences("save data", Context.MODE_PRIVATE );
                            String correo = result.getString("correo", "data no found");

                            startActivity(new Intent(getApplicationContext(), MainActivity.class)); //pasar a un activity diferente



                        } else {
                            Toast.makeText(Register.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                });

            }
        });
        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class)); //pasar a un activity diferente

            }
        });

    }
}
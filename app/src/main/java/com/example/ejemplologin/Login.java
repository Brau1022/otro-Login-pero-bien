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
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ejemplologin.Model.Persona;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    public static final int GOOGLE_SIGN_IN_CODE = 0;
    SignInButton signIn;
    GoogleSignInOptions gso;
    GoogleSignInClient signInClient;

    EditText mFullName, mEmail, mPassword, mPhone;
    Button mLogin;
    TextView mRegister;
    FirebaseAuth fAuth;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //always on top to reach all data bellow.
        initializarFirebase();


        setContentView(R.layout.activity_login);

        signIn = findViewById(R.id.signIn);

        mFullName = findViewById(R.id.txt_name);
        mEmail = findViewById(R.id.txt_email);
        mPassword = findViewById(R.id.txt_password_1);
        mPhone = findViewById(R.id.txt_phone);
        mRegister = findViewById(R.id.tv4_Register);
        mLogin = findViewById(R.id.btn_login);

        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar2);

        progressBar.setVisibility(View.GONE);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("1092452549718-lvi37bu4lj426ev6ktip3c5jves8k3rn.apps.googleusercontent.com")
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        signInClient = GoogleSignIn.getClient(this,gso);

        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (signInAccount != null){

            //pegar aqui lo de guardar datos

            initializarFirebase();

            String PersonId = mEmail.getText().toString().trim();
            Persona p = new Persona();
            p.getPersonId(PersonId);
            databaseReference.child("email2").child(p.getPersonId()).setValue(p);

            startActivity(new Intent(this, MainActivity.class));
            Toast.makeText(this, "User Logged in", Toast.LENGTH_SHORT).show();


            SharedPreferences result = getSharedPreferences("save data", Context.MODE_PRIVATE );
            String correo = result.getString("correo", "data no found");
        }

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sign =  signInClient.getSignInIntent();
                startActivityForResult(sign, GOOGLE_SIGN_IN_CODE);

            }
        });


        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //these variables are used to see if something is written in a textbox
                final String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();


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

                progressBar.setVisibility(View.VISIBLE);

                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {


                           String correo = fAuth.getUid().toString().trim();

                            Persona p = new Persona();
                            p.setEmail(correo);
                            databaseReference.child("email").child(p.getEmail()).setValue(p);
//ultima vaina que agregue aqui
                            Intent i = new Intent(getApplicationContext(),RecycleView_Drivers.class);
                            i.putExtra("code", correo);
                            startActivity(i);

                            Toast.makeText(Login.this, "Logged Successfully", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);


                            startActivity(new Intent(getApplicationContext(), MainActivity.class)); //pasar a un activity diferente


                        } else {
                            Toast.makeText(Login.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });



        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Register.class)); //pasar a un activity diferente
            }
        });


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GOOGLE_SIGN_IN_CODE){
            Task<GoogleSignInAccount> singInTask = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                GoogleSignInAccount signInAcc = singInTask.getResult(ApiException.class);
                final AuthCredential authCredential = GoogleAuthProvider.getCredential(signInAcc.getIdToken(),null);

                //datos del correo
                String personId = signInAcc.getId();
                String personName = signInAcc.getDisplayName();


                Persona p = new Persona();
                p.setPersonId(personId);



                //databaseReference.child("email").child(p.getPersonId()).setValue(p);

                //Toast.makeText(this, "Your App is connected" + personName + personId, Toast.LENGTH_SHORT).show();
               // startActivity(new Intent(getApplicationContext(), MainActivity.class));

                //Adding to Firebase database (authentication)
                fAuth.signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {


                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));

                        //Valor iniciando sesion con Google
                        String correo = fAuth.getUid().toString().trim();
                        sharedPreferences = getSharedPreferences("save data", Context.MODE_PRIVATE );
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("correo", correo);
                        editor.apply();


                        Toast.makeText(Login.this, ""+ correo, Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(Login.this, "Error Firebase", Toast.LENGTH_SHORT).show();

                    }
                });

            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
    }
    private void initializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        //firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();}

}
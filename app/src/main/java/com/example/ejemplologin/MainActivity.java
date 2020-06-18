package com.example.ejemplologin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    GridLayout mainGrid;

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainGrid = (GridLayout)findViewById(R.id.mainGrid);
        setSingleEvent(mainGrid);

        SharedPreferences result = getSharedPreferences("save data", Context.MODE_PRIVATE );
        String correo = result.getString("correo", "data no found");

    }

    private void setSingleEvent(GridLayout mainGrid) {
        for (int i=0; i<mainGrid.getChildCount(); i++){

            CardView cardView = (CardView)mainGrid.getChildAt(i);
            final int finalI = i;

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(MainActivity.this, "Click" + finalI, Toast.LENGTH_SHORT).show();
                    if (finalI == 3){
                        Toast.makeText(MainActivity.this, "Logout", Toast.LENGTH_SHORT).show();
                        logout();
                    }else if (finalI == 2){
                        Toast.makeText(MainActivity.this, "Tabla Ganancias Totales", Toast.LENGTH_SHORT).show();

                    }else if (finalI == 1){
                        Toast.makeText(MainActivity.this, "Tabla Vehiculos", Toast.LENGTH_SHORT).show();

                    }else if (finalI == 0){
                        Toast.makeText(MainActivity.this, "Tabla Choferes", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),RecycleView_Drivers.class)); //pasar a un activity diferente
                    }

                }

            });

        }
    }

    //logout process
    public void logout() {
        FirebaseAuth.getInstance().signOut();
        GoogleSignIn.getClient(this, new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build())
                .signOut().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                startActivity(new Intent(getApplicationContext(),Login.class)); //pasar a un activity diferente
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "SingOut Failed", Toast.LENGTH_SHORT).show();
            }
        });
        startActivity(new Intent(getApplicationContext(),Login.class)); //pasar a un activity diferente
        finish();
    }
}
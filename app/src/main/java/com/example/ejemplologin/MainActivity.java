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
    SharedPreferences sharedPreferences;

    GridLayout mainGrid;
    private Object SharedPreferences;

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences result = getSharedPreferences("save data", Context.MODE_PRIVATE );
        String correo = result.getString("correo", "data no found");
        Toast.makeText(this, "" + correo, Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainGrid = (GridLayout)findViewById(R.id.mainGrid);
        setSingleEvent(mainGrid);

    }

    private void setSingleEvent(GridLayout mainGrid) {
        for (int i=0; i<mainGrid.getChildCount(); i++){

            CardView cardView = (CardView)mainGrid.getChildAt(i);
            final int finalI = i;

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    SharedPreferences result = getSharedPreferences("save data", Context.MODE_PRIVATE );
                    String correo = result.getString("correo", "data no found");

                    //Toast.makeText(MainActivity.this, "Click" + finalI, Toast.LENGTH_SHORT).show();
                    if (finalI == 3){
                        //Toast.makeText(MainActivity.this, "Logout", Toast.LENGTH_SHORT).show();
                        logout();
                    }else if (finalI == 2){
                        Toast.makeText(MainActivity.this, "Tabla Ganancias Totales", Toast.LENGTH_SHORT).show();

                    }else if (finalI == 1){
                        Toast.makeText(MainActivity.this, "Tabla Vehiculos", Toast.LENGTH_SHORT).show();

                    }else if (finalI == 0){
                        Toast.makeText(MainActivity.this, "Tabla Choferes"+ correo, Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(getApplicationContext(),RecycleView_Drivers.class)); //pasar a un activity diferente
                    }

                }

            });

        }
    }


    //logout process
    public void logout() {

        //FirebaseAuth.getInstance().signOut();

        GoogleSignIn.getClient(this, new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build())
                .signOut().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                sharedPreferences = getSharedPreferences("save data", Context.MODE_PRIVATE );
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                editor.commit();


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "SingOut Failed", Toast.LENGTH_SHORT).show();
            }
        });
        Toast.makeText(this, "LOGOUT", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(),Login.class)); //pasar a un activity diferente
        finish();
    }
}
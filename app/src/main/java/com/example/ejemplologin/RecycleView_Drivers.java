package com.example.ejemplologin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ejemplologin.Model.Persona;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecycleView_Drivers extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Persona> arrayList;
    private FirebaseRecyclerOptions<Persona> options;
    private FirebaseRecyclerAdapter<Persona, FirebaseViewHolder> adapter;
    private DatabaseReference databaseReference;
    private SharedPreferences sharedPreferences;

    public String nombreChofer;
    public String apellidoChofer;
    public String cedulaChofer;
    public String placaChofer;
    public String edadChofer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view__drivers);



        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrayList = new ArrayList<Persona>();
//datos del correo.
        SharedPreferences result = getSharedPreferences("save data", Context.MODE_PRIVATE);
        String correo = result.getString("correo", "data no found");


        if (correo == null) {
            startActivity(new Intent(getApplicationContext(), DriverRegistration.class));
            Toast.makeText(this, "No CORREO", Toast.LENGTH_SHORT).show();
            finish();

        } else {

            Toast.makeText(this, "" + correo, Toast.LENGTH_SHORT).show();

            databaseReference = FirebaseDatabase.getInstance().getReference().child("email").child(correo);

            databaseReference.keepSynced(true);

            options = new FirebaseRecyclerOptions.Builder<Persona>().setQuery(databaseReference, Persona.class).build();

            adapter = new FirebaseRecyclerAdapter<Persona, FirebaseViewHolder>(options) {
                private final boolean b = false;

                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                protected void onBindViewHolder(@NonNull FirebaseViewHolder firebaseViewHolder, final int i, @NonNull final Persona persona) {


                    firebaseViewHolder.teamone.setText(persona.getNombre());
                    firebaseViewHolder.teamtwo.setText(persona.getApellido());
                    firebaseViewHolder.team3.setText(persona.getCedula());
                    firebaseViewHolder.team4.setText(persona.getCarLicensePlate());
                    Picasso.get().load(persona.getProfilePicture()).into(firebaseViewHolder.imagenchofer);




//on click it brings last user added.
                    firebaseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //Toast.makeText(RecycleView_Drivers.this, ""+ i, Toast.LENGTH_SHORT).show();

                            nombreChofer = persona.getNombre();
                            apellidoChofer = persona.getApellido();
                            cedulaChofer = persona.getCedula();
                            placaChofer = persona.getCarLicensePlate();
                            edadChofer = persona.getEdad();

                            //Nombre Chofer
                            sharedPreferences = getSharedPreferences("save data2", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("nombreChofer", nombreChofer);
                            editor.apply();
                            editor.commit();
                            //Apellido
                            sharedPreferences = getSharedPreferences("save data2", Context.MODE_PRIVATE);
                            editor = sharedPreferences.edit();
                            editor.putString("apellidoChofer", apellidoChofer);
                            editor.apply();
                            editor.commit();
                            //Cedula
                            sharedPreferences = getSharedPreferences("save data2", Context.MODE_PRIVATE);
                            editor = sharedPreferences.edit();
                            editor.putString("cedulaChofer", cedulaChofer);
                            editor.apply();
                            editor.commit();
                            //Placa asignada a chofer
                            sharedPreferences = getSharedPreferences("save data2", Context.MODE_PRIVATE);
                            editor = sharedPreferences.edit();
                            editor.putString("placaChofer", placaChofer);
                            editor.apply();
                            editor.commit();
                            //Edad
                            sharedPreferences = getSharedPreferences("save data2", Context.MODE_PRIVATE);
                            editor = sharedPreferences.edit();
                            editor.putString("edadChofer", edadChofer);
                            editor.apply();
                            editor.commit();

                            Toast.makeText(RecycleView_Drivers.this, ""+ nombreChofer, Toast.LENGTH_SHORT).show();
                           startActivity(new Intent(getApplicationContext(), Driver_editor.class));


                        }
                    });
              }


                @NonNull
                @Override
                public FirebaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                    return new FirebaseViewHolder(LayoutInflater.from(RecycleView_Drivers.this).inflate(R.layout.cardview_drivers, viewGroup, false));
                }
            };

            recyclerView.setAdapter(adapter);
        }
    }

    //@Override verificar esto para que no explote
    protected void onStart() {
        super.onStart();
        adapter.startListening();

    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_new_driver, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.icon_add) {

            startActivity(new Intent(getApplicationContext(), DriverRegistration.class));
            //finish();
        }
        return true;
    }




}



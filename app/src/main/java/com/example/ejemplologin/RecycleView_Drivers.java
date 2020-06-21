package com.example.ejemplologin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
    private FirebaseRecyclerAdapter<Persona,FirebaseViewHolder> adapter;
    private DatabaseReference databaseReference;



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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view__drivers);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrayList = new ArrayList<Persona>();

        SharedPreferences result = getSharedPreferences("save data", Context.MODE_PRIVATE );
        String correo = result.getString("correo", "data no found");




        if (correo == null){
            startActivity(new Intent(getApplicationContext(),DriverRegistration.class));
            Toast.makeText(this, "No CORREO", Toast.LENGTH_SHORT).show();
            finish();

        }else {

            Toast.makeText(this, ""+ correo, Toast.LENGTH_SHORT).show();

            databaseReference = FirebaseDatabase.getInstance().getReference().child("email").child(correo);

            databaseReference.keepSynced(true);

            options = new FirebaseRecyclerOptions.Builder<Persona>().setQuery(databaseReference, Persona.class).build();

            adapter = new FirebaseRecyclerAdapter<Persona, FirebaseViewHolder>(options) {
                @Override
                protected void onBindViewHolder(@NonNull FirebaseViewHolder firebaseViewHolder, int i, @NonNull Persona persona) {

                    firebaseViewHolder.teamone.setText(persona.getNombre());
                    firebaseViewHolder.teamtwo.setText(persona.getApellido());
                    firebaseViewHolder.team3.setText(persona.getCedula());
                    firebaseViewHolder.team4.setText(persona.getCarLicensePlate());
                    Picasso.get().load(persona.getProfilePicture()).into(firebaseViewHolder.imagenchofer);


                    //firebaseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                      //  @Override
                      //  public void onClick(View v) {

                       // }
                    //});
                }

               /* @Override
                protected void onBindViewHolder(@NonNull FirebaseViewHolder holder, int position, @NonNull DataSetFire model) {


                    holder.teamtwo.setText(model.getApellido());
                    holder.teamone.setText(model.getNombre());
                    holder.itemView.setOnClickListener(new View.OnClickListener()

                    {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                }*/

                @NonNull
                @Override
                public FirebaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                    return new FirebaseViewHolder(LayoutInflater.from(RecycleView_Drivers.this).inflate(R.layout.cardview_drivers,viewGroup,false));
                }
            };

            recyclerView.setAdapter(adapter);
        }}


    public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater().inflate(R.menu.menu_add_new_driver,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.icon_add){
            startActivity(new Intent(getApplicationContext(),DriverRegistration.class));
            //finish();
        }
        return true;
    }
}



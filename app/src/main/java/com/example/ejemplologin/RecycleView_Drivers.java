package com.example.ejemplologin;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ejemplologin.Model.MyDriverAdapter;
import com.example.ejemplologin.Model.Persona;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RecycleView_Drivers extends AppCompatActivity {

    DatabaseReference reference;
    RecyclerView recyclerView;
    ArrayList<Persona> list;
    MyDriverAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view__drivers);

        recyclerView = findViewById(R.id.Recycle_Drivers);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<Persona>();

        reference = FirebaseDatabase.getInstance().getReference().child("persona");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    Persona p = dataSnapshot1.getValue(Persona.class);
                    list.add(p);
                }
                adapter = new MyDriverAdapter(RecycleView_Drivers.this, list);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(RecycleView_Drivers.this, "Opsss... ", Toast.LENGTH_SHORT).show();
            }
        });


        }






    public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater().inflate(R.menu.menu_add_new_driver,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.icon_add){
            startActivity(new Intent(getApplicationContext(),DriverRegistration.class));
        }
        return true;
    }
}



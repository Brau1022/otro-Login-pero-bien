package com.example.ejemplologin;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import static com.example.ejemplologin.R.menu.menu_add_new_driver;

public class RecycleView_Drivers extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view__drivers);

        }


    public boolean onCreateOptionsMenu(Menu menu) {
        //to put menu icons into the main menu.
        getMenuInflater().inflate(menu_add_new_driver,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}



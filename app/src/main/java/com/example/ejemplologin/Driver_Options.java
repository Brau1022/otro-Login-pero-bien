package com.example.ejemplologin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class Driver_Options extends AppCompatActivity {

    GridLayout mainGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver__options);

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

                   //Toast.makeText(MainActivity.this, "Click" + finalI, Toast.LENGTH_SHORT).show();
                    if (finalI == 1) {
                        Toast.makeText(Driver_Options.this, "Editar Chofer", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), Driver_editor.class));
                        finish();

                    } else if (finalI == 0) {
                        Toast.makeText(Driver_Options.this, "Agregar Pago", Toast.LENGTH_SHORT).show();
                        //enviar a activity de agregar pago
                    }


                }
            });
        }
    }
}
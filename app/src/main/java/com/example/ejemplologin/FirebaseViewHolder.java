package com.example.ejemplologin;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class FirebaseViewHolder extends RecyclerView.ViewHolder {

    public TextView teamone, teamtwo;

    public FirebaseViewHolder(View itemView){
        super(itemView);

        teamone = itemView.findViewById(R.id.Teamone);
        teamtwo = itemView.findViewById(R.id.Teamtwo);


    }
}

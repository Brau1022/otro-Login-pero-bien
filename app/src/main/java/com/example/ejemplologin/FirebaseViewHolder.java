package com.example.ejemplologin;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class FirebaseViewHolder extends RecyclerView.ViewHolder {

    public TextView teamone, teamtwo, team3, team4;
    public ImageView imagenchofer;

    public FirebaseViewHolder(View itemView){
        super(itemView);

        teamone = itemView.findViewById(R.id.Teamone);
        teamtwo = itemView.findViewById(R.id.Teamtwo);
        team3 = itemView.findViewById(R.id.Team3);
        team4 = itemView.findViewById(R.id.Team4);
        imagenchofer = (ImageView) itemView.findViewById(R.id.img_profile_driver);


    }
}

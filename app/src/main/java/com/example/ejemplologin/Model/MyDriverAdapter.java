package com.example.ejemplologin.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ejemplologin.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MyDriverAdapter extends RecyclerView.Adapter<MyDriverAdapter.MyViewHolder> {
    FirebaseAuth fAuth;
    Context context;
    ArrayList<Persona> email;

    public MyDriverAdapter(Context c, ArrayList<Persona> p)
    {

        context = c;
        email = p;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final MyViewHolder myViewHolder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.cardview_drivers, parent, false));
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {



        holder.name.setText(email.get(position).getPersonId());
        holder.lastname.setText(email.get(position).getApellido());
        //Picasso.get().load(personas.get(position).getProfilePicture()).into(holder.img_profile_driver);

    }

    @Override
    public int getItemCount() {
        return email.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView name, lastname;
        ImageView img_profile_driver;

        public MyViewHolder (View itemView){
            super  (itemView);
            name = itemView.findViewById(R.id.tv_nombrePersona);
            lastname = itemView.findViewById(R.id.tv_apellidoPersona);
            img_profile_driver = itemView.findViewById(R.id.img_profile_driver);

        }
    }
}

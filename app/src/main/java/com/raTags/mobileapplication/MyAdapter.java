package com.raTags.mobileapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import androidx.annotation.NonNull;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

//import com.google.android.gms.identity.intents.Address;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context context;
    ArrayList<User> userArrayList;


    public MyAdapter(Context context, ArrayList<User> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position){
        User user=userArrayList.get(position);
        holder.Name.setText(user.Name);
        holder.Address.setText(user.Address);
        holder.Id.setText(user.Id);
        holder.Spot.setText(user.Spot);

    }

    @Override
    public int getItemCount(){
        return userArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        User model;
        TextView Name, Address, Id, Spot;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            Name=itemView.findViewById(R.id.estName);
            Address=itemView.findViewById(R.id.estAddress);
            Id=itemView.findViewById(R.id.estId);
            Spot=itemView.findViewById(R.id.estSpot);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(),
                            EstablishmentDetails.class);
                    intent.putExtra("ID", Id.getText());
                    intent.putExtra("SPOT", Spot.getText());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}

package com.raTags.mobileapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FeaturedAdapter extends RecyclerView.Adapter<FeaturedAdapter.MyViewHolder> {
    Context context;
    ArrayList<User> userArrayList;


    public FeaturedAdapter(Context context, ArrayList<User> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
    }

    @NonNull
    @Override
    public FeaturedAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);

        return new FeaturedAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FeaturedAdapter.MyViewHolder holder, int position){
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

        FirebaseFirestore db;
        DecimalFormat decimalFormat = new DecimalFormat("#");

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            Name=itemView.findViewById(R.id.estName);
            Address=itemView.findViewById(R.id.estAddress);
            Id=itemView.findViewById(R.id.estId);
            Spot=itemView.findViewById(R.id.estSpot);

            db = FirebaseFirestore.getInstance();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DocumentReference uidRef = db.collection(String.valueOf(Spot.getText())).document(String.valueOf(String.valueOf(Id.getText())));
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    String myLatitude = String.valueOf(Id.getText());
                    String myLongitude = String.valueOf(Spot.getText());
                    String labelLocation = String.valueOf(Name.getText());
                    intent.setData(Uri.parse("geo:<" + myLatitude  + ">,<" + myLongitude + ">?q=<" + myLatitude  + ">,<" + myLongitude + ">(" + labelLocation + ")"));
                    Intent chooser = Intent.createChooser(intent, "Launch Maps");
                    chooser.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    itemView.getContext().startActivity(chooser);
                }
            });
        }
    }
}

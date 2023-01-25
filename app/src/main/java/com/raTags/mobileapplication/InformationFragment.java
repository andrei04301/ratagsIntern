package com.raTags.mobileapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class InformationFragment extends Fragment {

    TextView est_info;
    FirebaseFirestore db;
    private ActivityCallback callback;
    String _id = "", _spot = "";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (ActivityCallback) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.est_information_fragment,container,false);
        db = FirebaseFirestore.getInstance();
        est_info = root.findViewById(R.id.est_info);
        _id = callback.getID();
        _spot = callback.getSpot();
        DocumentReference uidRef = db.collection(_spot).document(_id);
        uidRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        est_info.setText(document.getString("Information"));
                    }
                }
            }
        });

        return root;
    }
}

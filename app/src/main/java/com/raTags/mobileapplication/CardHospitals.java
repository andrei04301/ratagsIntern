package com.raTags.mobileapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CardHospitals extends AppCompatActivity {

    RecyclerView recyclerView;
    MyAdapter myAdapter;
    ArrayList<User> userArrayList;
    List<String> ids;
    FirebaseFirestore db;
    User user;

    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_hospitals);

        search = findViewById(R.id.search);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        userArrayList = new ArrayList<User>();
        ids = new ArrayList<String>();
        myAdapter = new MyAdapter(getApplicationContext(), userArrayList);
        user = new User();

        recyclerView.setAdapter(myAdapter);
        EventChangeListener(userArrayList);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                userArrayList.clear();
                ids.clear();
                myAdapter.notifyDataSetChanged();

                if (charSequence.length() > 0)
                    GetSearchResult(userArrayList, String.valueOf(charSequence));
                else
                    EventChangeListener(userArrayList);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void GetSearchResult(ArrayList<User> userArrayList, String charSequence) {
        userArrayList.clear();
        ids.clear();

        db.collection("Category Hospitals").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String uid = document.getId();
                        DocumentReference uidRef = db.collection("Category Hospitals").document(uid);
                        System.out.println(uid);
                        uidRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        if (Pattern.compile(Pattern.quote(charSequence), Pattern.CASE_INSENSITIVE).matcher(document.getString("Name")).find()) {
                                            String address = document.getString("Address");
                                            String name = document.getString("Name");
                                            user = new User(name, address, document.getId(), "Category Hospitals");
                                            if (!ids.contains(document.getId())) {
                                                ids.add(document.getId());
                                                userArrayList.add(user);
                                            }
                                        }
                                        myAdapter.notifyDataSetChanged();

                                    } else {
                                        Toast.makeText(getApplicationContext(), "No such document", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "Error getting document", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }

                }
            }
        });
    }

    private void EventChangeListener(ArrayList<User> userArrayList) {
        userArrayList.clear();
        ids.clear();

        db.collection("Category Hospitals").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String uid = document.getId();
                        DocumentReference uidRef = db.collection("Category Hospitals").document(uid);
                        System.out.println(uid);
                        uidRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        String address = document.getString("Address");
                                        String name = document.getString("Name");
                                        user = new User(name, address, document.getId(), "Category Hospitals");
                                        if (!ids.contains(document.getId())) {
                                            ids.add(document.getId());
                                            userArrayList.add(user);
                                        }
                                        myAdapter.notifyDataSetChanged();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "No such document", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "Error getting document", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }

                }
            }
        });
    }
}
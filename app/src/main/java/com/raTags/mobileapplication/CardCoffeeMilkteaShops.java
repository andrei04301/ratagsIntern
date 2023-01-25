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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.RangeSlider;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Currency;
import java.util.List;
import java.util.regex.Pattern;

public class CardCoffeeMilkteaShops extends AppCompatActivity {

    RecyclerView recyclerView;
    MyAdapter myAdapter;
    ArrayList<User> userArrayList;
    List<String> ids;
    FirebaseFirestore db;
    User user;

    EditText search;
    RangeSlider slider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_coffee_milktea_shops);

        search = findViewById(R.id.search);
        slider = findViewById(R.id.slider);
        slider.setLabelFormatter(new LabelFormatter() {
            @NonNull
            @Override
            public String getFormattedValue(float value) {
                NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
                currencyFormat.setCurrency(Currency.getInstance("PHP"));
                return currencyFormat.format(value);
            }
        });

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

        slider.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                userArrayList.clear();
                ids.clear();
                List<Float> values = slider.getValues();
                double min = Collections.min(values);
                double max = Collections.max(values);
                FilterResult(userArrayList, min, max);
            }
        });
    }

    private void FilterResult(ArrayList<User> userArrayList, double min, double max) {
        userArrayList.clear();
        ids.clear();

        db.collection("Category Coffee").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String uid = document.getId();
                        DocumentReference uidRef = db.collection("Category Coffee").document(uid);
                        System.out.println(uid);
                        uidRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        if (document.getDouble("lPrice") >= min && document.getDouble("hPrice") <= max) {
                                            String address = document.getString("Address");
                                            String name = document.getString("Name");
                                            user = new User(name, address, document.getId(), "Category Coffee");
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

    private void GetSearchResult(ArrayList<User> userArrayList, String charSequence) {
        userArrayList.clear();
        ids.clear();

        db.collection("Category Coffee").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String uid = document.getId();
                        DocumentReference uidRef = db.collection("Category Coffee").document(uid);
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
                                            user = new User(name, address, document.getId(), "Category Coffee");
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

        db.collection("Category Coffee").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String uid = document.getId();
                        DocumentReference uidRef = db.collection("Category Coffee").document(uid);
                        System.out.println(uid);
                        uidRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        String address = document.getString("Address");
                                        String name = document.getString("Name");
                                        user = new User(name, address, document.getId(), "Category Coffee");
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
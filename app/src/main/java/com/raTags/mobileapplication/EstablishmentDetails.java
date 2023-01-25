package com.raTags.mobileapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class EstablishmentDetails extends AppCompatActivity implements ActivityCallback {

    TabLayout tabLayout;
    ViewPager viewPager;
    FloatingActionButton fb,google,twitter;
    FirebaseFirestore db;
    StorageReference storageReference;

    ImageView img_details;
    TextView details_name, est_info;
    public String id = "";
    public String spot = "";

    Button btnGo;
    DecimalFormat decimalFormat = new DecimalFormat("#");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_establishment_details);
        db = FirebaseFirestore.getInstance();
        img_details = findViewById(R.id.img_details);
        details_name = findViewById(R.id.details_name);
        est_info = findViewById(R.id.est_info);
        btnGo = findViewById(R.id.btnGo);
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
//        fb = findViewById(R.id.fab_fb);
//        google = findViewById(R.id.fab_google);
//        twitter = findViewById(R.id.fab_twitter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        InformationAdapter informationAdapter = new InformationAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        informationAdapter.addFragment(new InformationFragment(), "Information");
        informationAdapter.addFragment(new FeaturedFragment(), "Featured Establishment");
        viewPager.setAdapter(informationAdapter);
//        final LoginAdapter adapter = new LoginAdapter(getSupportFragmentManager(), this,tabLayout.getTabCount());
//        viewPager.setAdapter(adapter);
//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//        fb.setTranslationY(300);
//        google.setTranslationY(300);
//        twitter.setTranslationY(300);

//        fb.setAlpha(v);
//        google.setAlpha(v);
//        twitter.setAlpha(v);
//        tabLayout.setAlpha(v);

//        fb.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
//        google.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(600).start();
//        twitter.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(800).start();
//        tabLayout.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(1000).start();


        Intent intent = getIntent();
        id = intent.getStringExtra("ID");
        spot = intent.getStringExtra("SPOT");
        getDataByID(id, spot);
        getImage(id, spot);
        onEditTextChange(id, spot);

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DocumentReference uidRef = db.collection(spot).document(String.valueOf(id));
                uidRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                String address = document.getString("Address");
                                String contact = document.getString("Contacts");
                                String img = document.getString("Img");
                                String information = document.getString("Information");
                                Double latitude = document.getDouble("Latitude");
                                Double longitude = document.getDouble("Longitude");
                                String name = document.getString("Name");
                                Double hPrice = document.getDouble("hPrice") == null ? 0 : document.getDouble("hPrice");
                                Double lPrice = document.getDouble("lPrice") == null ? 0 : document.getDouble("lPrice");

                                double count = document.getDouble("Count") == null ? 0 : document.getDouble("Count");
                                int _count = Integer.parseInt(decimalFormat.format(count));
                                _count += 1;

                                Map<String, Object> countMap = new HashMap<>();
                                countMap.put("Address", address);
                                countMap.put("Contacts", contact);
                                countMap.put("Img", img);
                                countMap.put("Information", information);
                                countMap.put("Latitude", latitude);
                                countMap.put("Longitude", longitude);
                                countMap.put("Name", name);
                                countMap.put("hPrice", hPrice);
                                countMap.put("lPrice", lPrice);
                                countMap.put("Count", _count);

                                db.collection(String.valueOf(spot)).document(String.valueOf(id)).set(countMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                    }
                                });
                            }
                        }
                    }
                });
                Intent mapIntent = new Intent(EstablishmentDetails.this, Maps.class);
                mapIntent.putExtra("ID", id);
                mapIntent.putExtra("SPOT", spot);
                startActivity(mapIntent);
            }
        });
    }

    void getDataByID(String id, String spot) {
        String result;
        DocumentReference uidRef = db.collection(spot).document(id);
        uidRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        details_name.setText(document.getString("Name"));

                    } else {
                        Toast.makeText(getApplicationContext(), "No such document", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Error getting document", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void getImage(String id, String spot) {
        storageReference = FirebaseStorage.getInstance().getReference();
        storageReference.child(spot+"/"+id+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(String.valueOf(uri)).into(img_details);
            }
        });
    }

    @Override
    public void onEditTextChange(String _id, String _spot) {
        this.id = _id;
        this.spot = _spot;
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public String getSpot() {
        return spot;
    }
}

//package com.raTags.mobileapplication;
//
//        import android.content.Context;
//
//        import androidx.fragment.app.Fragment;
//        import androidx.fragment.app.FragmentManager;
//        import androidx.fragment.app.FragmentPagerAdapter;
//
//public class LoginAdapter extends FragmentPagerAdapter {
//
//    private Context context;
//    int totalTabs;
//
//    public LoginAdapter(FragmentManager fm, Context context, int totalTabs){
//        super(fm);
//        this.context=context;
//        this.totalTabs= totalTabs;
//    }
//    @Override
//    public int getCount(){
//        return totalTabs;
//    }
//    public Fragment getItem(int position){
//        switch(position){
//            case 0:
//                LoginTabFragment loginTabFragment = new LoginTabFragment();
//            case 1:
//                SignupTabFragment signupTabFragment = new SignupTabFragment();
//            default:
//                return null;
//        }
//    }
//
//}

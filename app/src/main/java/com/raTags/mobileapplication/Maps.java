package com.raTags.mobileapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Maps extends FragmentActivity implements OnMapReadyCallback, TaskLoadedCallback {

    FirebaseFirestore db;
    GoogleMap map;
    String id = "", spot = "";

    final String weather_url = "http://api.openweathermap.org/data/2.5/weather?q=Tagaytay,Philippines&APPID=f82be4c397ebdbfe920bf46631132548";
    String icon_url = "http://openweathermap.org/img/w/";
    final String weather_api = "f82be4c397ebdbfe920bf46631132548";
    DecimalFormat decimalFormat = new DecimalFormat("#.##");

    TextView txt_cloud, lat, lang, txt_name, txt_est;
    ImageView imageView;
    Button btnGo, btnEnd;

    RecyclerView recyclerView;
    FeaturedAdapter featuredAdapter;
    ArrayList<User> userArrayList;
    User user;

    LocationManager lm;
    Location location;
    double myLongitude;
    double myLatitude;
    LocationListener locationListener;
    final String directions_api_key = "AIzaSyDPt4l1nCMFtBxXXIiWk1iyyE0dVrILgdE";
    Polyline directionLine;

    final String distance_matrix_key = "AIzaSyCuuiMhBDGYa-wpyh4TKQ5WQOWOgOYdmTc";
    final String places_key = "AIzaSyDX-LJYRpI-SZXCqRgbrbKkkv_pRl1MUcE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        txt_cloud = findViewById(R.id.txt_cloud);
        lat = findViewById(R.id.lat);
        lang = findViewById(R.id.lang);
        txt_name = findViewById(R.id.txt_name);
        imageView = findViewById(R.id.imageView);
        btnGo = findViewById(R.id.btnGo);
        btnEnd = findViewById(R.id.btnEnd);
        txt_est = findViewById(R.id.txt_est);

        db = FirebaseFirestore.getInstance();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        id = intent.getStringExtra("ID");
        spot = intent.getStringExtra("SPOT");
        getWeather();

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                String myLatitude = String.valueOf(lat.getText());
                String myLongitude = String.valueOf(lang.getText());
                String labelLocation = String.valueOf(txt_name.getText());
                intent.setData(Uri.parse("geo:<" + myLatitude  + ">,<" + myLongitude + ">?q=<" + myLatitude  + ">,<" + myLongitude + ">(" + labelLocation + ")"));
                Intent chooser = Intent.createChooser(intent, "Launch Maps");
                startActivity(chooser);
            }
        });
        btnEnd.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    Intent intent = new Intent(Maps.this, Categories.class);
                    startActivity(intent);
                }
                else if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                    Intent intent = new Intent(Maps.this, Categories.class);
                    startActivity(intent);
                }
                return true;}


        });






        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        db = FirebaseFirestore.getInstance();
        userArrayList = new ArrayList<User>();
        featuredAdapter = new FeaturedAdapter(getApplicationContext(), userArrayList);
        user = new User();

        recyclerView.setAdapter(featuredAdapter);

//        db.collection("Featured").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        String uid = document.getId();
//                        DocumentReference uidRef = db.collection("Featured").document(uid);
//                        System.out.println(uid);
//                        uidRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                            @Override
//                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                                if (task.isSuccessful()) {
//                                    DocumentSnapshot document = task.getResult();
//                                    if (document.exists()) {
//                                        String address = document.getString("Address");
//                                        String name = document.getString("Name");
//                                        user = new User(name, address, document.getId(), "Featured");
//                                        userArrayList.add(user);
//                                        featuredAdapter.notifyDataSetChanged();
//                                    }
//                                }
//                            }
//                        });
//                    }
//                }
//            }
//        });

        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Maps.this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 100);
            ActivityCompat.requestPermissions(Maps.this, new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION
            }, 100);
        }
        location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        myLongitude = location.getLongitude();
        myLatitude = location.getLatitude();

        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                myLongitude = location.getLongitude();
                myLatitude = location.getLatitude();
            }
        };
    }


    public void getWeather() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, weather_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String output = "";
                try {

                    JSONObject jsonResponse = new JSONObject(response);
                    String city = jsonResponse.getString("name");

                    JSONArray jsonArray = jsonResponse.getJSONArray("weather");
                    JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                    String icon = jsonObjectWeather.getString("icon");
                    icon_url += icon + ".png";
                    String description = jsonObjectWeather.getString("description");

                    JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
                    double temp = jsonObjectMain.getDouble("temp") - 273.15;
                    double feelsLike = jsonObjectMain.getDouble("feels_like") - 273.15;
                    float pressure = jsonObjectMain.getInt("pressure");
                    int humidity = jsonObjectMain.getInt("humidity");

                    JSONObject jsonObjectWind = jsonResponse.getJSONObject("wind");
                    String wind = jsonObjectWind.getString("speed");

                    JSONObject jsonObjectCloud = jsonResponse.getJSONObject("clouds");
                    String clouds = jsonObjectCloud.getString("all");

                    output += "Current Weather in " + city + ":\n"
                            + "Temp: " + decimalFormat.format(temp) + " ºC\n"
//                            + "Feels Like: " + decimalFormat.format(feelsLike) + " ºC\n"
                            + "Humidity: " + humidity + "%\n"
                            + "Description: " + description + "\n"
                            + "Wind Speed: " + wind + "m/s\n"
                            + "Cloudiness: " + clouds + "%\n"
                            +"--------------------------";
//                            + "Pressure: " + pressure + " hPa\n";
                    txt_cloud.setText(output);
                    Picasso.get().load(icon_url).into(imageView);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;

        DocumentReference uidRef = db.collection(spot).document(id);
        uidRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String name = document.getString("Name");
                        txt_name.setText(String.valueOf(name));
                        Double Lat = document.getDouble("Latitude");
                        lat.setText(String.valueOf(Lat));
                        Double Lang = document.getDouble("Longitude");
                        lang.setText(String.valueOf(Lang));
                        LatLng starting = new LatLng(Lat, Lang);
                        LatLng myPosition = new LatLng(myLatitude, myLongitude);
                        LatLng midPoint = new LatLng((Lat+myLatitude)/2,(Lang+myLongitude)/2);

                        CameraPosition cameraPosition = new CameraPosition.Builder()
                                .target(starting)
                                .tilt(50)
                                .zoom(17)
                                .bearing(0)
                                .build();
                        googleMap.addMarker(new MarkerOptions().position(starting).title(name));
                        googleMap.addMarker(new MarkerOptions().position(myPosition).title("My location"));
                        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                        String direction_url = getDistanceUrl(starting, myPosition, "driving");
                        new FetchURL(Maps.this).execute(direction_url, "driving");

                        String eta_url = getEtaUrl(starting, myPosition);
                        getEta(eta_url);

                        String places_url = getPlacesUrl(starting);
                        getPlaces(places_url);

                    } else {
                        Toast.makeText(getApplicationContext(), "No such document", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Error getting document", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void getPlaces(String places_url) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, places_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String output = "";
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray jsonResultsArray = jsonResponse.getJSONArray("results");

                    for (int i = 0; i < jsonResultsArray.length(); i++) {
                        JSONObject jsonResultObject = jsonResultsArray.getJSONObject(i);
                        JSONObject jsonGeometryObject = jsonResultObject.getJSONObject("geometry");
                        JSONObject jsonLocationObject = jsonGeometryObject.getJSONObject("location");
                        LatLng place = new LatLng(Double.parseDouble(jsonLocationObject.getString("lat")), Double.parseDouble(jsonLocationObject.getString("lng")));



                        String name = jsonResultObject.getString("name");
                        String address = jsonResultObject.getString("vicinity");
                        String lat = jsonLocationObject.getString("lat");
                        String lng = jsonLocationObject.getString("lng");

                        map.addMarker(new MarkerOptions().position(place).title(name).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

                        user = new User(name, address, lat, lng);
                        userArrayList.add(user);
                        featuredAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), String.valueOf(e), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private String getPlacesUrl(LatLng starting) {
        String location = "location=" + starting.latitude + "," + starting.longitude;
        String radius = "radius=100";
        String parameters = location + "&" + radius;
        String output = "json";

        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/" + output + "?" + parameters + "&key=" + places_key;
        Log.d("PLACES URL", url);
        return url;
    }

    public void getEta(String eta_url) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, eta_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String output = "";
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray jsonRowsArray = jsonResponse.getJSONArray("rows");
                    JSONObject jsonRowsObject = jsonRowsArray.getJSONObject(0);
                    JSONArray jsonElementsArray = jsonRowsObject.getJSONArray("elements");
                    JSONObject jsonElementsObject = jsonElementsArray.getJSONObject(0);
                    JSONObject jsonDistanceObject = jsonElementsObject.getJSONObject("distance");
                    JSONObject jsonDurationObject = jsonElementsObject.getJSONObject("duration");
                    String distance = jsonDistanceObject.getString("text");
                    String duration = jsonDurationObject.getString("text");
                    output += "Distance: " + distance + "\n"
                            + "ETA: " + duration;
                    txt_est.setText(output);
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), String.valueOf(e), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private String getEtaUrl(LatLng starting, LatLng myPosition) {
        String origin = "origins=" + starting.latitude + "," + starting.longitude;
        String destination = "destinations=" + myPosition.latitude + "," + myPosition.longitude;
        String parameters = origin + "&" + destination;
        String output = "json";

        String url = "https://maps.googleapis.com/maps/api/distancematrix/" + output + "?" + parameters + "&key=" + distance_matrix_key;
        return url;
    }

    private String getDistanceUrl(LatLng starting, LatLng myPosition, String directionMode) {
        String origin = "origin=" + starting.latitude + "," + starting.longitude;
        String destination = "destination=" + myPosition.latitude + "," + myPosition.longitude;
        String mode = "mode=" + directionMode;
        String parameters = origin + "&" + destination + "&" + mode;
        String output = "json";

        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + directions_api_key;
        return url;
    }

    @Override
    public void onTaskDone(Object... values) {
        if (directionLine != null) {
            directionLine.remove();
        }
        directionLine = map.addPolyline((PolylineOptions) values[0]);
    }
}
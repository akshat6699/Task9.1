package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class NewAdvertActivity extends  AppCompatActivity {
    private PostViewModel postViewModel;

    // for receiving location updates
    private FusedLocationProviderClient fusedLocationProviderClient;

    private Location currentLocation = null;

    TextView latLngText;
    TextView placeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_advert);

        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);

        if (isLocationPermissionGranted()) {
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        }

        if (!Places.isInitialized()) {
            Places.initialize(this, getString(R.string.maps_api_key));
        }
        PlacesClient placesClient = Places.createClient(this);

        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.auto_complete_fragment);

        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i("newad", "Place: " + place.getName() + ", " + place.getLatLng());

                if (place.getLatLng() != null)
                    latLngText.setText(place.getLatLng().latitude + "," + place.getLatLng().longitude);
                if (place.getName() != null)
                    placeName.setText(place.getName());
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("newad", "An error occurred: " + status);
            }
        });

        latLngText = findViewById(R.id.latLngText);
        placeName = findViewById(R.id.placeName);

        Button getLocBtn = findViewById(R.id.btn_get_location);
        getLocBtn.setOnClickListener(this::getCurrentLocation);

        Button saveBtn = findViewById(R.id.btn_save);
        saveBtn.setOnClickListener(this::savePost);
    }

    public void savePost(View view) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String postType = (((RadioGroup)findViewById(R.id.radioType)).getCheckedRadioButtonId() == R.id.radioLost) ? "Lost" : "Found";
        String name = ((EditText)findViewById(R.id.editTextName)).getText().toString();
        String phone = ((EditText)findViewById(R.id.editTextPhone)).getText().toString();
        String description = ((EditText)findViewById(R.id.editTextDescription)).getText().toString();
        Date date;
        try {
            date = df.parse(((EditText)findViewById(R.id.editTextDate)).getText().toString());
        } catch (Exception e) {
            date = new Date();
        }
        String location = placeName.getText().toString() + "@:" + latLngText.getText().toString();

        Post p = new Post(0, postType, name, phone, description, date, location);

        postViewModel.insert(p);
        startActivity(new Intent(this, MainActivity.class));
    }

    public void getCurrentLocation(View view) {
        if (isLocationPermissionGranted()) {
//            if (fusedLocationProviderClient == null) fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    currentLocation = task.getResult();
                    if (currentLocation != null) {
                        try {
                            Geocoder geocoder = new Geocoder(NewAdvertActivity.this, Locale.getDefault());
                            List<Address> addresses = geocoder.getFromLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), 1);
                            Log.i("asdf", addresses.get(0).getAddressLine(0));
                        } catch (IOException e) {
                            Log.d("NewAdvertActivity", "something went wrong");
                            e.printStackTrace();
                        }
                    } else {
                        Log.d("NewAdvertActivity", "currentLocation is null");
                    }
                }
            });
        }
    }

    private Boolean isLocationPermissionGranted() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            List<String> perms = Arrays.asList(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            );
            ActivityCompat.requestPermissions(
                    this,
                    Arrays.copyOf(perms.toArray(), perms.size(), String[].class), 0);
            return false;
        } else {
            return true;
        }
    }

}
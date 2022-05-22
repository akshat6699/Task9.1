package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button createAdvert = findViewById(R.id.btn_create_advert);
        createAdvert.setOnClickListener(this::onButtonClicked);

        Button showAllItems = findViewById(R.id.btn_show_all_items);
        showAllItems.setOnClickListener(this::onButtonClicked);

        Button showMap = findViewById(R.id.btn_show_map);
        showMap.setOnClickListener(this::onButtonClicked);
    }

    public void onButtonClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.btn_create_advert:
                intent = new Intent(this, NewAdvertActivity.class);
                break;
            case R.id.btn_show_all_items:
                intent = new Intent(this, AllPostsActivity.class);
                break;
            case R.id.btn_show_map:
                intent = new Intent(this, MapsActivity.class);
                break;
        }

        startActivity(intent);
    }
}
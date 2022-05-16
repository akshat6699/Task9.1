package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import java.util.Date;
import java.text.SimpleDateFormat;

public class NewAdvertActivity extends  AppCompatActivity {
    private PostViewModel postViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_advert);

        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);

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
        String location = ((EditText)findViewById(R.id.editTextLocation)).getText().toString();

        Post p = new Post(0, postType, name, phone, description, date, location);

        postViewModel.insert(p);
        startActivity(new Intent(this, MainActivity.class));
    }
}
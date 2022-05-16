package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.Date;
import java.time.temporal.ChronoUnit;

public class DetailsActivity extends  AppCompatActivity {
    private PostViewModel postViewModel;
    private Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        int id = getIntent().getIntExtra("postId", 0);

        // no provided id, go to home
        if (id == 0) {
            startActivity(new Intent(this, MainActivity.class));
        }

        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);
        postViewModel.getAllPosts().observe(this, posts -> {
            for (int i = 0; i < posts.size(); i++) {
                if (posts.get(i).id == id) {
                    post = posts.get(i);
                    updateView();
                    break;
                }
            }
        });
    }

    protected void updateView() {
        ((TextView)findViewById(R.id.itemName)).setText(post.name);
        ((TextView)findViewById(R.id.itemType)).setText(post.type);
        ((TextView)findViewById(R.id.itemDescription)).setText(post.description);
        ((TextView)findViewById(R.id.itemLocation)).setText("At " + post.location);
        ((TextView)findViewById(R.id.itemDate)).setText(ChronoUnit.DAYS.between(post.date.toInstant(), new Date().toInstant()) + " days ago.");
    }
}
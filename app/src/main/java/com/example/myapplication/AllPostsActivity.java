package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AllPostsActivity extends AppCompatActivity {
    private PostViewModel postViewModel;

    @Override
    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_posts);

        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);
        TextView noPosts = findViewById(R.id.no_items);
        RecyclerView rv = findViewById(R.id.recycler);
        final PostAdapter adapter = new PostAdapter(new PostAdapter.PostDiff());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        postViewModel.getAllPosts().observe(this, posts -> {
            adapter.submitList(posts);
            Log.i("ALLP", "n = " + posts.size());
            if (posts.isEmpty())
                noPosts.setVisibility(View.VISIBLE);
            else
                noPosts.setVisibility(View.INVISIBLE);
        });


        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
    }
}
package com.example.myapplication;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class PostViewModel extends AndroidViewModel {

    private PostRepository mRepository;

    private final LiveData<List<Post>> mAllPosts;

    public PostViewModel (Application application) {
        super(application);
        mRepository = new PostRepository(application);
        mAllPosts = mRepository.getAllPosts();
    }

    LiveData<List<Post>> getAllPosts() { return mAllPosts; }

    public void insert(Post post) { mRepository.insert(post); }
    public void delete(Post post) { mRepository.delete(post); }
}


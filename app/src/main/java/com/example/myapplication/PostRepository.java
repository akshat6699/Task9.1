package com.example.myapplication;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

class PostRepository {

    private PostDao mPostDao;
    private LiveData<List<Post>> mAllPosts;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    PostRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mPostDao = db.postDao();
        mAllPosts = mPostDao.getAll();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<Post>> getAllPosts() {
        return mAllPosts;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    void insert(Post post) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mPostDao.insertAll(post);
        });
    }

    void delete(Post post) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mPostDao.delete(post);
        });
    }
}

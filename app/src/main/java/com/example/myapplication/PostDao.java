package com.example.myapplication;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PostDao {
    @Insert
    void insertAll(Post... posts);

    @Delete
    void delete(Post post);

    @Query("DELETE FROM post")
    void deleteAll();

    @Query("SELECT * FROM post")
    LiveData<List<Post>> getAll();
}

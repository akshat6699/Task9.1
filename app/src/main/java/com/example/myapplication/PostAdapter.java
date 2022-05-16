package com.example.myapplication;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends ListAdapter<Post, PostViewHolder> {

    public PostAdapter(@NonNull DiffUtil.ItemCallback<Post> diffCallback) {
        super(diffCallback);
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return PostViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        Post current = getItem(position);
        holder.bind(current);
    }

    static class PostDiff extends DiffUtil.ItemCallback<Post> {

        @Override
        public boolean areItemsTheSame(@NonNull Post oldItem, @NonNull Post newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Post oldItem, @NonNull Post newItem) {
            return oldItem.name.equals(newItem.name);
        }
    }
}



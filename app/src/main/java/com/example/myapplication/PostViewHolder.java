package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

class PostViewHolder extends RecyclerView.ViewHolder {
    private final LinearLayout postContainer;
    private final TextView postItemName;
    private final TextView postItemType;

    private PostViewHolder(View itemView) {
        super(itemView);
        postContainer = itemView.findViewById(R.id.postContainer);
        postItemName = itemView.findViewById(R.id.postName);
        postItemType = itemView.findViewById(R.id.postType);
    }

    public void bind(Post post) {
        postItemName.setText(post.name);
        postItemType.setText(post.type);

        postContainer.setOnClickListener(view -> {
            Context c = postContainer.getContext();
            Intent i = new Intent(c, DetailsActivity.class);
            i.putExtra("postId", post.id);
            c.startActivity(i);
        });
    }

    static PostViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_item, parent, false);
        return new PostViewHolder(view);
    }
}

package com.claudia.restaurants.comment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Space;

import com.claudia.restaurants.R;

public class CommentListViewAdapter extends RecyclerView.Adapter<CommentItemViewHolder> {

    private final CommentActivity mParentActivity;
    private final CommentListServices commentListServices;

    public CommentListViewAdapter(
            CommentActivity parent, CommentListServices services) {
        mParentActivity = parent;
        commentListServices = services;
    }

    @Override
    public CommentItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_item, parent, false);

        return new CommentItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CommentItemViewHolder holder, int position) {
        final CommentItem item = commentListServices.getCommentAtPostion(position);

        holder.usernameTextView.setText(item.username);
        holder.sendDateTextView.setText(item.sendDate);
        holder.descriptionTextView.setText(item.description);
        if(item.ownComment) {
            holder.left_space.setVisibility(Space.VISIBLE);
            holder.right_space.setVisibility(Space.GONE);
        }else{
            holder.left_space.setVisibility(Space.GONE);
            holder.right_space.setVisibility(Space.VISIBLE);
        }


    }

    @Override
    public int getItemCount() {
        if(commentListServices!=null){
            return commentListServices.count();
        }
        return 0;
    }


}

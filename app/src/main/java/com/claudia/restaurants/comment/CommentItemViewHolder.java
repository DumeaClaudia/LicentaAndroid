package com.claudia.restaurants.comment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.claudia.restaurants.R;

public class CommentItemViewHolder extends RecyclerView.ViewHolder{
    final TextView usernameTextView;
    final TextView descriptionTextView;
    final TextView sendDateTextView;

    public CommentItemViewHolder(View view) {
        super(view);
        usernameTextView = view.findViewById( R.id.commentUsename_textView);
        descriptionTextView = view.findViewById( R.id.commentDescription_textView);
        sendDateTextView = view.findViewById(R.id.commentDate_textView);

    }
}

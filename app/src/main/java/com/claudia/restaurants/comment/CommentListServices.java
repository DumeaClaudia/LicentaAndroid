package com.claudia.restaurants.comment;

import java.util.ArrayList;
import java.util.List;

public class CommentListServices {

    private List<CommentItem> commentItems = new ArrayList<>();

    public CommentItem getCommentAtPostion(int position) {
        return commentItems.get(position);
    }

    public int count() {
        return commentItems.size();
    }


   public void addComment(CommentItem commentItem){
        commentItems.add(commentItem);
   }

   public void removeElements(){
        commentItems.clear();
   }


}

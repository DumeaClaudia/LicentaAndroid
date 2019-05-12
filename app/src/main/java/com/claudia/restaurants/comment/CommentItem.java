package com.claudia.restaurants.comment;

public class CommentItem {

    public String username;
    public String description;
    public String sendDate;
    public boolean ownComment;

    public CommentItem(String username, String description, String sendDate,  boolean ownComment) {
        this.username = username;
        this.description = description;
        this.sendDate = sendDate;
        this.ownComment= ownComment;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public boolean isOwnComment() {
        return ownComment;
    }

    public void setOwnComment(boolean ownComment) {
        this.ownComment = ownComment;
    }
}

package org.peenyaindustries.piaconnect.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Post implements Parcelable{

    private String postId, categoryId, type, url, status, title, content, excerpt, date, commentCount, thumbnailUrl, imageUrl;

    public static final Parcelable.Creator<Post> CREATOR = new Parcelable.Creator<Post>(){
        public Post createFromParcel(Parcel in){
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    public Post(String postId, String categoryId, String type, String url, String status, String title, String content, String excerpt, String date, String commentCount, String thumbnailUrl, String imageUrl) {
        this.postId = postId;
        this.categoryId = categoryId;
        this.type = type;
        this.url = url;
        this.status = status;
        this.title = title;
        this.content = content;
        this.excerpt = excerpt;
        this.date = date;
        this.commentCount = commentCount;
        this.thumbnailUrl = thumbnailUrl;
        this.imageUrl = imageUrl;
    }

    public Post() {
    }

    public Post(Parcel in) {
        postId = in.readString();
        categoryId = in.readString();
        type = in.readString();
        url = in.readString();
        status = in.readString();
        title = in.readString();
        excerpt = in.readString();
        date = in.readString();
        commentCount = in.readString();
        thumbnailUrl = in.readString();
        imageUrl = in.readString();
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(postId);
        dest.writeString(categoryId);
        dest.writeString(type);
        dest.writeString(url);
        dest.writeString(status);
        dest.writeString(title);
        dest.writeString(excerpt);
        dest.writeString(date);
        dest.writeString(commentCount);
        dest.writeString(thumbnailUrl);
        dest.writeString(imageUrl);
    }
}

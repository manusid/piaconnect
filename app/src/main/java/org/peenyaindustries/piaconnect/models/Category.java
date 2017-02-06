package org.peenyaindustries.piaconnect.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Category implements Parcelable{

    private String categoryId, postId, title, description, parent;

    public static final Parcelable.Creator<Category> CREATOR = new Parcelable.Creator<Category>(){

        public Category createFromParcel(Parcel in){
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }

    };

    public Category(String categoryId, String postId, String title, String description, String parent) {
        this.categoryId = categoryId;
        this.postId = postId;
        this.title = title;
        this.description = description;
        this.parent = parent;
    }

    public Category() {
    }

    public Category(Parcel in) {
        categoryId = in.readString();
        postId = in.readString();
        title = in.readString();
        description = in.readString();
        parent = in.readString();
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(categoryId);
        dest.writeString(postId);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(parent);
    }
}

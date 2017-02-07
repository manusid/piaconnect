package org.peenyaindustries.piaconnect.json;

import android.os.Build;
import android.text.Html;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.peenyaindustries.piaconnect.models.Category;
import org.peenyaindustries.piaconnect.models.Post;

import java.util.ArrayList;

public class Parser {
    public static ArrayList<Category> parseCategory(JSONObject response){

        ArrayList<Category> categoryArrayList = new ArrayList<>();

        if (response != null && response.length() > 0){
            try {
                JSONArray categoryArray = response.getJSONArray("categories");

                for (int i = 0; i < categoryArray.length(); i++){
                    String id = "NA";
                    String title ="NA";
                    String description = "NA";
                    String parent = "NA";

                    JSONObject categoryObject = categoryArray.getJSONObject(i);

                    if (categoryObject.has("id")){
                        id = categoryObject.getString("id");
                    }

                    if (categoryObject.has("title")){
                        title = categoryObject.getString("title");
                    }

                    if (categoryObject.has("description")){
                        description = categoryObject.getString("description");
                    }

                    if (categoryObject.has("parent")){
                        parent = categoryObject.getString("parent");
                    }

                    Category categoryModel = new Category();
                    categoryModel.setCategoryId(id);
                    categoryModel.setTitle(title);
                    categoryModel.setDescription(description);
                    categoryModel.setParent(parent);

                    categoryArrayList.add(categoryModel);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return categoryArrayList;
    }

    public static ArrayList<Post> parsePost(JSONObject response){

        ArrayList<Post> postArrayList = new ArrayList<>();

        if (response != null && response.length() > 0){

            try {
                JSONArray postArray = response.getJSONArray("posts");

                for (int i = 0; i < postArray.length(); i++) {

                    String postId = "NA";
                    String type = "NA";
                    String url = "NA";
                    String status = "NA";
                    String title = "NA";
                    String content = "NA";
                    String excerpt = "NA";
                    String date = "NA";
                    String commentCount = "NA";
                    String thumbnailUrl = "NA";
                    String imageUrl = "NA";
                    String categoryId = "NA";

                    JSONObject postObject = postArray.getJSONObject(i);

                    if (postObject.has("id")) {
                        postId = postObject.getString("id");
                    }

                    if (postObject.has("type")) {
                        type = postObject.getString("type");
                    }

                    if (postObject.has("url")) {
                        url = postObject.getString("url");
                    }

                    if (postObject.has("status")) {
                        status = postObject.getString("status");
                    }

                    if (postObject.has("title")) {
                        title = postObject.getString("title");
                    }

                    if (postObject.has("content")) {
                        content = postObject.getString("content");
                    }

                    if (postObject.has("excerpt")) {
                        excerpt = postObject.getString("excerpt");
                    }

                    if (postObject.has("date")) {
                        date = postObject.getString("date");
                    }

                    if (postObject.has("comment_count")) {
                        commentCount = postObject.getString("comment_count");
                    }

                    if (postObject.has("categories")) {
                        JSONArray categoryArray = postObject.getJSONArray("categories");

                        for (int j = 0; j < categoryArray.length(); j++) {

                            String id = "NA";
                            JSONObject categoryObject = categoryArray.getJSONObject(j);
                            if (categoryObject.has("id")) {
                                id = categoryObject.getString("id");
                                if (categoryId.equals("NA")) {
                                    categoryId = id;
                                } else {
                                    categoryId = categoryId + "-" + id;
                                }
                            }
                        }
                    }

                    if (postObject.has("thumbnail_images")) {
                        JSONObject thumbnailObject = postObject.getJSONObject("thumbnail_images");
                        if (thumbnailObject.has("megamenu-thumb")) {
                            JSONObject thumbnail = thumbnailObject.getJSONObject("megamenu-thumb");
                            if (thumbnail.has("url")) {
                                thumbnailUrl = thumbnail.getString("url");
                            }
                        }

                        if (thumbnailObject.has("accesspress-mag-block-small-thumb")) {
                            JSONObject image = thumbnailObject.getJSONObject("accesspress-mag-block-small-thumb");
                            if (image.has("url")) {
                                imageUrl = image.getString("url");
                            }
                        }
                    }

                    if (Build.VERSION.SDK_INT >= 24) {
                        title = String.valueOf(Html.fromHtml(title, Html.FROM_HTML_MODE_LEGACY));
                    } else {
                        title = String.valueOf(Html.fromHtml(title));
                    }

                    Post postModel = new Post();
                    postModel.setPostId(postId);
                    postModel.setCategoryId(categoryId);
                    postModel.setType(type);
                    postModel.setUrl(url);
                    postModel.setStatus(status);
                    postModel.setTitle(title);
                    postModel.setContent(content);
                    postModel.setExcerpt(excerpt);
                    postModel.setDate(date);
                    postModel.setCommentCount(commentCount);
                    postModel.setThumbnailUrl(thumbnailUrl);
                    postModel.setImageUrl(imageUrl);

                    postArrayList.add(postModel);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return postArrayList;
    }
}

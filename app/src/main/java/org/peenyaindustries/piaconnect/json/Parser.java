package org.peenyaindustries.piaconnect.json;

import android.os.Build;
import android.text.Html;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.peenyaindustries.piaconnect.extras.Constants;
import org.peenyaindustries.piaconnect.models.Category;
import org.peenyaindustries.piaconnect.models.Post;

import java.util.ArrayList;

import static org.peenyaindustries.piaconnect.extras.Keys.CATEGORIES;
import static org.peenyaindustries.piaconnect.extras.Keys.CATEGORY_DESCRIPTION;
import static org.peenyaindustries.piaconnect.extras.Keys.CATEGORY_ID;
import static org.peenyaindustries.piaconnect.extras.Keys.CATEGORY_PARENT;
import static org.peenyaindustries.piaconnect.extras.Keys.CATEGORY_TITLE;
import static org.peenyaindustries.piaconnect.extras.Keys.POSTS;
import static org.peenyaindustries.piaconnect.extras.Keys.POST_COMMENT_COUNT;
import static org.peenyaindustries.piaconnect.extras.Keys.POST_CONTENT;
import static org.peenyaindustries.piaconnect.extras.Keys.POST_DATE;
import static org.peenyaindustries.piaconnect.extras.Keys.POST_EXCERPT;
import static org.peenyaindustries.piaconnect.extras.Keys.POST_ID;
import static org.peenyaindustries.piaconnect.extras.Keys.POST_IMAGE;
import static org.peenyaindustries.piaconnect.extras.Keys.POST_STATUS;
import static org.peenyaindustries.piaconnect.extras.Keys.POST_THUMBNAIL;
import static org.peenyaindustries.piaconnect.extras.Keys.POST_THUMBNAIL_IMAGES;
import static org.peenyaindustries.piaconnect.extras.Keys.POST_TITLE;
import static org.peenyaindustries.piaconnect.extras.Keys.POST_TYPE;
import static org.peenyaindustries.piaconnect.extras.Keys.POST_URL;
import static org.peenyaindustries.piaconnect.extras.Keys.URL;

public class Parser {
    public static ArrayList<Category> parseCategory(JSONObject response) {

        ArrayList<Category> categoryArrayList = new ArrayList<>();

        if (response != null && response.length() > 0) {
            try {
                JSONArray categoryArray = response.getJSONArray(CATEGORIES);

                for (int i = 0; i < categoryArray.length(); i++) {
                    String id = Constants.NA;
                    String title = Constants.NA;
                    String description = Constants.NA;
                    String parent = Constants.NA;

                    JSONObject categoryObject = categoryArray.getJSONObject(i);

                    if (categoryObject.has(CATEGORY_ID)) {
                        id = categoryObject.getString(CATEGORY_ID);
                    }

                    if (categoryObject.has(CATEGORY_TITLE)) {
                        title = categoryObject.getString(CATEGORY_TITLE);
                    }

                    if (categoryObject.has(CATEGORY_DESCRIPTION)) {
                        description = categoryObject.getString(CATEGORY_DESCRIPTION);
                    }

                    if (categoryObject.has(CATEGORY_PARENT)) {
                        parent = categoryObject.getString(CATEGORY_PARENT);
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

    public static ArrayList<Post> parsePost(JSONObject response) {

        ArrayList<Post> postArrayList = new ArrayList<>();

        if (response != null && response.length() > 0) {

            try {
                JSONArray postArray = response.getJSONArray(POSTS);

                for (int i = 0; i < postArray.length(); i++) {

                    String postId = Constants.NA;
                    String type = Constants.NA;
                    String url = Constants.NA;
                    String status = Constants.NA;
                    String title = Constants.NA;
                    String content = Constants.NA;
                    String excerpt = Constants.NA;
                    String date = Constants.NA;
                    String commentCount = Constants.NA;
                    String thumbnailUrl = Constants.NA;
                    String imageUrl = Constants.NA;
                    String categoryId = Constants.NA;
                    String categoryTitle = Constants.NA;

                    JSONObject postObject = postArray.getJSONObject(i);

                    if (postObject.has(POST_ID)) {
                        postId = postObject.getString(POST_ID);
                    }

                    if (postObject.has(POST_TYPE)) {
                        type = postObject.getString(POST_TYPE);
                    }

                    if (postObject.has(POST_URL)) {
                        url = postObject.getString(POST_URL);
                    }

                    if (postObject.has(POST_STATUS)) {
                        status = postObject.getString(POST_STATUS);
                    }

                    if (postObject.has(POST_TITLE)) {
                        title = postObject.getString(POST_TITLE);
                    }

                    if (postObject.has(POST_CONTENT)) {
                        content = postObject.getString(POST_CONTENT);
                    }

                    if (postObject.has(POST_EXCERPT)) {
                        excerpt = postObject.getString(POST_EXCERPT);
                    }

                    if (postObject.has(POST_DATE)) {
                        date = postObject.getString(POST_DATE);
                    }

                    if (postObject.has(POST_COMMENT_COUNT)) {
                        commentCount = postObject.getString(POST_COMMENT_COUNT);
                    }

                    if (postObject.has(CATEGORIES)) {
                        JSONArray categoryArray = postObject.getJSONArray(CATEGORIES);

                        for (int j = 0; j < categoryArray.length(); j++) {

                            String id;
                            JSONObject categoryObject = categoryArray.getJSONObject(j);
                            if (categoryObject.has(CATEGORY_ID)) {
                                id = categoryObject.getString(CATEGORY_ID);
                                if (categoryId.equals(Constants.NA)) {
                                    categoryId = id;
                                } else {
                                    categoryId = categoryId + "-" + id;
                                }
                            }
                        }
                    }

                    if (postObject.has(POST_THUMBNAIL_IMAGES)) {
                        JSONObject thumbnailObject = postObject.getJSONObject(POST_THUMBNAIL_IMAGES);
                        if (thumbnailObject.has(POST_THUMBNAIL)) {
                            JSONObject thumbnail = thumbnailObject.getJSONObject(POST_THUMBNAIL);
                            if (thumbnail.has(URL)) {
                                thumbnailUrl = thumbnail.getString(URL);
                            }
                        }

                        if (thumbnailObject.has(POST_IMAGE)) {
                            JSONObject image = thumbnailObject.getJSONObject(POST_IMAGE);
                            if (image.has(URL)) {
                                imageUrl = image.getString(URL);
                            }
                        }
                    }

                    if (Build.VERSION.SDK_INT >= 24) {
                        title = String.valueOf(Html.fromHtml(title, Html.FROM_HTML_MODE_LEGACY));
                        excerpt = String.valueOf(Html.fromHtml(excerpt, Html.FROM_HTML_MODE_LEGACY));
                    } else {
                        title = String.valueOf(Html.fromHtml(title));
                        excerpt = String.valueOf(Html.fromHtml(excerpt));
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

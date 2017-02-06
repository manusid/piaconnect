package org.peenyaindustries.piaconnect.json;

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

        }

        return postArrayList;
    }
}

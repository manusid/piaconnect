package org.peenyaindustries.piaconnect.extras;

import com.android.volley.RequestQueue;

import org.json.JSONObject;
import org.peenyaindustries.piaconnect.json.Parser;
import org.peenyaindustries.piaconnect.json.Requestor;
import org.peenyaindustries.piaconnect.models.Category;
import org.peenyaindustries.piaconnect.models.Post;
import org.peenyaindustries.piaconnect.piaconnect.MyApplication;

import java.util.ArrayList;

import static org.peenyaindustries.piaconnect.extras.URLEndPoints.URL_CATEGORY;
import static org.peenyaindustries.piaconnect.extras.URLEndPoints.URL_POST;

public class NewsUtils {

    public static ArrayList<Category> loadCategories(RequestQueue requestQueue) {
        JSONObject response = Requestor.requestCategory(requestQueue, URL_CATEGORY);
        ArrayList<Category> categoryArrayList = Parser.parseCategory(response);
        MyApplication.getWritableDatabase().insertCategory(categoryArrayList, true);
        return categoryArrayList;
    }

    public static ArrayList<Post> loadPosts(RequestQueue requestQueue) {
        JSONObject response = Requestor.requestPost(requestQueue, URL_POST);
        ArrayList<Post> postArrayList = Parser.parsePost(response);
        MyApplication.getWritableDatabase().insertPost(postArrayList, true);
        return postArrayList;
    }
}

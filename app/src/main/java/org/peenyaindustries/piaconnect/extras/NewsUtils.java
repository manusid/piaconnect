package org.peenyaindustries.piaconnect.extras;

import com.android.volley.RequestQueue;

import org.json.JSONObject;
import org.peenyaindustries.piaconnect.json.Parser;
import org.peenyaindustries.piaconnect.json.Requestor;
import org.peenyaindustries.piaconnect.models.Category;
import org.peenyaindustries.piaconnect.models.Post;
import org.peenyaindustries.piaconnect.piaconnect.MyApplication;

import java.util.ArrayList;

public class NewsUtils {

    //TODO - URL IS EMPTY
    public static ArrayList<Category> loadCategories(RequestQueue requestQueue){
        JSONObject response = Requestor.requestCategory(requestQueue, "http://www.peenyaindustries.org/api/get_category_index/");
        ArrayList<Category> categoryArrayList = Parser.parseCategory(response);
        MyApplication.getWritableDatabase().insertCategory(categoryArrayList, true);
        return categoryArrayList;
    }

    //TODO - URL IS EMPTY
    public static ArrayList<Post> loadPosts(RequestQueue requestQueue){
        JSONObject response = Requestor.requestPost(requestQueue, "http://www.peenyaindustries.org/api/get_posts/");
        ArrayList<Post> postArrayList = Parser.parsePost(response);
        MyApplication.getWritableDatabase().insertPost(postArrayList, true);
        return postArrayList;
    }
}

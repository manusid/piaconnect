package org.peenyaindustries.piaconnect.tasks;

import android.os.AsyncTask;

import com.android.volley.RequestQueue;

import org.peenyaindustries.piaconnect.callbacks.PostLoadedListener;
import org.peenyaindustries.piaconnect.extras.NewsUtils;
import org.peenyaindustries.piaconnect.models.Post;
import org.peenyaindustries.piaconnect.network.VolleySingleton;

import java.util.ArrayList;

public class TaskLoadPost extends AsyncTask<Void, Void, ArrayList<Post>>{

    private PostLoadedListener myComponent;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;

    public TaskLoadPost(PostLoadedListener myComponent){

        this.myComponent = myComponent;
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
    }

    @Override
    protected ArrayList<Post> doInBackground(Void... params) {

        return NewsUtils.loadPosts(requestQueue);
    }

    @Override
    protected void onPostExecute(ArrayList<Post> postArrayList) {
        if (myComponent != null){
            myComponent.onPostLoadedListener(postArrayList);
        }
    }
}

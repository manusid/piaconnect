package org.peenyaindustries.piaconnect.tasks;

import android.os.AsyncTask;

import com.android.volley.RequestQueue;

import org.peenyaindustries.piaconnect.callbacks.CategoryLoadedListener;
import org.peenyaindustries.piaconnect.extras.NewsUtils;
import org.peenyaindustries.piaconnect.models.Category;
import org.peenyaindustries.piaconnect.network.VolleySingleton;

import java.util.ArrayList;

public class TaskLoadCategory extends AsyncTask<Void, Void, ArrayList<Category>> {

    private CategoryLoadedListener myComponent;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;

    public TaskLoadCategory(CategoryLoadedListener myComponent) {

        this.myComponent = myComponent;
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
    }

    @Override
    protected ArrayList<Category> doInBackground(Void... params) {

        return NewsUtils.loadCategories(requestQueue);
    }

    @Override
    protected void onPostExecute(ArrayList<Category> categoryArrayList) {
        if (myComponent != null) {
            myComponent.onCategoryLoadedListener(categoryArrayList);
        }
    }
}


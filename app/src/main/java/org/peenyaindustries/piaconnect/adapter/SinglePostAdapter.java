package org.peenyaindustries.piaconnect.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.ImageLoader;

import org.peenyaindustries.piaconnect.R;
import org.peenyaindustries.piaconnect.models.Category;
import org.peenyaindustries.piaconnect.network.VolleySingleton;

import java.util.ArrayList;

public class SinglePostAdapter extends RecyclerView.Adapter<SinglePostAdapter.ViewHolder>{

    private Context context;
    private ArrayList<Category> postsArrayList;
    private ImageLoader imageLoader;

    public SinglePostAdapter(Context context, ArrayList<Category> postsArrayList) {
        this.context = context;
        this.postsArrayList = postsArrayList;
        imageLoader = VolleySingleton.getInstance().getImageLoader();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}

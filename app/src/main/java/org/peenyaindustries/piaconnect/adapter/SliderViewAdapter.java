package org.peenyaindustries.piaconnect.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.ImageLoader;

import org.peenyaindustries.piaconnect.R;
import org.peenyaindustries.piaconnect.models.Post;
import org.peenyaindustries.piaconnect.network.VolleySingleton;

import java.util.ArrayList;

public class SliderViewAdapter extends RecyclerView.Adapter<SliderViewAdapter.ViewHolder>{

    private Context context;
    private ArrayList<Post> postsArrayList;
    private ImageLoader imageLoader;

    public SliderViewAdapter(Context context, ArrayList<Post> postsArrayList) {
        this.context = context;
        this.postsArrayList = postsArrayList;
        imageLoader = VolleySingleton.getInstance().getImageLoader();
    }

    public void setPost(ArrayList<Post> postArrayList){
        this.postsArrayList = postArrayList;

        notifyDataSetChanged();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_item, parent, false);
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

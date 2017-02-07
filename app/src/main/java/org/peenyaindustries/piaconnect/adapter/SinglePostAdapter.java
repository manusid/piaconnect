package org.peenyaindustries.piaconnect.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import org.peenyaindustries.piaconnect.R;
import org.peenyaindustries.piaconnect.models.Post;
import org.peenyaindustries.piaconnect.network.VolleySingleton;
import org.peenyaindustries.piaconnect.utilities.L;

import java.util.ArrayList;

public class SinglePostAdapter extends RecyclerView.Adapter<SinglePostAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Post> postArrayList;
    private ImageLoader imageLoader;

    public SinglePostAdapter(Context context, ArrayList<Post> postArrayList) {
        this.context = context;
        this.postArrayList = postArrayList;
        imageLoader = VolleySingleton.getInstance().getImageLoader();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Post postModel = postArrayList.get(position);
        L.Log("" + position + " - " + postModel.getCategoryId() + " - " + postModel.getTitle());
        holder.postText.setText(postModel.getTitle());
    }

    @Override
    public int getItemCount() {
        if (postArrayList.size() < 5) {
            return postArrayList.size();
        } else {
            return 5;
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView postImage;
        TextView postText;

        public ViewHolder(View itemView) {
            super(itemView);

            postImage = (ImageView) itemView.findViewById(R.id.postImage);
            postText = (TextView) itemView.findViewById(R.id.postText);
        }
    }
}

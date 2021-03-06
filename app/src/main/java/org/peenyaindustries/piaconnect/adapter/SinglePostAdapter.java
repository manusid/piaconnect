package org.peenyaindustries.piaconnect.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import org.peenyaindustries.piaconnect.R;
import org.peenyaindustries.piaconnect.activities.NewsDetailActivity;
import org.peenyaindustries.piaconnect.extras.Constants;
import org.peenyaindustries.piaconnect.models.Post;
import org.peenyaindustries.piaconnect.network.VolleySingleton;
import org.peenyaindustries.piaconnect.storage.SessionManager;

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

        holder.postText.setText(postModel.getTitle());

        String urlThumbnail = postModel.getThumbnailUrl();
        loadImages(urlThumbnail, holder);

        holder.postId = postModel.getPostId();
        holder.postType = postModel.getType();
        holder.postUrl = postModel.getUrl();
        holder.postStatus = postModel.getStatus();
        holder.postTitle = postModel.getTitle();
        holder.postContent = postModel.getContent();
        holder.postExcerpt = postModel.getExcerpt();
        holder.postDate = postModel.getDate();
        holder.postCommentCount = postModel.getCommentCount();
        holder.postCategory = postModel.getCategoryId();
        holder.postThumbnailUrl = postModel.getThumbnailUrl();
        holder.postImageUrl = postModel.getImageUrl();
    }

    private void loadImages(String urlThumbnail, final ViewHolder holder) {
        if (!urlThumbnail.equals(Constants.NA)) {
            imageLoader.get(urlThumbnail, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    holder.postImage.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }
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
        String postId, postType, postUrl, postStatus, postTitle, postContent, postExcerpt, postDate, postCommentCount, postCategory, postThumbnailUrl, postImageUrl;


        public ViewHolder(View itemView) {
            super(itemView);

            postImage = (ImageView) itemView.findViewById(R.id.postImage);
            postText = (TextView) itemView.findViewById(R.id.postText);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SessionManager session = new SessionManager(context);
                    session.storeData(postId, postType, postUrl, postStatus, postTitle, postContent, postExcerpt, postDate, postCommentCount, postCategory, postThumbnailUrl, postImageUrl);
                    context.startActivity(new Intent(context, NewsDetailActivity.class));
                }
            });
        }
    }
}

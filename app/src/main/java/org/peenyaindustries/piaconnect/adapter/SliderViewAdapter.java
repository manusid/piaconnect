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

public class SliderViewAdapter extends RecyclerView.Adapter<SliderViewAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Post> postsArrayList;
    private ImageLoader imageLoader;

    public SliderViewAdapter(Context context, ArrayList<Post> postArrayList) {
        this.context = context;
        this.postsArrayList = postArrayList;
        imageLoader = VolleySingleton.getInstance().getImageLoader();
    }

    public void setPost(ArrayList<Post> postArrayList) {
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
        if (postsArrayList.size() != 0) {
            Post postModel = postsArrayList.get(position);

            holder.sliderText.setText(postModel.getTitle());

            String url = postModel.getImageUrl();
            loadImages(url, holder);

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
            holder.postThumbnail = postModel.getThumbnailUrl();
            holder.postImage = postModel.getImageUrl();

        }
    }

    @Override
    public int getItemCount() {
        if (postsArrayList.size() < 9) {
            return postsArrayList.size();
        } else {
            return 9;
        }
    }

    private void loadImages(String urlThumbnail, final ViewHolder holder) {
        if (!urlThumbnail.equals(Constants.NA)) {
            imageLoader.get(urlThumbnail, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    holder.sliderImage.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView sliderText;
        ImageView sliderImage;
        String postId, postType, postUrl, postStatus, postTitle, postContent, postExcerpt, postDate, postCommentCount, postCategory, postThumbnail, postImage;

        public ViewHolder(View itemView) {
            super(itemView);

            sliderImage = (ImageView) itemView.findViewById(R.id.sliderImage);
            sliderText = (TextView) itemView.findViewById(R.id.sliderText);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SessionManager session = new SessionManager(context);
                    session.storeData(postId, postType, postUrl, postStatus, postTitle, postContent, postExcerpt, postDate, postCommentCount, postCategory, postThumbnail, postImage);
                    context.startActivity(new Intent(context, NewsDetailActivity.class));
                }
            });
        }
    }
}

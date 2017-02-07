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
import org.peenyaindustries.piaconnect.models.Category;
import org.peenyaindustries.piaconnect.models.Post;
import org.peenyaindustries.piaconnect.network.VolleySingleton;
import org.peenyaindustries.piaconnect.piaconnect.MyApplication;
import org.peenyaindustries.piaconnect.storage.SessionManager;
import org.peenyaindustries.piaconnect.utilities.L;

import java.util.ArrayList;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Post> postArrayList;
    private ImageLoader imageLoader;
    private String categoryId;

    public NewsListAdapter(Context context, String id) {
        this.context = context;
        this.categoryId = id;
        imageLoader = VolleySingleton.getInstance().getImageLoader();
    }

    public void setPost(ArrayList<Post> postArrayList, String categoryId) {
        this.postArrayList = postArrayList;
        this.categoryId = categoryId;

        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Post postModel = postArrayList.get(position);
        L.Log("CategoryId Intitated" + categoryId);

        ArrayList<Category> categoryArrayList = MyApplication.getWritableDatabase().readCategoryById(categoryId);


        if (categoryArrayList.size() != 0) {
            for (int i = 0; i < categoryArrayList.size(); i++) {
                Category categoryModel = categoryArrayList.get(i);
                holder.newsListCategory.setText(categoryModel.getTitle());
            }
        }


        holder.newsListTitle.setText(postModel.getTitle());
        holder.newsListExcerpt.setText(postModel.getExcerpt());

        String imageUrl = postModel.getImageUrl();
        loadImages(imageUrl, holder);

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

    private void loadImages(String urlThumbnail, final ViewHolder holder) {
        if (!urlThumbnail.equals(Constants.NA)) {
            imageLoader.get(urlThumbnail, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    holder.newsListImage.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return postArrayList.size();
    }

    public void setFilter(ArrayList<Post> mNewsModel) {
        postArrayList = new ArrayList<>();
        postArrayList.addAll(mNewsModel);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView newsListImage;
        TextView newsListCategory, newsListTitle, newsListExcerpt;
        String postId, postType, postUrl, postStatus, postTitle, postContent, postExcerpt, postDate, postCommentCount, postCategory, postThumbnail, postImage;


        public ViewHolder(View itemView) {
            super(itemView);
            newsListImage = (ImageView) itemView.findViewById(R.id.newsListImage);
            newsListCategory = (TextView) itemView.findViewById(R.id.newsListCategory);
            newsListTitle = (TextView) itemView.findViewById(R.id.newsListTitle);
            newsListExcerpt = (TextView) itemView.findViewById(R.id.newsListExcerpt);

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

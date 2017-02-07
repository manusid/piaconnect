package org.peenyaindustries.piaconnect.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import org.peenyaindustries.piaconnect.R;
import org.peenyaindustries.piaconnect.activities.NewsActivity;
import org.peenyaindustries.piaconnect.models.Category;
import org.peenyaindustries.piaconnect.models.Post;
import org.peenyaindustries.piaconnect.network.VolleySingleton;
import org.peenyaindustries.piaconnect.piaconnect.MyApplication;
import org.peenyaindustries.piaconnect.utilities.L;

import java.util.ArrayList;

public class CategoryViewAdapter extends RecyclerView.Adapter<CategoryViewAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Category> categoryArrayList;
    private ImageLoader imageLoader;
    private SinglePostAdapter adapter;

    public CategoryViewAdapter(Context context) {
        this.context = context;
        imageLoader = VolleySingleton.getInstance().getImageLoader();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new ViewHolder(view);
    }

    public void setCategory(ArrayList<Category> categoryArrayList) {
        this.categoryArrayList = categoryArrayList;

        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Category categoryModel = categoryArrayList.get(position);

        holder.categoryText.setText(categoryModel.getTitle());
        holder.moreText.setText("MORE");

        ArrayList<Post> postArrayList = MyApplication.getWritableDatabase().readPostsByCategoryId(categoryModel.getCategoryId());

        adapter = new SinglePostAdapter(context, postArrayList);
        holder.singlePostView.setNestedScrollingEnabled(false);
        holder.singlePostView.setHasFixedSize(true);
        holder.singlePostView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.singlePostView.setAdapter(adapter);

        holder.moreText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewsActivity.class);
                intent.putExtra("id", categoryModel.getCategoryId());
                L.Log("Model" + categoryModel.getCategoryId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView categoryText, moreText;
        RecyclerView singlePostView;

        public ViewHolder(View itemView) {
            super(itemView);

            categoryText = (TextView) itemView.findViewById(R.id.categoryText);
            moreText = (TextView) itemView.findViewById(R.id.moreText);
            singlePostView = (RecyclerView) itemView.findViewById(R.id.singlePostView);
        }
    }
}

package org.peenyaindustries.piaconnect.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.peenyaindustries.piaconnect.R;
import org.peenyaindustries.piaconnect.activities.NewsActivity;
import org.peenyaindustries.piaconnect.models.Category;

import java.util.ArrayList;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Category> categoryArrayList;

    public CategoryListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.category_list, parent, false);
        return new ViewHolder(view);
    }

    public void setCategory(ArrayList<Category> categoryArrayList) {
        this.categoryArrayList = categoryArrayList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Category categoryModel = categoryArrayList.get(position);

        holder.categoryList.setText(categoryModel.getTitle());
        holder.categoryId = categoryModel.getCategoryId();
    }

    @Override
    public int getItemCount() {
        return categoryArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView categoryList;
        String categoryId;

        public ViewHolder(View itemView) {
            super(itemView);

            categoryList = (TextView) itemView.findViewById(R.id.categoryList);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, NewsActivity.class);
                    i.putExtra("id", categoryId);
                    context.startActivity(i);
                    ((NewsActivity) context).finish();
                }
            });
        }
    }
}

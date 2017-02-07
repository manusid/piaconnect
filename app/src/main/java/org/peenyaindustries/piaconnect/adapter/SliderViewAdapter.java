package org.peenyaindustries.piaconnect.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import org.peenyaindustries.piaconnect.R;
import org.peenyaindustries.piaconnect.models.Post;
import org.peenyaindustries.piaconnect.network.VolleySingleton;

import java.util.ArrayList;

public class SliderViewAdapter extends RecyclerView.Adapter<SliderViewAdapter.ViewHolder>{

    private Context context;
    private ArrayList<Post> postsArrayList;
    private ImageLoader imageLoader;

    public SliderViewAdapter(Context context, ArrayList<Post> postArrayList) {
        this.context = context;
        this.postsArrayList = postArrayList;
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
        if (postsArrayList.size() != 0) {
            Post postModel = postsArrayList.get(position);

            holder.sliderText.setText(postModel.getTitle());

            String url = postModel.getImageUrl();
            loadImages(url, holder);
        }
    }

    @Override
    public int getItemCount() {
        if (postsArrayList.size() < 5) {
            return postsArrayList.size();
        } else {
            return 5;
        }
    }

    private void loadImages(String urlThumbnail, final ViewHolder holder) {
        if (!urlThumbnail.equals("NA")) {
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

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView sliderText;
        ImageView sliderImage;

        public ViewHolder(View itemView) {
            super(itemView);

            sliderImage = (ImageView) itemView.findViewById(R.id.sliderImage);
            sliderText = (TextView) itemView.findViewById(R.id.sliderText);
        }
    }
}

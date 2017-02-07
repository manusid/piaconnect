package org.peenyaindustries.piaconnect.callbacks;

import org.peenyaindustries.piaconnect.models.Post;

import java.util.ArrayList;

public interface PostLoadedListener {
    void onPostLoadedListener(ArrayList<Post> postArrayList);
}

package org.peenyaindustries.piaconnect.callbacks;

import org.peenyaindustries.piaconnect.models.Category;

import java.util.ArrayList;

public interface CategoryLoadedListener {
    public void onCategoryLoadedListener(ArrayList<Category> categoryArrayList);
}

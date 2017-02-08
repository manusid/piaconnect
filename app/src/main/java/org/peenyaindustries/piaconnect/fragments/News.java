package org.peenyaindustries.piaconnect.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import org.peenyaindustries.piaconnect.R;
import org.peenyaindustries.piaconnect.adapter.NewsListAdapter;
import org.peenyaindustries.piaconnect.models.Category;
import org.peenyaindustries.piaconnect.models.Post;
import org.peenyaindustries.piaconnect.piaconnect.MyApplication;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link News#newInstance} factory method to
 * create an instance of this fragment.
 */
public class News extends Fragment implements SearchView.OnQueryTextListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String STATE_POST = "state_post";
    public String id = "1";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView newsListView;
    private NewsListAdapter newsListAdapter;
    private ArrayList<Post> postArrayList;
    private ArrayList<Category> categoryArrayList;


    public News() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment News.
     */
    // TODO: Rename and change types and number of parameters
    public static News newInstance(String param1, String param2) {
        News fragment = new News();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        Bundle extras = getActivity().getIntent().getExtras();

        if (extras != null) {
            id = extras.getString("id");
        } else {
            id = "empty";
        }

        newsListView = (RecyclerView) view.findViewById(R.id.newsListView);
        newsListView.setHasFixedSize(true);
        newsListView.setLayoutManager(new LinearLayoutManager(getActivity()));

        setHasOptionsMenu(true);

        if (id.equals("empty")) {
            postArrayList = MyApplication.getWritableDatabase().readPosts();
        } else {
            postArrayList = MyApplication.getWritableDatabase().readPostsByCategoryId(id);
        }

        newsListAdapter = new NewsListAdapter(getActivity(), id);
        newsListView.setAdapter(newsListAdapter);

        newsListAdapter.setPost(postArrayList, id);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //save the data list to a parcelable prior to rotation or configuration change
        outState.putParcelableArrayList(STATE_POST, postArrayList);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final ArrayList<Post> filteredModelList = filter(postArrayList, newText);
        newsListAdapter.setFilter(filteredModelList);
        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_news_activity, menu);

        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(item,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        // Do something when collapsed
                        newsListAdapter.setFilter(postArrayList);
                        return true; // Return true to collapse action view
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        // Do something when expanded
                        return true; // Return true to expand action view
                    }
                });
    }

    private ArrayList<Post> filter(ArrayList<Post> models, String query) {
        query = query.toLowerCase();
        final ArrayList<Post> filteredModelList = new ArrayList<>();
        for (Post model : models) {
            final String text = model.getTitle().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }
}

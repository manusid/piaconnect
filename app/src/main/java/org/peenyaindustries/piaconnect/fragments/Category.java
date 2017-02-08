package org.peenyaindustries.piaconnect.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.peenyaindustries.piaconnect.R;
import org.peenyaindustries.piaconnect.activities.NewsActivity;
import org.peenyaindustries.piaconnect.adapter.CategoryListAdapter;
import org.peenyaindustries.piaconnect.piaconnect.MyApplication;

import java.util.ArrayList;


public class Category extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String STATE_CATEGORY = "state_category";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList<org.peenyaindustries.piaconnect.models.Category> categoryArrayList;
    private CategoryListAdapter adapter;

    public Category() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Category.
     */
    // TODO: Rename and change types and number of parameters
    public static Category newInstance(String param1, String param2) {
        Category fragment = new Category();
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
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        RecyclerView categoryListView = (RecyclerView) view.findViewById(R.id.categoryListView);
        categoryListView.setHasFixedSize(true);
        categoryListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new CategoryListAdapter(getActivity());
        categoryArrayList = MyApplication.getWritableDatabase().readCategory();
        adapter.setCategory(categoryArrayList);
        categoryListView.setAdapter(adapter);

        TextView viewAll = (TextView) view.findViewById(R.id.viewAll);
        viewAll.setText("VIEW ALL");
        viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), NewsActivity.class);
                getActivity().startActivity(i);
                getActivity().finish();
            }
        });
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //save the data list to a parcelable prior to rotation or configuration change
        outState.putParcelableArrayList(STATE_CATEGORY, categoryArrayList);
    }


}

package org.peenyaindustries.piaconnect.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import org.peenyaindustries.piaconnect.R;
import org.peenyaindustries.piaconnect.adapter.CategoryViewAdapter;
import org.peenyaindustries.piaconnect.adapter.SliderViewAdapter;
import org.peenyaindustries.piaconnect.callbacks.CategoryLoadedListener;
import org.peenyaindustries.piaconnect.callbacks.PostLoadedListener;
import org.peenyaindustries.piaconnect.models.Category;
import org.peenyaindustries.piaconnect.models.Post;
import org.peenyaindustries.piaconnect.piaconnect.MyApplication;
import org.peenyaindustries.piaconnect.tasks.TaskLoadCategory;
import org.peenyaindustries.piaconnect.tasks.TaskLoadPost;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, CategoryLoadedListener, PostLoadedListener {

    //The key used to store array list of data objects to and from parcelable
    private static final String STATE_POST = "state_post";
    private static final String STATE_CATEGORY = "state_category";

    private RecyclerView sliderView;
    private RecyclerView categoryPostView;
    private SliderViewAdapter sliderAdapter;
    private CategoryViewAdapter categoryAdapter;
    private ArrayList<Category> categoryArrayList;
    private ArrayList<Post> postArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        sliderView = (RecyclerView) findViewById(R.id.sliderView);
        sliderView.setNestedScrollingEnabled(false);
        sliderView.setHasFixedSize(true);
        sliderView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        sliderAdapter = new SliderViewAdapter(this, postArrayList);
        sliderView.setAdapter(sliderAdapter);

        categoryPostView = (RecyclerView) findViewById(R.id.categoryPostView);
        categoryPostView.setNestedScrollingEnabled(false);
        categoryPostView.setHasFixedSize(true);
        categoryPostView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        categoryAdapter = new CategoryViewAdapter(this);
        categoryPostView.setAdapter(categoryAdapter);

        if (savedInstanceState != null) {
            postArrayList = savedInstanceState.getParcelableArrayList(STATE_POST);
            categoryArrayList = savedInstanceState.getParcelableArrayList(STATE_CATEGORY);
        } else {

            //categoryArrayList = MyApplication.getWritableDatabase().readCategory();
            categoryArrayList = loadValueManually();
            postArrayList = MyApplication.getWritableDatabase().readPosts();

            if (categoryArrayList.isEmpty()) {

            }

            if (postArrayList.isEmpty()) {
                new TaskLoadPost(this).execute();
                new TaskLoadCategory(this).execute();
            }
        }

        categoryAdapter.setCategory(categoryArrayList);
        sliderAdapter.setPost(postArrayList);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList(STATE_CATEGORY, categoryArrayList);
        outState.putParcelableArrayList(STATE_POST, postArrayList);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_news) {
            startActivity(new Intent(this, NewsActivity.class));
        } else if (id == R.id.nav_industry) {

        } else if (id == R.id.nav_events) {

        } else if (id == R.id.nav_jobs) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_faq) {

        } else if (id == R.id.nav_help) {

        } else if (id == R.id.nav_pia) {

        } else if (id == R.id.nav_pia_office_bearers) {

        } else if (id == R.id.nav_managing_council_members) {

        } else if (id == R.id.nav_panel_list) {

        } else if (id == R.id.nav_invitee_members) {

        } else if (id == R.id.nav_past_presidents) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onCategoryLoadedListener(ArrayList<Category> categoryArrayList) {
        categoryAdapter.setCategory(categoryArrayList);
    }

    @Override
    public void onPostLoadedListener(ArrayList<Post> postArrayList) {
        sliderAdapter.setPost(postArrayList);
    }

    public ArrayList<Category> loadValueManually() {

        ArrayList<Category> categoryManualArrayList = new ArrayList<>();

        ArrayList<String> categoryTitleArray = new ArrayList<>();
        categoryTitleArray.add(0, "PIA News");
        categoryTitleArray.add(1, "Peenya News");
        categoryTitleArray.add(2, "Message");
        categoryTitleArray.add(3, "News");

        ArrayList<String> categoryIdArray = new ArrayList<>();
        categoryIdArray.add(0, "1");
        categoryIdArray.add(1, "26");
        categoryIdArray.add(2, "18");
        categoryIdArray.add(3, "30");

        ArrayList<String> categoryDescriptionArray = new ArrayList<>();
        categoryDescriptionArray.add(0, "NA");
        categoryDescriptionArray.add(1, "NA");
        categoryDescriptionArray.add(2, "NA");
        categoryDescriptionArray.add(3, "NA");

        ArrayList<String> categoryParentArray = new ArrayList<>();
        categoryParentArray.add(0, "0");
        categoryParentArray.add(1, "0");
        categoryParentArray.add(2, "0");
        categoryParentArray.add(3, "0");

        for (int i = 0; i < categoryTitleArray.size(); i++) {
            Category categoryModel = new Category();

            categoryModel.setCategoryId(categoryIdArray.get(i));
            categoryModel.setTitle(categoryTitleArray.get(i));
            categoryModel.setDescription(categoryDescriptionArray.get(i));
            categoryModel.setParent(categoryParentArray.get(i));

            categoryManualArrayList.add(categoryModel);
        }

        return categoryManualArrayList;
    }
}

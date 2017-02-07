package org.peenyaindustries.piaconnect.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import org.peenyaindustries.piaconnect.R;
import org.peenyaindustries.piaconnect.adapter.ViewPagerAdapter;
import org.peenyaindustries.piaconnect.fragments.Category;
import org.peenyaindustries.piaconnect.fragments.News;

public class NewsActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private int icons[] = {R.drawable.ic_menu_camera, R.drawable.ic_menu_gallery};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Disable Title on Toolbar
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        viewPager = (ViewPager) findViewById(R.id.newsViewPager);
        setUpViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.newsTabLayout);
        tabLayout.setupWithViewPager(viewPager);
        setUpTabIcons();
    }

    public void setUpViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new News(), "News");
        adapter.addFragment(new Category(), "Categories");
        viewPager.setAdapter(adapter);
    }

    public void setUpTabIcons() {
        tabLayout.getTabAt(0).setIcon(icons[0]);
        tabLayout.getTabAt(1).setIcon(icons[1]);
    }
}

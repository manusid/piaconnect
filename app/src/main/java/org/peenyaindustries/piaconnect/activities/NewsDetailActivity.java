package org.peenyaindustries.piaconnect.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import org.peenyaindustries.piaconnect.R;
import org.peenyaindustries.piaconnect.extras.Constants;
import org.peenyaindustries.piaconnect.network.VolleySingleton;
import org.peenyaindustries.piaconnect.storage.SessionManager;

import java.util.HashMap;

public class NewsDetailActivity extends AppCompatActivity {


    private SessionManager session;
    private ImageLoader imageLoader;
    private TextView detailTitle, detailContent;
    private ImageView detailImage;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        detailImage = (ImageView) findViewById(R.id.detailImage);
        detailTitle = (TextView) findViewById(R.id.detailTitle);
        detailContent = (TextView) findViewById(R.id.detailContent);
        webView = (WebView) findViewById(R.id.webView);

        session = new SessionManager(this);

        HashMap<String, String> data = session.getPostDetails();

        imageLoader = VolleySingleton.getInstance().getImageLoader();

        detailTitle.setText(data.get(SessionManager.KEY_TITLE));
        //detailContent.setText(data.get(SessionManager.KEY_CONTENT));
        loadImages(data.get(SessionManager.KEY_IMAGE_URL));
        webView.getSettings().setJavaScriptEnabled(false);
        webView.loadData(data.get(SessionManager.KEY_CONTENT), "text/html", "UTF-8");
    }

    private void loadImages(String urlThumbnail) {
        if (!urlThumbnail.equals(Constants.NA)) {
            imageLoader.get(urlThumbnail, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    detailImage.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

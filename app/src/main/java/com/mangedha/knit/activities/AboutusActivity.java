package com.mangedha.knit.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mangedha.knit.R;
import com.mangedha.knit.http.models.AboutUsModel;

public class AboutusActivity extends MangedhaKnitActivity {

    TextView toolbartitle, about_us_text;
    RelativeLayout loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();


    }

    void init(){
        toolbartitle=(TextView)findViewById(R.id.toolbartitle);
        toolbartitle.setText("About us");
        about_us_text = (TextView) findViewById(R.id.about_us_text);
        loader = (RelativeLayout) findViewById(R.id.loader);
        loader.setVisibility(View.VISIBLE);

        AboutUsModel.get(new AboutUsModel.AboutUsModelInterface() {
            @Override
            public void onSuccess(AboutUsModel aboutUsModel) {
                loader.setVisibility(View.INVISIBLE);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    about_us_text.setText(Html.fromHtml(aboutUsModel.getAbout_us(), Html.FROM_HTML_MODE_LEGACY));
                } else {
                    about_us_text.setText(Html.fromHtml(aboutUsModel.getAbout_us()));
                }
            }

            @Override
            public void onFail(String error) {
                loader.setVisibility(View.INVISIBLE);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    about_us_text.setText(Html.fromHtml(error, Html.FROM_HTML_MODE_LEGACY));
                } else {
                    about_us_text.setText(Html.fromHtml(error));
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


}

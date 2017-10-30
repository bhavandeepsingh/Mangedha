package com.mangedha.knit.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.mangedha.knit.MangedhaApplication;
import com.mangedha.knit.R;
import com.mangedha.knit.adapters.ImageGalleryAdaptor;
import com.mangedha.knit.http.models.ProductsModel;

public class ImageGalleryActivity extends MangedhaKnitActivity{


    ImageView crossimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_image_gallery);
        ProductsModel.Product product= MangedhaApplication.getProduct();
        setupGalleryView(product);

        crossimg = (ImageView) findViewById(R.id.crossimg);
        crossimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setupGalleryView(ProductsModel.Product product) {
        setupViewPager(product);
        ((TextView) findViewById(R.id.gallery_view_news_title)).setText(product.getName());
    }

    void setupViewPager(final ProductsModel.Product product){

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new ImageGalleryAdaptor(this, product.getProductFiles()));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changeTotalTextView(position + 1, product.getProductFiles().size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        int index = (int) getIntent().getExtras().get("slide_index");
        viewPager.setCurrentItem(index);
        changeTotalTextView(index + 1, product.getProductFiles().size());
    }

    void changeTotalTextView(int page_no, int total_size){
        ((TextView) findViewById(R.id.gallery_view_total)).setText( String.valueOf(page_no) + "/" + String.valueOf(total_size));
    }

}
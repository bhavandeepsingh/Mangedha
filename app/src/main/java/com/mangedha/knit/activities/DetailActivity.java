package com.mangedha.knit.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mangedha.knit.MangedhaApplication;
import com.mangedha.knit.R;
import com.mangedha.knit.adapters.DetailImageView;
import com.mangedha.knit.helpers.AlertHelper;
import com.mangedha.knit.http.models.FavoriteCallModel;
import com.mangedha.knit.http.models.ProductsModel;

public class DetailActivity extends MangedhaKnitActivity implements View.OnClickListener {

    Context context = DetailActivity.this;
    ImageView detail_page_back_button, detail_favorite;
    TextView product_name, detail_category, detail_price, detail_description;
    ProductsModel.Product product;
    ViewPager mImageViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        product = MangedhaApplication.getProduct();
        setup();
        setupViewPager();
    }

    void setupViewPager() {

        mImageViewPager = (ViewPager) findViewById(R.id.detail_view_pager);
        if(product != null) {
            mImageViewPager.setAdapter(new DetailImageView(context, product.getProductFiles()));
        }
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabDots);
        tabLayout.setupWithViewPager(mImageViewPager, true);
    }

    void setup(){
        detail_page_back_button = (ImageView) findViewById(R.id.detail_page_back_button);
        detail_page_back_button.setOnClickListener(this);

        detail_favorite = (ImageView) findViewById(R.id.detail_favorite);
        detail_favorite.setOnClickListener(this);

        product_name = (TextView) findViewById(R.id.product_name);
        detail_category = (TextView) findViewById(R.id.detail_category);
        detail_price = (TextView) findViewById(R.id.detail_price);
        detail_description = (TextView) findViewById(R.id.detail_description);

        if(product != null){
            product_name.setText(product.getName());
            detail_category.setText(product.getCategory().getName());
            detail_price.setText(String.valueOf(product.getPrice()));
            detail_description.setText(product.getDescription());
        }

        if(product != null && product.getFavoriteModel() != null){
            detail_favorite.setImageResource(R.mipmap.ic_star);
        }else{
            detail_favorite.setImageResource(R.mipmap.ic_star_disable);
        }

    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.detail_page_back_button){
            super.onBackPressed();
        }

        if(view.getId() == R.id.detail_favorite){
            favorite();
        }
    }

    void favorite(){
        if(product.getFavoriteModel() != null){
            favoriteRemove();
        }else{
            favoriteAdd();
        }
    }

    private void favoriteRemove() {
        FavoriteCallModel.favoriteRemove(product.getId(), new FavoriteCallModel.FavoriteCallInteface() {
            @Override
            public void onSuccess(FavoriteCallModel favoriteCallModel) {
                MangedhaApplication.getProduct().setFavoriteModel(null);
                detail_favorite.setImageResource(R.mipmap.ic_star_disable);
                AlertHelper.success("Product remove from favorite successfully.", DetailActivity.this);
            }

            @Override
            public void onFail(String error) {
                AlertHelper.error(error, DetailActivity.this);
            }
        });
    }

    private void favoriteAdd() {
        FavoriteCallModel.favoriteAdd(product.getId(), new FavoriteCallModel.FavoriteCallInteface() {
            @Override
            public void onSuccess(FavoriteCallModel favoriteCallModel) {
                MangedhaApplication.getProduct().setFavoriteModel(favoriteCallModel.getFavoriteModel());
                detail_favorite.setImageResource(R.mipmap.ic_star);
                AlertHelper.success("Product add to favorite successfully.", DetailActivity.this);
            }

            @Override
            public void onFail(String error) {
                AlertHelper.error(error, DetailActivity.this);
            }
        });
    }
}

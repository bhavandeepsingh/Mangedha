package com.mangedha.knit.activities;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mangedha.knit.MangedhaApplication;
import com.mangedha.knit.R;
import com.mangedha.knit.adapters.DetailImageView;
import com.mangedha.knit.backround_service.NotificationService;
import com.mangedha.knit.helpers.AlertHelper;
import com.mangedha.knit.helpers.AlertMoblieRequired;
import com.mangedha.knit.helpers.BadgeUtils;
import com.mangedha.knit.helpers.PayumoneyHelper;
import com.mangedha.knit.helpers.UserHelper;
import com.mangedha.knit.http.models.DownloadZipModel;
import com.mangedha.knit.http.models.FavoriteCallModel;
import com.mangedha.knit.http.models.NotificationModel;
import com.mangedha.knit.http.models.ProductPaymentModel;
import com.mangedha.knit.http.models.ProductsModel;
import com.payumoney.core.entity.TransactionResponse;
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager;

public class DetailActivity extends MangedhaKnitActivity implements View.OnClickListener {

    Context context = DetailActivity.this;
    ImageView detail_page_back_button, detail_favorite;
    TextView product_name, detail_category, detail_price, detail_description, member_ship_buy_details, buy_now_button, download_files;
    ProductsModel.Product product;
    ViewPager mImageViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        preInit();
    }

    void preInit(){
        product = MangedhaApplication.getProduct();
        setup();
        setupViewPager();
    }

    void setupViewPager() {

        mImageViewPager = (ViewPager) findViewById(R.id.detail_view_pager);
        if(product != null) {
            mImageViewPager.setAdapter(new DetailImageView(context, product));
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
        member_ship_buy_details = (TextView) findViewById(R.id.member_ship_buy_details);
        buy_now_button = (TextView) findViewById(R.id.buy_now_button);

        download_files = (TextView) findViewById(R.id.download_files);
        download_files.setOnClickListener(this);

        if(product != null){
            product_name.setText(product.getName());
            detail_category.setText(product.getCategory().getName());
            detail_price.setText("\u20B9" + String.valueOf(product.getPrice()));
            detail_description.setText(product.getDescription());
        }


        if(product != null && product.getFavoriteModel() != null){
            detail_favorite.setImageResource(R.mipmap.ic_star);
        }else{
            detail_favorite.setImageResource(R.mipmap.ic_star_disable);
        }

        if(product.getType() == ProductsModel.PRODUCT_TYPE_FREE){
            member_ship_buy_details.setText("This product is free.");
            member_ship_buy_details.setVisibility(View.VISIBLE);
        }
        else if(product.getType() == ProductsModel.PRODUCT_TYPE_BUY && product.getBuyProduct() == null){
            buy_now_button.setVisibility(View.VISIBLE);
            buy_now_button.setOnClickListener(this);
            detail_price.setVisibility(View.VISIBLE);
        }else if(product.getType() == ProductsModel.PRODUCT_TYPE_KNIT){
            member_ship_buy_details.setText("This product is under member ship plan.");
            member_ship_buy_details.setVisibility(View.VISIBLE);
        }

        if(product.getNotificationStatus() == null){
            decreaseCount(1);
            NotificationModel.unReadNotification(product.getId());
        }

        if(product.isProductVisible()){
            download_files.setVisibility(View.VISIBLE);
        }
    }

    void decreaseCount(int cnt){
        int count = NotificationService.getCount() - cnt;
        if(count > 0) BadgeUtils.setBadge(getApplicationContext(), count);
        else BadgeUtils.clearBadge(getApplicationContext());
        NotificationService.updateCount(cnt);
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.detail_page_back_button){
            super.onBackPressed();
        }

        if(view.getId() == R.id.detail_favorite){
            favorite();
        }

        if(view.getId() == R.id.buy_now_button){
            productBuyInit();
        }

        if(view.getId() == R.id.download_files){
            downloadFiles();
        }
    }

    private void productBuyInit() {
        if(!UserHelper.getAppUserMobile().equals("")) {
            PayumoneyHelper payumoneyHelper = PayumoneyHelper.getInstance(this);
            payumoneyHelper.setPhone(UserHelper.getAppUserMobile());
            payumoneyHelper.setEmail(UserHelper.getEmail());
            payumoneyHelper.setAmount(Double.parseDouble(String.valueOf(product.getPrice())));
            payumoneyHelper.setProduct_info(product.getName());
            payumoneyHelper.setPaymentType(PayumoneyHelper.PRODUCT_PAYMENT);
            payumoneyHelper.setTypeValue(String.valueOf(product.getId()));
            payumoneyHelper.setFirst_name("Mangedha");
            payumoneyHelper.initiate();
        }else{
            AlertMoblieRequired.show(this, new AlertMoblieRequired.AlertMobileInterface() {
                @Override
                public void onMobileSuccess(String mobile) {
                    productBuyInit();
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PayUmoneyFlowManager.REQUEST_CODE_PAYMENT && resultCode == RESULT_OK && data != null) {
            TransactionResponse transactionResponse = data.getParcelableExtra(PayUmoneyFlowManager.INTENT_EXTRA_TRANSACTION_RESPONSE);
            if (transactionResponse != null && transactionResponse.getPayuResponse() != null) {
                if(transactionResponse.getTransactionStatus().equals(TransactionResponse.TransactionStatus.SUCCESSFUL)){
                    //Success Transaction
                    mangedhaLoader.start();
                    ProductPaymentModel.productPaymentSuccess(transactionResponse, new ProductPaymentModel.ProductPaymentInterface() {
                        @Override
                        public void onSuccess(ProductPaymentModel productPaymentModel) {
                            mangedhaLoader.stop();
                            MangedhaApplication.getProduct().setBuyProduct(productPaymentModel.getProduct());
                            AlertHelper.success("Product buy successfully.", DetailActivity.this);
                            buy_now_button.setVisibility(View.GONE);
                            preInit();
                        }

                        @Override
                        public void onFail(String error) {
                            mangedhaLoader.stop();
                            AlertHelper.error(error, DetailActivity.this);
                        }
                    });
                } else{
                    //Failure Transaction
                    Log.d("PAY_SUCCESS", transactionResponse.toString());
                }
            }
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

    void downloadFiles(){
        if(haveStoragePermission()) {
            mangedhaLoader.start();
            DownloadZipModel.downloadFile(product.getId(), new DownloadZipModel.DownloadZipInterface() {
                @Override
                public void onSuccess(DownloadZipModel downloadZipModel) {
                    mangedhaLoader.stop();
                    downloadFile(downloadZipModel.getPath(), product.getName() + ".zip");
                }

                @Override
                public void onError(String error) {
                    mangedhaLoader.stop();
                }
            });
        }
    }

    void downloadFile(String url, String name){
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setTitle(name);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        }
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, name);

        // get download service and enqueue file
        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }

    public  boolean haveStoragePermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.e("Permission error","You have permission");
                return true;
            } else {

                Log.e("Permission error","You have asked for permission");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //you dont need to worry about these stuff below api level 23
            Log.e("Permission error","You already have the permission");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            downloadFiles();
        }
    }
}

package com.mangedha.knit.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.mangedha.knit.R;
import com.mangedha.knit.adapters.Adapter_Notification;
import com.mangedha.knit.helpers.NetworkHelper;
import com.mangedha.knit.http.models.ProductsModel;

import java.util.Map;

public class NotificationActivity extends AppCompatActivity {


    public RelativeLayout loader, no_records_found, no_internet;
    Context context = NotificationActivity.this;
    public boolean loadingNextPage = false;
    private int visibleItemCount;
    int page_no = 1;
    private int totalItemCount;
    private int pastVisiblesItems;
    Adapter_Notification adapter_notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("");
        loader = (RelativeLayout) findViewById(R.id.loader);
        no_records_found = (RelativeLayout) findViewById(R.id.no_records_found);
        no_internet = (RelativeLayout) findViewById(R.id.no_internet);
        recyclerView();
    }



    void recyclerView() {
        if(!NetworkHelper.state()){
            noInernetShow();
        }else {
            showLoading();
            final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
            recyclerView.setHasFixedSize(true);
            final LinearLayoutManager llm = new GridLayoutManager(context, 1);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(llm);
            recyclerView.setNestedScrollingEnabled(false);

            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                    if (dy > 0) //check for scroll down
                    {
                        visibleItemCount = llm.getChildCount();
                        totalItemCount = llm.getItemCount();
                        pastVisiblesItems = llm.findFirstVisibleItemPosition();

                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            if (!loadingNextPage) {
                                nextPage();
                                loadingNextPage = true;
                                loader.setVisibility(View.VISIBLE);
                            }
                        }

                    }
                }
            });

            ProductsModel.getList(new ProductsModel.ProductInterface() {
                @Override
                public void onSuccess(ProductsModel productsModel) {
                    hideLoading();
                    if (productsModel.getProductList() != null) {
                        if (productsModel.getPagination().isLoad_more()) {
                            loadingNextPage = false;
                            page_no++;
                        } else {
                            loadingNextPage = true;
                        }
                        adapter_notification = new Adapter_Notification(NotificationActivity.this, productsModel);
                        recyclerView.setAdapter(adapter_notification);
                    } else {
                        showNoRecords();
                    }
                }

                @Override
                public void onFail(String error) {

                }
            }, makeCall(), 1);
        }
    }

    private void nextPage() {

        ProductsModel.getList(new ProductsModel.ProductInterface() {
            @Override
            public void onSuccess(ProductsModel productsModel) {
                hideLoading();
                if (productsModel.getProductList() != null){
                    if (productsModel.getPagination().isLoad_more()) {
                        loadingNextPage = false;
                        page_no++;
                    } else {
                        loadingNextPage = true;
                    }
                    adapter_notification.getProductsModel().getProductList().addAll(productsModel.getProductList());
                    adapter_notification .notifyDataSetChanged();
                }
            }

            @Override
            public void onFail(String error) {

            }
        }, makeCall(), page_no);

    }

    private Map<String, String> makeCall() {
        Map<String, String> stringStringMap = new ArrayMap<>();
        stringStringMap.put("ProductsSearch[notification]", "1");
        return  stringStringMap;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public void noInernetShow(){
        no_internet.setVisibility(View.VISIBLE);
    }

    public void noInternetHide(){
        no_internet.setVisibility(View.INVISIBLE);
    }

    public void showNoRecords(){
        no_records_found.setVisibility(View.VISIBLE);
    }

    public void hideNoRecords(){
        no_records_found.setVisibility(View.GONE);
    }

    public void showLoading(){

        loader.setVisibility(View.VISIBLE);
    }

    public void hideLoading(){

        loader.setVisibility(View.GONE);
    }


}

package com.mangedha.knit.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mangedha.knit.MangedhaApplication;
import com.mangedha.knit.R;
import com.mangedha.knit.adapters.Adapter_Filters;
import com.mangedha.knit.adapters.Adapter_MyProducts;
import com.mangedha.knit.helpers.SmoothCheckBox;
import com.mangedha.knit.helpers.UserHelper;
import com.mangedha.knit.navigation.CustomNavigationView;

public class ProductsActivity extends MangedhaKnitActivity{

    Toolbar toolbar;
    ProductsActivity navigationActivity;
    DrawerLayout drawerLayout;
    CustomNavigationView navView;
    Context context = ProductsActivity.this;
    TextView toolbartitle;
    Adapter_MyProducts adapter_myProducts;
    public RelativeLayout loader, no_records_found, no_internet;
    EditText product_search_edit_text;
    ImageView product_search_button;
    NavigationView navigationView;

    private int totalItemCount;
    private int pastVisiblesItems;
    private boolean loadingNextPage = false;
    private int visibleItemCount;
    int page_no = 1;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        loader = (RelativeLayout) findViewById(R.id.loader);
        no_records_found = (RelativeLayout) findViewById(R.id.no_records_found);
        no_internet = (RelativeLayout) findViewById(R.id.no_internet);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        toolbartitle = (TextView) findViewById(R.id.toolbartitle);
        toolbartitle.setText("Products");
        init();
    }


    void init() {
        drawerSetup();
        recyclerView();
        filterDrawer();
    }


    void filterDrawer() {
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        ImageView backarroww = (ImageView) navigationView.findViewById(R.id.backarroww);
        backarroww.setColorFilter(Color.parseColor("#ffffff"));
        backarroww.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.closeDrawer(GravityCompat.END);
            }
        });
        RecyclerView recyclerView = (RecyclerView) navigationView.findViewById(R.id.category_filter_recycler_view);
        recyclerView.setHasFixedSize(true);
        final LinearLayoutManager llm = new GridLayoutManager(context, 2);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        final Adapter_Filters adapter = new Adapter_Filters(this);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);

        navigationView.findViewById(R.id.category_select_all).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SmoothCheckBox smoothCheckBox = (SmoothCheckBox) view;
                if(smoothCheckBox.isChecked()){
                    smoothCheckBox.setChecked(false);
                    adapter.allUnchecked();
                }else{
                    smoothCheckBox.setChecked(true);
                    adapter.allChecked();
                }
            }
        });

        navigationView.findViewById(R.id.apply_category_filter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.closeDrawer(GravityCompat.END);
                page_no = 1;
                adapter_myProducts.searchCategory(adapter.getFilter_ids());
            }
        });

    }


    void recyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        setupProductAdapter();
    }

    void setupProductAdapter(){

        adapter_myProducts = new Adapter_MyProducts(this);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter_myProducts);

        final LinearLayoutManager llm = new GridLayoutManager(context, 2);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

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
                            adapter_myProducts.nextPage();
                            loadingNextPage = true;
                            loader.setVisibility(View.VISIBLE);
                        }
                    }

                }
            }
        });
    }


    void drawerSetup() {
        ImageView imageView = (ImageView) findViewById(R.id.navIcon);
        navView = (CustomNavigationView) findViewById(R.id.navView);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navView.setHeaderView(getHeader(), 0);
        navView.setScrollState(CustomNavigationView.MENU_ITEM_SCROLLABLE);
        navigationActivity = (ProductsActivity) ProductsActivity.this;
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigationActivity.drawerLayout.openDrawer(navigationActivity.navView, true);
            }
        });

    }

    private View getHeader() {
        View view = getLayoutInflater().inflate(R.layout.drawer, null);
        LinearLayout drawerview = (LinearLayout) view.findViewById(R.id.drawerview);
        LinearLayout productslayer = (LinearLayout) view.findViewById(R.id.productslayer);
        LinearLayout membershiplayer = (LinearLayout) view.findViewById(R.id.membershiplayer);
        LinearLayout myprofilelayer = (LinearLayout) view.findViewById(R.id.myprofilelayer);
        LinearLayout favouriteproductlayer = (LinearLayout) view.findViewById(R.id.favouriteproductlayer);
        LinearLayout myproductlayer = (LinearLayout) view.findViewById(R.id.myproductlayer);
        LinearLayout changepass = (LinearLayout) view.findViewById(R.id.changepass);
        LinearLayout aboutuslayer = (LinearLayout) view.findViewById(R.id.aboutuslayer);
        LinearLayout contctuslayer = (LinearLayout) view.findViewById(R.id.contctuslayer);
        LinearLayout logoutlayer = (LinearLayout) view.findViewById(R.id.logoutlayer);

        productslayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigationActivity.drawerLayout.closeDrawer(navigationActivity.navView, true);
                page_no = 1;
                adapter_myProducts.allList();
                toolbartitle.setText("Products");
            }
        });
        membershiplayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MembershipActivity.class);
                startActivity(intent);
            }
        });
        myprofilelayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MyProfile.class);
                startActivity(intent);
            }
        });
        favouriteproductlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            toolbartitle.setText("Favorite Products");
            page_no = 1;
            adapter_myProducts.favoriteList();
            navigationActivity.drawerLayout.closeDrawer(navigationActivity.navView, true);
            }
        });
        myproductlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            navigationActivity.drawerLayout.closeDrawer(navigationActivity.navView, true);
                toolbartitle.setText("My Product");
            }
        });
        changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChangePasswordActivity.class);
                startActivity(intent);
            }
        });
        aboutuslayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AboutusActivity.class);
                startActivity(intent);

            }
        });
        contctuslayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ContactActivity.class);
                startActivity(intent);
            }
        });
        logoutlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserHelper.logout();
                Intent intent = new Intent(context, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        product_search_button = (ImageView) findViewById(R.id.product_search_button);
        product_search_edit_text = (EditText) findViewById(R.id.product_search_edit_text);
        product_search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                page_no = 1;
                adapter_myProducts.searchProduct(product_search_edit_text.getText().toString());
            }
        });

        return view;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_products, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_filter) {
//            Intent intent = new Intent(context, FiltersActivity.class);
//            startActivity(intent);

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.openDrawer(GravityCompat.END);

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (navigationActivity.drawerLayout.isDrawerOpen(navigationActivity.navView)) {
            navigationActivity.drawerLayout.closeDrawer(navigationActivity.navView, true);
        } else if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
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

    public void unCheckSelectAll(){
        SmoothCheckBox smoothCheckBox = (SmoothCheckBox) navigationView.findViewById(R.id.category_select_all);
        smoothCheckBox.setChecked(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(MangedhaApplication.getProduct_index() >= 0){
            if(adapter_myProducts != null && adapter_myProducts.getProductsModel() != null) {
                if(MangedhaApplication.getProduct() != null) {
                    adapter_myProducts.getProductsModel().getProductList().set(MangedhaApplication.getProduct_index(), MangedhaApplication.getProduct());
                    adapter_myProducts.notifyDataSetChanged();
                }
            }
        }
    }

    public void afterNextPage(int records_count) {
        hideLoading();
        if(records_count > 0) {
            loadingNextPage = false;
            page_no++;
        }
    }

    public int getPage_no() {
        return page_no;
    }
}

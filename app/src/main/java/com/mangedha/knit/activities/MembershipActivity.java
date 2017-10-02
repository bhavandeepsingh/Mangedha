package com.mangedha.knit.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.mangedha.knit.R;
import com.mangedha.knit.adapters.MemberShipAdapter;
import com.mangedha.knit.http.models.MemmberShipModel;

public class MembershipActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView toolbartitle = (TextView) findViewById(R.id.toolbartitle);
        setTitle("");
        toolbartitle.setText("Membership Plans");

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.member_ship_recycleer_view);
        recyclerView.setHasFixedSize(true);
        final LinearLayoutManager llm = new GridLayoutManager(this, 2);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setNestedScrollingEnabled(false);

        MemmberShipModel.getMembershipList(new MemmberShipModel.MemberShipInterface() {
            @Override
            public void onSuccess(MemmberShipModel memmberShipModel) {
                MemberShipAdapter memberShipAdapter = new MemberShipAdapter(memmberShipModel);
                recyclerView.setAdapter(memberShipAdapter);
            }

            @Override
            public void onFail(String error) {

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

package com.mangedha.knit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.mangedha.knit.R;
import com.mangedha.knit.adapters.MemberShipAdapter;
import com.mangedha.knit.helpers.AlertHelper;
import com.mangedha.knit.http.models.MemmberShipModel;
import com.mangedha.knit.http.models.PaymentHashModel;
import com.mangedha.knit.http.models.SettingModel;
import com.payumoney.core.entity.TransactionResponse;
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager;

public class MembershipActivity extends MangedhaKnitActivity {

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
        final LinearLayoutManager llm = new GridLayoutManager(this, 1);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setNestedScrollingEnabled(false);

        mangedhaLoader.start();
        MemmberShipModel.getMembershipList(new MemmberShipModel.MemberShipInterface() {
            @Override
            public void onSuccess(MemmberShipModel memmberShipModel) {
                mangedhaLoader.stop();
                if(memmberShipModel.getMembership() != null){

                }else {
                    MemberShipAdapter memberShipAdapter = new MemberShipAdapter(memmberShipModel, MembershipActivity.this);
                    recyclerView.setAdapter(memberShipAdapter);
                }

            }

            @Override
            public void onFail(String error) {
                mangedhaLoader.stop();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("MainActivity", "request code " + requestCode + " resultcode " + resultCode);
        if (requestCode == PayUmoneyFlowManager.REQUEST_CODE_PAYMENT && resultCode == RESULT_OK && data != null) {
            TransactionResponse transactionResponse = data.getParcelableExtra(PayUmoneyFlowManager.INTENT_EXTRA_TRANSACTION_RESPONSE);
            if (transactionResponse != null && transactionResponse.getPayuResponse() != null) {
                if(transactionResponse.getTransactionStatus().equals(TransactionResponse.TransactionStatus.SUCCESSFUL)){
                    //Success Transaction
                    mangedhaLoader.start();
                    PaymentHashModel.processPayment(transactionResponse, new PaymentHashModel.ProcessPayment() {
                        @Override
                        public void onSuccess(SettingModel settingModel) {
                            mangedhaLoader.stop();
                            AlertHelper.success("Membership buy successfully.", MembershipActivity.this);
                        }

                        @Override
                        public void onFail(String error) {
                            mangedhaLoader.stop();
                            AlertHelper.error(error, MembershipActivity.this);
                        }
                    });
                } else{
                    //Failure Transaction
                    Log.d("PAY_SUCCESS", transactionResponse.toString());
                }
            }
        }
    }
}

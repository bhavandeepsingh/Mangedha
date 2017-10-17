package com.mangedha.knit.helpers;

import android.support.v4.util.ArrayMap;

import com.mangedha.knit.R;
import com.mangedha.knit.activities.MangedhaKnitActivity;
import com.mangedha.knit.http.models.PaymentHashModel;
import com.payumoney.core.PayUmoneySdkInitializer;
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager;

import java.util.Map;

/**
 * Created by bhavan on 10/8/17.
 */

public class PayumoneyHelper {

    public String merchant_id = "4934580";
    public String key = "rjQUPktU";
    public String sUrl = "http://uniquecoders.in/mangedha/api/web/v1/payumoney/success-url";
    public String fUrl = "http://uniquecoders.in/mangedha/api/web/v1/payumoney/fail-url";
    public String txn_id, phone, email, product_info, first_name, amount, udf1, udf2, udf3, udf4, udf5, udf6, udf7, udf8, udf9, udf10;

    public static String MEMBERSHIP_PAYMENT = "MEMBERSHIP_PAYMENT";
    public static String PRODUCT_PAYMENT = "PRODUCT_PAYMENT";

    PayUmoneySdkInitializer.PaymentParam paymentParam;
    MangedhaKnitActivity mangedhaKnitActivity;
    MangedhaLoader mangedhaLoader;
    PayUmoneySdkInitializer.PaymentParam.Builder builder;

    public PayumoneyHelper(MangedhaKnitActivity mangedhaKnitActivity) {
        txn_id = String.valueOf(System.currentTimeMillis());
        builder = new PayUmoneySdkInitializer.PaymentParam.Builder();
        builder.setMerchantId(merchant_id)
        .setKey(key)
        .setTxnId(txn_id)
        .setIsDebug(true)
        .setsUrl(sUrl)
        .setfUrl(fUrl);
        this.mangedhaKnitActivity = mangedhaKnitActivity;
        mangedhaLoader = MangedhaLoader.init(mangedhaKnitActivity);
    }

    public static PayumoneyHelper getInstance(MangedhaKnitActivity mangedhaKnitActivity){
        PayumoneyHelper payumoneyHelper = new PayumoneyHelper(mangedhaKnitActivity);
        return  payumoneyHelper;
    }

    public void setPaymentType(String type){
        udf1 = type;
        builder.setUdf1(type);
    }

    public void setTypeValue(String value){
        udf2 = value;
        builder.setUdf2(value);
    }

    public void setPhone(String phone) {
        this.phone = phone;
        builder.setPhone(phone);
    }

    public void setEmail(String email) {
        this.email = email;
        builder.setEmail(email);
    }

    public void setProduct_info(String product_info) {
        this.product_info = product_info;
        builder.setProductName(product_info);
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
        builder.setFirstName(first_name);
    }

    public void setAmount(Double amount) {
        this.amount = String.valueOf(amount);
        builder.setAmount(amount);
    }

    public void initiate(){
        mangedhaLoader.start();
        PaymentHashModel.getHash(makeCall(), new PaymentHashModel.PaymentHashInterface() {
            @Override
            public void onSuccess(PaymentHashModel paymentHashModel) {
                mangedhaLoader.stop();
                paymentParam = builder.build();
                paymentParam.setMerchantHash(paymentHashModel.getHash());
                PayUmoneyFlowManager.startPayUMoneyFlow(
                        paymentParam,
                        mangedhaKnitActivity,
                        R.style.payumoney,
                        false
                );
            }

            @Override
            public void onFail(String error) {
                mangedhaLoader.stop();
                AlertHelper.error(error, mangedhaKnitActivity);
            }
        });
    }

    Map<String, String> makeCall(){
        Map<String, String> stringStringMap = new ArrayMap<>();
        stringStringMap.put("PayumoneyHash[key]", key);
        stringStringMap.put("PayumoneyHash[txn_id]", txn_id);
        stringStringMap.put("PayumoneyHash[amount]", amount);
        stringStringMap.put("PayumoneyHash[txn_id]", txn_id);
        stringStringMap.put("PayumoneyHash[product_name]", product_info);
        stringStringMap.put("PayumoneyHash[first_name]", first_name);
        stringStringMap.put("PayumoneyHash[email]", email);
        stringStringMap.put("PayumoneyHash[udf1]", udf1);
        stringStringMap.put("PayumoneyHash[udf2]", udf2);
        return stringStringMap;
    }

}

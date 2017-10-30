package com.mangedha.knit;

import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.mangedha.knit.activities.MangedhaKnitActivity;
import com.mangedha.knit.helpers.UILApplication;
import com.mangedha.knit.http.models.PaymentsModel;
import com.mangedha.knit.http.models.ProductsModel;
import com.mangedha.knit.http.models.ProfileModel;
import com.mangedha.knit.http.models.SettingModel;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

/**
 * Created by gurpreetsingh on 24/04/17.
 */

public class MangedhaApplication extends UILApplication {

    static MangedhaApplication mangedhaApplication ;

    static List<ProfileModel> profileModels;
    static PaymentsModel paymentsModel;
    static ProductsModel.Product product;
    static int product_index;

    static boolean paymentRecreateStatus = false;

    static SettingModel.Membership membership;

    @Override
    public void onCreate() {
        super.onCreate();
        adjustFontScale(getApplicationContext(),getApplicationContext().getResources().getConfiguration());
        mangedhaApplication = this;

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(MangedhaApplication.getMangedhaApplication()).build();
        ImageLoader.getInstance().init(config);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // In some cases modifying newConfig leads to unexpected behavior,
        // so it's better to edit new instance.
        Configuration configuration = new Configuration(newConfig);
        adjustFontScale(getApplicationContext(), configuration);
    }

    public static void adjustFontScale(Context context, Configuration configuration) {
        if (configuration.fontScale != 1) {
            configuration.fontScale = 1;
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            wm.getDefaultDisplay().getMetrics(metrics);
            metrics.scaledDensity = configuration.fontScale * metrics.density;
            context.getResources().updateConfiguration(configuration, metrics);
        }
    }

    public static MangedhaApplication getMangedhaApplication () {
        return mangedhaApplication ;
    }

    public void setMangedhaApplication (MangedhaApplication mangedhaApplication ) {
        this.mangedhaApplication  = mangedhaApplication ;
    }

    public static Context getMangedhaApplicationContext(){ return getMangedhaApplication().getApplicationContext(); }

    public static MangedhaKnitActivity getMangedhaActivity(Context context){
        return (MangedhaKnitActivity) context;
    }

    public static List<ProfileModel> getProfileModels() {
        return profileModels;
    }

    public static void setProfileModels(List<ProfileModel> profileModels) {
        MangedhaApplication.profileModels = profileModels;
    }


    public static PaymentsModel getPaymentsModel() {
        return paymentsModel;
    }

    public static void setPaymentsModel(PaymentsModel paymentsModel) {
        MangedhaApplication.paymentsModel = paymentsModel;
    }

    public static boolean isPaymentRecreateStatus() {
        return paymentRecreateStatus;
    }

    public static void setPaymentRecreateStatus(boolean paymentRecreateSattus) {
        MangedhaApplication.paymentRecreateStatus = paymentRecreateSattus;
    }

    public static ProductsModel.Product getProduct() {
        return product;
    }

    public static void setProduct(ProductsModel.Product product) {
        MangedhaApplication.product = product;
    }

    public static int getProduct_index() {
        return product_index;
    }

    public static void setProduct_index(int product_index) {
        MangedhaApplication.product_index = product_index;
    }

    public static SettingModel.Membership getMembership() {
        return membership;
    }

    public static void setMembership(SettingModel.Membership membership) {
        MangedhaApplication.membership = membership;
    }

}


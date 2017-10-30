package com.mangedha.knit.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mangedha.knit.R;
import com.mangedha.knit.activities.ImageGalleryActivity;
import com.mangedha.knit.helpers.ImageHelper;
import com.mangedha.knit.http.models.ProductFiles;
import com.mangedha.knit.http.models.ProductsModel;

import java.util.List;

/**
 * Created by bhavan on 2/10/17.
 */

public class DetailImageView extends PagerAdapter {


    Context context;
    List<ProductFiles> productFiles;
    ProductsModel.Product product;
    private LayoutInflater inflater;


    public DetailImageView(Context context, ProductsModel.Product product) {
        this.context = context;
        this.product = product;
        this.productFiles = product.getProductFiles();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if (productFiles != null)
            return productFiles.size();
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = inflater.inflate(R.layout.pager_item, container, false);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.img_pager_item);
        if(product.isProductVisible()) {
            ImageHelper.loadImage(productFiles.get(position).getImage_path(), imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(DetailImageView.this.context, ImageGalleryActivity.class);
                    intent.putExtra("slide_index", position);
                    DetailImageView.this.context.startActivity(intent);
                }
            });
        }else{
            imageView.setImageResource(R.mipmap.lock_image);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        }
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}

package com.mangedha.knit.adapters;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mangedha.knit.R;
import com.mangedha.knit.helpers.ImageHelper;
import com.mangedha.knit.helpers.ZoomableImageView;
import com.mangedha.knit.http.models.ProductFiles;

import java.util.List;


/**
 * Created by bhavan on 2/10/17.
 */

public class ImageGalleryAdaptor extends PagerAdapter {

    List<ProductFiles> productFilesList;
    Context context;

    private LayoutInflater inflater;


    public ImageGalleryAdaptor(Context context, List<ProductFiles> productFiles) {
        this.context = context;
        this.productFilesList= productFiles;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if (productFilesList!= null)
            return productFilesList.size();
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = inflater.inflate(R.layout.image_gallery_adapter, container, false);
        ZoomableImageView touch = (ZoomableImageView) itemView.findViewById(R.id.galleryimage);
        ImageHelper.loadImage(productFilesList.get(position).getImage_path(), (ImageView) touch);
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }

}
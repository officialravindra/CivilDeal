/*
package com.civildeal.civildeal.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;


import com.civildeal.civildeal.Activity.BidderImages;
import com.civildeal.civildeal.R;
import com.civildeal.civildeal.api.ModelResponse.BidderBannerImagesResponse;
import com.civildeal.civildeal.api.ModelResponse.ProjectPostBannerImagesResponse;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.List;

public class BidderBannerImageViewPager extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    List<BidderBannerImagesResponse> bannerImages;
    ArrayList<String> images = new ArrayList<>();



    public BidderBannerImageViewPager(Context context, List<BidderBannerImagesResponse> bannerImages,ArrayList<String> images) {
        this.context = context;
        this.bannerImages = bannerImages;
        this.images = images;

    }


    @Override
    public int getCount() {
        return images.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
       */
/* layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.banner_list, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.myImageView);
*//*




        View saini= LayoutInflater.from(container.getContext()).inflate(R.layout.banner_list,container,false);
        ImageView imageView=saini.findViewById(R.id.myImageView);
        Picasso
                .get()
                .load(bannerImages.get(position).getImagePath()+ "/" +images.get(position))
                .placeholder(R.drawable.icon_image)
                .into(imageView);
        Log.d("tag","img "+bannerImages.get(position).getImagePath()+ "/" +images.get(position));

        container.addView(saini);
        return saini;

       // ViewPager viewPager = (ViewPager)container;
        */
/*container.addView(view);
        return view;*//*






    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
    */
/*    ViewPager viewPager = (ViewPager)container;
        View view  = (View)object;
        viewPager.removeView(viewPager);
*//*

        container.removeView((ViewGroup)object);

    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view ==object;



    }
}
*/

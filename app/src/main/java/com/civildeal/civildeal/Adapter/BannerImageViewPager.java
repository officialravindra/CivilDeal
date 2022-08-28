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


import com.civildeal.civildeal.R;
import com.civildeal.civildeal.api.ModelResponse.ProjectPostBannerImagesResponse;
import com.squareup.picasso.Picasso;


import java.util.List;

public class BannerImageViewPager extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    List<ProjectPostBannerImagesResponse> bannerImages;

    public BannerImageViewPager(Context context, List<ProjectPostBannerImagesResponse> bannerImages) {
        this.context = context;
        this.bannerImages = bannerImages;
    }

    @Override
    public int getCount() {
        return bannerImages.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.banner_list, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.myImageView);

        Picasso
                .get()
                .load(bannerImages.get(position).getImagePath()+ "/" +bannerImages.get(position).getImages().get(0))
                .placeholder(R.drawable.icon_image)
                .error(R.drawable.icon_image)
                .into(imageView);
        Log.d("tag","img "+bannerImages.get(position).getImagePath()+ "/"+bannerImages.get(position).getImages().get(0).toString());



        ViewPager viewPager = (ViewPager)container;
        viewPager.addView(view);
        return view;


    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager viewPager = (ViewPager)container;
        View view  = (View)object;
        viewPager.removeView(view);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view ==object;


    }
}

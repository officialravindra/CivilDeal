package com.civildeal.civildeal.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.civildeal.civildeal.Adapter.BannerImageViewPager;
/*import com.civildeal.civildeal.Adapter.BidderBannerImageViewPager;*/
import com.civildeal.civildeal.R;
import com.civildeal.civildeal.api.Api_Client;
import com.civildeal.civildeal.api.ModelResponse.BidderBannerImagesResponse;
import com.civildeal.civildeal.api.ModelResponse.BidderBannerImagesResultResponse;
import com.civildeal.civildeal.api.ModelResponse.ProjectPostBannerImagesResponse;
import com.civildeal.civildeal.api.ModelResponse.ProjectPostBannerImagesResultResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BidderImages extends AppCompatActivity {

    ViewPager viewPager;
    String post_id,vendor_id,path;
    ArrayList<String> images = new ArrayList<>();
    ArrayList<BidderBannerImagesResponse> bannerImages = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bidder_images);
        viewPager = findViewById(R.id.banner_image);

        Intent intent = getIntent();
        post_id = intent.getStringExtra("post_id");
        vendor_id = intent.getStringExtra("vendor_id");

        getBannerImages();
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(),2000,6000);


    }

    private void getBannerImages() {

        Log.d("tag","post_id "+post_id);
        Log.d("tag","vendor_id "+vendor_id);

        Call<BidderBannerImagesResultResponse> call = Api_Client
                .getInstance()
                .bidderBannerImages(post_id,vendor_id);
        call.enqueue(new Callback<BidderBannerImagesResultResponse>() {
            @Override
            public void onResponse(Call<BidderBannerImagesResultResponse> call, Response<BidderBannerImagesResultResponse> response) {

                if (response.body().getCode()==200){
                    try {
                        BidderBannerImagesResultResponse bidderBannerImagesResultResponse = response.body();

                       // List<BidderBannerImagesResponse> bannerImages = Collections.singletonList(bidderBannerImagesResultResponse.getData());

                        images.clear();
                        for (int i =0;i<bidderBannerImagesResultResponse.getData().getImages().size();i++){



                            images.add(bidderBannerImagesResultResponse.getData().getImages().get(i));
                            Log.d("tag","image "+images);

                            path = bidderBannerImagesResultResponse.getData().getImagePath().toString();

                            if(i==bidderBannerImagesResultResponse.getData().getImages().size()-1)
                            {
                                Log.d("tag","image "+images.size());

                                adapter bidderBannerImageViewPager = new adapter(BidderImages.this,bannerImages,images);
                                viewPager.setAdapter(bidderBannerImageViewPager);

                            }
                        }

                        Log.d("tag","SucessImg "+response.body().getMessage());
                    }catch (Exception e){

                        Log.d("tag",""+e);
                    }




                }else if (response.body().getMessage().equals("Sorry! Bid image not available")){


                    Toast.makeText(BidderImages.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }else {
                    Log.d("tag","failImg "+response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<BidderBannerImagesResultResponse> call, Throwable t) {
                Log.d("tag","failuer "+t.getMessage());

            }
        });
    }





    class adapter extends PagerAdapter {


        private Context context;
        List<BidderBannerImagesResponse> bannerImages;
        ArrayList<String> images = new ArrayList<>();


        public adapter(BidderImages bidderImages, List<BidderBannerImagesResponse> bannerImages, ArrayList<String> images) {

            this.bannerImages = bannerImages;
            this.images = images;
            this.context = bidderImages;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return images.size();

        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view==object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view= LayoutInflater.from(container.getContext()).inflate(R.layout.banner_list,container,false);
            ImageView imageView=view.findViewById(R.id.myImageView);



            Picasso
                    .get()
                    .load(path+ "/" +images.get(position))
                    .placeholder(R.drawable.icon_image)
                    .into(imageView);
            Log.d("tag","img "+path + "/" +images.get(position));
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((ViewGroup)object);
        }
    }

    public class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            BidderImages.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (viewPager.getCurrentItem() == 0){
                        viewPager.setCurrentItem(1);
                    }else if (viewPager.getCurrentItem() == 1){
                        viewPager.setCurrentItem(2);
                    }else {
                        viewPager.setCurrentItem(0);
                    }
                }
            });
        }

    }
}
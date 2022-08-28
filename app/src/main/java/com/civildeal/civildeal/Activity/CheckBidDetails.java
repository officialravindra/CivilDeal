package com.civildeal.civildeal.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.civildeal.civildeal.Adapter.BannerImageViewPager;
import com.civildeal.civildeal.Prefrences.SharedPrefManager;
import com.civildeal.civildeal.R;
import com.civildeal.civildeal.api.Api_Client;
import com.civildeal.civildeal.api.ModelResponse.BidDetailsResultResponse;
import com.civildeal.civildeal.api.ModelResponse.BidderBannerImagesResponse;
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

public class CheckBidDetails extends AppCompatActivity {
    Toolbar toolbar;
    ImageView image;
    TextView title,sizetype,size,budget,location,date,desc;
    String user_id,post_id;
    SharedPrefManager sharedPrefManager;
    ViewPager viewPager;
    private Handler sliderHandler = new Handler();
    ArrayList<ProjectPostBannerImagesResponse> bannerImages = new ArrayList<>();
    ArrayList<String> images = new ArrayList<>();
    String path;
    ProjectPostBannerImagesResultResponse projectPostBannerImagesResultResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_bid_details);

        toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        image= findViewById(R.id.image);
        title= findViewById(R.id.title);
        sizetype= findViewById(R.id.sizetype);
        size= findViewById(R.id.size);
        budget= findViewById(R.id.budget);
        location= findViewById(R.id.location);
        date= findViewById(R.id.date);
        desc =findViewById(R.id.Desc);
        viewPager=findViewById(R.id.ViewPager);

        sharedPrefManager = new SharedPrefManager(getApplicationContext());
        user_id = sharedPrefManager.getUserId();

        Intent intent = getIntent();
        post_id = intent.getStringExtra("post_id");
        Log.d("tag","post_id "+post_id);


        getBannerImages();

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(),2000,5000);
        getDetails();
    }

    private void getBannerImages() {

        Log.d("tag","post1 "+post_id);
        ProgressDialog progressDialog = new ProgressDialog(CheckBidDetails.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait..");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        Call<ProjectPostBannerImagesResultResponse> call = Api_Client
                .getInstance()
                .bannerImages(post_id);
        call.enqueue(new Callback<ProjectPostBannerImagesResultResponse>() {
            @Override
            public void onResponse(Call<ProjectPostBannerImagesResultResponse> call, Response<ProjectPostBannerImagesResultResponse> response) {

                progressDialog.dismiss();
                if (response.body().getCode()==200){

                    try {
                        projectPostBannerImagesResultResponse = response.body();
//                        List<ProjectPostBannerImagesResponse> bannerImages = Collections.singletonList(projectPostBannerImagesResultResponse.getData());
//                        BannerImageViewPager bannerImageViewPager = new BannerImageViewPager(CheckBidDetails.this,bannerImages);
//                        viewPager.setAdapter(bannerImageViewPager);
//                        Log.d("tag","SucessImg "+response.body().getMessage());
                        path = projectPostBannerImagesResultResponse.getData().getImagePath();

                        images.clear();
                        for (int i =0;i<projectPostBannerImagesResultResponse.getData().getImages().size();i++){



                            images.add(projectPostBannerImagesResultResponse.getData().getImages().get(i));
                            Log.d("tag","image "+images);

                            if(i==projectPostBannerImagesResultResponse.getData().getImages().size()-1)
                            {
                                Log.d("tag","image "+images.size());

                                CheckBidDetails.adapter bannerImageViewPager = new CheckBidDetails.adapter(CheckBidDetails.this,bannerImages,images);
                                viewPager.setAdapter(bannerImageViewPager);

                            }
                        }
                    }catch (Exception e){

                        Log.d("tag",""+e);
                    }



                }else {

                    Log.d("tag","failImg "+response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ProjectPostBannerImagesResultResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("tag","failuer "+t.getMessage());

            }
        });
    }

    private void getDetails() {

        Log.d("tag","user "+user_id);
        Log.d("tag","post "+post_id);
        Call<BidDetailsResultResponse> call = Api_Client
                .getInstance()
                .getBidDetails(user_id,post_id);
        call.enqueue(new Callback<BidDetailsResultResponse>() {
            @Override
            public void onResponse(Call<BidDetailsResultResponse> call, Response<BidDetailsResultResponse> response) {

                if (response.body().getCode().equals(200)){
                    try {
                        BidDetailsResultResponse bidDetailsResultResponse = response.body();

                        title.setText(bidDetailsResultResponse.getData().get(0).getTitle());
                        sizetype.setText(bidDetailsResultResponse.getData().get(0).getSelectSize());
                        size.setText(bidDetailsResultResponse.getData().get(0).getSize());
                        budget.setText(bidDetailsResultResponse.getData().get(0).getMinBujet()+ " - "+ bidDetailsResultResponse.getData().get(0).getMaxBujet());
                        if (bidDetailsResultResponse.getData().get(0).getLocationName()!=null){
                            location.setText(bidDetailsResultResponse.getData().get(0).getLocationName().toString());
                        }else {
                            location.setVisibility(View.GONE);
                        }

                        desc.setText(bidDetailsResultResponse.getData().get(0).getDescription());
                        date.setText(bidDetailsResultResponse.getData().get(0).getCreatedAt());
                        Log.d("tag","Sucess "+response.body().getMessage());
                    }catch (Exception e){
                        Log.d("tag","Exception "+response.body().getMessage());
                    }



                }else {
                    Log.d("tag","fail "+response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<BidDetailsResultResponse> call, Throwable t) {
                Log.d("tag","failuer "+t.getLocalizedMessage());

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    public class MyTimerTask extends TimerTask{

        @Override
        public void run() {
            CheckBidDetails.this.runOnUiThread(new Runnable() {
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

    class adapter extends PagerAdapter {


        private Context context;
        List<ProjectPostBannerImagesResponse> bannerImages;
        ArrayList<String> images = new ArrayList<>();


        public adapter(CheckBidDetails checkBidDetails, List<ProjectPostBannerImagesResponse> bannerImages, ArrayList<String> images) {

            this.bannerImages = bannerImages;
            this.images = images;
            this.context = checkBidDetails;
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
                    .error(R.drawable.icon_image)
                    .placeholder(R.drawable.icon_image)
                    .into(imageView);
            Log.d("tag","img "+path+ "/" +images.get(position));
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((ViewGroup)object);
        }
    }
}
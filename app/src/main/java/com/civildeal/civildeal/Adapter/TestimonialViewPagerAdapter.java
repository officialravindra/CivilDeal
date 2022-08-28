package com.civildeal.civildeal.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.civildeal.civildeal.Model.TestimonialData;
import com.civildeal.civildeal.R;

import java.util.List;

public class TestimonialViewPagerAdapter extends PagerAdapter {

    private List<TestimonialData> testimonialDataList;
    private LayoutInflater layoutInflater;
    private Context context;

    public TestimonialViewPagerAdapter(List<TestimonialData> testimonialDataList, LayoutInflater layoutInflater, Context context) {
        this.testimonialDataList = testimonialDataList;
        this.layoutInflater = layoutInflater;
        this.context = context;
    }

    public TestimonialViewPagerAdapter(List<TestimonialData> testimonialDataList, Context context) {
        this.testimonialDataList = testimonialDataList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return testimonialDataList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.testimonial_custom_layout, null);

        ImageView imageView;
        TextView title,name;

        imageView = view.findViewById(R.id.Image);
        title = view.findViewById(R.id.des);
        name = view.findViewById(R.id.name);

        imageView.setImageResource(testimonialDataList.get(position).getImage());
        title.setText(testimonialDataList.get(position).getQuote());
        name.setText(testimonialDataList.get(position).getName());

        container.addView(view,0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}

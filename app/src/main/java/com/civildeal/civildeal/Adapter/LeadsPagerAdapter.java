package com.civildeal.civildeal.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.civildeal.civildeal.Fragments.ProductLeadFragment;
import com.civildeal.civildeal.Fragments.ServiceLeadFragment;

public class LeadsPagerAdapter extends FragmentPagerAdapter {

    int mNoOfTabs;

    public LeadsPagerAdapter(@NonNull FragmentManager fm, int mNoOfTabs) {
        super(fm);
        this.mNoOfTabs = mNoOfTabs;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position)
        {

            case 0:
                ServiceLeadFragment serviceLeadFragment = new ServiceLeadFragment();
                return serviceLeadFragment;
            case 1:
                ProductLeadFragment productLeadFragment = new ProductLeadFragment();
                return productLeadFragment;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return mNoOfTabs;
    }
}

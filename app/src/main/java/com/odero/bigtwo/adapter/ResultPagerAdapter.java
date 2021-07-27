package com.odero.bigtwo.adapter;

import android.support.annotation.NonNull;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.odero.bigtwo.models.Result;
import com.odero.bigtwo.ui.ResultDetailFragment;

import java.util.ArrayList;
import java.util.List;

public class ResultPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Result> mResults;
    private String mSource;

    public ResultPagerAdapter(@NonNull FragmentManager fm,  ArrayList<Result> results, String source) {
        super(fm);
        mResults = results;
        mSource = source;
    }

    @Override
    public Fragment getItem(int position) {
        return ResultDetailFragment.newInstance(mResults, position, mSource);
    }

    @Override
    public int getCount() {
        return mResults.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mResults.get(position).getArtistName();
    }
}

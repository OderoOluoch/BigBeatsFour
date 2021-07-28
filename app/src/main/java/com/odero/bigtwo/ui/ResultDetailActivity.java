package com.odero.bigtwo.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.odero.bigtwo.Constants;
import com.odero.bigtwo.R;
import com.odero.bigtwo.adapter.ResultPagerAdapter;
import com.odero.bigtwo.models.Result;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResultDetailActivity extends AppCompatActivity {
    @BindView(R.id.viewPager) ViewPager mViewPager;
    private ResultPagerAdapter adapterViewPager;
    ArrayList<Result> mResult;
    private String mSource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_detail);
        ButterKnife.bind(this);
        mSource = getIntent().getStringExtra(Constants.KEY_SOURCE);
        mResult = Parcels.unwrap(getIntent().getParcelableExtra(Constants.EXTRA_KEY_RESULT));
        int startingPosition = getIntent().getIntExtra(Constants.EXTRA_KEY_POSITION, 0);
        adapterViewPager = new ResultPagerAdapter(getSupportFragmentManager(), mResult, mSource);
        mViewPager.setAdapter(adapterViewPager);
        mViewPager.setCurrentItem(startingPosition);
    }

}
package com.odero.bigtwo.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.odero.bigtwo.R;
import com.odero.bigtwo.models.Result;

import org.parceler.Parcels;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResultDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class ResultDetailFragment extends Fragment {
    @BindView(R.id.frtrackImageView) ImageView mImageLabel;
    @BindView(R.id.frtrackNameTextView) TextView mNameLabel;
    @BindView(R.id.frTrackDescriptionTextView) TextView mdescription;

    private Result mResult;

    public ResultDetailFragment() {
        // Required empty public constructor
    }



    public static ResultDetailFragment newInstance(Result result) {
        ResultDetailFragment restaurantDetailFragment = new ResultDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("result", Parcels.wrap(result));
        restaurantDetailFragment.setArguments(args);
        return restaurantDetailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        mResult = Parcels.unwrap(getArguments().getParcelable("result"));
    }









    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result_detail, container, false);
    }
}
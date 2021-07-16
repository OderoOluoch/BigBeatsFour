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
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_result_detail, container, false);
        ButterKnife.bind(this, view);
        Picasso.get().load(mResult.getArtworkUrl100()).into(mImageLabel);

        //.setText(mResult.getLongDescription());

        mdescription.setText("Artist: "+mResult.getArtistName()
                + "Warapper type :" + mResult.getWrapperType()
                + "Track count :" + mResult.getTrackCount()
                + "Collection name :" + mResult.getCollectionName()
                + "Url :" + mResult.getCollectionArtistViewUrl()
                + "Censored Name :" + mResult.getCollectionCensoredName()
                + "Explisitness :" + mResult.getCollectionExplicitness()
                + "Advisory rating :" + mResult.getContentAdvisoryRating()
                + "Time :" + mResult.getTrackTimeMillis()
                + "url :" + mResult.getPreviewUrl()
                + "RELEASE DATE :" + mResult.getReleaseDate()
                + "cOUNTY :" + mResult.getCountry()
                + "gENRE :" + mResult.getPrimaryGenreName()
                + "sHORT dESC :" + mResult.getShortDescription()
                + "Release Date :" + mResult.getReleaseDate()
                + "URL :" + mResult.getTrackViewUrl()
                + "Warapper type :" + mResult.getWrapperType()
        );
//        mCategoriesLabel.setText(android.text.TextUtils.join(", ", categories));
//        mRatingLabel.setText(Double.toString(mRestaurant.getRating()) + "/5");
//        mPhoneLabel.setText(mRestaurant.getPhone());
//        mAddressLabel.setText(mRestaurant.getLocation().toString());

        return view;
    }










}
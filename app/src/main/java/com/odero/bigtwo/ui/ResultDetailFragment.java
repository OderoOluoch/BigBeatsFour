package com.odero.bigtwo.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.odero.bigtwo.Constants;
import com.odero.bigtwo.R;
import com.odero.bigtwo.models.Result;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResultDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class ResultDetailFragment extends Fragment {
    private ArrayList<Result> mResults;
    private int mPosition;
    private String mSource;

    @BindView(R.id.frtrackImageView) ImageView mImageLabel;
    @BindView(R.id.frtrackNameTextView) TextView mNameLabel;
    @BindView(R.id.frCollectionName) TextView mCollectionName;
    @BindView(R.id.frCountry) TextView mCountry;
    @BindView(R.id.frGenre) TextView mGenre;
    @BindView(R.id.goToWeb) Button goToWeb;
    @BindView(R.id.saveToFireBase) Button saveToFireBase;
    @BindView(R.id.frReleaseDate) TextView mReleaseDate;
    @BindView(R.id.frTrackDescriptionTextView) TextView martistName;


    private Result mResult;

    public ResultDetailFragment() {
        // Required empty public constructor
    }


    public static ResultDetailFragment newInstance(ArrayList<Result> restaurants, Integer position, String source) {
        ResultDetailFragment restaurantDetailFragment = new ResultDetailFragment();
        Bundle args = new Bundle();

        args.putParcelable(Constants.EXTRA_KEY_RESULT, Parcels.wrap(restaurants));
        args.putInt(Constants.EXTRA_KEY_POSITION, position);
        args.putString(Constants.KEY_SOURCE, source);
        restaurantDetailFragment.setArguments(args);
        return restaurantDetailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mResults = Parcels.unwrap(getArguments().getParcelable(Constants.EXTRA_KEY_RESULT));
        mPosition = getArguments().getInt(Constants.EXTRA_KEY_POSITION);
        mSource = getArguments().getString(Constants.KEY_SOURCE);
        setHasOptionsMenu(true);
        mResult = mResults.get(mPosition);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_result_detail, container, false);
        ButterKnife.bind(this, view);
        Picasso.get().load(mResult.getArtworkUrl100()).into(mImageLabel);
        mNameLabel.setText(mResult.getTrackName());
        mCollectionName.setText("Collection name :" + mResult.getCollectionName());
        mCountry.setText("Country :" + mResult.getCountry());
        mGenre.setText("Genre :" + mResult.getPrimaryGenreName());
//        mPreview.setText("url :" + mResult.getPreviewUrl());
        mReleaseDate.setText("Release Date :" + mResult.getReleaseDate());
        martistName.setText("Artist Name " + mResult.getArtistName());

        goToWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToUrl(mResult.getCollectionViewUrl());
            }
        });


        if (mSource.equals(Constants.SOURCE_SAVED)) {
            saveToFireBase.setVisibility(View.GONE);
        } else {
            saveToFireBase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    String uid = user.getUid();
                    DatabaseReference resultRef = FirebaseDatabase
                            .getInstance()
                            .getReference(Constants.FIREBASE_CHILD_RESULTS)
                            .child(uid);
                    DatabaseReference pushRef = resultRef.push();
                    String pushId = pushRef.getKey();
                    mResult.setPushId(pushId);
                    pushRef.setValue(mResult);

                    Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
                }
            });
        }


        return view;
    }

    private void goToUrl(String s){
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }










}
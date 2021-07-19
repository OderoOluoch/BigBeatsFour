package com.odero.bigtwo.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.odero.bigtwo.Constants;
import com.odero.bigtwo.R;
import com.odero.bigtwo.adapter.FirebaseResultsViewHolder;
import com.odero.bigtwo.models.Result;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SavedResultListActivity extends AppCompatActivity {

    private DatabaseReference mRestaurantReference;
    private FirebaseRecyclerAdapter<Result, FirebaseResultsViewHolder> mFirebaseAdapter;

    @BindView(R.id.postRecyclerView) RecyclerView mRecyclerView;
    //@BindView(R.id.errorTextView) TextView mErrorTextView;
    @BindView(R.id.progressBar) ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        ButterKnife.bind(this);

        mRestaurantReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_RESULTS);
        setUpFirebaseAdapter();
        hideProgressBar();
        showRestaurants();
    }

    private void setUpFirebaseAdapter(){
        FirebaseRecyclerOptions<Result> options =
                new FirebaseRecyclerOptions.Builder<Result>()
                        .setQuery(mRestaurantReference, Result.class)
                        .build();

        mFirebaseAdapter = new FirebaseRecyclerAdapter<Result, FirebaseResultsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FirebaseResultsViewHolder firebaseRestaurantViewHolder, int position, @NonNull Result restaurant) {
                firebaseRestaurantViewHolder.bindRestaurant(restaurant);
            }

            @NonNull
            @Override
            public FirebaseResultsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
                return new FirebaseResultsViewHolder(view);
            }
        };

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mFirebaseAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mFirebaseAdapter!= null) {
            mFirebaseAdapter.stopListening();
        }
    }

    private void showRestaurants() {
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

}
package com.odero.bigtwo.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.odero.bigtwo.R;
import com.odero.bigtwo.adapter.TrackListApater;
import com.odero.bigtwo.models.Result;
import com.odero.bigtwo.models.TrackResponse;
import com.odero.bigtwo.network.TracksClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ProgressBar progressBar;
    LinearLayoutManager layoutManager;
    TrackListApater adapter;
    List<Result> resultList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        recyclerView = findViewById(R.id.postRecyclerView);
        progressBar = findViewById(R.id.progressBar);
        layoutManager = new LinearLayoutManager(this);
        Log.e("TAG", "We got here");
        recyclerView.setLayoutManager(layoutManager);
        Intent intent = getIntent();
        String TypedSearchKeyWOrd = intent.getStringExtra("TypedSearchKeyWOrd");

        fetchPosts(TypedSearchKeyWOrd);

    }
    private void fetchPosts(String term){
        progressBar.setVisibility(View.VISIBLE);
        TracksClient.getRetrofitClient().getTracks(term).enqueue(new Callback<TrackResponse>() {
            @Override
            public void onResponse(Call<TrackResponse> call, Response<TrackResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    resultList = response.body().getResults();
                    progressBar.setVisibility(View.GONE);
                    adapter = new TrackListApater(ResultsActivity.this,resultList);
                    recyclerView.setAdapter(adapter);

                    adapter.notifyDataSetChanged();
                }else{
                    showUnsuccessfulMessage();
                }
            }

            @Override
            public void onFailure(Call<TrackResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ResultsActivity.this,"Error "+ t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void showUnsuccessfulMessage() {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(ResultsActivity.this,"Something went wrong, please make another search, or try later",Toast.LENGTH_SHORT).show();

    }
}
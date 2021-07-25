package com.odero.bigtwo.ui;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


public class ResultListFragment extends Fragment {
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private String mRecentKeyWords;

    RecyclerView recyclerView;
    ProgressBar progressBar;
    LinearLayoutManager layoutManager;
    TrackListApater adapter;
    List<Result> resultList ;

    public ResultListFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mEditor = mSharedPreferences.edit();

        // Instructs fragment to include menu options:
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result_list, container, false);
    }


    private void fetchPosts(String term){
        progressBar.setVisibility(View.VISIBLE);
        TracksClient.getRetrofitClient().getTracks(term).enqueue(new Callback<TrackResponse>() {
            @Override
            public void onResponse(Call<TrackResponse> call, Response<TrackResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    resultList = response.body().getResults();
                    progressBar.setVisibility(View.GONE);
                    adapter = new TrackListApater(Resthis,resultList);
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
}
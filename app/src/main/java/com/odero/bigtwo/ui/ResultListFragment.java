package com.odero.bigtwo.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.odero.bigtwo.Constants;
import com.odero.bigtwo.R;
import com.odero.bigtwo.adapter.TrackListApater;
import com.odero.bigtwo.models.Result;
import com.odero.bigtwo.models.TrackResponse;
import com.odero.bigtwo.network.TracksClient;
import com.odero.bigtwo.util.OnRestaurantSelectedListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ResultListFragment extends Fragment {
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.progressBar) ProgressBar progressBar;

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private String mRecentKeyWords;

    private OnRestaurantSelectedListener mOnRestaurantSelectedListener;

    TrackListApater adapter;
    ArrayList<Result> resultList ;

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
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnRestaurantSelectedListener = (OnRestaurantSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + e.getMessage());
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result_list, container, false);
        ButterKnife.bind(this, view);
        mRecentKeyWords = mSharedPreferences.getString(Constants.PREFERENCES_RESULT_KEY, null);

        if (mRecentKeyWords != null) {
            fetchPosts(mRecentKeyWords);
        }
        return view;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_search, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                addToSharedPreferences(query);
                fetchPosts(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    private void fetchPosts(String term){
        progressBar.setVisibility(View.VISIBLE);
        TracksClient.getRetrofitClient().getTracks(term).enqueue(new Callback<TrackResponse>() {
            @Override
            public void onResponse(Call<TrackResponse> call, Response<TrackResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    resultList = (ArrayList<Result>) response.body().getResults();
                    progressBar.setVisibility(View.GONE);


                    getActivity().runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            adapter = new TrackListApater(getActivity(), resultList, mOnRestaurantSelectedListener);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                        }
                    });

                }else{
                    showUnsuccessfulMessage();
                }
            }

            @Override
            public void onFailure(Call<TrackResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                t.getMessage();
                //Toast.makeText(ResultsActivity.this,"Error "+ t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void addToSharedPreferences(String location) {
        mEditor.putString(Constants.PREFERENCES_RESULT_KEY, location).apply();
    }
    private void showUnsuccessfulMessage() {
        progressBar.setVisibility(View.GONE);
        //Toast.makeText(ResultsActivity.this,"Something went wrong, please make another search, or try later",Toast.LENGTH_SHORT).show();

    }
}
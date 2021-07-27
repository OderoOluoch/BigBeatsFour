package com.odero.bigtwo.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.odero.bigtwo.Constants;
import com.odero.bigtwo.R;
import com.odero.bigtwo.adapter.TrackListApater;
import com.odero.bigtwo.models.Result;
import com.odero.bigtwo.models.TrackResponse;
import com.odero.bigtwo.network.TracksClient;
import com.odero.bigtwo.util.OnRestaurantSelectedListener;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultsActivity extends AppCompatActivity  implements OnRestaurantSelectedListener {
    private Integer mPosition;
    ArrayList<Result> mResults;
    String mSource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        if (savedInstanceState != null) {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                mPosition = savedInstanceState.getInt(Constants.EXTRA_KEY_POSITION);
                mResults = Parcels.unwrap(savedInstanceState.getParcelable(Constants.EXTRA_KEY_RESULT));
                mSource = savedInstanceState.getString(Constants.KEY_SOURCE);

                if (mPosition != null && mResults != null) {
                    Intent intent = new Intent(this, ResultDetailActivity.class);
                    intent.putExtra(Constants.EXTRA_KEY_POSITION, mPosition);
                    intent.putExtra(Constants.EXTRA_KEY_RESULT, Parcels.wrap(mResults));
                    intent.putExtra(Constants.KEY_SOURCE, mSource);
                    startActivity(intent);
                }
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mPosition != null && mResults != null) {
            outState.putInt(Constants.EXTRA_KEY_POSITION, mPosition);
            outState.putParcelable(Constants.EXTRA_KEY_RESULT, Parcels.wrap(mResults));
            outState.putString(Constants.KEY_SOURCE, mSource);
        }
    }



    @Override
    public void onRestaurantSelected(Integer position, ArrayList<Result> results, String source) {
        mPosition = position;
        mResults = results;
        mSource = source;
    }
}
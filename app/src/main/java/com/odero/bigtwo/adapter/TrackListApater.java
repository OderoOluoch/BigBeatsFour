package com.odero.bigtwo.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.odero.bigtwo.Constants;
import com.odero.bigtwo.R;
import com.odero.bigtwo.models.Result;
import com.odero.bigtwo.ui.ResultDetailActivity;
import com.odero.bigtwo.ui.ResultDetailFragment;
import com.odero.bigtwo.util.OnRestaurantSelectedListener;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrackListApater extends RecyclerView.Adapter<TrackListApater.TrackViewHolder> {
    private ArrayList<Result> resultList = new ArrayList<>();
    private Context mContext;
    private OnRestaurantSelectedListener mOnRestaurantSelectedListener;


    public TrackListApater(Context context, ArrayList<Result> results, OnRestaurantSelectedListener restaurantSelectedListener) {
        mContext = context;
        resultList = results;
        mOnRestaurantSelectedListener = restaurantSelectedListener;
    }


    @Override
    public TrackListApater.TrackViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        TrackViewHolder viewHolder = new TrackViewHolder(view, resultList, mOnRestaurantSelectedListener);
        return viewHolder;
    }




    @Override
    public void onBindViewHolder(TrackListApater.TrackViewHolder holder, int position) {
        //holder.tvBody.setText("Artist Name: " + resultList.get(position).getArtistName());
        //holder.tvTitle.setText("Collection Name: " + resultList.get(position).getCollectionName());
        holder.mAlbumNameTextView.setText( resultList.get(position).getCollectionName());
        holder.mAlbumArtistTextView.setText(resultList.get(position).getArtistName());
        holder.mAlbumTrackCountTextView.setText(resultList.get(position).getTrackCount()+" Songs" );
        Picasso.get().load(resultList.get(position).getArtworkUrl100()).into(holder.mAlbumImageView);

    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public class TrackViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {
    //TextView tvBody,tvTitle;
        private int mOrientation;
        private Context mContext;
        private ArrayList<Result> mResults = new ArrayList<>();
        private OnRestaurantSelectedListener mResultSelectedListener;

        @BindView(R.id.albumImage) ImageView mAlbumImageView;
        @BindView(R.id.albumName) TextView mAlbumNameTextView;
        @BindView(R.id.albumArtsistName) TextView mAlbumArtistTextView;
        @BindView(R.id.numberOfSongs) TextView mAlbumTrackCountTextView;

        public TrackViewHolder(View itemView, ArrayList<Result> results, OnRestaurantSelectedListener restaurantSelectedListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            mContext = itemView.getContext();
            mOrientation = itemView.getResources().getConfiguration().orientation;
            mResults = results;
            mResultSelectedListener = restaurantSelectedListener;
            if (mOrientation == Configuration.ORIENTATION_LANDSCAPE){
                createDetailFragment(0);
            }
            itemView.setOnClickListener(this);
        }


        private void createDetailFragment(int position){
            ResultDetailFragment detailFragment = ResultDetailFragment.newInstance(mResults, position, Constants.SOURCE_FIND);
            FragmentTransaction ft = ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.resultsDetailContainer, detailFragment);
            ft.commit();
        }



        @Override
        public void onClick(View v) {
            int itemPosition = getLayoutPosition();
            mResultSelectedListener.onRestaurantSelected(itemPosition, mResults, Constants.SOURCE_FIND);
            if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                createDetailFragment(itemPosition);
            } else {
                Intent intent = new Intent(mContext, ResultDetailActivity.class);
                intent.putExtra(Constants.EXTRA_KEY_POSITION, itemPosition);
                intent.putExtra(Constants.EXTRA_KEY_RESULT, Parcels.wrap(mResults));
                intent.putExtra(Constants.KEY_SOURCE, Constants.SOURCE_FIND);
                mContext.startActivity(intent);
            }
        }
    }
}

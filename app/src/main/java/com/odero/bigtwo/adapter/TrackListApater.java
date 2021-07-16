package com.odero.bigtwo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.odero.bigtwo.R;
import com.odero.bigtwo.models.Result;
import com.odero.bigtwo.ui.ResultDetailActivity;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrackListApater extends RecyclerView.Adapter<TrackListApater.TrackViewHolder> {

    private List<Result> resultList;
    private Context mContext;


    public TrackListApater(Context context,List<Result> mtrackList) {
        mContext = context;
        this.resultList = mtrackList;
    }

    @Override
    public TrackViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item,parent,false);
        return new TrackViewHolder(view);
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

        @BindView(R.id.albumImage) ImageView mAlbumImageView;
        @BindView(R.id.albumName) TextView mAlbumNameTextView;
        @BindView(R.id.albumArtsistName) TextView mAlbumArtistTextView;
        @BindView(R.id.numberOfSongs) TextView mAlbumTrackCountTextView;


        public TrackViewHolder(View itemView) {
            super(itemView);
           ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);

            //tvBody = itemView.findViewById(R.id.tvBody);
            //tvTitle = itemView.findViewById(R.id.tvTitle);

        }
        @Override
        public void onClick(View v) {
            int itemPosition = getLayoutPosition();
            Intent intent = new Intent(mContext, ResultDetailActivity.class);
            intent.putExtra("position", itemPosition);
            intent.putExtra("result", Parcels.wrap(resultList));
            mContext.startActivity(intent);
        }
    }
}

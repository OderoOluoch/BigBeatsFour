package com.odero.bigtwo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.odero.bigtwo.Constants;
import com.odero.bigtwo.R;
import com.odero.bigtwo.models.Result;
import com.odero.bigtwo.ui.ResultDetailActivity;
import com.odero.bigtwo.util.ItemTouchHelperViewHolder;
import com.squareup.picasso.Picasso;
import org.parceler.Parcels;
import java.util.ArrayList;

import butterknife.BindView;


public class FirebaseResultsViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {
    private static final int MAX_WIDTH = 200;
    private static final int MAX_HEIGHT = 200;

    View mView;
    Context mContext;
    public ImageView mResultImageView;

    public FirebaseResultsViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
    }

    public void bindResult(Result result) {
        mResultImageView = (ImageView) mView.findViewById(R.id.albumImage);
        //ImageView mAlbumImageView = (ImageView) mView.findViewById(R.id.albumImage);
        TextView mAlbumNameTextView = (TextView) mView.findViewById(R.id.albumName);
        TextView mAlbumArtistTextView = (TextView) mView.findViewById(R.id.albumArtsistName);
        TextView mAlbumTrackCountTextView = (TextView) mView.findViewById(R.id.numberOfSongs);

        Picasso.get().load(result.getArtworkUrl100()).into(mResultImageView);
        mAlbumNameTextView.setText(result.getCollectionName());
        mAlbumArtistTextView.setText(result.getArtistName());
        mAlbumTrackCountTextView.setText( result.getTrackCount() + " Songs");
    }
    @Override
    public void onItemSelected() {
        itemView.animate()
                .alpha(0.7f)
                .scaleX(0.9f)
                .scaleY(0.9f)
                .setDuration(500);
    }

    @Override
    public void onItemClear() {
        itemView.animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f);
    }
}
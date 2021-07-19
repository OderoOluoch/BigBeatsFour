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
import com.squareup.picasso.Picasso;
import org.parceler.Parcels;
import java.util.ArrayList;

import butterknife.BindView;


public class FirebaseResultsViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener{
    View mView;
    Context mContext;

    public FirebaseResultsViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    public void bindRestaurant(Result result) {

        ImageView mAlbumImageView = (ImageView) mView.findViewById(R.id.albumImage);
        TextView mAlbumNameTextView = (TextView) mView.findViewById(R.id.albumName);
        TextView mAlbumArtistTextView = (TextView) mView.findViewById(R.id.albumArtsistName);
        TextView mAlbumTrackCountTextView = (TextView) mView.findViewById(R.id.numberOfSongs);

        Picasso.get().load(result.getArtworkUrl100()).into(mAlbumImageView);
        mAlbumNameTextView.setText(result.getCollectionName());
        mAlbumArtistTextView.setText(result.getArtistName());
        mAlbumTrackCountTextView.setText( result.getTrackCount() + " Songs");
    }

    @Override
    public void onClick(View view) {
        final ArrayList<Result> results = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_RESULTS);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    results.add(snapshot.getValue(Result.class));
                }

                int itemPosition = getLayoutPosition();

                Intent intent = new Intent(mContext, ResultDetailActivity.class);
                intent.putExtra("position", itemPosition + "");
                intent.putExtra("restaurants", Parcels.wrap(results));

                mContext.startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}

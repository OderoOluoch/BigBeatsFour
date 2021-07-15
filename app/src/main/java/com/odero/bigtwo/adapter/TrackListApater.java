package com.odero.bigtwo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.odero.bigtwo.R;
import com.odero.bigtwo.models.Result;

import java.util.List;

public class TrackListApater extends RecyclerView.Adapter<TrackListApater.TrackViewHolder>{

    private List<Result> resultList;


    public TrackListApater(List<Result> mtrackList) {
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
        holder.tvBody.setText("Artist Name: " + resultList.get(position).getArtistName());
        holder.tvTitle.setText("Collection Name: " + resultList.get(position).getCollectionName());

    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public class TrackViewHolder extends  RecyclerView.ViewHolder {
        TextView tvTitle,tvBody;

        public TrackViewHolder(View itemView) {
            super(itemView);

            tvBody = itemView.findViewById(R.id.tvBody);
            tvTitle = itemView.findViewById(R.id.tvTitle);

        }
    }
}

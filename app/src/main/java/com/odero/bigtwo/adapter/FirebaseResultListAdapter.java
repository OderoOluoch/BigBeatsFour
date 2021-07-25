package com.odero.bigtwo.adapter;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.odero.bigtwo.R;
import com.odero.bigtwo.models.Result;
import com.odero.bigtwo.ui.ResultDetailActivity;
import com.odero.bigtwo.util.ItemTouchHelperAdapter;
import com.odero.bigtwo.util.OnStartDragListener;

import org.jetbrains.annotations.NotNull;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collections;

public class FirebaseResultListAdapter extends FirebaseRecyclerAdapter<Result, FirebaseResultsViewHolder> implements ItemTouchHelperAdapter {
    private Query mRef;
    private OnStartDragListener mOnStartDragListener;
    private Context mContext;
    private ChildEventListener mChildEventListener;
    private ArrayList<Result> mResults = new ArrayList<>();

    public FirebaseResultListAdapter(FirebaseRecyclerOptions<Result> options, Query ref, OnStartDragListener onStartDragListener, Context context){
        super(options);
        mRef = ref.getRef();
        mOnStartDragListener = onStartDragListener;
        mContext = context;

        mChildEventListener = mRef.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mResults.add(dataSnapshot.getValue(Result.class));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onBindViewHolder(@androidx.annotation.NonNull @NotNull FirebaseResultsViewHolder firebaseResultViewHolder, int position, @androidx.annotation.NonNull @NotNull Result result) {
        firebaseResultViewHolder.bindResult(result);
        firebaseResultViewHolder.mResultImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                    mOnStartDragListener.onStartDrag(firebaseResultViewHolder);
                }
                return false;
            }
        });

        firebaseResultViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ResultDetailActivity.class);
                intent.putExtra("position", firebaseResultViewHolder.getAdapterPosition());
                intent.putExtra("results", Parcels.wrap(mResults));
                mContext.startActivity(intent);
            }
        });
    }
    @NonNull
    @Override
    public FirebaseResultsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.result_list_item_drag, parent, false);
        return new FirebaseResultsViewHolder(view);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition){
        Collections.swap(mResults, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        setIndexInFirebase();
        return false;
    }


    @Override
    public void onItemDismiss(int position) {
        mResults.remove(position);
        getRef(position).removeValue();
    }

    private void setIndexInFirebase() {
        for (Result result : mResults) {
            int index = mResults.indexOf(result);
            DatabaseReference ref = getRef(index);
            result.setIndex(Integer.toString(index));
            ref.setValue(result);
        }
    }

    @Override
    public void stopListening() {
        super.stopListening(); mRef.removeEventListener(mChildEventListener);
    }


}

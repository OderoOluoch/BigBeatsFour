package com.odero.bigtwo.adapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.odero.bigtwo.R;
import com.odero.bigtwo.models.Result;
import com.odero.bigtwo.util.ItemTouchHelperAdapter;
import com.odero.bigtwo.util.OnStartDragListener;

public class FirebaseResultListAdapter extends FirebaseRecyclerAdapter<Result, FirebaseResultsViewHolder> implements ItemTouchHelperAdapter {
    private DatabaseReference mRef;
    private OnStartDragListener mOnStartDragListener;
    private Context mContext;


    public FirebaseResultListAdapter(FirebaseRecyclerOptions<Result> options,
                                         DatabaseReference ref,
                                         OnStartDragListener onStartDragListener,
                                         Context context){
        super(options);
        mRef = ref.getRef();
        mOnStartDragListener = onStartDragListener;
        mContext = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull final FirebaseResultsViewHolder firebaseResultViewHolder, int position, @NonNull Result result) {
        firebaseResultViewHolder.bindResult(result);
        firebaseResultViewHolder.mResultImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getActionMasked() == MotionEvent.ACTION_DOWN){
                    mOnStartDragListener.onStartDrag(firebaseResultViewHolder);
                }
                return false;
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
        notifyItemMoved(fromPosition, toPosition);
        return false;
    }

    @Override
    public void onItemDismiss(int position){
        getRef(position).removeValue();
    }
}

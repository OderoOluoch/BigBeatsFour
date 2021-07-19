package com.odero.bigtwo.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.odero.bigtwo.Constants;
import com.odero.bigtwo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {
    //private SharedPreferences mSharedPreferences;
    //private SharedPreferences.Editor mEditor;

    private DatabaseReference mSearchedKeyWordReference;
    private ValueEventListener mSearchedKeyWordReferenceListener;


    //Using BIndView from ButterKnife.
    @BindView(R.id.goToResultsFromSearch) Button searchButton;
    @BindView(R.id.keyWordInputSearchView) EditText searchKeyWOrd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mSearchedKeyWordReference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child(Constants.FIREBASE_CHILD_SEARCHED_KEY_WORD);

        mSearchedKeyWordReferenceListener = mSearchedKeyWordReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot locationSnapshot : dataSnapshot.getChildren()) {
                    String location = locationSnapshot.getValue().toString();
                    Log.d("Locations updated", "location: " + location);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //BindViews
        ButterKnife.bind(this);

        //mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        //mEditor = mSharedPreferences.edit();



        //Implemented because of the onclick interface
        searchButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == searchButton){
            if( searchKeyWOrd.getText().toString().length() == 0 ){
                searchKeyWOrd.setError( "Please Enter a search key word to proceed" );
                searchKeyWOrd.requestFocus();
            }else {
                String TypedSearchKeyWOrd = searchKeyWOrd.getText().toString();
                Intent intent = new Intent(SearchActivity.this, ResultsActivity.class);
                intent.putExtra("TypedSearchKeyWOrd", TypedSearchKeyWOrd);
                saveKeyWordToFirebase(TypedSearchKeyWOrd);
                // if(!(location).equals("")) {
                //      addToSharedPreferences(TypedSearchKeyWOrd);
                //  }
                startActivity(intent);
            }
        }
    }
    public void saveKeyWordToFirebase(String SearchKeyWOrd) {
        mSearchedKeyWordReference.push().setValue(SearchKeyWOrd);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSearchedKeyWordReference.removeEventListener(mSearchedKeyWordReferenceListener);
    }

    //    private void addToSharedPreferences(String location) {
    //        mEditor.putString(Constants.PREFERENCES_LOCATION_KEY, location).apply();
    //    }


}
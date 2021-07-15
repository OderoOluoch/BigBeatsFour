package com.odero.bigtwo.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.odero.bigtwo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {


    //Using BIndView from ButterKnife.
    @BindView(R.id.goToResultsFromSearch) Button searchButton;
    @BindView(R.id.keyWordInputSearchView) EditText searchKeyWOrd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //BindViews
        ButterKnife.bind(this);

        //Implemented because of the onclick interface
        searchButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == searchButton){
            if( searchKeyWOrd.getText().toString().length() == 0 ){
                searchKeyWOrd.setError( "Please Enter a search key word to proceed" );
            }else {
                String TypedSearchKeyWOrd = searchKeyWOrd.getText().toString();
                Intent intent = new Intent(SearchActivity.this, ResultsActivity.class);
                intent.putExtra("TypedSearchKeyWOrd", TypedSearchKeyWOrd);
                startActivity(intent);
            }
        }
    }
}
package com.odero.bigtwo.network;

import com.odero.bigtwo.models.Result;
import com.odero.bigtwo.models.TrackResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("/search")
    Call<TrackResponse> getTracks(
            @Query("term") String term
    );
}

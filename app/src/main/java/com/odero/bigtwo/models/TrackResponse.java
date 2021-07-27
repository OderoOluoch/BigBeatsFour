package com.odero.bigtwo.models;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class TrackResponse {

    @SerializedName("resultCount")
    @Expose
    private Integer resultCount;
    @SerializedName("results")
    @Expose
    private ArrayList<Result> results = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public TrackResponse() {
    }

    /**
     *
     * @param resultCount
     * @param results
     */
    public TrackResponse(Integer resultCount, ArrayList<Result> results) {
        super();
        this.resultCount = resultCount;
        this.results = results;
    }

    public Integer getResultCount() {
        return resultCount;
    }

    public void setResultCount(Integer resultCount) {
        this.resultCount = resultCount;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(ArrayList<Result> results) {
        this.results = results;
    }

}


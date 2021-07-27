package com.odero.bigtwo.util;

import com.odero.bigtwo.models.Result;

import java.util.ArrayList;

public interface OnRestaurantSelectedListener {
    public void onRestaurantSelected(Integer position, ArrayList<Result> restaurants, String source);
}
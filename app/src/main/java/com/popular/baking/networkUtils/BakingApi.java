package com.popular.baking.networkUtils;

import com.popular.baking.constants.Constants;
import com.popular.baking.dto.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

//Retrofit data feed
public interface BakingApi {


    @GET(Constants.FEED)
    Call<List<Recipe>> getRecipes();



}

package com.popular.baking.networkUtils;

import android.util.Log;

import com.popular.baking.constants.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit = null;
    private static final String TAG = RetrofitClient.class.getName();

    public static Retrofit getClient() {

        if (null == retrofit) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BAKING_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        Log.d(TAG, "getClient: retrofit client... " + retrofit);

        return retrofit;
    }

}


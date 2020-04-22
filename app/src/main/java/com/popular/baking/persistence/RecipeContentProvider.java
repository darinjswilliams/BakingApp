package com.popular.baking.persistence;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.popular.baking.constants.Constants;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class RecipeContentProvider extends ContentProvider {

    private AppDatabase appRepository;
    public static final UriMatcher sUriMatcher = buildUriMatcher();


    private static final String TAG = RecipeContentProvider.class.getSimpleName();

    private static UriMatcher buildUriMatcher() {

        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(Contract.AUTHORITY, Contract.PATH_RECIPE_INGREDIENTS, Contract.RECIPE_CODE);

        return uriMatcher;
    }


    @Override
    public boolean onCreate() {
        Context context = this.getContext();
        appRepository = AppDatabase.getsInstance(context);

        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREF, Context.MODE_PRIVATE);
        int recipeId = sharedPreferences.getInt(Constants.RECIPE_ID, 0);

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {


        int match  = sUriMatcher.match(uri);
        int recipeId = 1;

        Cursor retCursor = null;

        switch (match ){
            case Contract.RECIPE_CODE:
                retCursor = appRepository.ingredientsDao().getSelectedIngredients(recipeId);

                break;
        }

        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}

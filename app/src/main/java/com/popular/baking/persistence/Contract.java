package com.popular.baking.persistence;

import android.net.Uri;
import android.provider.BaseColumns;

public class Contract implements BaseColumns {

    public static final String TABLE_NAME = "Ingredients";
    public static final String RECIPE_TASK = "task";
    public static final int RECIPE_CODE = 100;

    public static final String SCHMEA = "content://";
    public static final String AUTHORITY = "com.popular.baking.persistence";
    public static final Uri BASE_CONTENT_URI = Uri.parse(SCHMEA + AUTHORITY);
    public static final String PATH_RECIPE_INGREDIENTS = "ingredients";
    public static final Uri PATH_INGRIDENTS_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_RECIPE_INGREDIENTS).build();
}

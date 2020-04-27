package com.popular.baking.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.popular.baking.R;
import com.popular.baking.constants.Constants;
import com.popular.baking.dto.RecipeStepsAndIngredients;
import com.popular.baking.networkUtils.AppRepository;

import java.util.ArrayList;
import java.util.List;

public class RecipeAppWidgetService extends RemoteViewsService {

    private final static String TAG = RecipeAppRemoteFactory.class.getSimpleName();

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RecipeAppRemoteFactory(getApplicationContext(), intent);
    }

    class RecipeAppRemoteFactory implements RemoteViewsService.RemoteViewsFactory {
        private Context mContext;
        private List <RecipeStepsAndIngredients> mIngredientsArray = new ArrayList<RecipeStepsAndIngredients>();
        private String recipeName;
        private String listOfIngredients;
        private int appWidgetId;
        private AppRepository appRepository;


        public RecipeAppRemoteFactory(Context mContext, Intent intent) {
            this.mContext = mContext;
            this.appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
            appRepository = AppRepository.getInstance(getApplication());
        }


        @Override
        public void onCreate() {

            //Connect to Data Source, no heavy operations
            Log.i(TAG, "RecipeAppWidgetService: onCreate: ");
            SharedPreferences prefs = getApplicationContext().getSharedPreferences(Constants.PREF, Context.MODE_PRIVATE);
            int recipeId = prefs.getInt(Constants.RECIPE_ID, 0);
            recipeName = prefs.getString(Constants.NAME_OF_RECIPE, null );

            mIngredientsArray = appRepository.getIngridentsForWidgets(recipeId);

            Log.i(TAG, "onCreate: not empty.." + mIngredientsArray.size());
            if(mIngredientsArray.size() > 0) {
                for (RecipeStepsAndIngredients rcp : mIngredientsArray) { ;
                    listOfIngredients = rcp.getFormatInfo();
                }

            }
        }

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {
            // close connection to data source

        }

        @Override
        public int getCount() {
            //Display items contain in List
            return mIngredientsArray.size() > 0 ? mIngredientsArray.size() : 0;
        }

        @Override
        public RemoteViews getViewAt(int position) {
            //Load data onto fields
            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.recipe_app_widget_item);
            views.setTextViewText(R.id.recipe_example_widget_item_text, listOfIngredients);
            views.setTextViewText(R.id.recipe_name_text, recipeName);

            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            // since we have only one layout, return only 1
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
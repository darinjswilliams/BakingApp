package com.popular.baking.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.popular.baking.R;
import com.popular.baking.constants.Constants;
import com.popular.baking.dto.Ingredients;
import com.popular.baking.dto.RecipeStepsAndIngredients;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class RecipeAppWidgetService extends RemoteViewsService {

    private final static String TAG = RecipeAppRemoteFactory.class.getSimpleName();

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RecipeAppRemoteFactory(getApplicationContext(), intent);
    }

    class RecipeAppRemoteFactory implements RemoteViewsService.RemoteViewsFactory {
        private Context mContext;
        private List<RecipeStepsAndIngredients> mIngredientsArray = new ArrayList<RecipeStepsAndIngredients>();
        private List<Ingredients> mIngArray = new ArrayList<Ingredients>();
        private String recipeName;
        private int appWidgetId;
        private StringBuilder sb = new StringBuilder();

        public RecipeAppRemoteFactory(Context mContext, Intent intent) {
            this.mContext = mContext;
            this.appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }


        @Override
        public void onCreate() {
            loadSharePreferences();

        }

        private void loadSharePreferences() {
            //Connect to Data Source, no heavy operations
            Log.i(TAG, "RecipeAppWidgetService: onCreate: ");
            SharedPreferences prefs = getApplicationContext().getSharedPreferences(Constants.PREF, Context.MODE_PRIVATE);
            recipeName = prefs.getString(Constants.NAME_OF_RECIPE, null);
            Gson gson = new Gson();
            String json = prefs.getString(Constants.SAVE_LIST_FOR_WIDGET, null);
            Type type = new TypeToken<ArrayList<Ingredients>>() {
            }.getType();
            mIngArray = gson.fromJson(json, type);

            //travese alist of elements
            ListIterator<Ingredients> igIter = mIngArray.listIterator();
           while( igIter.hasNext() ){
               sb.append( igIter.next() );
               sb.append("\n");
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
            return 1;
        }

        @Override
        public RemoteViews getViewAt(int position) {
            //Load data onto fields
            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.recipe_app_widget_item);
            views.setTextViewText(R.id.recipe_example_widget_item_text, sb.toString());
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
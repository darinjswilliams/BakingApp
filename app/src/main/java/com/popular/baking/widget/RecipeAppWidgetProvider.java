package com.popular.baking.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;

import com.popular.baking.R;
import com.popular.baking.dto.RecipeStepsAndIngredients;
import com.popular.baking.view.MainActivity;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeAppWidgetProvider extends AppWidgetProvider {

    private static final String TAG = RecipeAppWidgetProvider.class.getSimpleName();
    public static RecipeStepsAndIngredients mRecipeSelected;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {


        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_app_widget_provider);
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.widgetTitleLabel, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);

            Intent serviceIntent = new Intent(context, RecipeAppWidgetService.class);
            serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME)));

            //Set Remote object
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_app_widget_provider);
            views.setRemoteAdapter(R.id.example_widget_stack_view, serviceIntent);
            views.setEmptyView(R.id.example_widget_stack_view, R.id.example_widget_empty_view);

            Bundle appWidgetOptions = appWidgetManager.getAppWidgetOptions(appWidgetId);
            resizeWidget(appWidgetOptions, views);

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.collection_widget_list_item);

        resizeWidget(newOptions, views);
    }

    private void resizeWidget(Bundle appWidgetOptins, RemoteViews views){

        int minWidth = appWidgetOptins.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);
        int maxWidth = appWidgetOptins.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_WIDTH);
        int minHeight = appWidgetOptins.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT);
        int maxHeight = appWidgetOptins.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT);

        if(maxHeight > 100){
            views.setViewVisibility(R.id.recipe_example_widget_item_text, View.VISIBLE);
        } else {
            views.setViewVisibility(R.id.recipe_example_widget_item_text, View.GONE);
        }
    }
//    private static void setNonEmptyWidgets(Context context, int[] appWidgetIds,
//                                           AppWidgetManager appWidgetManager, boolean displayUIMessage) {
//        for (int appWidgetId : appWidgetIds) {
//
//            // Construct the RemoteViews object
//            RemoteViews views = getIngredientsRemoteListView(context);
//
//            setUpNonEmptyUIProperties(views);
//
//            // Set UI and listener
//            MyUtilWidget.setWidgetUI(context, views, mRecipeSelected);
//
//            // Instruct the widget manager to update the widget
//            appWidgetManager.updateAppWidget(appWidgetId, views);
//        }
//
//    }
//
//    private static void setUpNonEmptyUIProperties(RemoteViews views) {
//        views.setViewVisibility(R.id.widgetTitleLabel, View.VISIBLE);
//        views.setViewVisibility(R.id.widgetListView, View.VISIBLE);
//
//
//        if (MainActivity.mTabletPane) {
//            views.setTextViewTextSize(R.id.widgetTitleLabel, COMPLEX_UNIT_SP, 24);
//        }
//
//
//    }
//
//    private static void setEmptyWidgetsUI(Context context, int[] appWidgetIds, AppWidgetManager appWidgetManager) {
//        for (int appWidgetId : appWidgetIds) {
//            // Construct the RemoteViews object
//            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_app_widget_provider);
//
//            // Set up UI and listener
//            setUpEmptyUIProperties(context, views);
//
//            // Update widget
//            appWidgetManager.updateAppWidget(appWidgetId, views);
//        }
//    }
//
//
//    private RemoteViews getIngredientsRemoteListView(Context context) {
//        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_app_widget_provider);
//
//        Intent intent = new Intent(context, RecipeAppWidgetService.class);
//        views.setRemoteAdapter(R.id.widgetListView, intent);
//
//        views.setEmptyView(R.id.widgetListView, R.id.empty_widgetTitleLabel );
//
//        return views;
//    }
//
//    private static void setUpEmptyUIProperties(Context context, RemoteViews views) {
//
//
//
//        Log.i(TAG, "setUpEmptyUIProperties: inside function");
//        if (MainActivity.mTabletPane) {
//            views.setTextViewTextSize(R.id.widgetTitleLabel, COMPLEX_UNIT_SP, 24);
//        }
//    }
}


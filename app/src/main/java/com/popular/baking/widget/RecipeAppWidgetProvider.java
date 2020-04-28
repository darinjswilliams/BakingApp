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

    public RecipeAppWidgetProvider() {
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {


        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_app_widget_provider);
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.widgetTitleLabel_text, pendingIntent);

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

    //method is called first when place on screen and resized is done.
    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.collection_widget_list_item);

        resizeWidget(newOptions, views);
    }

    private void resizeWidget(Bundle appWidgetOptins, RemoteViews views) {

        int minWidth = appWidgetOptins.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);
        int maxWidth = appWidgetOptins.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_WIDTH);
        int minHeight = appWidgetOptins.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT);
        int maxHeight = appWidgetOptins.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT);

        if (maxHeight > 100) {
            views.setViewVisibility(R.id.recipe_example_widget_item_text, View.VISIBLE);
        } else {
            views.setViewVisibility(R.id.recipe_example_widget_item_text, View.GONE);
        }
    }

    public void updateWidgetRecipe(Context context, AppWidgetManager appWidgetManager,
                                   int recipeSelected, int[] appWidgetIds) {
        this.onUpdate(context, appWidgetManager, appWidgetIds);

    }

}


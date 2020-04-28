package com.popular.baking.adapters;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.popular.baking.R;
import com.popular.baking.constants.Constants;
import com.popular.baking.databinding.IngredientItemsBinding;
import com.popular.baking.dto.Ingredients;
import com.popular.baking.dto.RecipeStepsAndIngredients;
import com.popular.baking.networkUtils.BuildUtils;
import com.popular.baking.widget.RecipeAppWidgetProvider;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class RecipeDetailsAdapter extends RecyclerView.Adapter<RecipeDetailsAdapter.MyRecipeDetailsAdapterHolder> {

    public RecipeStepsAndIngredients recipeStepsAndIngredients;
    public OnClickDetailListener onClickDetailListener;
    public final static String TAG = RecipeDetailsAdapter.class.getSimpleName();
    private Context mContext;
    private RecipeAppWidgetProvider recipeAppWidgetProvider;
    public int recipeId;


    public RecipeDetailsAdapter(OnClickDetailListener onClickDetailListener) {
        this.onClickDetailListener = onClickDetailListener;
    }

    public interface OnClickDetailListener {
        void clickRecipeDetails(RecipeStepsAndIngredients recipeStepsAndIngredients, int position);

        void clickRecipeDetails(String recipeDetails, String nameOfRecipe);
    }

    @NonNull
    @Override
    public RecipeDetailsAdapter.MyRecipeDetailsAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        //Set Context
        mContext = parent.getContext();

        IngredientItemsBinding ingredientItemsBinding = DataBindingUtil.inflate(layoutInflater, R.layout.ingredient_items, parent,
                false);


        return new MyRecipeDetailsAdapterHolder(ingredientItemsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeDetailsAdapter.MyRecipeDetailsAdapterHolder holder, int position) {


        switch (position) {
            case 0:

                Log.i(TAG, "onBindViewHolder: position 0.." + recipeStepsAndIngredients.recipe.getName());
                holder.bind(Constants.INGRIDENTS);
                holder.clickBindIngredients(BuildUtils.menuBuilderForIngridents(
                        recipeStepsAndIngredients.ingredients), this.onClickDetailListener,
                        recipeStepsAndIngredients.recipe.getName());

                saveToSharePrefrences(recipeStepsAndIngredients.ingredients, recipeStepsAndIngredients.recipe.getName(),
                        recipeStepsAndIngredients.recipe.getId());
                break;

            default:

                holder.bindRecipePosition(recipeStepsAndIngredients.steps.get(position - 1).getShortDescription(),
                        recipeStepsAndIngredients, this.onClickDetailListener, position - 1);
                break;
        }

    }

    private void saveToSharePrefrences(List<Ingredients> ingredientList, String rName, int rId) {
        SharedPreferences prefs = mContext.getSharedPreferences(Constants.PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        Log.i(TAG, "saveToSharePrefrences: " + ingredientList);
        //Save Ingridents using Gson
        Gson gson = new Gson();
        String json = gson.toJson(ingredientList);
        editor.putString(Constants.SAVE_LIST_FOR_WIDGET, json);
        editor.putString(Constants.NAME_OF_RECIPE, rName );
        editor.putInt(Constants.RECIPE_ID, recipeId);
        editor.clear();
        editor.apply();

        recipeAppWidgetProvider = new RecipeAppWidgetProvider();
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(mContext);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(mContext, RecipeAppWidgetProvider.class));

        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.recipe_example_widget_item_text);

        recipeAppWidgetProvider.updateWidgetRecipe(mContext, appWidgetManager, recipeId, appWidgetIds);
    }

    @Override
    public int getItemCount() {
        return recipeStepsAndIngredients != null ? recipeStepsAndIngredients.steps.size() + 1: 0;
    }

    public void setRecipeStepsAndIngredients(RecipeStepsAndIngredients recipeStepsAndIngredients) {
        this.recipeStepsAndIngredients = recipeStepsAndIngredients;
        notifyDataSetChanged();
    }

    public class MyRecipeDetailsAdapter {
    }

    public class MyRecipeDetailsAdapterHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private IngredientItemsBinding mIngredientItemsBinding;

        public MyRecipeDetailsAdapterHolder(@NonNull IngredientItemsBinding ingredientItemsBinding) {
            super(ingredientItemsBinding.getRoot());

            this.mIngredientItemsBinding = ingredientItemsBinding;
        }

        @Override
        public void onClick(View v) {

        }

        public void bind(String ingridents) {
            mIngredientItemsBinding.ingrds.setText(ingridents);
        }

        public void clickBindIngredients(String list, OnClickDetailListener onClickDetailListener, String ingredList) {
            mIngredientItemsBinding.executePendingBindings();
            mIngredientItemsBinding.ingrds.setOnClickListener(v -> onClickDetailListener.clickRecipeDetails(list, ingredList));
        }

        public void bindRecipePosition(String shortDescription, RecipeStepsAndIngredients recipeStepsAndIngredients, OnClickDetailListener onClickDetailListener, int position) {

            mIngredientItemsBinding.ingrds.setText(shortDescription);

            // Evaluates the pending bindings, updating any Views that have expressions bound to
            mIngredientItemsBinding.executePendingBindings();

            mIngredientItemsBinding.ingrds.setOnClickListener(v -> onClickDetailListener.clickRecipeDetails(recipeStepsAndIngredients, position));

        }

    }
}

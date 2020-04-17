package com.popular.baking.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.popular.baking.R;
import com.popular.baking.constants.Constants;
import com.popular.baking.databinding.IngredientItemsBinding;
import com.popular.baking.dto.RecipeStepsAndIngredients;
import com.popular.baking.networkUtils.BuildUtils;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class RecipeDetailsAdapter extends RecyclerView.Adapter<RecipeDetailsAdapter.MyRecipeDetailsAdapterHolder> {

    public RecipeStepsAndIngredients recipeStepsAndIngredients;
    public OnClickDetailListener onClickDetailListener;
    public final static String TAG = RecipeDetailsAdapter.class.getSimpleName();


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
                break;

            default:

                Log.i(TAG, "onBindViewHolder: description.." + recipeStepsAndIngredients.steps.get(position).getShortDescription());
                holder.bindRecipePosition(recipeStepsAndIngredients.steps.get(position - 1).getShortDescription(),
                        recipeStepsAndIngredients, this.onClickDetailListener, position - 1);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return recipeStepsAndIngredients != null ? recipeStepsAndIngredients.steps.size() + 1: 0;
    }

    public void setRecipeStepsAndIngredients(RecipeStepsAndIngredients recipeStepsAndIngredients) {
        this.recipeStepsAndIngredients = recipeStepsAndIngredients;
        Log.d(TAG, "setRecipeStepsAndIngredients: Ingridents " + recipeStepsAndIngredients.ingredients.size());
        Log.d(TAG, "setRecipeStepsAndIngredients: Steps " + recipeStepsAndIngredients.steps.size());
        Log.d(TAG, "setRecipeStepsAndIngredients: " + recipeStepsAndIngredients.toString());
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
            Log.i(TAG, "bind: HERE ARE THE INGRIDENTS.." + ingridents);
            mIngredientItemsBinding.ingrds.setText(ingridents);
            mIngredientItemsBinding.executePendingBindings();
        }

        public void clickBindIngredients(String list, OnClickDetailListener onClickDetailListener, String ingredList) {
            mIngredientItemsBinding.executePendingBindings();
            Log.i(TAG, "clickBindIngredients: Detail.." + list);
            mIngredientItemsBinding.ingrds.setOnClickListener(v -> onClickDetailListener.clickRecipeDetails(list, ingredList));

        }

        public void bindRecipePosition(String shortDescription, RecipeStepsAndIngredients recipeStepsAndIngredients, OnClickDetailListener onClickDetailListener, int position) {

            Log.i(TAG, "bindRecipePosition: ShortDescription.." + shortDescription);
            mIngredientItemsBinding.ingrds.setText(shortDescription);

            // Evaluates the pending bindings, updating any Views that have expressions bound to
            mIngredientItemsBinding.executePendingBindings();

            mIngredientItemsBinding.ingrds.setOnClickListener(v -> onClickDetailListener.clickRecipeDetails(recipeStepsAndIngredients, position));

        }


    }
}

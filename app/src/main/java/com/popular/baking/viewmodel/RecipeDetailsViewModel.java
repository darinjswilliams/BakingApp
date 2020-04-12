package com.popular.baking.viewmodel;

import com.popular.baking.dto.RecipeStepsAndIngredients;
import com.popular.baking.networkUtils.AppRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class
RecipeDetailsViewModel extends ViewModel {

    private LiveData<RecipeStepsAndIngredients> recipeIngredientAndSteps;
    private static final String TAG = RecipeDetailsViewModel.class.getSimpleName();

    public RecipeDetailsViewModel(@NonNull AppRepository appRepo, int id) {

        recipeIngredientAndSteps = appRepo.getRecipeStepsAndIngredients(id);
    }

    public LiveData<RecipeStepsAndIngredients> getRecipeIngredientAndSteps() {
        return recipeIngredientAndSteps;
    }
}

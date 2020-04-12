package com.popular.baking.viewmodel;

import android.app.Application;

import com.popular.baking.dto.Recipe;
import com.popular.baking.networkUtils.AppRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class RecipeViewModel extends AndroidViewModel {

    private LiveData<List<Recipe>> recipe;
    private AppRepository appRepo;
    private static final String TAG = RecipeViewModel.class.getSimpleName();

    public RecipeViewModel(@NonNull Application application) {
        super(application);

        appRepo = AppRepository.getInstance(this.getApplication());
        recipe = appRepo.getRecipes();
    }

    public LiveData<List<Recipe>> getRecipe() {
        return recipe;
    }

}

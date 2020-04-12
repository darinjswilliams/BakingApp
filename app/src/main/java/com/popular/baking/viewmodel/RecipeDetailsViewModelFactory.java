package com.popular.baking.viewmodel;

import android.app.Application;

import com.popular.baking.networkUtils.AppRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class RecipeDetailsViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private AppRepository appRepo;
    private int recipeId;

    public RecipeDetailsViewModelFactory(Application context, int recipeId) {
        this.appRepo = AppRepository.getInstance(context);
        this.recipeId = recipeId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new RecipeDetailsViewModel(appRepo, recipeId);
    }
}

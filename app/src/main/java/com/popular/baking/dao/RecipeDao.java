package com.popular.baking.dao;

import com.popular.baking.dto.Recipe;
import com.popular.baking.dto.RecipeStepsAndIngredients;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

@Dao
public interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRecipe(List<Recipe> bList);

    @Query("SELECT * FROM recipe")
    LiveData<List<Recipe>>getRecipes();


    //Room will perfrom get all instance of the data class that pairs the parent entity
    //and Child Entity. Room will run all queries automatically
    @Transaction
    @Query("SELECT * FROM Recipe where id = :recipeId")
    LiveData<RecipeStepsAndIngredients> getIngredientsAndSteps(int recipeId);


    //Used by Content provider
    @Query("SELECT * FROM Ingredients where id = :recipeId")
     List<RecipeStepsAndIngredients> getIngredientsAndStepsforWidget(int recipeId);

}

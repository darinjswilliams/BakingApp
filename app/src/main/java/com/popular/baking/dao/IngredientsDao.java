package com.popular.baking.dao;

import android.database.Cursor;

import com.popular.baking.dto.Ingredients;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface IngredientsDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTask(List<Ingredients> ingredList);


    @Query("Select * from Ingredients where recipeId = :recipeId")
    Cursor getSelectedIngredients(int recipeId);
}

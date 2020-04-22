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


    @Query("SELECT * FROM Ingredients where recipeId = :recipeId")
    List<Ingredients>getIngredients(int recipeId);

    @Query("Select * from Ingredients")
    Cursor getAll();

    @Query("Select * from Ingredients where recipeId = :recipeId")
    Cursor getSelectedIngredients(int recipeId);
}

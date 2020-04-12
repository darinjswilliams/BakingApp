package com.popular.baking.dao;

import com.popular.baking.dto.Ingredients;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

@Dao
public interface IngredientsDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTask(List<Ingredients> ingredList);

}

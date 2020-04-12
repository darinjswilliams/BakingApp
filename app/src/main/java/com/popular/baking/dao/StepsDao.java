package com.popular.baking.dao;

import com.popular.baking.dto.Steps;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

@Dao
public interface StepsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTask(List<Steps> stepList);
}

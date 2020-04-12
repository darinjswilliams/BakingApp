package com.popular.baking.persistence;

import android.content.Context;
import android.util.Log;

import com.popular.baking.dao.IngredientsDao;
import com.popular.baking.dao.RecipeDao;
import com.popular.baking.dao.StepsDao;
import com.popular.baking.dto.Ingredients;
import com.popular.baking.dto.Recipe;
import com.popular.baking.dto.Steps;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


//ADD THE TYPE CONVERTERS SO ROOM KNOWS HOW TO DEAL WITH List CONVERSION
@Database(entities = {Recipe.class, Ingredients.class, Steps.class}, version = 3,  exportSchema = false)
//@TypeConverters({DataConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "Baking";
    private  static AppDatabase sInstance;

    public static AppDatabase getsInstance(Context context) {
        if(sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG,   "Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, AppDatabase.DATABASE_NAME)
                        //TODO -  Queries should be done in a separate thread to avoid locking the UI
                        //We will allow this only temporally to see that our db is working
                        //remove allowMainThreadQueries after verification.
                        .fallbackToDestructiveMigration()
//                        .allowMainThreadQueries()
                        .build();
            }
        }

        Log.d(LOG_TAG, "GETTING DATABASE INSTANCE");
        return sInstance;
    }

    public abstract RecipeDao recipeDao();
    public abstract IngredientsDao ingredientsDao();
    public abstract StepsDao stepsDao();
}

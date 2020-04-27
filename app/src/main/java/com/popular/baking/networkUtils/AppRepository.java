package com.popular.baking.networkUtils;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.popular.baking.dto.Ingredients;
import com.popular.baking.dto.Recipe;
import com.popular.baking.dto.RecipeStepsAndIngredients;
import com.popular.baking.dto.Steps;
import com.popular.baking.persistence.AppDatabase;
import com.popular.baking.persistence.AppExecutors;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AppRepository {
    private static AppRepository ourInstance;

    private static final String TAG = AppRepository.class.getSimpleName();

    private BakingApi mBakingApi;
    private Retrofit retrofit;
    private Call<List<Recipe>> call;
    private List<Recipe> mRecipe = new ArrayList<Recipe>();
    private List<Ingredients> mIngredientsList = new ArrayList<Ingredients>();
    private List<Steps> mStepsLists = new ArrayList<Steps>();
    private List<RecipeStepsAndIngredients> mIngredients = new ArrayList<RecipeStepsAndIngredients>();
    AppDatabase appDB;
    AppExecutors appExecutors;

    //Content Provider for widget
    Cursor mCursor;

    public static AppRepository getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new AppRepository(context.getApplicationContext());
        }

        return ourInstance;
    }

    private AppRepository(Context context) {
        retrofit = RetrofitClient.getClient();
        mBakingApi = retrofit.create(BakingApi.class);
        appDB = AppDatabase.getsInstance(context);
        appExecutors = AppExecutors.getInstance();
    }


    //** Retrofit Operations

    public void loadRecipesFromWeb() {
        //Parse with retofit
        call = mBakingApi.getRecipes();


        //Place in background thread
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(@NonNull Call<List<Recipe>> call,
                                   @NonNull Response<List<Recipe>> response) {
                Log.d(TAG, "onResponse: AppRepository success..");
                if (response.isSuccessful()) {

                    List<Recipe> recipeList = response.body();
                    mIngredientsList = new ArrayList<>();
                    mStepsLists = new ArrayList<>();

                    for (Recipe bList : recipeList) {
                        Log.i(TAG, "onResponse: " + bList.getName());


                        mIngredientsList.addAll(bList.getIngredients());
                        mStepsLists.addAll(bList.getSteps());
                        bList.setIngredientsAndStepId();
                    }

                    for (int i = 0; i < mIngredientsList.size(); i++) {
                        mIngredientsList.get(i).setId(i);
                    }

                    for (int i = 0; i < mStepsLists.size(); i++) {
                        mStepsLists.get(i).setId(i);
                        Log.i(TAG, "onResponse: Short Description .." + mStepsLists.get(i).getShortDescription());
                    }


                    mRecipe.addAll(recipeList);

                    //insert into database
                    insertRecipe();

                }

            }

            @Override
            public void onFailure(@NonNull Call<List<Recipe>> call, @NonNull Throwable t) {
                Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
            }
        });


    }


    //*** Database Operations

    //Retrieve Recipes
    //Room does back ground thread automatic, so do not call Executor
    public LiveData<List<Recipe>> getRecipes() {
        Log.d(TAG, "get recipes ");

        loadRecipesFromWeb();

        return appDB.recipeDao().getRecipes();

    }


    //Get Ingredients and Steps
    public LiveData<RecipeStepsAndIngredients> getRecipeStepsAndIngredients(int id) {

        Log.i(TAG, "getRecipeStepsAndIngredients: ");
        return appDB.recipeDao().getIngredientsAndSteps(id);

    }


    //Content Provider
    public List<RecipeStepsAndIngredients> getIngridentsForWidgets(int recipeId){
        Log.i(TAG, "getIngridentsForWidgets: ");

        appExecutors.mDbExecutor().execute(new Runnable() {
            @Override
            public void run() {
                mIngredients = appDB.recipeDao().getIngredientsAndStepsforWidget(recipeId);
            }
        });

        return mIngredients;
    }


    //Insert Recipe
    public void insertRecipe() {
        Log.d(TAG, "insertBakingList: ");
        appExecutors.mDbExecutor().execute(new Runnable() {
            @Override
            public void run() {
                appDB.recipeDao().insertRecipe(mRecipe);
                appDB.ingredientsDao().insertTask(mIngredientsList);
                appDB.stepsDao().insertTask(mStepsLists);
            }
        });

    }


}

package com.popular.baking.persistence;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.popular.baking.dto.Ingredients;
import com.popular.baking.dto.Steps;

import java.lang.reflect.Type;
import java.util.List;

import androidx.room.TypeConverter;

public class DataConverter {
    @TypeConverter
    public String fromIngriedentsList(List<Ingredients> ingredients){
        if(ingredients == null){
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Ingredients>>() {}.getType();
        String json = gson.toJson(ingredients, type);
        return json;
    }

    @TypeConverter
    public List<Ingredients> toIngredientsList(String ingredientsString){
        if(ingredientsString == null){
            return  null;
        }

        Gson gson = new Gson();
        Type type = new TypeToken<List<Ingredients>>() {}.getType();

        List<Ingredients> ingredientList = gson.fromJson(ingredientsString, type);
        return ingredientList;
    }


    @TypeConverter
    public String fromStepsList(List<Steps> steps){
        if(steps == null){
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Steps>>() {}.getType();
        String json = gson.toJson(steps, type);
        return json;
    }

    @TypeConverter
    public List<Steps> toStepsList(String stepsString){
        if(stepsString == null){
            return  null;
        }

        Gson gson = new Gson();
        Type type = new TypeToken<List<Steps>>() {}.getType();

        List<Steps> stepsList = gson.fromJson(stepsString, type);
        return stepsList;
    }
}

package com.popular.baking.dto;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Relation;

public class RecipeStepsAndIngredients {

    @Embedded
    public Recipe recipe;

    @Relation(
            parentColumn = "id",
            entityColumn = "recipeId",
            entity = Ingredients.class
            )
    public List<Ingredients> ingredients;


    @Relation(
            parentColumn = "id",
            entityColumn = "recipeId",
            entity = Steps.class
    )
    public List<Steps> steps;

    @NonNull
    @Override
    public String toString() {
        return "Name: "     +  recipe.getName() +
                "Serving:"    + recipe.getServings() +
                "Ingredients:" + ingredients.toString() +
                "Steps:" + steps.toString();

    }


    public String getFormatInfo() {
        StringBuilder sb = new StringBuilder();
        for(Ingredients ingList: ingredients) {
            sb.append(ingList.getQuantity());
            sb.append(" ");
            sb.append(ingList.getMeasure().toLowerCase());
            sb.append(" of ");
            sb.append(ingList.getIngredient());
            sb.append(".");
            sb.append("\n");
        }
        return sb.toString();
    }

}


package com.popular.baking.dto;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "recipe")
public class Recipe implements Parcelable {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private  Integer id;

    @SerializedName("name")
    @Expose
    private String name;

    @Ignore
    private List<Ingredients> ingredients;


    @Ignore
    private  List<Steps> steps;

    @SerializedName("servings")
    @Expose
    private String servings;

    @SerializedName("image")
    @Expose
    private  String image;



    public Recipe() {
    }

    @Ignore
    public Recipe(String name, List<Ingredients> ingredients, List<Steps> steps, String servings, String image) {
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
        this.image = image;
    }

    @Ignore
    public Recipe(Integer id, String name, List<Ingredients> ingredients, List<Steps> steps, String servings, String image) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Ingredients> getIngredients() {
        return ingredients;
    }

    public List<Steps> getSteps() {
        return steps;
    }

    public String getServings() {
        return servings;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIngredients(List<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }

    public void setSteps(List<Steps> steps) {
        this.steps = steps;
    }

    public String getImage() {
        return image;
    }

    public void setServings(String servings) {
        this.servings = servings;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setIngredientsAndStepId(){
        for(Steps step: steps){
            step.setRecipeId(id);
        }

        for(Ingredients ingred: ingredients){
            ingred.setRecipeId(id);
        }
    }



    protected Recipe(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.image =  ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.ingredients, (com.popular.baking.dto.Ingredients.class.getClassLoader()) );
        this.name =  ((String) in.readValue((String.class.getClassLoader())));
        this.servings =  ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.steps, (com.popular.baking.dto.Steps.class.getClassLoader()));
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeValue(this.id);
        dest.writeValue(this.image);
        dest.writeTypedList(this.ingredients);
        dest.writeValue(this.name);
        dest.writeValue(this.servings);
        dest.writeTypedList(this.steps);
    }
}

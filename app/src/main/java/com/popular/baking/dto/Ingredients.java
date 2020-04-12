package com.popular.baking.dto;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "Ingredients", foreignKeys = @ForeignKey(
        entity=Recipe.class,
        parentColumns = "id",
        childColumns = "recipeId",
        onDelete = CASCADE
        ),
        indices = @Index(value = "recipeId", name = "idx_ingredients_recipe_id")
)
public class Ingredients implements Parcelable {


    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("recipeId")
    @Expose
    private Integer recipeId;


    @SerializedName("quantity")
    @Expose
    private String quantity;

    @SerializedName("measure")
    @Expose
    private String measure;

    @SerializedName("ingredient")
    @Expose
    private String ingredient;


    public Ingredients() {
    }

    @Ignore
    public Ingredients(Integer id, Integer recipeId, String quantity, String measure, String ingredient) {
        this.id = id;
        this.recipeId = recipeId;
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    @Ignore
    public Ingredients(String quantity, String measure, String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    public Integer getId() {
        return id;
    }

    public Integer getRecipeId() {
        return recipeId;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setRecipeId(Integer recipeId) {
        this.recipeId = recipeId;
    }

    protected Ingredients(Parcel in) {
        this.ingredient = ((String) in.readValue((String.class.getClassLoader())));
        this.measure = ((String) in.readValue((String.class.getClassLoader())));
        this.ingredient = ((String) in.readValue((String.class.getClassLoader())));
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.ingredient);
        dest.writeValue(this.measure);
        dest.writeValue(this.quantity);
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(quantity);
        sb.append(" ");
        sb.append(measure.toLowerCase());
        sb.append(" of ");
        sb.append(ingredient);
        sb.append(".");

        return sb.toString();
    }

    public static final Creator<Ingredients> CREATOR = new Creator<Ingredients>() {
        @Override
        public Ingredients createFromParcel(Parcel in) {
            return new Ingredients(in);
        }

        @Override
        public Ingredients[] newArray(int size) {
            return new Ingredients[size];
        }
    };

}

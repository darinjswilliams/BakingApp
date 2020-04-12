package com.popular.baking.dto;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity (tableName = "Steps",
foreignKeys = @ForeignKey(
        entity=Recipe.class,
        parentColumns = "id",
        childColumns = "recipeId",
        onDelete = CASCADE

),
        indices = @Index(value = "recipeId", name = "idx_steps_recipe_id")
)
public class Steps implements Parcelable {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("recipeId")
    @Expose
    private Integer recipeId;

    @SerializedName("shortDescription")
    @Expose
    private String shortDescription;


    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("videoURL")
    @Expose
    private String videoURL;


    @SerializedName("thumbnailURL")
    @Expose
    private String thumbnailURL;


    public Steps() {
    }

    @Ignore
    public Steps(Integer id, String shortDescription, String description, String videoURL, String thumbnailURL) {
        this.id = id;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
    }

    @Ignore
    public Steps(Integer idd, Integer recipeId, String shortDescription, String description, String videoURL, String thumbnailURL) {
        this.id = id;
        this.recipeId = recipeId;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
    }

    public Integer getId() {
        return id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setRecipeId(Integer recipeId) {
        this.recipeId = recipeId;
    }

    public Integer getRecipeId() {
        return recipeId;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    protected Steps(Parcel in) {

        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.shortDescription = ((String) in.readValue((String.class.getClassLoader())));
        this.description = ((String) in.readValue((String.class.getClassLoader())));
        this.videoURL = ((String) in.readValue((String.class.getClassLoader())));
        this.thumbnailURL = ((String) in.readValue((String.class.getClassLoader())));    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.description);
        dest.writeValue(this.id);
        dest.writeValue(this.shortDescription);
        dest.writeValue(this.thumbnailURL);
        dest.writeValue(this.videoURL);
    }

    public static final Creator<Steps> CREATOR = new Creator<Steps>() {
        @Override
        public Steps createFromParcel(Parcel in) {
            return new Steps(in);
        }

        @Override
        public Steps[] newArray(int size) {
            return new Steps[size];
        }
    };
}

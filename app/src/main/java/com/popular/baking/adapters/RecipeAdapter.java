package com.popular.baking.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.popular.baking.R;
import com.popular.baking.constants.Constants;
import com.popular.baking.dto.Recipe;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.MyRecipeHolder> {


    private List<Recipe> mRecipes = new ArrayList<>();
    private final static String TAG = RecipeAdapter.class.getSimpleName();
    private OnItemClickListener onItemClickListener;

    public RecipeAdapter(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public RecipeAdapter() {

    }

    public interface OnItemClickListener {
        void onClick(Recipe recipe);
    }


    @NonNull
    @Override
    public MyRecipeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_item_list, parent, false);


        return new MyRecipeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecipeHolder holder, int position) {

        Recipe recipe = mRecipes.get(position);
//        holder.mTextView.setText(recipe.getName());
    holder.bind(recipe, this);
    }

    @Override
    public int getItemCount() {

        //Check for size of zero
        if (mRecipes != null) {
            return mRecipes.size();
        }
        return Constants.EMPTY_RECIPE_LIST;
    }

    public void setRecipes(List<Recipe> recipes) {
        Log.d(TAG, "setRecipes: " + recipes.size());
        this.mRecipes = recipes;
        notifyDataSetChanged();

    }

    public class MyRecipeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mTextView;

        public MyRecipeHolder(@NonNull View itemView) {
            super(itemView);
            this.mTextView = itemView.findViewById(R.id.my_recipe_name);

        }

        @Override
        public void onClick(View v) {
            int index = getLayoutPosition();
            Recipe recipe = mRecipes.get(index);
            //todo set click handerl
            Log.d(TAG, "onClick: " + recipe.getName());
        }


        public void bind(Recipe recipe, RecipeAdapter mListener) {

            Log.d(TAG, "bind: " + recipe.getName());
            mTextView.setText(recipe.getName());

            mTextView.setOnClickListener(v -> mListener.onItemClickListener.onClick(recipe));
        }


    }
}

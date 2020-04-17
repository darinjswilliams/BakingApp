package com.popular.baking.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.popular.baking.R;
import com.popular.baking.adapters.RecipeAdapter;
import com.popular.baking.constants.Constants;
import com.popular.baking.databinding.RecipeFragmentBinding;
import com.popular.baking.dto.Recipe;
import com.popular.baking.networkUtils.LifeCycleEventManager;
import com.popular.baking.view.MainActivity;
import com.popular.baking.viewmodel.RecipeViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecipeFragment extends Fragment implements RecipeAdapter.OnItemClickListener {

    private RecipeViewModel mViewModel;
    private RecyclerView mRecyclerView;
    private RelativeLayout mRelativeLayout;
    private RecipeAdapter mRecipeAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private RecipeViewModel mRecipeViewModel;
    private LinearLayoutManager linearLayoutManager;
    private RecipeFragmentBinding recipeFragmentBinding;


    public static final String TAG = RecipeFragment.class.getSimpleName();

    //Required empty constructor
    public RecipeFragment() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.recipe_fragment, container, false);

        mRecyclerView = rootView.findViewById(R.id.my_recycler_view);

        initRecycleView(rootView.getContext());

        mRecipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);


        mRecipeViewModel.getRecipe().observe(getViewLifecycleOwner(), recipes -> {
            Log.d(TAG, "onCreateView: RecipeFragment.." + recipes.size());
            mRecipeAdapter.setRecipes(recipes);
        });

        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getActivity().setTitle(Constants.BAKING);
        }

        new LifeCycleEventManager(TAG).registerLifeCycleEvent(getLifecycle());
        return rootView;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    private void initRecycleView(Context context) {


//        layoutManager = new LinearLayoutManager(context);

        //Todo check for table and get correct layout

        if (getActivity() instanceof MainActivity) {

            //Check to see if Tablet, if true than set up grid
            if (((MainActivity) getActivity()).mTabletPane) {

                Log.d(TAG, "initRecycleView: I AM A TABLET");
                linearLayoutManager = new GridLayoutManager(context, 3, RecyclerView.VERTICAL,
                        false);
            } else {

                linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL,
                        false);

            }

        }

        mRecipeAdapter = new RecipeAdapter(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mRecipeAdapter);


    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Constants.BAKING, RecipeFragment.TAG);
    }

    @Override
    public void onClick(Recipe recipe) {
        //Todo go to Fragment details
        Log.d(TAG, "onClick: CODE FRAGEMENT DETAILS");
        Bundle arguments = new Bundle();

        Log.d(TAG, "onClick: Name.." + recipe.getName());
        Log.d(TAG, "onClick: id.." + recipe.getId());
        Log.d(TAG, "onClick: Serving.." + recipe.getServings());

        arguments.putInt(Constants.TAG_DETAILS_FRAGMENT_KEY, recipe.getId());
        RecipeDetailsFragment fragment = new RecipeDetailsFragment();

        //Pass bundle to fragment
        fragment.setArguments(arguments);

        getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_containier, fragment, RecipeFragment.TAG)
                .addToBackStack(null)
                .commit();
    }
}

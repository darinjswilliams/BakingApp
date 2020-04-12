package com.popular.baking.fragments;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.popular.baking.R;
import com.popular.baking.adapters.RecipeDetailsAdapter;
import com.popular.baking.constants.Constants;
import com.popular.baking.dto.RecipeStepsAndIngredients;
import com.popular.baking.networkUtils.LifeCycleEventManager;
import com.popular.baking.view.MainActivity;
import com.popular.baking.viewmodel.RecipeDetailsViewModel;
import com.popular.baking.viewmodel.RecipeDetailsViewModelFactory;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecipeDetailsFragment extends Fragment implements RecipeDetailsAdapter.OnClickDetailListener {

    public static final String TAG = RecipeDetailsFragment.class.getSimpleName();
    private RecipeDetailsViewModel mViewModel;
    private RecyclerView mRecylcerView;
    private RecipeDetailsAdapter mRecipeDetailsAdapter;
    private MainActivity mHostActivty;
//    private RecipeDetailsFragmentBinding recipeDetailsFragmentBinding;
    public int recipeId;

    //Binding

    public RecipeDetailsFragment() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater,container, savedInstanceState);

        final View view = inflater.inflate(R.layout.recipe_details_fragment, container, false);
//        recipeDetailsFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.recipe_details_fragment, container, false);

        //Check Bundle for Arguments
        Bundle arguments = getArguments();


        if (arguments != null) {

            recipeId = arguments.getInt(Constants.TAG_DETAILS_FRAGMENT_KEY, -1);

            //lets check state
            if (savedInstanceState != null && recipeId == -1) {
                recipeId = savedInstanceState.getInt(Constants.TAG_DETAILS_FRAGMENT_KEY);
            }

            Log.i(TAG, "onCreateView: ID....." + recipeId);
        }

        //toolbar
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //bind recylcer view
        bindRecylcerView(view);


        RecipeDetailsViewModelFactory rdvmFactory = new RecipeDetailsViewModelFactory(
                (Application) this.getContext().getApplicationContext(), recipeId);


        RecipeDetailsViewModel recipeDetailsViewModel = new ViewModelProvider(this, rdvmFactory).get(RecipeDetailsViewModel.class);
        recipeDetailsViewModel.getRecipeIngredientAndSteps().observe(getViewLifecycleOwner(), recipeStepsAndIngredients -> {
            mRecipeDetailsAdapter.setRecipeStepsAndIngredients(recipeStepsAndIngredients);
            getActivity().setTitle(recipeStepsAndIngredients.recipe.getName());
        });



//        return recipeDetailsFragmentBinding.getRoot().getRootView();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //let keep track of lifecycle
        new LifeCycleEventManager(TAG).registerLifeCycleEvent(getLifecycle());
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Constants.SAVED_RECIPE_ID, recipeId);
    }

    private void bindRecylcerView(View view) {
//        mRecylcerView = recipeDetailsFragmentBinding.getRoot().getRootView().findViewById(R.id.detail_fragment);
        mRecylcerView = view.findViewById(R.id.detail_fragment);
        mRecipeDetailsAdapter = new RecipeDetailsAdapter(this);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recipeDetailsFragmentBinding.getRoot().getContext(),
//                RecyclerView.VERTICAL, false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(),
                RecyclerView.VERTICAL, false);

        mRecylcerView.setLayoutManager(linearLayoutManager);
        mRecylcerView.setAdapter(mRecipeDetailsAdapter);
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(RecipeDetailsViewModel.class);
//        // TODO: Use the ViewModel
//    }

    @Override
    public void clickRecipeDetails(RecipeStepsAndIngredients recipeStepsAndIngredients, int position) {
        RecipeDetailsFragment fragment = new RecipeDetailsFragment();
        getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_containier, fragment, RecipeDetailsFragment.TAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void clickRecipeDetails(String recipeDetails, String nameOfRecipe) {
        RecipeDetailsFragment fragment = new RecipeDetailsFragment();

        getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_details_containier, fragment, RecipeDetailsFragment.TAG)
                .addToBackStack(null)
                .commit();

    }

}

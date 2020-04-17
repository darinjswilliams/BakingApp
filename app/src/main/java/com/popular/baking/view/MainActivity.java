package com.popular.baking.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.popular.baking.R;
import com.popular.baking.constants.Constants;
import com.popular.baking.dto.Recipe;
import com.popular.baking.fragments.RecipeFragment;
import com.popular.baking.networkUtils.AppRepository;
import com.popular.baking.networkUtils.LifeCycleEventManager;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private ActionBarDrawerToggle toggle;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FragmentManager fragmentManager;
    private AppRepository appRepository;
    private List<Recipe> mRecipe = new ArrayList<>();
    public boolean mTabletPane;
    public FrameLayout paneOne;
    public FrameLayout paneTwo;
    public View divider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragmentManager = getSupportFragmentManager();

//        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setContentView(R.layout.activity_main);


        //Check Pane Layout to determine if it is a tablet or phone
        //the two Pane layout does not exist on phone
        if((findViewById(R.id.recipe_linear_layout) != null)){

            Log.d(TAG, "onCreate: I AM A TABLET");
            mTabletPane = true;
            divider = findViewById(R.id.fragment_divider);
            paneOne = findViewById(R.id.fragment_containier);
            paneTwo = findViewById(R.id.fragment_details_containier);
            ViewGroup.LayoutParams  layoutParams = paneOne.getLayoutParams();
            layoutParams.width = MATCH_PARENT;

            divider.setVisibility(View.GONE);
            paneTwo.setVisibility(View.GONE);

        } else {
            mTabletPane = false;

        }

        setTitle("Recipes");

        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        RecipeFragment recipeFragment = new RecipeFragment();

        fragmentManager.beginTransaction()
                .add(R.id.fragment_containier, recipeFragment, RecipeFragment.TAG)
                .addToBackStack(null)
                .commit();

        new LifeCycleEventManager(TAG).registerLifeCycleEvent(getLifecycle());

    }

    @Override
    public void onBackPressed() {

        if (fragmentManager.getBackStackEntryCount() == Constants.STACKCOUNT) {
            finish();
        } else if (fragmentManager.getBackStackEntryCount() > Constants.STACKCOUNT) {

            if (fragmentManager.getBackStackEntryCount() == Constants.STACK_ITEMS) {
                setTitle("Recipe Details");
            }
            fragmentManager.popBackStack();

        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        fragmentManager.popBackStack();
        return true;
    }

    private class MyOnClickListener implements View.OnClickListener {

        private final Context context;

        public MyOnClickListener(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View v) {
            Log.i(TAG, "onClick: RecipeAcitivity");
        }
    }
}

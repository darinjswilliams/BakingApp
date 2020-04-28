package com.popular.baking.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.popular.baking.R;
import com.popular.baking.constants.Constants;
import com.popular.baking.databinding.ActivityMainBinding;
import com.popular.baking.dto.Recipe;
import com.popular.baking.fragments.RecipeFragment;
import com.popular.baking.networkUtils.AppRepository;
import com.popular.baking.networkUtils.LifeCycleEventManager;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private ActionBarDrawerToggle toggle;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FragmentManager fragmentManager;
    private AppRepository appRepository;
    private List<Recipe> mRecipe = new ArrayList<>();
    public static boolean mTabletPane;
    public FrameLayout paneOne;
    public FrameLayout paneTwo;
    public View divider;
    public ActivityMainBinding mBinding;
    private boolean mVisiable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragmentManager = getSupportFragmentManager();

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setContentView(R.layout.activity_main);

        //Check Pane Layout to determine if it is a tablet or phone

        if((findViewById(R.id.recipe_linear_layout) != null)){

            Log.d(TAG, "onCreate: I AM A TABLET");
            mTabletPane = true;
            divider = findViewById(R.id.fragment_divider);
            paneOne =  mBinding.fragmentContainier;
            paneTwo =  mBinding.fragmentDetailsContainier;
;

        } else {
            mTabletPane = false;

        }

        setTitle("Recipes");

        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        if (savedInstanceState == null) {
            RecipeFragment recipeFragment = new RecipeFragment();
            addFragment(recipeFragment, RecipeFragment.TAG);
        } else {
            mVisiable = savedInstanceState.getBoolean(Constants.MAKE_DETAIL_VISIBLE);
            togglePane(mVisiable);
        }

        new LifeCycleEventManager(TAG).registerLifeCycleEvent(getLifecycle());

    }

    @Override
    public void onBackPressed() {

        if (fragmentManager.getBackStackEntryCount() == Constants.STACKCOUNT) {
            finish();
        } else {
            if(mVisiable) togglePane( mVisiable );
            fragmentManager.popBackStackImmediate();
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

    private void addFragment(Fragment frag, String fragTag){
        if(mTabletPane){
            togglePane(mTabletPane);
            launchFragment(frag, fragTag, false, R.id.fragment_details_containier);
            return;
        }

        togglePane(mTabletPane);
        launchFragment(frag, fragTag, !mTabletPane, R.id.fragment_containier);
    }

    private void togglePane(boolean viewSecondPane){
        mVisiable = viewSecondPane;
        if(mTabletPane){
            paneTwo.setVisibility((viewSecondPane ? View.VISIBLE: View.GONE));
            divider.setVisibility(viewSecondPane ? View.VISIBLE : View.GONE);
        }
    }

    private void launchFragment(Fragment fragment, String tag, boolean addToBackStack, int containier){
        if(addToBackStack){
            fragmentManager.beginTransaction()
                    .replace(containier, fragment, RecipeFragment.TAG)
                    .addToBackStack(tag)
                    .commit();
        }else{
            fragmentManager.beginTransaction()
                    .replace(containier, fragment, tag)
                    .commit();
        }
    }
}

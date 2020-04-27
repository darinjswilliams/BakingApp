package com.popular.baking.main;

import com.popular.baking.Assertions.RecyclerViewAssertions;
import com.popular.baking.R;
import com.popular.baking.view.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    public static final String MYRecipe = "Cheesecake";
    public static final int mRecipeCount = 4;
    public static final int ITEM_AT_POSITION = 1;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);


    @Test
    public void clickRecipeItem() {
        //find the view
        //Perform an action on the view
        //check the assertion results is expected output
        onView(ViewMatchers.withId (R.id.my_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(ITEM_AT_POSITION, click()));

        String itemElement = getApplicationContext().getResources().getString(R.id.my_recipe_name)
                + String.valueOf(ITEM_AT_POSITION);

        onView(withText(itemElement)).check(matches(isDisplayed()));
    }

    @Test
    public void countNumberOfCardViews(){

        onView(withId(R.id.my_recycler_view)).check(new RecyclerViewAssertions(mRecipeCount));

    }
}

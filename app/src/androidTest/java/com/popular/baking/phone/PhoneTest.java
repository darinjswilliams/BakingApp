package com.popular.baking.phone;

import com.popular.baking.R;
import com.popular.baking.dto.RecipeStepsAndIngredients;
import com.popular.baking.view.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class PhoneTest {

    private RecipeStepsAndIngredients recipeStepsAndIngredients;
    private int firtsNumber;


    private int phoneTest;


    @Rule
   public ActivityTestRule<MainActivity> activitityTestRule =
            new ActivityTestRule<MainActivity>(MainActivity.class);

   @Before
    public void init(){
       activitityTestRule.getActivity().getSupportFragmentManager().beginTransaction();
   }

   @Test
   //lets check for yellow cake
   public void recipeFragmentTest(){
       onView(ViewMatchers.withId(R.id.my_recycler_view))
               .perform(RecyclerViewActions.scrollToPosition(1));

       String middleElement = activitityTestRule.getActivity().getResources()
               .getString(R.string.reicpe_list);
    onView(withText(middleElement)).check(matches(isDisplayed()));
   }
}

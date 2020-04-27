package com.popular.baking.GeneralTestSuites;

import com.popular.baking.main.MainActivityTest;
import com.popular.baking.phone.PhoneTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Suite that contains all test that must run correctly
 * both on phones and tablets
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({
        MainActivityTest.class,
        PhoneTest.class
})

public class GeneralTestSuites {
    // the class remains empty,
    // used only as a holder for the above annotations
}

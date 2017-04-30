package com.example.ryan.qwiktix;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static android.app.PendingIntent.getActivity;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
/**
 * Created by lukeb on 3/5/2017.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginTest {

    //@Rule
    //public ActivityTestRule mBaseRule = new ActivityTestRule<>(
    //        BaseActivity.class
    //);
    @Rule
    public ActivityTestRule mLoginRule = new ActivityTestRule<>(
           LoginActivity.class);
    @Rule
    public ActivityTestRule mHomePageRule = new ActivityTestRule<>(
            HomePageActivity.class);
    @Rule
    public ActivityTestRule mProfileRule = new ActivityTestRule<>(
            ProfileActivity.class);

    @Test
    public void testIncorrectLogin() throws Exception{



        //Context appContext = InstrumentationRegistry.getTargetContext();

        Espresso.onView(ViewMatchers.withId(R.id.lEmail))
                .perform(ViewActions.typeText("not-registered@uiowa.edu"));
        Espresso.onView(ViewMatchers.withId(R.id.lPassword))
                .perform(ViewActions.typeText("password"));
        Espresso.onView(ViewMatchers.withId(R.id.lLoginButton))
                .perform(ViewActions.click());
        //intended(hasComponent(HomePageActivity.class.getName()));
        Thread.sleep(1000);
        // make sure it doesn't go to home page after failed login
        Espresso.onView(ViewMatchers.withId(R.id.lEmail))
                .check(ViewAssertions.matches(isDisplayed()));

    }
    @Test
    public void testIncorrectPassword() throws Exception{



        //Context appContext = InstrumentationRegistry.getTargetContext();

        Espresso.onView(ViewMatchers.withId(R.id.lEmail))
                .perform(ViewActions.typeText("lucas-bombal@uiowa.edu"));
        Espresso.onView(ViewMatchers.withId(R.id.lPassword))
                .perform(ViewActions.typeText("asdf"));
        Espresso.onView(ViewMatchers.withId(R.id.lLoginButton))
                .perform(ViewActions.click());
        //intended(hasComponent(HomePageActivity.class.getName()));
        Thread.sleep(1000);
        // make sure it doesn't go to home page after failed login
        Espresso.onView(ViewMatchers.withId(R.id.lEmail))
                .check(ViewAssertions.matches(isDisplayed()));

    }
    @Test
    public void testRightLogin() throws Exception{



        //Context appContext = InstrumentationRegistry.getTargetContext();

        Espresso.onView(ViewMatchers.withId(R.id.lEmail))
                .perform(ViewActions.typeText("lucas-bombal@uiowa.edu"));
        Espresso.onView(ViewMatchers.withId(R.id.lPassword))
                .perform(ViewActions.typeText("password"));
        Espresso.onView(ViewMatchers.withId(R.id.lLoginButton))
                .perform(ViewActions.click());
        //intended(hasComponent(HomePageActivity.class.getName()));
        Thread.sleep(1000);
        Espresso.onView(ViewMatchers.withId(R.id.homeList))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));



    }

}

package com.example.ryan.qwiktix;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.app.PendingIntent.getActivity;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

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


    @Test
    public void testLogin() throws Exception{



        //Context appContext = InstrumentationRegistry.getTargetContext();

        onView(withId(R.id.lEmail))
                .perform(typeText("lucas-bombal@uiowa.edu"));
        onView(withId(R.id.lPassword))
                .perform(typeText("password"));
        onView(withId(R.id.lLoginButton))
                .perform(click());
        //intended(hasComponent(HomePageActivity.class.getName()));
        Thread.sleep(1000);
        onView(withId(R.id.homeList))
                .check(matches(isDisplayed()));


    }
}

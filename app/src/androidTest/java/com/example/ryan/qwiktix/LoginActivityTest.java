package com.example.ryan.qwiktix;


import android.content.ComponentName;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;



import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by mayowa.adegeye on 01/06/2016.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginActivityTest {
    @Rule
    public ActivityTestRule<LoginActivity> mLoginActivityTestRule =
            new ActivityTestRule<LoginActivity>(LoginActivity.class);


    @Rule
    public ActivityTestRule mHomePageRule = new ActivityTestRule<>(
            HomePageActivity.class);


    @Test
    public void clickLoginButton_showsSuccessScreenAfterLogin() throws InterruptedException {
        String email = "ryan-kuemmel@uiowa.edu";
        String password = "password";

        //type in email
        onView(withId(R.id.lEmail)).perform(typeText(email), closeSoftKeyboard());

        //type in password
        onView(withId(R.id.lPassword)).perform(typeText(password), closeSoftKeyboard());

        //click on login button
        onView(withId(R.id.lLoginButton)).perform(click());

        //verify that success screen shows
        //String successString = "onAuthStateChanged:signed_in:";
        //onView(withText(successString)).check(matches(isDisplayed()));
        Thread.sleep(1000);
        onView(withId(R.id.homeList))
                .check(matches(isDisplayed()));


    }
}
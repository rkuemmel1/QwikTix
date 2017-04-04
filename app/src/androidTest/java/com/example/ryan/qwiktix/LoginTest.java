package com.example.ryan.qwiktix;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
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
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

/**
 * Created by lukeb on 3/5/2017.
 */
@RunWith(AndroidJUnit4.class)
public class LoginTest {
    @Rule
    public ActivityTestRule<ChatActivity> mActivityRule = new ActivityTestRule<>(
            ChatActivity.class);
    @Test
    public void useAppContext() throws Exception{

        Context appContext = InstrumentationRegistry.getTargetContext();

        onView(withId(R.id.lEmail))
                .perform(typeText("lucas-bombal@uiowa.edu"));
        onView(withId(R.id.lPassword))
                .perform(typeText("password"));
        onView(withId(R.id.lLoginButton))
                .perform(click());
        //intended(hasComponent(YourExpectedActivity.class.getName()));
        onView(withId(R.id.homeList))
                .check(matches(isDisplayed()));

        //onView(withId(R.id.lLoginButton))      //ViewMatcher
        //        .perform(click())               // click() is a ViewAction
        //        .check(matches(isDisplayed())); // matches(isDisplayed()) is a ViewAssertion
    }
}

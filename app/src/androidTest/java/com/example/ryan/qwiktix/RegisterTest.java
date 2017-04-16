package com.example.ryan.qwiktix;

import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class RegisterTest {
    @Rule
    public ActivityTestRule mSignUpActivityTestRule =
            new ActivityTestRule(RegisterActivity.class);


    @Rule
    public ActivityTestRule mLoginRule = new ActivityTestRule<>(
            LoginActivity.class);



    @Test
    public void clickSignUpButtonAfterFillingForm_showProgressAndSuccessScreen() throws InterruptedException {
        String first_name = "Ryan";
        String last_name = "Kuemmel";
        String emailAddress = "ryan-kuemmel@uiowa.edu";
        String password = "password";


        onView(withId(R.id.lRegisterLink)).perform((click()));

        //find the firstname edit text and type in the first name
        onView(withId(R.id.rFirstName)).perform(typeText(first_name), closeSoftKeyboard());

        //find the lastname edit text and type in the last name
        onView(withId(R.id.rLastName)).perform(typeText(last_name), closeSoftKeyboard());

        //find the email address edit text and type in the email address
        onView(withId(R.id.rEmail)).perform(typeText(emailAddress), closeSoftKeyboard());

        //find the password edit text and type in the password
        onView(withId(R.id.rPassword)).perform(typeText(password), closeSoftKeyboard());

        //find the password confirm text and type in the password
        onView(withId(R.id.rPasswordConfirm)).perform(typeText(password), closeSoftKeyboard());

        //click the signup button
        onView(withId(R.id.rSubmitButton)).perform(click());

        Thread.sleep(1000);
        onView(withId(R.id.lEmail))
                .check(matches(isDisplayed()));
    }
}
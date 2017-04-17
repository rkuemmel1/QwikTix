package com.example.ryan.qwiktix;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.filters.SmallTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase;
import android.test.ApplicationTestCase;
import android.test.InstrumentationTestCase;
import android.util.Log;
import android.widget.Toast;

import com.fasterxml.jackson.databind.deser.Deserializers;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import static android.content.ContentValues.TAG;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by brade_000 on 4/14/2017.
 */

@RunWith(AndroidJUnit4.class)

public class EditProfileActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> mEditProfileRule = new ActivityTestRule<>(LoginActivity.class);


    private IdlingResource mIdlingResource;

    @Before
    public void registerIdlingResource(){
        mIdlingResource = new SimpleIdlingResource();
        Espresso.registerIdlingResources(mIdlingResource);
    }

    @Before
    public void navigateToEditProfile()throws Exception{

        String username = "bradley-a-evans@uiowa.edu";
        String password = "abc123";

        logOutIfPossible();

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.lEmail),
                        withParent(allOf(withId(R.id.activity_login),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatEditText.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.lEmail),
                        withParent(allOf(withId(R.id.activity_login),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText(username), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.lPassword),
                        withParent(allOf(withId(R.id.activity_login),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText(password), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.lLoginButton), withText("Login"),
                        withParent(allOf(withId(R.id.activity_login),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton.perform(click());

        Thread.sleep(1000);

        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.action_profile), isDisplayed()));

        bottomNavigationItemView.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.pEditProfile), withText("Edit Profile"),
                        withParent(allOf(withId(R.id.activity_profile),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton2.perform(click());
    }
    @Test
    public void checkCorrectUserInfo() throws Exception{


        ViewInteraction appCompatEdiText4 = onView(
                allOf(withId(R.id.editFirstName),
                        withParent(allOf(withId(R.id.activity_edit_profile),
                                withParent(allOf(withId(R.id.my_scrollview),
                                        withParent(allOf(withId(android.R.id.content))))))),
                        isDisplayed()));
        appCompatEdiText4.check(matches(withText("brad")));


        ViewInteraction appCompatEdiText5 = onView(
                allOf(withId(R.id.editLastName),
                        withParent(allOf(withId(R.id.activity_edit_profile),
                                withParent(allOf(withId(R.id.my_scrollview),
                                        withParent(allOf(withId(android.R.id.content))))))),
                        isDisplayed()));
        appCompatEdiText5.check(matches(withText("evans")));

        ViewInteraction appCompatEdiText6 = onView(
                allOf(withId(R.id.editPayPalEmail),
                        withParent(allOf(withId(R.id.activity_edit_profile),
                                withParent(allOf(withId(R.id.my_scrollview),
                                        withParent(allOf(withId(android.R.id.content))))))),
                        isDisplayed()));
        appCompatEdiText6.check(matches(withText("no@paypal.com")));

    }

    public void logOutIfPossible() throws Exception{

        Thread.sleep(500);
        try {

            ViewInteraction bottomNavigationItemView = onView(
                    allOf(withId(R.id.action_profile), isDisplayed()));

            bottomNavigationItemView.perform(click());

            ViewInteraction appCompatButton = onView(
                    allOf(withId(R.id.pLogOut), withText("Log Out"),
                        withParent(allOf(withId(R.id.activity_profile),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
            appCompatButton.perform(click());
        }
        catch(NoMatchingViewException e){
            Log.d("in test", "exception");
        }
    }


    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }

}

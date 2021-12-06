package es.unex.giiis.koreku;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openContextualActionModeOverflowMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.widget.DatePicker;

import androidx.test.espresso.contrib.PickerActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.*;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class UIConsolesTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void shouldAddConsoleToRecyclerView(){
        onView(withId(R.id.navigation_consoles)).perform(click());
        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.title)).perform(typeText("tituloConsola"), closeSoftKeyboard());
        onView(withId(R.id.desc)).perform(typeText("companyConsola"), closeSoftKeyboard());
        onView(withId(R.id.date_picker_button)).perform(click()).perform(PickerActions.setDate(2017, 6, 30));

        // Perform a click() action on R.id.submitButton
        onView(withId(R.id.submitButton)).perform(click());
/*
        // Check that R.id.my_recycler_view hasDescendant withId R.id.titleView
        onView(withId(R.id.my_recycler_view)).check(matches(hasDescendant(withId(R.id.titleView))));
        // Check that R.id.my_recycler_view hasDescendant with the input text
        onView(withId(R.id.my_recycler_view)).check(matches(hasDescendant(withText(testingString))));
        // Check that R.id.my_recycler_view hasDescendant withId R.id.statusCheckBox
        onView(withId(R.id.my_recycler_view)).check(matches(hasDescendant(withId(R.id.statusCheckBox))));*/
    }

    @After
    public void deleteElements(){
        // Open Contextual Action Mode Overflow Menu (abrir menu de 3 puntos)
        /*openContextualActionModeOverflowMenu();
        // Perform a click() action on the view withText "Delete all" (Should be a R.string.* reference)
        onView(withText(R.string.delete_consoles)).perform(click());*/
    }
}
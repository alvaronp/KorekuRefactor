package es.unex.giiis.koreku;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onIdle;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openContextualActionModeOverflowMenu;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressBack;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.widget.DatePicker;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.*;
import org.junit.After;
import org.junit.Before;
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
    public ActivityTestRule<MainActivity> mActivityRule=new ActivityTestRule<>(MainActivity.class,true,true);
    private IdlingResource mIdlingResource;
    private MainActivity mMainActivity = null;

    @Before
    public void initTest(){
        mMainActivity = mActivityRule.getActivity();
        onView(withId(R.id.navigation_consoles)).perform(click());
        mIdlingResource = mActivityRule.getActivity().getIdlingResource();
    }

    @Test
    public void shouldAddConsoleToRecyclerView(){
        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.title)).perform(typeText("tituloConsola"), closeSoftKeyboard());
        onView(withId(R.id.desc)).perform(typeText("companyConsola"), closeSoftKeyboard());
        onView(withId(R.id.date_picker_button)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2017, 8, 17));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.submitButton)).perform(click());

        onView(withId(R.id.my_recycler_view)).check(matches(hasDescendant(withId(R.id.titleView))));
        // Check that R.id.my_recycler_view hasDescendant with the input text
        onView(withId(R.id.my_recycler_view)).check(matches(hasDescendant(withText("tituloConsola"))));
    }

    @Test
    public void shouldEditConsoleOfRecyclerView(){
        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.title)).perform(typeText("tituloConsola"), closeSoftKeyboard());
        onView(withId(R.id.desc)).perform(typeText("companyConsola"), closeSoftKeyboard());
        onView(withId(R.id.date_picker_button)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2017, 8, 17));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.submitButton)).perform(click());

        onView(withId(R.id.my_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.Edit)).perform(click());
        onView(withId(R.id.title)).perform(clearText())
                                  .perform(click())
                                  .perform(typeText("cambio el titulo"), closeSoftKeyboard());
        onView(withId(R.id.submitButton)).perform(click());
        onView(withId(R.id.navigation_consoles)).perform(click());
    }

    @Test
    public void shouldDeleteConsoleOfRecyclerView() {
        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.title)).perform(typeText("tituloConsola"), closeSoftKeyboard());
        onView(withId(R.id.desc)).perform(typeText("companyConsola"), closeSoftKeyboard());
        onView(withId(R.id.date_picker_button)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2017, 8, 17));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.submitButton)).perform(click());
        onView(withId(R.id.my_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.delete_console)).perform(click());
        onView(withId(R.id.navigation_consoles)).perform(click());
    }

    @Test
    public void shouldGetAPIInfoOnConsoles() throws InterruptedException {
        IdlingRegistry.getInstance().register(mIdlingResource);
        onView(withId(R.id.apiFab)).perform(click());
        onView(withId(R.id.searchBox)).perform(typeText("Wii"), closeSoftKeyboard());
        onView(withId(R.id.searchButton)).perform(click());
        Thread.sleep(3000);
        Espresso.pressBack();
        onView(withId(R.id.navigation_consoles)).perform(click());
    }

    @Test
    public void shouldOrderConsolesByBuyDate(){
        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.title)).perform(typeText("tituloConsola1"), closeSoftKeyboard());
        onView(withId(R.id.desc)).perform(typeText("companyConsola"), closeSoftKeyboard());
        onView(withId(R.id.date_picker_button)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2017, 8, 17));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.submitButton)).perform(click());

        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.title)).perform(typeText("tituloConsola2"), closeSoftKeyboard());
        onView(withId(R.id.desc)).perform(typeText("companyConsola"), closeSoftKeyboard());
        onView(withId(R.id.date_picker_button)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2018, 8, 17));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.submitButton)).perform(click());

        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.title)).perform(typeText("tituloConsola3"), closeSoftKeyboard());
        onView(withId(R.id.desc)).perform(typeText("companyConsola"), closeSoftKeyboard());
        onView(withId(R.id.date_picker_button)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2016, 8, 17));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.submitButton)).perform(click());

        openContextualActionModeOverflowMenu();
        onView(withText(R.string.list_by_date)).perform(click());
        onView(withId(R.id.my_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.titleGameDetail)).check(matches(hasDescendant(withText("Segundo"))));
        Espresso.pressBack();
    }

    @After
    public void deleteElements() {
        openContextualActionModeOverflowMenu();
        onView(withText(R.string.delete_consoles)).perform(click());
    }
}
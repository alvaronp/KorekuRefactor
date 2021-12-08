package es.unex.giiis.koreku;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openContextualActionModeOverflowMenu;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Context;
import android.widget.DatePicker;

import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class UIGamesTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);


    @Before
    public void initTest() {
        onView(withId(R.id.navigation_games)).perform(click());
    }
    @Test
    public void shouldAddGameToRecyclerView(){
        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.title)).perform(typeText("Test Games"), closeSoftKeyboard());
        onView(withId(R.id.desc)).perform(typeText("Test Games Desc"), closeSoftKeyboard());
        onView(withId(R.id.genero_edit)).perform(typeText("Test Games Genre"), closeSoftKeyboard());
        onView(withId(R.id.date_picker_button)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2017, 8, 17));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.submitButton)).perform(click());

        onView(withId(R.id.my_recycler_view_game)).check(matches(hasDescendant(withId(R.id.titleView))));
        onView(withId(R.id.my_recycler_view_game)).check(matches(hasDescendant(withText("Test Games"))));
    }


    @Test
    public void shouldEditGameOnRecyclerView(){
        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.title)).perform(typeText("Test Games"), closeSoftKeyboard());
        onView(withId(R.id.desc)).perform(typeText("Test Games Desc"), closeSoftKeyboard());
        onView(withId(R.id.genero_edit)).perform(typeText("Test Games Genre"), closeSoftKeyboard());
        onView(withId(R.id.date_picker_button)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2017, 8, 17));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.submitButton)).perform(click());

        onView(withId(R.id.my_recycler_view_game)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.update_game)).perform(click());
        onView(withId(R.id.title)).perform(clearText())
                .perform(click())
                .perform(typeText("cambio el titulo"), closeSoftKeyboard());
        onView(withId(R.id.submitButton)).perform(click());
        onView(withId(R.id.navigation_games)).perform(click());
    }

    @Test
    public void shouldDeleteGameOnRecyclerView(){
        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.title)).perform(typeText("Test Games"), closeSoftKeyboard());
        onView(withId(R.id.desc)).perform(typeText("Test Games Desc"), closeSoftKeyboard());
        onView(withId(R.id.genero_edit)).perform(typeText("Test Games Genre"), closeSoftKeyboard());
        onView(withId(R.id.date_picker_button)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2017, 8, 17));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.submitButton)).perform(click());

        onView(withId(R.id.my_recycler_view_game)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.delete_game)).perform(click());
        onView(withId(R.id.navigation_games)).perform(click());
    }

    @Test
    public void shouldOrderByDate(){

        //segundo en la lista
        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.title)).perform(typeText("Test Games 2"), closeSoftKeyboard());
        onView(withId(R.id.desc)).perform(typeText("Test Games Desc"), closeSoftKeyboard());
        onView(withId(R.id.genero_edit)).perform(typeText("Test Games Genre"), closeSoftKeyboard());
        onView(withId(R.id.date_picker_button)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2019, 8, 17));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.submitButton)).perform(click());

        //tercero en la lista
        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.title)).perform(typeText("Test Games 3"), closeSoftKeyboard());
        onView(withId(R.id.desc)).perform(typeText("Test Games Desc"), closeSoftKeyboard());
        onView(withId(R.id.genero_edit)).perform(typeText("Test Games Genre"), closeSoftKeyboard());
        onView(withId(R.id.date_picker_button)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2021, 8, 17));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.submitButton)).perform(click());

        //tercero en la lista
        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.title)).perform(typeText("Test Games 1"), closeSoftKeyboard());
        onView(withId(R.id.desc)).perform(typeText("Test Games Desc"), closeSoftKeyboard());
        onView(withId(R.id.genero_edit)).perform(typeText("Test Games Genre"), closeSoftKeyboard());
        onView(withId(R.id.date_picker_button)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2018, 8, 17));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.submitButton)).perform(click());

        // Open Contextual Action Mode Overflow Menu (abrir menu de 3 puntos)
        openContextualActionModeOverflowMenu();
        // Perform a click() action on the view withText "Delete all" (Should be a R.string.* reference)
        onView(withText(R.string.list_by_date)).perform(click());

        // Se selecciona el segundo
        onView(withId(R.id.my_recycler_view_game)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Se comprueba que se han ordenado
        onView(withId(R.id.titleGameDetail)).check(matches(withText("Test Games 1")));

        // Nos vamos a Service
        onView(withId(R.id.navigation_games)).perform(click());

    }

    @Test
    public void shouldOrderByGenre(){

        //segundo en la lista
        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.title)).perform(typeText("Test Games 2"), closeSoftKeyboard());
        onView(withId(R.id.desc)).perform(typeText("Test Games Desc"), closeSoftKeyboard());
        onView(withId(R.id.genero_edit)).perform(typeText("Test Games Genre 2"), closeSoftKeyboard());
        onView(withId(R.id.date_picker_button)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2019, 8, 17));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.submitButton)).perform(click());

        //tercero en la lista
        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.title)).perform(typeText("Test Games 3"), closeSoftKeyboard());
        onView(withId(R.id.desc)).perform(typeText("Test Games Desc"), closeSoftKeyboard());
        onView(withId(R.id.genero_edit)).perform(typeText("Test Games Genre 3"), closeSoftKeyboard());
        onView(withId(R.id.date_picker_button)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2021, 8, 17));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.submitButton)).perform(click());

        //tercero en la lista
        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.title)).perform(typeText("Test Games 1"), closeSoftKeyboard());
        onView(withId(R.id.desc)).perform(typeText("Test Games Desc"), closeSoftKeyboard());
        onView(withId(R.id.genero_edit)).perform(typeText("Test Games Genre 1"), closeSoftKeyboard());
        onView(withId(R.id.date_picker_button)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2018, 8, 17));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.submitButton)).perform(click());

        // Open Contextual Action Mode Overflow Menu (abrir menu de 3 puntos)
        openContextualActionModeOverflowMenu();
        // Perform a click() action on the view withText "Delete all" (Should be a R.string.* reference)
        onView(withText(R.string.list_by_gender)).perform(click());

        // Se selecciona el segundo
        onView(withId(R.id.my_recycler_view_game)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Se comprueba que se han ordenado
        onView(withId(R.id.titleGameDetail)).check(matches(withText("Test Games 1")));

        // Nos vamos a Service
        onView(withId(R.id.navigation_games)).perform(click());
    }

    @Test
    public void shouldAddBug(){
        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.title)).perform(typeText("Test Games"), closeSoftKeyboard());
        onView(withId(R.id.desc)).perform(typeText("Test Games Desc"), closeSoftKeyboard());
        onView(withId(R.id.genero_edit)).perform(typeText("Test Games Genre"), closeSoftKeyboard());
        onView(withId(R.id.date_picker_button)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2017, 8, 17));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.submitButton)).perform(click());

        onView(withId(R.id.my_recycler_view_game)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.error_button)).perform(click());
        onView(withId(R.id.bug_text)).perform(typeText("Test bug"), closeSoftKeyboard());
        onView(withId(R.id.submit_bug_button)).perform(click());
        onView(withId(R.id.navigation_games)).perform(click());


    }

    @After
    public void deleteElements(){
        // Open Contextual Action Mode Overflow Menu (abrir menu de 3 puntos)
        openContextualActionModeOverflowMenu();
        // Perform a click() action on the view withText "Delete all" (Should be a R.string.* reference)
        onView(withText(R.string.delete_games)).perform(click());
    }

}
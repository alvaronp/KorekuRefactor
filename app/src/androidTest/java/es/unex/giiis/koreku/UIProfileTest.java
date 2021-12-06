package es.unex.giiis.koreku;

import static androidx.test.espresso.Espresso.onView;
import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.content.Intent;
import android.view.View;

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
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Rule;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class UIProfileTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void shouldAddProfileToRecyclerView() {
        mActivityRule.launchActivity(new Intent());
        String name = "name";
        String platform = "platform";
        String mail = "mail";
        onView(withId(R.id.navigation_profile)).perform(click());
        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.nombretxt)).perform(typeText(name), closeSoftKeyboard());
        onView(withId(R.id.telefonotxt)).perform(typeText(platform), closeSoftKeyboard());
        onView(withId(R.id.correotxt)).perform(typeText(mail), closeSoftKeyboard());
        onView(withId(R.id.submitProfile)).perform(click());
    }

    @Test
    public void shouldBuscarPerfilToRecyclerView() {

        mActivityRule.launchActivity(new Intent());
        String name = "name";
        String platform = "platform";
        String mail = "mail";
        onView(withId(R.id.navigation_profile)).perform(click());
        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.nombretxt)).perform(typeText(name), closeSoftKeyboard());
        onView(withId(R.id.telefonotxt)).perform(typeText(platform), closeSoftKeyboard());
        onView(withId(R.id.correotxt)).perform(typeText(mail), closeSoftKeyboard());
        onView(withId(R.id.submitProfile)).perform(click());
        onView(withId(R.id.navigation_profile)).perform(click());
        onView(withId(R.id.Busqueda)).perform(click());
        onView(withId(R.id.edtCodigo)).perform(typeText(name), closeSoftKeyboard());
        onView(withId(R.id.btnBuscar)).perform(click());
    }

    @Test
    public void shouldDeletePerfilToRecyclerView() {
        mActivityRule.launchActivity(new Intent());
        String name = "name";
        String platform = "platform";
        String mail = "mail";
        onView(withId(R.id.navigation_profile)).perform(click());
        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.nombretxt)).perform(typeText(name), closeSoftKeyboard());
        onView(withId(R.id.telefonotxt)).perform(typeText(platform), closeSoftKeyboard());
        onView(withId(R.id.correotxt)).perform(typeText(mail), closeSoftKeyboard());
        onView(withId(R.id.submitProfile)).perform(click());


        String name2 = "name2";
        String platform2 = "platform2";
        String mail2 = "mail2";
        onView(withId(R.id.navigation_profile)).perform(click());
        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.nombretxt)).perform(typeText(name2), closeSoftKeyboard());
        onView(withId(R.id.telefonotxt)).perform(typeText(platform2), closeSoftKeyboard());
        onView(withId(R.id.correotxt)).perform(typeText(mail2), closeSoftKeyboard());
        onView(withId(R.id.submitProfile)).perform(click());

        onView(withId(R.id.navigation_profile)).perform(click());
        onView(withId(R.id.recyclerviewprofile)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.delete_profile)).perform(click());
        onView(withId(R.id.navigation_profile)).perform(click());
    }

    @Before
        public void shouldDeleteAllProfileToRecyclerView() {
        onView(withId(R.id.navigation_profile)).perform(click());
        openContextualActionModeOverflowMenu();
        // Perform a click() action on the view withText "Delete all" (Should be a R.string.* reference)
        onView(withText(R.string.delete_profile)).perform(click());
    }

    @Test
    public void shouldAddCommentToRecyclerView() {
        mActivityRule.launchActivity(new Intent());
        String name = "name";
        String platform = "platform";
        String mail = "mail";
        onView(withId(R.id.navigation_profile)).perform(click());
        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.nombretxt)).perform(typeText(name), closeSoftKeyboard());
        onView(withId(R.id.telefonotxt)).perform(typeText(platform), closeSoftKeyboard());
        onView(withId(R.id.correotxt)).perform(typeText(mail), closeSoftKeyboard());
        onView(withId(R.id.submitProfile)).perform(click());


        String name2 = "name2";
        String platform2 = "platform2";
        String mail2 = "mail2";
        onView(withId(R.id.navigation_profile)).perform(click());
        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.nombretxt)).perform(typeText(name2), closeSoftKeyboard());
        onView(withId(R.id.telefonotxt)).perform(typeText(platform2), closeSoftKeyboard());
        onView(withId(R.id.correotxt)).perform(typeText(mail2), closeSoftKeyboard());
        onView(withId(R.id.submitProfile)).perform(click());

        onView(withId(R.id.navigation_profile)).perform(click());
        onView(withId(R.id.recyclerviewprofile)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.comment_button)).perform(click());
        onView(withId(R.id.comment_text)).perform(typeText("estoesuncomentario"), closeSoftKeyboard());
        onView(withId(R.id.comment_submit)).perform(click());
    }

    @Test
    public void shouldEdditToRecyclerView() {
        mActivityRule.launchActivity(new Intent());
        String name = "name";
        String platform = "platform";
        String mail = "mail";
        onView(withId(R.id.navigation_profile)).perform(click());
        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.nombretxt)).perform(typeText(name), closeSoftKeyboard());
        onView(withId(R.id.telefonotxt)).perform(typeText(platform), closeSoftKeyboard());
        onView(withId(R.id.correotxt)).perform(typeText(mail), closeSoftKeyboard());
        onView(withId(R.id.submitProfile)).perform(click());


        String name2 = "name2";
        String platform2 = "platform2";
        String mail2 = "mail2";
        onView(withId(R.id.navigation_profile)).perform(click());
        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.nombretxt)).perform(typeText(name2), closeSoftKeyboard());
        onView(withId(R.id.telefonotxt)).perform(typeText(platform2), closeSoftKeyboard());
        onView(withId(R.id.correotxt)).perform(typeText(mail2), closeSoftKeyboard());
        onView(withId(R.id.submitProfile)).perform(click());

        onView(withId(R.id.navigation_profile)).perform(click());
        onView(withId(R.id.recyclerviewprofile)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.editarPerfil)).perform(click());
        onView(withId(R.id.nombretxt)).perform(clearText())
                .perform(typeText("nombreEDITADO"), closeSoftKeyboard());
        onView(withId(R.id.submitProfile)).perform(click());
        onView(withId(R.id.navigation_profile)).perform(click());
    }
    }

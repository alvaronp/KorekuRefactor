package es.unex.giiis.koreku;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.content.Intent;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

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
        onView(withId(R.id.navigation_profile)).perform(click());
        onView(withId(R.id.Busqueda)).perform(click());
        onView(withId(R.id.edtCodigo)).perform(typeText(name), closeSoftKeyboard());
        onView(withId(R.id.btnBuscar)).perform(click());
    }
}
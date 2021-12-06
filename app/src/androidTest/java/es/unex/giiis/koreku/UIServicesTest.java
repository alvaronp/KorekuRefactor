package es.unex.giiis.koreku;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openContextualActionModeOverflowMenu;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

import android.content.Intent;
import android.widget.DatePicker;

import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matchers;
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
public class UIServicesTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void shouldAddServiceToRecyclerView() {

        mActivityRule.launchActivity(new Intent());

        String titleTest = "Testeo del insertar";
        String eMailTest = "gguibert@alumnos.unex.es";
        String priceTest = "5";

        // Nos vamos a Service
        onView(withId(R.id.navigation_services)).perform(click());

        // Para hacer click en el botón del añadir
        onView(withId(R.id.fragmentService_Button)).perform(click());

        // Rellenamos los campos
        onView(withId(R.id.serviceName)).perform(typeText(titleTest), closeSoftKeyboard());
        onView(withId(R.id.serviceSpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Twitch Prime"))).perform(click());
        onView(withId(R.id.serviceEmail)).perform(typeText(eMailTest), closeSoftKeyboard());
        onView(withId(R.id.servicePrice)).perform(typeText(priceTest), closeSoftKeyboard());
        onView(withId(R.id.serviceDueDateButton)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2017, 8, 17));
        onView(withId(android.R.id.button1)).perform(click());

        // Se inserta
        onView(withId(R.id.serviceSubmitButton)).perform(click());

    }

    @Test
    public void shouldEditServiceOfRecyclerView(){

        mActivityRule.launchActivity(new Intent());

        String titleTest = "Testeo del update";
        String eMailTest = "gguibert@alumnos.unex.es";
        String priceTest = "5";

        // Nos vamos a Service
        onView(withId(R.id.navigation_services)).perform(click());

        // Para hacer click en el botón del añadir
        onView(withId(R.id.fragmentService_Button)).perform(click());

        // Rellenamos los campos
        onView(withId(R.id.serviceName)).perform(typeText(titleTest), closeSoftKeyboard());
        onView(withId(R.id.serviceSpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Twitch Prime"))).perform(click());
        onView(withId(R.id.serviceEmail)).perform(typeText(eMailTest), closeSoftKeyboard());
        onView(withId(R.id.servicePrice)).perform(typeText(priceTest), closeSoftKeyboard());
        onView(withId(R.id.serviceDueDateButton)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2017, 8, 17));
        onView(withId(android.R.id.button1)).perform(click());

        // Se inserta
        onView(withId(R.id.serviceSubmitButton)).perform(click());

        // Se selecciona el primero
        onView(withId(R.id.fragment_service_recycledView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Se epieza a editar
        onView(withId(R.id.editButton_service_detail)).perform(click());
        onView(withId(R.id.update_service_EditName)).perform(clearText()).perform(click())
                .perform(typeText("Updateo correcto"), closeSoftKeyboard());
        onView(withId(R.id.update_service_submit_button)).perform(click());
        onView(withId(R.id.navigation_services)).perform(click());

    }

    @Test
    public void shouldDeleteServiceOfRecyclerView(){

        mActivityRule.launchActivity(new Intent());

        String titleTest = "Testeo del delete unitario";
        String eMailTest = "gguibert@alumnos.unex.es";
        String priceTest = "5";

        // Nos vamos a Service
        onView(withId(R.id.navigation_services)).perform(click());

        // Para hacer click en el botón del añadir
        onView(withId(R.id.fragmentService_Button)).perform(click());

        // Rellenamos los campos
        onView(withId(R.id.serviceName)).perform(typeText(titleTest), closeSoftKeyboard());
        onView(withId(R.id.serviceSpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Twitch Prime"))).perform(click());
        onView(withId(R.id.serviceEmail)).perform(typeText(eMailTest), closeSoftKeyboard());
        onView(withId(R.id.servicePrice)).perform(typeText(priceTest), closeSoftKeyboard());
        onView(withId(R.id.serviceDueDateButton)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2017, 8, 17));
        onView(withId(android.R.id.button1)).perform(click());

        // Se inserta
        onView(withId(R.id.serviceSubmitButton)).perform(click());

        // Se selecciona el primero
        onView(withId(R.id.fragment_service_recycledView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Se borra
        onView(withId(R.id.deleteButtonService)).perform(click());
        onView(withId(R.id.navigation_services)).perform(click());

    }

    @After
    public void shouldDeleteAll(){

        // Open Contextual Action Mode Overflow Menu (abrir menu de 3 puntos)
        openContextualActionModeOverflowMenu();
        // Perform a click() action on the view withText "Delete all" (Should be a R.string.* reference)
        onView(withText(R.string.delete_services)).perform(click());

    }

}
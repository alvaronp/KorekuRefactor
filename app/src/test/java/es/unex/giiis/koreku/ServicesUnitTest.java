package es.unex.giiis.koreku;


import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Date;

import es.unex.giiis.koreku.ui.service.Service;

public class ServicesUnitTest {

    @Test
    public void shouldGetID() {

        Service s = new Service(1, "Prime Knekro", "Twitch Prime",
                "gguibert@alumnos.unex.es", "5", new Date(2021, 9, 12));

        assertEquals(s.getId(), 1);

    }

    @Test
    public void shouldSetID() {

        Service s = new Service(1, "Prime Knekro", "Twitch Prime",
                "gguibert@alumnos.unex.es", "5", new Date(2021, 9, 12));

        s.setId(2);

        assertEquals(s.getId(), 2);

    }

    @Test
    public void shouldGetTitle() {

        Service s = new Service(1, "Prime de IlloJuan", "Twitch Prime",
                "gguibert@alumnos.unex.es", "5", new Date(2021, 9, 12));

        assertEquals(s.getTitle(), "Prime de IlloJuan");

    }

    @Test
    public void shouldSetTitle() {

        Service s = new Service(1, "Prime de JPery", "Twitch Prime",
                "gguibert@alumnos.unex.es", "5", new Date(2021, 9, 12));

        s.setTitle("Prime de IlloJuan");

        assertEquals(s.getTitle(), "Prime de IlloJuan");

    }

    @Test
    public void shouldGetSubscription() {

        Service s = new Service(1, "El internet de la Ps4", "PlayStation Now",
                "gguibert@alumnos.unex.es", "20", new Date(2021, 9, 12));

        assertEquals(s.getSubscription(), "Twitch Prime");

    }

    @Test
    public void shouldSetSubscription() {

        Service s = new Service(1, "El internet de la Ps4", "Twitch Prime",
                "gguibert@alumnos.unex.es", "20", new Date(2021, 9, 12));

        s.setSubscription("PlayStation Now");

        assertEquals(s.getSubscription(), "PlayStation Now");

    }

    @Test
    public void shouldGetEmail() {

        Service s = new Service(1, "Prime Knekro", "Twitch Prime",
                "gguibert@alumnos.unex.es", "5", new Date(2021, 9, 12));

        assertEquals(s.getEmail(), "gguibert@alumnos.unex.es");

    }

    @Test
    public void shouldSetEmail() {

        Service s = new Service(1, "Prime Knekro", "Twitch Prime",
                "gguibert@alumnos.com", "5", new Date(2021, 9, 12));

        s.setEmail("gguibert@alumnos.unex.es");

        assertEquals(s.getEmail(), "gguibert@alumnos.unex.es");

    }

    @Test
    public void shouldGetPrice() {

        Service s = new Service(1, "Prime Knekro", "Twitch Prime",
                "gguibert@alumnos.unex.es", "5", new Date(2021, 9, 12));

        assertEquals(s.getPrice(), "5");

    }

    @Test
    public void shouldSetPrice() {

        Service s = new Service(1, "Prime Knekro", "Twitch Prime",
                "gguibert@alumnos.unex.es", "2.5", new Date(2021, 9, 12));

        s.setPrice("5");

        assertEquals(s.getPrice(), "5");

    }

    @Test
    public void shouldGetDueDate() {

        Service s = new Service(1, "Prime Knekro", "Twitch Prime",
                "gguibert@alumnos.unex.es", "5", new Date(2021, 9, 12));

        assertEquals(s.getDueDate(), new Date(2021, 9, 12));

    }

    @Test
    public void shouldSetDueDate() {

        Service s = new Service(1, "Prime Knekro", "Twitch Prime",
                "gguibert@alumnos.unex.es", "5", new Date(2020, 9, 12));

        s.setDueDate(new Date(2021, 9, 12));

        assertEquals(s.getDueDate(), new Date(2021, 9, 12));

    }

}

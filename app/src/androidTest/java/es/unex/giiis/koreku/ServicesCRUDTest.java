package es.unex.giiis.koreku;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.room.Room;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import es.unex.giiis.koreku.roomdb.KorekuDatabase;
import es.unex.giiis.koreku.roomdb.ServiceDAO;
import es.unex.giiis.koreku.ui.service.Service;

public class ServicesCRUDTest {

    private ServiceDAO serviceDAO;
    private KorekuDatabase db;

    // Se necesita para testear el LiveData
    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Before
    public void createDb() {

        // Create a mock database
        Context context = getInstrumentation().getTargetContext();
        db = Room.inMemoryDatabaseBuilder(context, KorekuDatabase.class).allowMainThreadQueries().build();

        // Take the dao from the created database
        serviceDAO = db.getDao4();

    }

    @After
    public void closeDb() throws IOException {

        // Closing database
        db.close();

    }

    @Test
    public void shouldInsertItemIntoDB() throws Exception{

        // Se crea el Item a insertar
        Service s = new Service(1, "Mi primer servicio", "Xbox Game Pass",
                "gguibert@alumnos.unex.es", "1", new Date(2022, 1, 4));

        // Se inserta el item
        serviceDAO.insert(s);

        // Se recupera en el LiveData
        LiveData<List<Service>> liveServices = serviceDAO.getAll();
        List<Service> serviceList = LiveDataTestUtils.getValue(liveServices);

        // Se inician las comprobaciones de que el item se ha insertado correctamente
        assertEquals(serviceList.size(), 1);
        assertEquals(serviceList.get(0).getId(), 1);
        assertEquals(serviceList.get(0).getTitle(), "Mi primer servicio");
        assertEquals(serviceList.get(0).getSubscription(), "Xbox Game Pass");
        assertEquals(serviceList.get(0).getEmail(), "gguibert@alumnos.unex.es");
        assertEquals(serviceList.get(0).getPrice(), "1");
        assertEquals(serviceList.get(0).getDueDate(), new Date(2022, 1, 4));

    }

    @Test
    public void shouldUpdateItemOnDB() throws Exception{

        // Se crea el Item a insertar
        Service s = new Service(1, "Prime de Willyrex", "Twitch Prime",
                "gguibert@alumnos.unex.es", "1", new Date(2022, 2, 2));

        // Se inserta el item
        serviceDAO.insert(s);

        // Se cambia algún parámetro
        s.setTitle("Prime de JPery");

        // Se hace el update
        serviceDAO.update(s);

        // Se recupera en el LiveData
        LiveData<List<Service>> liveServices = serviceDAO.getAll();
        List<Service> serviceList = LiveDataTestUtils.getValue(liveServices);

        // Se inician las comprobaciones de que el update se ha realizado correctamente
        assertEquals(serviceList.get(0).getTitle(), "Prime del JPery");

    }

    @Test
    public void shouldDeleteAllItemsOnList() throws Exception{

        // Se crean los Items a insertar
        Service s1 = new Service(1, "Prime de Knekro", "Twitch Prime",
                "gguibert@alumnos.unex.es", "1", new Date(2022, 1, 6));
        Service s2 = new Service(2, "Prime Felipez", "Twitch Prime",
                "gguibert@alumnos.unex.es", "1", new Date(2022, 1, 6));
        Service s3 = new Service(3, "Prime de JPery", "Twitch Prime",
                "gguibert@alumnos.unex.es", "1", new Date(2022, 1, 6));
        Service s4 = new Service(4, "Prime de Ander", "Twitch Prime",
                "gguibert@alumnos.unex.es", "1", new Date(2022, 1, 6));

        // Se insertan los items
        serviceDAO.insert(s1);
        serviceDAO.insert(s2);
        serviceDAO.insert(s3);
        serviceDAO.insert(s4);

        //Se borran TODOS los services
        serviceDAO.deleteAll();

        // Se recupera en el LiveData
        LiveData<List<Service>> liveServices = serviceDAO.getAll();
        List<Service> serviceList = LiveDataTestUtils.getValue(liveServices);

        // Se comprueba que se han borrado correctamente
        assertEquals(serviceList.size(), 0);

    }

    @Test
    public void shouldDeleteOneItemOnList() throws Exception{

        // Se crean los Items a insertar
        Service s1 = new Service(1, "Prime de Knekro", "Twitch Prime",
                "gguibert@alumnos.unex.es", "1", new Date(2022, 1, 6));
        Service s2 = new Service(2, "Prime Felipez", "Twitch Prime",
                "gguibert@alumnos.unex.es", "1", new Date(2022, 1, 6));
        Service s3 = new Service(3, "Prime de JPery", "Twitch Prime",
                "gguibert@alumnos.unex.es", "1", new Date(2022, 1, 6));
        Service s4 = new Service(4, "Prime de Ander", "Twitch Prime",
                "gguibert@alumnos.unex.es", "1", new Date(2022, 1, 6));

        // Se insertan los items
        serviceDAO.insert(s1);
        serviceDAO.insert(s2);
        serviceDAO.insert(s3);
        serviceDAO.insert(s4);

        // Borramos uno de ellos
        serviceDAO.deleteService("Prime de JPery");

        // Se recupera el LiveData
        LiveData<List<Service>> liveServices = serviceDAO.getAll();
        List<Service> serviceList = LiveDataTestUtils.getValue(liveServices);

        // Se comprueba que se ha borrado correctamente
        assertEquals(serviceList.size(), 3);
        assertFalse(serviceList.get(0).getTitle().equals("Prime de JPery"));
        assertFalse(serviceList.get(1).getTitle().equals("Prime de JPery"));
        assertFalse(serviceList.get(2).getTitle().equals("Prime de JPery"));

    }

    @Test
    public void shouldReturnAllItemsOrderedByDueDate() throws Exception{

        // Se crean los Items a insertar
        Service s1 = new Service(1, "Prime de Knekro", "Twitch Prime",
                "gguibert@alumnos.unex.es", "1", new Date(2024, 1, 6));
        Service s2 = new Service(2, "Prime Felipez", "Twitch Prime",
                "gguibert@alumnos.unex.es", "1", new Date(2023, 1, 6));
        Service s3 = new Service(3, "Prime de JPery", "Twitch Prime",
                "gguibert@alumnos.unex.es", "1", new Date(2022, 1, 6));
        Service s4 = new Service(4, "Prime de Ander", "Twitch Prime",
                "gguibert@alumnos.unex.es", "1", new Date(2021, 1, 6));

        // Se insertan los items
        serviceDAO.insert(s1);
        serviceDAO.insert(s2);
        serviceDAO.insert(s3);
        serviceDAO.insert(s4);

        // Se recupera en el LiveData
        LiveData<List<Service>> liveServices = serviceDAO.getAllByDueDate();
        List<Service> serviceList = LiveDataTestUtils.getValue(liveServices);

        // Se comprueba que se han ordenado correctamente
        assertEquals(serviceList.get(0).getId(), 4);
        assertEquals(serviceList.get(1).getId(), 3);
        assertEquals(serviceList.get(2).getId(), 2);
        assertEquals(serviceList.get(3).getId(), 1);

    }

}
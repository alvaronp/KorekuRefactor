package es.unex.giiis.koreku;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import es.unex.giiis.koreku.roomdb.ConsolasDAO;
import es.unex.giiis.koreku.roomdb.KorekuDatabase;
import es.unex.giiis.koreku.ui.consoles.Consolas;

@RunWith(AndroidJUnit4.class)
public class ConsolesCRUDTest {

    // created instances for the DAO to test and our implementation of RoomDatabase
    private KorekuDatabase db;
    private ConsolasDAO consolasDAO;

    // necessary to test the LiveData
    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Before
    public void createDb() {

        Context context = getInstrumentation().getTargetContext();
        db = Room.inMemoryDatabaseBuilder(context, KorekuDatabase.class).allowMainThreadQueries().build();
        consolasDAO = db.getDao2();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void shouldAddConsoleToDB() throws Exception {

        // Se crea el item
        Consolas c = new Consolas();

        c.setTitle("Wii");
        c.setDate(new Date(2021, 12, 05));
        c.setCompany("Nintendo");
        c.setImage("");

        // Se inserta el item
        consolasDAO.insert(c);
        // Se recupera en el LiveData
        LiveData<List<Consolas>> liveConsoles = consolasDAO.getAll();
        List<Consolas> consolas = LiveDataTestUtils.getValue(liveConsoles);

        assertEquals(consolas.size(), 1);
        assertEquals(consolas.get(0).getTitle(), "Wii");
        assertEquals(consolas.get(0).getDate(), new Date(2021, 12, 05));
        assertEquals(consolas.get(0).getCompany(), "Nintendo");
        assertEquals(consolas.get(0).getImage(), "");
    }

    @Test
    public void shouldUpdateConsoleOnDB() throws InterruptedException {
        // Se crea el Item a insertar
        Consolas c = new Consolas(1, "Ps5", new Date(2006, 11, 11),
                "Sony",
                "https://es.wikipedia.org/wiki/PlayStation_3#/media/Archivo:PS3Versions.png");
        // Se inserta el item
        consolasDAO.insert(c);
        // Se cambia algún parámetro
        c.setTitle("Ps3");
        // Se hace el update
        consolasDAO.update(c);
        // Se recupera en el LiveData
        LiveData<List<Consolas>> liveConsolas = consolasDAO.getAll();
        List<Consolas> consolasList = LiveDataTestUtils.getValue(liveConsolas);
        // Se inician las comprobaciones de que el update se ha realizado correctamente
        assertEquals(consolasList.get(0).getTitle(), "Ps3");
    }


    @Test
    public void shouldDeleteAllConsolesOnDB() throws InterruptedException {
        Consolas c = new Consolas();

        c.setTitle("Wii");
        c.setDate(new Date(2021, 12, 05));
        c.setCompany("Nintendo");
        c.setImage("");

        // Se inserta el item
        consolasDAO.insert(c);
        LiveData<List<Consolas>> liveConsolas = consolasDAO.getAll();
        List<Consolas> consolasList = LiveDataTestUtils.getValue(liveConsolas);
        assertEquals(consolasList.size(), 1);
        //Se borra
        consolasDAO.deleteConsole(c.getTitle());
        consolasList = LiveDataTestUtils.getValue(liveConsolas);
        assertEquals(consolasList.size(), 0);
    }
}
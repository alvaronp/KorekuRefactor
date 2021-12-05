package es.unex.giiis.koreku;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
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
import es.unex.giiis.koreku.ui.consoles.ConsoleRepository;
import es.unex.giiis.koreku.ui.consoles.ConsoleViewModel;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ConsolesCRUDTest {
    private KorekuDatabase db;
    private ConsolasDAO dao;

    // necessary to test the LiveData
    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Before
    public void createDb() {
        Context context = getInstrumentation().getTargetContext();
        db = Room.inMemoryDatabaseBuilder(context, KorekuDatabase.class).allowMainThreadQueries().build();
        dao = db.getDao2();
    }

    @After
    public void closeDb() {
        db.close();
    }

    @Test
    public void shouldAddConsoleToDB() throws InterruptedException {
        Consolas c = new Consolas();
        c.setTitle("Wii");
        c.setDate(Date.from(Instant.now()));
        c.setCompany("Nintendo");

        dao.insert(c);
        LiveData<List<Consolas>> consolas = dao.getAll();
        List<Consolas> clist = LiveDataTestUtils.getValue(consolas);

        assertEquals(clist.size(), 1);
        assertEquals(clist.get(0).getTitle(), "Wii");
    }


    @Test
    public void shouldUpdateConsoleOnDB() throws InterruptedException {
        Consolas c = new Consolas();
        c.setTitle("Wii");
        c.setDate(Date.from(Instant.now()));
        c.setCompany("Nintendo");
        dao.insert(c);
        LiveData <List<Consolas>> consolas = dao.getAll();
        List<Consolas> clist = LiveDataTestUtils.getValue(consolas);
        assertEquals(clist.size(), 1);
        assertEquals(clist.get(0).getTitle(), "Wii");

        c.setTitle("Nintendo DS");
        dao.update(c);
        clist = LiveDataTestUtils.getValue(dao.getAll());
        assertEquals(clist.size(), 1);
        assertEquals(clist.get(0).getTitle(), "Nintendo DS");
    }


    @Test
    public void shouldDeleteAllConsolesOnDB() {
        Consolas c = new Consolas();
        c.setTitle("Wii");
        c.setDate(Date.from(Instant.now()));
        c.setCompany("Nintendo");

        dao.insert(c);
    }

    @Test
    public void shouldGetCorrectConsoleFromDB(){
        Consolas c = new Consolas();
        c.setTitle("Wii");
        c.setDate(Date.from(Instant.now()));
        c.setCompany("Nintendo");

        dao.insert(c);
    }
}
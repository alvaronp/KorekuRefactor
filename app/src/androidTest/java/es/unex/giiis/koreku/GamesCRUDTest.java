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

import es.unex.giiis.koreku.roomdb.GamesDAO;
import es.unex.giiis.koreku.roomdb.KorekuDatabase;
import es.unex.giiis.koreku.ui.games.Games;

@RunWith(AndroidJUnit4.class)
public class GamesCRUDTest {

    // created instances for the DAO to test and our implementation of RoomDatabase
    private KorekuDatabase db;
    private GamesDAO gamesDAO;

    // necessary to test the LiveData
    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Before
    public void createDb() {

        Context context = getInstrumentation().getTargetContext();
        db = Room.inMemoryDatabaseBuilder(context, KorekuDatabase.class).allowMainThreadQueries().build();
        gamesDAO = db.getDao1();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void shouldAddGameToDB() throws Exception {

        // Se crea el item
        Games g = new Games();

        g.setTitle("Virtua Cop");
        g.setStatus(Games.Status.NOTFINISHED);
        g.setBuydate(new Date(2021, 12, 05));
        g.setGenero("Shooter");

        // Se inserta el item
        gamesDAO.insert(g);
        // Se recupera en el LiveData
        LiveData<List<Games>> liveGames = gamesDAO.getAll();
        List<Games> games = LiveDataTestUtils.getValue(liveGames);

        assertEquals(games.size(), 1);
        assertEquals(games.get(0).getTitle(), "Virtua Cop");
        assertEquals(games.get(0).getBuydate(), new Date(2021, 12, 05));
        assertEquals(games.get(0).getStatus(), Games.Status.NOTFINISHED);
    }

    @Test
    public void shouldUpdateGameOnDB() throws InterruptedException {
        // Se crea el Item a insertar
        Games g = new Games(1, "Garou: Mark of the Wolves", Games.Status.NOTFINISHED, new Date(2006, 11, 11), "","","Fighting","","");
        // Se inserta el item
        gamesDAO.insert(g);
        // Se cambia algún parámetro
        g.setTitle("KoF '98");
        // Se hace el update
        gamesDAO.update(g);
        // Se recupera en el LiveData
        LiveData<List<Games>> liveGames = gamesDAO.getAll();
        List<Games> gamesList = LiveDataTestUtils.getValue(liveGames);
        // Se inician las comprobaciones de que el update se ha realizado correctamente
        assertEquals(gamesList.get(0).getTitle(), "KoF '98");
    }


    @Test
    public void shouldDeleteAllConsolesOnDB() throws InterruptedException {
        Games g = new Games();

        g.setTitle("Virtua Cop");
        g.setStatus(Games.Status.NOTFINISHED);
        g.setBuydate(new Date(2021, 12, 05));
        g.setGenero("Shooter");

        // Se inserta el item
        gamesDAO.insert(g);
        // Se recupera en el LiveData
        LiveData<List<Games>> liveGames = gamesDAO.getAll();
        List<Games> games = LiveDataTestUtils.getValue(liveGames);

        assertEquals(games.size(), 1);
        //Se borra
        gamesDAO.deleteGame(g.getTitle());
        games = LiveDataTestUtils.getValue(liveGames);
        assertEquals(games.size(), 0);
    }
}
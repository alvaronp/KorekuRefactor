package es.unex.giiis.koreku;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import es.unex.giiis.koreku.roomdb.PerfilDAO;
import es.unex.giiis.koreku.roomdb.KorekuDatabase;
import es.unex.giiis.koreku.ui.consoles.Consolas;
import es.unex.giiis.koreku.ui.profile.Perfil;

@RunWith(AndroidJUnit4.class)
public class ProfileCRUDTest {
    private KorekuDatabase db;
    private PerfilDAO perfilesDAO;

    // necessary to test the LiveData
    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Before
    public void createDb() {
        Context context = getInstrumentation().getTargetContext();
        db = Room.inMemoryDatabaseBuilder(context, KorekuDatabase.class).allowMainThreadQueries().build();
        perfilesDAO = db.getDao3();

    }
    @After
    public void closeDb() throws IOException {

        db.close();

    }

    @Test
    public void shouldAddProfileToDB() throws Exception {

        // Se crea el item
        Perfil x =new Perfil();
        x.setTitle("title");
        x.setPhone("phone");
        x.setMail("mail");
        // Se inserta el item
        perfilesDAO.insert(x);
        // Se recupera en el LiveData
        LiveData<List<Perfil>> livePerfiles = perfilesDAO.getAll();
        List<Perfil> perfiles = LiveDataTestUtils.getValue(livePerfiles);

        assertEquals(perfiles.size(), 1);
        assertEquals(perfiles.get(0).getTitle(), "title");
        assertEquals(perfiles.get(0).getPhone(), "phone");
        assertEquals(perfiles.get(0).getMail(), "mail");
    }
    @Test
    public void shouldUpdateProfileOnDB() throws InterruptedException {
        // Se crea el Item a insertar
        Perfil c = new Perfil( 1, "title",  "phone",  "mail",  "image");
        // Se inserta el item
        perfilesDAO.insert(c);
        // Se cambia algún parámetro
        c.setTitle("title2");
        // Se hace el update
        perfilesDAO.update(c);
        // Se recupera en el LiveData
        LiveData<List<Perfil>> livePerfiles = perfilesDAO.getAll();
        List<Perfil> perfilesList = LiveDataTestUtils.getValue(livePerfiles);
        // Se inician las comprobaciones de que el update se ha realizado correctamente
        assertEquals(perfilesList.get(0).getTitle(), "title2");
    }

    @Test
    public void shouldDeleteAllProfilesOnDB() throws InterruptedException {
        Perfil x =new Perfil();
        x.setTitle("title");
        x.setPhone("phone");
        x.setMail("mail");
        Perfil c =new Perfil();
        c.setTitle("title2");
        c.setPhone("phone2");
        c.setMail("mail2");

        // Se inserta el item
        perfilesDAO.insert(x);
        perfilesDAO.insert(c);
        LiveData<List<Perfil>> livePerfiles = perfilesDAO.getAll();
        List<Perfil> perfilList = LiveDataTestUtils.getValue(livePerfiles);
        assertEquals(perfilList.size(), 2);
        //Se borra
        perfilesDAO.deleteAll();
        perfilList = LiveDataTestUtils.getValue(livePerfiles);
        assertEquals(perfilList.size(), 0);
    }

    @Test
    public void shouldDeleteOneProfilesOnDB() throws InterruptedException {
        Perfil x =new Perfil();
        x.setTitle("title");
        x.setPhone("phone");
        x.setMail("mail");

        // Se inserta el item
        perfilesDAO.insert(x);

        LiveData<List<Perfil>> livePerfiles = perfilesDAO.getAll();
        List<Perfil> perfilList = LiveDataTestUtils.getValue(livePerfiles);
        assertEquals(perfilList.size(), 1);
        //Se borra
        perfilesDAO.deleteProfile(x.getTitle());
        perfilList = LiveDataTestUtils.getValue(livePerfiles);
        assertEquals(perfilList.size(), 0);
    }
}
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.unex.giiis.koreku.new_api.Product;
import es.unex.giiis.koreku.roomdb.ConsolasDAO;
import es.unex.giiis.koreku.roomdb.KorekuDatabase;
import es.unex.giiis.koreku.roomdb.ProductsDAO;
import es.unex.giiis.koreku.ui.consoles.Consolas;
import es.unex.giiis.koreku.ui.profile.Perfil;

@RunWith(AndroidJUnit4.class)
public class ProductsCRUDTest {

    // created instances for the DAO to test and our implementation of RoomDatabase
    private KorekuDatabase db;
    private ProductsDAO productsDAO;

    // necessary to test the LiveData
    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Before
    public void createDb() {
        Context context = getInstrumentation().getTargetContext();
        db = Room.inMemoryDatabaseBuilder(context, KorekuDatabase.class).allowMainThreadQueries().build();
        productsDAO = db.getDao5();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void shouldAddProductToDB() throws Exception {

        List<Product> products = new ArrayList<>();

        Product p1 = new Product();
        p1.setId(0);
        p1.setProductname("Mario Party");
        p1.setConsolename("Nintendo DS");
        p1.setStatus("success");
        products.add(p1);

        Product p2 = new Product();
        p2.setId(1);
        p2.setProductname("Ejemplo");
        p2.setConsolename("Consola ejemplo");
        p2.setStatus("failed");
        products.add(p2);

        // Se inserta el item
        productsDAO.bulkInsert(products);
        // Se recupera en el LiveData
        LiveData<List<Product>> liveProducts = productsDAO.getProductsByConsole("Nintendo DS");
        List<Product> ps = LiveDataTestUtils.getValue(liveProducts);

        assertEquals(ps.size(), 1);
        assertEquals(products.get(0).getConsolename(), "Nintendo DS");
        assertEquals(products.get(0).getProductname(), "Mario Party");
        assertEquals(products.get(0).getStatus(),"success");

        int n = productsDAO.getNumberProductsByConsole("Consola ejemplo");
        assertEquals(n,1);
    }
}

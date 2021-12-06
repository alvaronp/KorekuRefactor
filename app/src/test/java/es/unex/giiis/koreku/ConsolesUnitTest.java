package es.unex.giiis.koreku;


import static org.junit.Assert.assertEquals;

import org.junit.Test;
import java.util.Date;

import es.unex.giiis.koreku.ui.consoles.Consolas;

public class ConsolesUnitTest {

    @Test
    public void shouldGetId() {
        Consolas c = new Consolas(0, "Ejemplo Consola", new Date(2010,12,9), "Company", "Image");
        assertEquals(c.getId(), 0);
    }

    @Test
    public void shouldSetId() {
        Consolas c = new Consolas(0, "Ejemplo Consola", new Date(2010,12,9), "Company", "Image");
        c.setId(1);
        assertEquals(c.getId(), 1);
    }

    @Test
    public void shouldGetConsoleName() {
        Consolas c = new Consolas(0, "Ejemplo Consola", new Date(2010,12,9), "Company", "Image");
        assertEquals(c.getTitle(), "Ejemplo Consola");
    }

    @Test
    public void shouldSetConsoleName() {
        Consolas c = new Consolas(0, "Ejemplo Consola", new Date(2010,12,9), "Company", "Image");
        c.setTitle("Otro ejemplo");
        assertEquals(c.getTitle(), "Otro ejemplo");
    }

    @Test
    public void shouldGetBuyDate() {
        Consolas c = new Consolas(0, "Ejemplo Consola", new Date(2010,12,9), "Company", "Image");
        assertEquals(c.getDate(), new Date(2010,12,9));
    }

    @Test
    public void shouldSetBuyDate() {
        Consolas c = new Consolas(0, "Ejemplo Consola", new Date(2010,12,9), "Company", "Image");
        c.setDate(new Date(2021,12,9));
        assertEquals(c.getDate(), new Date(2021,12,9));
    }

    @Test
    public void shouldGetCompany() {
        Consolas c = new Consolas(0, "Ejemplo Consola", new Date(2010,12,9), "Company", "Image");
        assertEquals(c.getCompany(), "Company");
    }

    @Test
    public void shouldSetCompany() {
        Consolas c = new Consolas(0, "Ejemplo Consola", new Date(2010,12,9), "Company", "Image");
        c.setCompany("Nueva Company");
        assertEquals(c.getCompany(), "Nueva Company");
    }

    @Test
    public void shouldGetImage() {
        Consolas c = new Consolas(0, "Ejemplo Consola", new Date(2010,12,9), "Company", "Image");
        assertEquals(c.getImage(), "Image");
    }

    @Test
    public void shouldSetImage() {
        Consolas c = new Consolas(0, "Ejemplo Consola", new Date(2010,12,9), "Company", "Image");
        c.setImage("Nueva Imagen");
        assertEquals(c.getImage(), "Nueva Imagen");
    }

}

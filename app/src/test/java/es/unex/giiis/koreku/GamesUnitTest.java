package es.unex.giiis.koreku;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.Date;

import es.unex.giiis.koreku.ui.consoles.Consolas;
import es.unex.giiis.koreku.ui.games.Games;

public class GamesUnitTest {
    @Test
    public void shouldGetId() {
        Games g = new Games(0, "Ejemplo Juego", Games.Status.NOTFINISHED, new Date(2006, 11, 11), "","","","","");
        assertEquals(g.getId(), 0);
    }

    @Test
    public void shouldSetId() {
        Games g = new Games(0, "Ejemplo Juego", Games.Status.NOTFINISHED, new Date(2006, 11, 11), "","","","","");
        g.setId(1);
        assertEquals(g.getId(), 1);
    }

    @Test
    public void shouldGetTitle() {
        Games g = new Games(0, "Ejemplo Juego", Games.Status.NOTFINISHED, new Date(2006, 11, 11), "","","","","");
        assertEquals(g.getTitle(), "Ejemplo Juego");
    }

    @Test
    public void shouldSetTitle() {
        Games g = new Games(0, "Ejemplo Juego", Games.Status.NOTFINISHED, new Date(2006, 11, 11), "","","","","");
        g.setTitle("Otro Ejemplo");
        assertEquals(g.getTitle(), "Otro Ejemplo");
    }

    @Test
    public void shouldGetStatus() {
        Games g = new Games(0, "Ejemplo Juego", Games.Status.NOTFINISHED, new Date(2006, 11, 11), "","","","","");
        assertEquals(g.getStatus(), Games.Status.NOTFINISHED);
    }

    @Test
    public void shouldSetStatus() {
        Games g = new Games(0, "Ejemplo Juego", Games.Status.NOTFINISHED, new Date(2006, 11, 11), "","","","","");
        g.setStatus(Games.Status.FINISHED);
        assertEquals(g.getStatus(), Games.Status.FINISHED);
    }

    @Test
    public void shouldGetDate() {
        Games g = new Games(0, "Ejemplo Juego", Games.Status.NOTFINISHED, new Date(2006, 11, 11), "","","","","");
        assertEquals(g.getBuydate(), new Date(2006,11,11));
    }

    @Test
    public void shouldSetDate() {
        Games g = new Games(0, "Ejemplo Juego", Games.Status.NOTFINISHED, new Date(2006, 11, 11), "","","","","");
        g.setBuydate(new Date(2010,12,9));
        assertEquals(g.getBuydate(), new Date(2010,12,9));
    }

    @Test
    public void shouldGetDesc() {
        Games g = new Games(0, "Ejemplo Juego", Games.Status.NOTFINISHED, new Date(2006, 11, 11), "Ejemplo","","","","");
        assertEquals(g.getDesc(), "Ejemplo");
    }

    @Test
    public void shouldSetDesc() {
        Games g = new Games(0, "Ejemplo Juego", Games.Status.NOTFINISHED, new Date(2006, 11, 11), "Ejemplo","","","","");
        g.setDesc("Otro Ejemplo");
        assertEquals(g.getDesc(), "Otro Ejemplo");
    }

    @Test
    public void shouldGetImage() {
        Games g = new Games(0, "Ejemplo Juego", Games.Status.NOTFINISHED, new Date(2006, 11, 11), "","Ejemplo","","","");
        assertEquals(g.getImage(), "Ejemplo");
    }

    @Test
    public void shouldSetImage() {
        Games g = new Games(0, "Ejemplo Juego", Games.Status.NOTFINISHED, new Date(2006, 11, 11), "","Ejemplo","","","");
        g.setImage("Otro Ejemplo");
        assertEquals(g.getImage(), "Otro Ejemplo");
    }

    @Test
    public void shouldGetBugs() {
        Games g = new Games(0, "Ejemplo Juego", Games.Status.NOTFINISHED, new Date(2006, 11, 11), "","","Ejemplo","","");
        assertEquals(g.getBugs(), "Ejemplo");
    }

    @Test
    public void shouldSetBugs() {
        Games g = new Games(0, "Ejemplo Juego", Games.Status.NOTFINISHED, new Date(2006, 11, 11), "","","Ejemplo","","");
        g.setBugs("Otro Ejemplo");
        assertEquals(g.getBugs(), "Otro Ejemplo");
    }

    @Test
    public void shouldGetGenre() {
        Games g = new Games(0, "Ejemplo Juego", Games.Status.NOTFINISHED, new Date(2006, 11, 11), "","","","Ejemplo","");
        assertEquals(g.getGenero(), "Ejemplo");
    }

    @Test
    public void shouldSetGenre() {
        Games g = new Games(0, "Ejemplo Juego", Games.Status.NOTFINISHED, new Date(2006, 11, 11), "","","","Ejemplo","");
        g.setGenero("Otro Ejemplo");
        assertEquals(g.getGenero(), "Otro Ejemplo");
    }

    @Test
    public void shouldGetConsole() {
        Games g = new Games(0, "Ejemplo Juego", Games.Status.NOTFINISHED, new Date(2006, 11, 11), "","","","","Ejemplo");
        assertEquals(g.getConsole(), "Ejemplo");
    }

    @Test
    public void shouldSetConsole() {
        Games g = new Games(0, "Ejemplo Juego", Games.Status.NOTFINISHED, new Date(2006, 11, 11), "","","","","Ejemplo");
        g.setConsole("Sega Saturn");
        assertEquals(g.getConsole(), "Sega Saturn");
    }

}

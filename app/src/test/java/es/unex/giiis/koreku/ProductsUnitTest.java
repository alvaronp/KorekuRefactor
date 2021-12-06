package es.unex.giiis.koreku;


import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.Date;

import es.unex.giiis.koreku.new_api.Product;
import es.unex.giiis.koreku.ui.consoles.Consolas;

public class ProductsUnitTest {
    @Test
    public void shouldGetId() {
        Product p = new Product(0, "Ejemplo Consola", "Ejemplo Producto", "status");
        assertEquals(p.getId(), 0);
    }

    @Test
    public void shouldSetId() {
        Product p = new Product(0, "Ejemplo Consola", "Ejemplo Producto", "status");
        p.setId(1);
        assertEquals(p.getId(), 1);
    }

    @Test
    public void shouldGetConsoleName() {
        Product p = new Product(0, "Ejemplo Consola", "Ejemplo Producto", "status");
        assertEquals(p.getConsolename(), "Ejemplo Consola");
    }

    @Test
    public void shouldSetConsoleName() {
        Product p = new Product(0, "Ejemplo Consola", "Ejemplo Producto", "status");
        p.setConsolename("Otra consola");
        assertEquals(p.getConsolename(), "Otra consola");
    }

    @Test
    public void shouldGetProductName() {
        Product p = new Product(0, "Ejemplo Consola", "Ejemplo Producto", "status");
        assertEquals(p.getProductname(), "Ejemplo Producto");
    }

    @Test
    public void shouldSetProductName() {
        Product p = new Product(0, "Ejemplo Consola", "Ejemplo Producto", "status");
        p.setProductname("Otro producto");
        assertEquals(p.getProductname(), "Otro producto");
    }

    @Test
    public void shouldGetStatus() {
        Product p = new Product(0, "Ejemplo Consola", "Ejemplo Producto", "status");
        assertEquals(p.getStatus(), "status");
    }

    @Test
    public void shouldSetStatus() {
        Product p = new Product(0, "Ejemplo Consola", "Ejemplo Producto", "status");
        p.setStatus("wrong");
        assertEquals(p.getStatus(), "wrong");
    }
}

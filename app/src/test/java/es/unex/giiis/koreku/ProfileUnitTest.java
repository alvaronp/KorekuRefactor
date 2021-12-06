package es.unex.giiis.koreku;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import java.util.Date;

import es.unex.giiis.koreku.ui.profile.Perfil;

import es.unex.giiis.koreku.ui.profile.Perfil;

public class ProfileUnitTest {
    @Test
    public void getIdTEST(){
        Perfil p= new Perfil(0, "title" ,  "phone",  "mail",  "image","comments");
		assertEquals(0,p.getId());
    }
    @Test
    public void setIdTEST(){
        Perfil p = new Perfil();
        p.setId(5);
        assertEquals(5,p.getId());
    }
    @Test
    public void getTitleTEST(){
        Perfil p= new Perfil(0, "title" ,  "phone",  "mail",  "image","comments");
        assertEquals("title",p.getTitle());
    }
    @Test
    public void setTitleTEST(){
        Perfil p = new Perfil();
        p.setTitle("title");
        assertEquals("title",p.getTitle());
    }
    @Test
    public void getPhoneTEST(){
        Perfil p= new Perfil(0, "title" ,  "phone",  "mail",  "image","comments");
        assertEquals("phone",p.getPhone());
    }
    @Test
    public void setPhoneTEST(){
        Perfil p = new Perfil();
        p.setPhone("phone");
        assertEquals("phone",p.getPhone());
    }
    @Test
    public void getMailTEST(){
        Perfil p= new Perfil(0, "title" ,  "phone",  "mail",  "image","comments");
        assertEquals("mail",p.getMail());
    }
    @Test
    public void setMailTEST(){
        Perfil p = new Perfil();
        p.setMail("mail");
        assertEquals("mail",p.getMail());
    }
    @Test
    public void getImageTEST(){
        Perfil p= new Perfil(0, "title" ,  "phone",  "mail",  "image","comments");
        assertEquals("image",p.getImage());
    }
    @Test
    public void setImageTEST(){
        Perfil p = new Perfil();
        p.setImage("images");
        assertEquals("images",p.getImage());
    }
    @Test
    public void getCommentsTEST(){
        Perfil p= new Perfil(0, "title" ,  "phone",  "mail",  "image","comments");
        assertEquals("comments",p.getComments());
    }
    @Test
    public void setCommentsTEST(){
    Perfil p = new Perfil();
    p.setComments("comments");
    assertEquals("comments",p.getComments());
    }
}

package es.unex.giiis.koreku.roomdb;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import es.unex.giiis.koreku.ui.profile.Perfil;

@Dao
public interface PerfilDAO {
    @Query("SELECT * FROM perfil")
    public List<Perfil> getAll();
    @Insert
    public long insert(Perfil item);
    @Query("DELETE FROM perfil")
    public void deleteAll();
    @Query("DELETE FROM perfil WHERE TITLE = :title")
    public void deleteProfile(String title);
    @Update
    public int update(Perfil item);

}

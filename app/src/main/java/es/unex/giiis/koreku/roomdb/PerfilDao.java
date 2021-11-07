package es.unex.giiis.koreku.roomdb;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import es.unex.giiis.koreku.Perfil;

@Dao
public interface PerfilDao {
    @Query("SELECT * FROM todo")
    public List<Perfil> getAll();
    @Insert
    public long insert(Perfil item);
    @Query("DELETE FROM todo")
    public void deleteAll();
    @Update
    public int update(Perfil item);

}

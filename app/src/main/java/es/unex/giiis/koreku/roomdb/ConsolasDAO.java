package es.unex.giiis.koreku.roomdb;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import es.unex.giiis.koreku.Consolas;

@Dao
public interface ConsolasDAO {
    @Query("SELECT * FROM consolas")
    public List<Consolas> getAll();
    @Insert
    public long insert(Consolas item);
    @Query("DELETE FROM consolas")
    public void deleteAll();
    @Update
    public int update(Consolas item);

}

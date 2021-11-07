package es.unex.giiis.koreku.roomdb;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import es.unex.giiis.asee.todomanager_db.Consolas;

@Dao
public interface ConsolasDao {
    @Query("SELECT * FROM todo")
    public List<Consolas> getAll();
    @Insert
    public long insert(Consolas item);
    @Query("DELETE FROM todo")
    public void deleteAll();
    @Update
    public int update(Consolas item);

}

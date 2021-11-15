package es.unex.giiis.koreku.roomdb;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import es.unex.giiis.koreku.Games;

@Dao //Esta interfaz es un Dao, así, Room sabrá que lo tiene que implementar como eso
public interface GamesDAO {
    @Query("SELECT * FROM game") //Antes definimos que nuestra tabla se llamaría así
    public List<Games> getAll();

    @Insert
    public long insert(Games game);

    @Query("DELETE FROM game")
    public void deleteAll();

    @Update
    public int update(Games game);

    @Update
    public int updateBugs(Games game);

    @Query("SELECT * FROM game ORDER BY TITLE ") //Antes definimos que nuestra tabla se llamaría así
    public List<Games> getAllByGender();

    @Query("SELECT * FROM game ORDER BY DATE ") //Antes definimos que nuestra tabla se llamaría así
    public List<Games> getAllByDate();
}

package es.unex.giiis.koreku.roomdb;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import es.unex.giiis.koreku.ui.consoles.Consolas;
import es.unex.giiis.koreku.ui.games.Games;

@Dao //Esta interfaz es un Dao, así, Room sabrá que lo tiene que implementar como eso
public interface GamesDAO {
    @Query("SELECT * FROM game") //Antes definimos que nuestra tabla se llamaría así
    public LiveData<List<Games>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long insert(Games game);

    @Query("DELETE FROM game")
    public void deleteAll();

    @Query("DELETE FROM game WHERE TITLE=:title")
    public void deleteGame(String title);

    @Update
    public int update(Games game);

    @Query("UPDATE game SET bugs = :bugs WHERE title = :title")
    public int update(String title, String bugs);

    @Query("SELECT * FROM game ORDER BY GENERO ") //Antes definimos que nuestra tabla se llamaría así
    public LiveData<List<Games>> getAllByGender();

    @Query("SELECT * FROM game ORDER BY DATE ")
    public LiveData<List<Games>> getAllByDate();

}

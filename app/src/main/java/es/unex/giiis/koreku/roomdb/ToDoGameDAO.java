package es.unex.giiis.koreku.roomdb;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import es.unex.giiis.koreku.ToDoGame;

@Dao //Esta interfaz es un Dao, así, Room sabrá que lo tiene que implementar como eso
public interface ToDoGameDAO {
    @Query("SELECT * FROM game") //Antes definimos que nuestra tabla se llamaría así
    public List<ToDoGame> getAll();

    @Insert
    public long insert(ToDoGame game);

    @Query("DELETE FROM game")
    public void deleteAll();

    @Update
    public int update(ToDoGame game);
}

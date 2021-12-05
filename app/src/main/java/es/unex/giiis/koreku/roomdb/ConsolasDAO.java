package es.unex.giiis.koreku.roomdb;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import es.unex.giiis.koreku.ui.consoles.Consolas;

@Dao
public interface ConsolasDAO {
    @Query("SELECT * FROM consolas")
    public LiveData<List<Consolas>> getAll();
    @Insert
    public void insert(Consolas item);
    @Query("DELETE FROM consolas")
    public void deleteAll();

    @Query("DELETE FROM consolas WHERE TITLE=:id")
    public void deleteConsole(String id);

    @Update
    public int update(Consolas item);

    @Query("SELECT * FROM consolas ORDER BY DATE ")
    public LiveData<List<Consolas>> getAllByDate();
}

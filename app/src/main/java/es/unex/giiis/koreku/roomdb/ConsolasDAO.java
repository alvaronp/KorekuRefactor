package es.unex.giiis.koreku.roomdb;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import es.unex.giiis.koreku.Consolas;
import es.unex.giiis.koreku.Games;

@Dao
public interface ConsolasDAO {
    @Query("SELECT * FROM consolas")
    public List<Consolas> getAll();
    @Insert
    public long insert(Consolas item);
    @Query("DELETE FROM consolas")
    public void deleteAll();

    @Query("DELETE FROM consolas WHERE TITLE=:id")
    public void deleteConsole(String id);

    @Update
    public int update(Consolas item);
    @Query("SELECT * FROM consolas WHERE console_id=:id")
    public Consolas get(Long id);

    @Query("SELECT * FROM consolas ORDER BY DATE ")
    public List<Consolas> getAllByDate();

    @Query("UPDATE consolas SET title=:title,date=:date,company=:company,image=:image WHERE title=:title2")
    public int updateSobrecargado(String title2,String title, String date,String company,String image);

}

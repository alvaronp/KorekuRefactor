package es.unex.giiis.koreku.roomdb;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import es.unex.giiis.koreku.Service;

@Dao //Esta interfaz es un Dao, así, Room sabrá que lo tiene que implementar como eso
public interface ServiceDAO {

    @Query("SELECT * FROM service")  //Antes definimos que nuestra tabla se llamaría así
    public List<Service> getAll();
    @Query("DELETE FROM service WHERE subscription = :subscription")
    public void deleteService(String subscription);
    @Insert
    public long insert(Service service);

    @Query("DELETE FROM service")
    public void deleteAll();
//
    @Update
    public int update(Service service);

    @Query("SELECT * FROM service ORDER BY dueDate ")
    public List<Service> getAllByDueDate();

}

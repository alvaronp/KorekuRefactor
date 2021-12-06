package es.unex.giiis.koreku.roomdb;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import es.unex.giiis.koreku.new_api.Product;
import es.unex.giiis.koreku.ui.consoles.Consolas;

@Dao
public interface ProductsDAO {
    @Insert(onConflict = REPLACE)
    void bulkInsert(List<Product> products);

    @Query("SELECT * FROM product WHERE consolename = :cname")
    LiveData<List<Product>> getProductsByConsole(String cname);

    @Query("SELECT count(*) FROM product WHERE consolename = :cname")
    int getNumberProductsByConsole(String cname);

    @Query("DELETE FROM product WHERE consolename = :cname")
    int deleteReposByUser(String cname);
}
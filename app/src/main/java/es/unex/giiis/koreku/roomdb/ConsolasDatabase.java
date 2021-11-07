package es.unex.giiis.koreku.roomdb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import es.unex.giiis.koreku.Consolas;

@Database(entities = {Consolas.class},version=1)
public abstract class ConsolasDatabase extends RoomDatabase {
    private static ConsolasDatabase instance;

    public static ConsolasDatabase getInstance(Context context){
        if(instance ==null)
            instance= Room.databaseBuilder(context, ConsolasDatabase.class,"consolas.db").build();
        return instance;
    }
    public abstract Consolas getDao();
}

package es.unex.giiis.koreku.roomdb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import es.unex.giiis.koreku.Perfil;

@Database(entities = {Perfil.class},version=1)
public abstract class PerfilDatabase extends RoomDatabase {
    private static PerfilDatabase instance;

    public static PerfilDatabase getInstance(Context context){
        if(instance ==null)
            instance= Room.databaseBuilder(context, PerfilDatabase.class,"perfil.db").build();
        return instance;
    }
    public abstract Perfil getDao();
}

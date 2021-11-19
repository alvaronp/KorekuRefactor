package es.unex.giiis.koreku.roomdb;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import es.unex.giiis.koreku.Consolas;
import es.unex.giiis.koreku.Games;
import es.unex.giiis.koreku.Perfil;
import es.unex.giiis.koreku.Service;

@Database(entities = {Games.class, Consolas.class, Perfil.class, Service.class} , version = 1, exportSchema = false)
public abstract class KorekuDatabase extends RoomDatabase {
    private static KorekuDatabase instance; //SINGLETON

    public static synchronized KorekuDatabase getInstance(Context context){
        if (instance == null)
            instance = Room.databaseBuilder(context, KorekuDatabase.class, "koreku.db")
                    .build();
        return instance;
    }

    public abstract GamesDAO getDao1(); //Room implementará esto por nosotros
    public abstract ConsolasDAO getDao2();
    public abstract PerfilDAO getDao3();
    public abstract ServiceDAO getDao4();

}

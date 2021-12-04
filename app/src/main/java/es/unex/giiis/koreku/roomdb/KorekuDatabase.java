package es.unex.giiis.koreku.roomdb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import es.unex.giiis.koreku.ui.consoles.Consolas;
import es.unex.giiis.koreku.ui.games.Games;
import es.unex.giiis.koreku.ui.profile.Perfil;
import es.unex.giiis.koreku.ui.service.Service;

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

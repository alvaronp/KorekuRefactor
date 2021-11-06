package es.unex.giiis.koreku.roomdb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import es.unex.giiis.koreku.ToDoGame;

@Database(entities = {ToDoGame.class}, version = 1)
public abstract class
ToDoDatabase extends RoomDatabase {
    private static ToDoDatabase instance; //SINGLETON

    public static ToDoDatabase getInstance(Context context){
        if (instance == null)
            instance = Room.databaseBuilder(context, ToDoDatabase.class, "koreku.db").build();
        return instance;
    }

    public abstract ToDoGameDAO getDao(); //Room implementará esto por nosotros
}

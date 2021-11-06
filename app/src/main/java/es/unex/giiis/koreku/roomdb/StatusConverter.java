package es.unex.giiis.koreku.roomdb;

import androidx.room.TypeConverter;

import es.unex.giiis.koreku.ToDoGame;

public class StatusConverter {
    @TypeConverter
    public static String toString(ToDoGame.Status status){
        return status == null ? null : status.name();
    }

    @TypeConverter
    public static ToDoGame.Status toStatus(String status){
        return status ==null ? null : ToDoGame.Status.valueOf(status);
    }
}

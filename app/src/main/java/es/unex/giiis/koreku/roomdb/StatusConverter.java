package es.unex.giiis.koreku.roomdb;

import androidx.room.TypeConverter;

import es.unex.giiis.koreku.Games;

public class StatusConverter {
    @TypeConverter
    public static String toString(Games.Status status){
        return status == null ? null : status.name();
    }

    @TypeConverter
    public static Games.Status toStatus(String status){
        return status ==null ? null : Games.Status.valueOf(status);
    }
}

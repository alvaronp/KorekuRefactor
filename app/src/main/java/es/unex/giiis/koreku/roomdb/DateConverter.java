package es.unex.giiis.koreku.roomdb;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateConverter {
    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null /*Si es nulo*/: date.getTime(); /*Si no lo es*/
    }
    @TypeConverter
    public static Date toDate(Long timestamp){
        return timestamp == null ? null: new Date(timestamp);
    }
}

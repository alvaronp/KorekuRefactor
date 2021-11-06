package es.unex.giiis.koreku.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ToDoManagerDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "koreku.db";


    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_GAME =
            "CREATE TABLE " + DBContract.TodoGame.TABLE_NAME + " (" +
                    DBContract.TodoGame._ID + " INTEGER PRIMARY KEY," +
                    DBContract.TodoGame.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    DBContract.TodoGame.COLUMN_NAME_STATUS + TEXT_TYPE + COMMA_SEP +
                    DBContract.TodoGame.COLUMN_NAME_BUYDATE + TEXT_TYPE + COMMA_SEP +
                    DBContract.TodoGame.COLUMN_NAME_DESC + TEXT_TYPE + COMMA_SEP +
                    DBContract.TodoGame.COLUMN_NAME_IMAGE + TEXT_TYPE +
                     " )";

    private static final String SQL_DELETE_GAME =
            "DROP TABLE IF EXISTS " + DBContract.TodoGame.TABLE_NAME;

    public ToDoManagerDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_GAME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_GAME);
        onCreate(db);
    }
}

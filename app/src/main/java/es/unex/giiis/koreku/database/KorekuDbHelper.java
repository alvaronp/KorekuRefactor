package es.unex.giiis.koreku.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class KorekuDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "koreku.db";


    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_GAME =
            "CREATE TABLE " + DBContract.Games.TABLE_NAME + " (" +
                    DBContract.Games._ID + " INTEGER PRIMARY KEY," +
                    DBContract.Games.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    DBContract.Games.COLUMN_NAME_STATUS + TEXT_TYPE + COMMA_SEP +
                    DBContract.Games.COLUMN_NAME_BUYDATE + TEXT_TYPE + COMMA_SEP +
                    DBContract.Games.COLUMN_NAME_DESC + TEXT_TYPE + COMMA_SEP +
                    DBContract.Games.COLUMN_NAME_IMAGE + TEXT_TYPE +
                     " )";

    private static final String SQL_CREATE_CONSOLA =
            "CREATE TABLE " + DBContract.Consolas.TABLE_NAME + " (" +
                    DBContract.Consolas._ID + " INTEGER PRIMARY KEY," +
                    DBContract.Consolas.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    DBContract.Consolas.COLUMN_NAME_DATE + TEXT_TYPE + COMMA_SEP +
                    DBContract.Consolas.COLUMN_NAME_COMPANY + TEXT_TYPE + COMMA_SEP +
                    DBContract.Consolas.COLUMN_NAME_IMAGE + TEXT_TYPE +
                    " )";
    private static final String SQL_CREATE_PERFIL =
            "CREATE TABLE " + DBContract.Consolas.TABLE_NAME + " (" +
                    DBContract.Perfil._ID + " INTEGER PRIMARY KEY," +
                    DBContract.Perfil.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    DBContract.Perfil.COLUMN_NAME_PHONE+ TEXT_TYPE + COMMA_SEP +
                    DBContract.Perfil.COLUMN_NAME_MAIL+ TEXT_TYPE + COMMA_SEP +
                    DBContract.Perfil.COLUMN_NAME_IMAGE + TEXT_TYPE +
                    DBContract.Perfil.COLUMN_NAME_COMMENTS + TEXT_TYPE +

                    " )";

    private static final String SQL_DELETE_GAME =
            "DROP TABLE IF EXISTS " + DBContract.Games.TABLE_NAME;

    private static final String SQL_DELETE_CONSOLA =
            "DROP TABLE IF EXISTS " + DBContract.Consolas.TABLE_NAME;
    private static final String SQL_DELETE_PERFIL =
            "DROP TABLE IF EXISTS " + DBContract.Perfil.TABLE_NAME;

    public KorekuDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_GAME);
        db.execSQL(SQL_CREATE_CONSOLA);
        db.execSQL(SQL_CREATE_PERFIL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_GAME);
        db.execSQL(SQL_DELETE_CONSOLA);
        db.execSQL(SQL_DELETE_PERFIL);
        onCreate(db);
    }
}

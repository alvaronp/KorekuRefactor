package es.unex.giiis.koreku.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public final class GamesCRUD {

    private KorekuDbHelper mDbHelper;
    private static GamesCRUD mInstance;

    private GamesCRUD(Context context) {
        mDbHelper = new KorekuDbHelper(context);
    }

    public static GamesCRUD getInstance(Context context){
        if (mInstance == null)
            mInstance = new GamesCRUD(context);

        return mInstance;
    }

    public List<es.unex.giiis.koreku.Games> getAll(){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                DBContract.Games._ID,
                DBContract.Games.COLUMN_NAME_TITLE,
                DBContract.Games.COLUMN_NAME_STATUS,
                DBContract.Games.COLUMN_NAME_BUYDATE,
                DBContract.Games.COLUMN_NAME_DESC,
                DBContract.Games.COLUMN_NAME_IMAGE
        };

        String selection = null;
        String[] selectionArgs = null;

        String sortOrder = null;

        Cursor cursor = db.query(
                DBContract.Games.TABLE_NAME,           // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );


        ArrayList<es.unex.giiis.koreku.Games> items = new ArrayList<>();
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                items.add(getToDoGameFromCursor(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return items;
    }

    public long insert(es.unex.giiis.koreku.Games game){
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DBContract.Games.COLUMN_NAME_TITLE, game.getTitle());
        values.put(DBContract.Games.COLUMN_NAME_STATUS, game.getStatus().name());
        values.put(DBContract.Games.COLUMN_NAME_BUYDATE, es.unex.giiis.koreku.Games.FORMAT.format(game.getBuydate()));
        values.put(DBContract.Games.COLUMN_NAME_DESC, game.getDesc());
        values.put(DBContract.Games.COLUMN_NAME_IMAGE, game.getImage());


        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(DBContract.Games.TABLE_NAME, null, values);

        return newRowId;
    }

    public void deleteAll() {
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Define 'where' part of query.
        String selection = null;
        // Specify arguments in placeholder order.
        String[] selectionArgs = null;

        // Issue SQL statement.
        db.delete(DBContract.Games.TABLE_NAME, selection, selectionArgs);
    }

    public int updateStatus(long ID, es.unex.giiis.koreku.Games.Status status) {

        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Log.d("ToDoGameCRUD","GAME ID: "+ID);

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(DBContract.Games.COLUMN_NAME_STATUS, status.name());

        // Which row to update, based on the ID
        String selection = DBContract.Games._ID + " = ?";
        String[] selectionArgs = { Long.toString(ID) };

        int count = db.update(
                DBContract.Games.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        return count;
    }

    public void close(){
        if (mDbHelper!=null) mDbHelper.close();
    }

    public static es.unex.giiis.koreku.Games getToDoGameFromCursor(Cursor cursor) {

        long ID = cursor.getInt(cursor.getColumnIndex(DBContract.Games._ID));
        String title = cursor.getString(cursor.getColumnIndex(DBContract.Games.COLUMN_NAME_TITLE));
        String status = cursor.getString(cursor.getColumnIndex(DBContract.Games.COLUMN_NAME_STATUS));
        String buydate = cursor.getString(cursor.getColumnIndex(DBContract.Games.COLUMN_NAME_BUYDATE));
        String desc = cursor.getString(cursor.getColumnIndex(DBContract.Games.COLUMN_NAME_DESC));
        String image = cursor.getString(cursor.getColumnIndex(DBContract.Games.COLUMN_NAME_IMAGE));

        es.unex.giiis.koreku.Games game = new es.unex.giiis.koreku.Games(ID,title,status,buydate,desc,image);

        Log.d("ToDoGameCRUD",game.toLog());

        return game;
    }
}

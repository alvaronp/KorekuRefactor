package es.unex.giiis.koreku.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import es.unex.giiis.koreku.ToDoGame;

public final class ToDoGameCRUD {

    private ToDoManagerDbHelper mDbHelper;
    private static ToDoGameCRUD mInstance;

    private ToDoGameCRUD(Context context) {
        mDbHelper = new ToDoManagerDbHelper(context);
    }

    public static ToDoGameCRUD getInstance(Context context){
        if (mInstance == null)
            mInstance = new ToDoGameCRUD(context);

        return mInstance;
    }

    public List<ToDoGame> getAll(){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                DBContract.TodoGame._ID,
                DBContract.TodoGame.COLUMN_NAME_TITLE,
                DBContract.TodoGame.COLUMN_NAME_STATUS,
                DBContract.TodoGame.COLUMN_NAME_BUYDATE,
                DBContract.TodoGame.COLUMN_NAME_DESC,
                DBContract.TodoGame.COLUMN_NAME_IMAGE
        };

        String selection = null;
        String[] selectionArgs = null;

        String sortOrder = null;

        Cursor cursor = db.query(
                DBContract.TodoGame.TABLE_NAME,           // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );


        ArrayList<ToDoGame> items = new ArrayList<>();
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                items.add(getToDoGameFromCursor(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return items;
    }

    public long insert(ToDoGame game){
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DBContract.TodoGame.COLUMN_NAME_TITLE, game.getTitle());
        values.put(DBContract.TodoGame.COLUMN_NAME_STATUS, game.getStatus().name());
        values.put(DBContract.TodoGame.COLUMN_NAME_BUYDATE, ToDoGame.FORMAT.format(game.getBuydate()));
        values.put(DBContract.TodoGame.COLUMN_NAME_DESC, game.getDesc());
        values.put(DBContract.TodoGame.COLUMN_NAME_IMAGE, game.getImage());


        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(DBContract.TodoGame.TABLE_NAME, null, values);

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
        db.delete(DBContract.TodoGame.TABLE_NAME, selection, selectionArgs);
    }

    public int updateStatus(long ID, ToDoGame.Status status) {

        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Log.d("ToDoGameCRUD","GAME ID: "+ID);

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(DBContract.TodoGame.COLUMN_NAME_STATUS, status.name());

        // Which row to update, based on the ID
        String selection = DBContract.TodoGame._ID + " = ?";
        String[] selectionArgs = { Long.toString(ID) };

        int count = db.update(
                DBContract.TodoGame.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        return count;
    }

    public void close(){
        if (mDbHelper!=null) mDbHelper.close();
    }

    public static ToDoGame getToDoGameFromCursor(Cursor cursor) {

        long ID = cursor.getInt(cursor.getColumnIndex(DBContract.TodoGame._ID));
        String title = cursor.getString(cursor.getColumnIndex(DBContract.TodoGame.COLUMN_NAME_TITLE));
        String status = cursor.getString(cursor.getColumnIndex(DBContract.TodoGame.COLUMN_NAME_STATUS));
        String buydate = cursor.getString(cursor.getColumnIndex(DBContract.TodoGame.COLUMN_NAME_BUYDATE));
        String desc = cursor.getString(cursor.getColumnIndex(DBContract.TodoGame.COLUMN_NAME_DESC));
        String image = cursor.getString(cursor.getColumnIndex(DBContract.TodoGame.COLUMN_NAME_IMAGE));

        ToDoGame game = new ToDoGame(ID,title,status,buydate,desc,image);

        Log.d("ToDoGameCRUD",game.toLog());

        return game;
    }
}

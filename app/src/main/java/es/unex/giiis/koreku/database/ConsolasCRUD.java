package es.unex.giiis.koreku.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import es.unex.giiis.koreku.Consolas;

public final class ConsolasCRUD {

    private KorekuDbHelper mDbHelper;
    private static ConsolasCRUD mInstance;

    private ConsolasCRUD(Context context) {
        mDbHelper = new KorekuDbHelper(context);
    }

    public static ConsolasCRUD getInstance(Context context){
        if (mInstance == null)
            mInstance = new ConsolasCRUD(context);

        return mInstance;
    }

    public List<Consolas> getAll(){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                DBContract.Consolas._ID,
                DBContract.Consolas.COLUMN_NAME_TITLE,
                DBContract.Consolas.COLUMN_NAME_DATE,
                DBContract.Consolas.COLUMN_NAME_COMPANY,
                DBContract.Consolas.COLUMN_NAME_IMAGE
        };

        String selection = null;
        String[] selectionArgs = null;

        String sortOrder = null;

        Cursor cursor = db.query(
                DBContract.Consolas.TABLE_NAME,           // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );


        ArrayList<Consolas> items = new ArrayList<>();
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                items.add(getConsolasFromCursor(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return items;
    }

    public long insert(Consolas cons){
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DBContract.Consolas._ID, cons.getId());
        values.put(DBContract.Consolas.COLUMN_NAME_TITLE, cons.getTitle());
        values.put(DBContract.Consolas.COLUMN_NAME_COMPANY, cons.getCompany());
        values.put(DBContract.Consolas.COLUMN_NAME_IMAGE, cons.getImage());
        values.put(DBContract.Consolas.COLUMN_NAME_DATE, Consolas.FORMAT.format(cons.getDate()));


        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(DBContract.Consolas.TABLE_NAME, null, values);

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
        db.delete(DBContract.Consolas.TABLE_NAME, selection, selectionArgs);
    }

    public int updateImage(long ID, String image) {

        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Log.d("ConsolasCRUD","Item ID: "+ID);

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(DBContract.Consolas.COLUMN_NAME_IMAGE, image);

        // Which row to update, based on the ID
        String selection = DBContract.Consolas._ID + " = ?";
        String[] selectionArgs = { Long.toString(ID) };

        int count = db.update(
                DBContract.Consolas.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        return count;
    }

    public void close(){
        if (mDbHelper!=null) mDbHelper.close();
    }

    public static Consolas getConsolasFromCursor(Cursor cursor) {

        long ID = cursor.getInt(cursor.getColumnIndex(DBContract.Consolas._ID));
        String title = cursor.getString(cursor.getColumnIndex(DBContract.Consolas.COLUMN_NAME_TITLE));
        String date = cursor.getString(cursor.getColumnIndex(DBContract.Consolas.COLUMN_NAME_DATE));
        String company = cursor.getString(cursor.getColumnIndex(DBContract.Consolas.COLUMN_NAME_COMPANY));
        String image = cursor.getString(cursor.getColumnIndex(DBContract.Consolas.COLUMN_NAME_IMAGE));

        Consolas item = new Consolas(ID,title,date,company,image);

        Log.d("ConsolasCRUD",item.toLog());

        return item;
    }
}
